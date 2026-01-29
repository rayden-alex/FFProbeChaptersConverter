package by.rayden.ffprobe;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChaptersItem(

	@JsonProperty("start_time")
	String startTime,

	@JsonProperty("time_base")
	String timeBase,

	@JsonProperty("start")
	int start,

	@JsonProperty("end_time")
	String endTime,

	@JsonProperty("end")
	int end,

	@JsonProperty("id")
	int id,

	@JsonProperty("tags")
	Tags tags
) {
}
