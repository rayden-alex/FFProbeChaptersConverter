package by.rayden.ffprobechaptersconverter;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.jupiter.params.support.ParameterDeclarations;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.json.JsonMapper;

import java.io.InputStream;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

public class Jackson3ClasspathProvider implements ArgumentsProvider, AnnotationConsumer<Jackson3JsonClasspathSource> {
    private String[] files;

    // Use the same mapper as in the main application
    private static final JsonMapper MAPPER = CliApplication.getJsonMapper();

    @Override
    public void accept(Jackson3JsonClasspathSource annotation) {
        this.files = annotation.value();
    }

    @Override
    @NonNull
    public Stream<? extends Arguments> provideArguments(@NonNull ParameterDeclarations parameters,
                                                        ExtensionContext context) {
        // Extracting the full parameter type (including Generic, for example List<User>)
        Parameter parameter = context.getRequiredTestMethod().getParameters()[0];
        JavaType targetType = MAPPER.getTypeFactory().constructType(parameter.getParameterizedType());

        return Stream.of(this.files)
                     .map(file -> getArguments(file, targetType, context));
    }

    @NonNull
    private Arguments getArguments(String file, JavaType targetType, ExtensionContext context) {
        try (InputStream is = context.getRequiredTestClass().getClassLoader().getResourceAsStream(file)) {
            if (is == null) {
                throw new IllegalArgumentException("File not found: " + file);
            }

            // Deserialization based on the Generic type
            Object obj = MAPPER.readValue(is, targetType);
            return Arguments.of(obj);
        } catch (Exception e) {
            throw new RuntimeException("Jackson 3 failed to parse " + file, e);
        }
    }

}
