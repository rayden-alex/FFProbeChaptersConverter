package by.rayden.ffprobechaptersconverter.ffprobe;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Format(

    @JsonProperty("duration")
    String duration,

    @JsonProperty("start_time")
    String startTime,

    @JsonProperty("bit_rate")
    String bitRate,

    @JsonProperty("filename")
    String filename,

    @JsonProperty("size")
    String size,

    @JsonProperty("probe_score")
    int probeScore,

    @JsonProperty("nb_programs")
    int nbPrograms,

    @JsonProperty("format_long_name")
    String formatLongName,

    @JsonProperty("nb_stream_groups")
    int nbStreamGroups,

    @JsonProperty("nb_streams")
    int nbStreams,

    @JsonProperty("format_name")
    String formatName
) {
}
