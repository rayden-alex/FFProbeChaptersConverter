package by.rayden.ffprobechaptersconverter.service;

import by.rayden.ffprobechaptersconverter.ffprobe.FFProbeChaptersMetadata;
import tools.jackson.databind.json.JsonMapper;

import java.io.Reader;

public class FFProbeTransformer {
    final JsonMapper mapper;

    public FFProbeTransformer(JsonMapper mapper) {
        this.mapper = mapper;
    }

    public FFProbeChaptersMetadata getMetadata(final Reader reader) {
        return this.mapper.readValue(reader, FFProbeChaptersMetadata.class);
    }

}
