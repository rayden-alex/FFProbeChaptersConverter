package by.rayden.ffprobechaptersconverter.service;

import by.rayden.ffprobechaptersconverter.ffprobe.FFProbeChaptersMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public class ConvertService {
    private static final Logger log = LoggerFactory.getLogger(ConvertService.class);

    private final FFProbeTransformer ffProbeTransformer;
    private final Supplier<OutputTransformer> outputTransformerSupplier;

    public ConvertService(FFProbeTransformer ffProbeTransformer, Supplier<OutputTransformer> outputTransformerSupplier) {
        this.ffProbeTransformer = ffProbeTransformer;
        this.outputTransformerSupplier = outputTransformerSupplier;
    }

    public void convert(String inFileName, String outFileName) throws IOException {
        final boolean isReadFromStdIn = "-".equals(inFileName);
        final boolean isWriteToStdOut = "-".equals(outFileName);

        FFProbeChaptersMetadata chaptersMetadata;

        if (isReadFromStdIn) {
            Reader inReader = new BufferedReader(new InputStreamReader(System.in,
                System.getProperty("stdin.encoding")));
            chaptersMetadata = readChaptersFrom(inReader);
        } else {
            // try-with-resources --- need to close file reader
            try (Reader inReader = Files.newBufferedReader(Path.of(inFileName))) {
                chaptersMetadata = readChaptersFrom(inReader);
            }
        }

        String output = this.outputTransformerSupplier.get().transform(chaptersMetadata);
        log.info("FFProbe JSON converted to output string");

        if (isWriteToStdOut) {
            System.out.print(output);
        } else {
            Files.writeString(Path.of(outFileName), output);
        }
    }

    private FFProbeChaptersMetadata readChaptersFrom(Reader reader) {
        try {
            FFProbeChaptersMetadata metadata = this.ffProbeTransformer.getMetadata(reader);
            log.info("FFProbe JSON read successfully!");
            return metadata;
        } catch (Exception e) {
            log.error("FFProbe JSON converting error!");
            throw e;
        }
    }

}
