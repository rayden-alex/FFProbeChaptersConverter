package by.rayden.ffprobechaptersconverter.ffprobe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record FFProbeChaptersMetadata(
    @JsonProperty("chapters")
    List<ChaptersItem> chaptersList,

    @JsonProperty("streams")
    List<StreamsItem> streamsList,

    @JsonProperty("format")
    Format format) {

    @JsonCreator
    public FFProbeChaptersMetadata(@JsonProperty("chapters") List<ChaptersItem> chaptersList,
                                   @JsonProperty("streams") List<StreamsItem> streamsList,
                                   @JsonProperty("format") Format format) {
        this.chaptersList = Objects.requireNonNullElseGet(chaptersList, Collections::emptyList);
        this.streamsList = Objects.requireNonNullElseGet(streamsList, Collections::emptyList);
        this.format = format;
    }

}
