package by.rayden;

import by.rayden.ffprobe.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FFProbeChapters2Cue {
    static void main() {
        final Logger log = LoggerFactory.getLogger(FFProbeChapters2Cue.class);

        Charset jsonCharset = StandardCharsets.UTF_8;

        // Read from PIPE
        InputStreamReader reader = new InputStreamReader(new BufferedInputStream(System.in), jsonCharset);

        JsonMapper mapper = JsonMapper
            .builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .build();

        Metadata ffProbeMetadata = mapper.readValue(reader, Metadata.class);

        log.info("JSON read successfully!");

        System.out.println(ffProbeMetadata.toString());
    }
}
