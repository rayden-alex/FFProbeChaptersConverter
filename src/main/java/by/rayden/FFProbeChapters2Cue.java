package by.rayden;

import by.rayden.ffprobe.Metadata;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FFProbeChapters2Cue {
    static void main() {
        Charset jsonCharset = StandardCharsets.UTF_8;

        // Read from PIPE
        InputStreamReader reader = new InputStreamReader(new BufferedInputStream(System.in), jsonCharset);

        JsonMapper mapper = JsonMapper
            .builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .build();

        Metadata ffProbeMetadata = mapper.readValue(reader, Metadata.class);

        System.out.println("JSON read successfully!");
        System.out.println();

        System.out.println(ffProbeMetadata.toString());
    }
}
