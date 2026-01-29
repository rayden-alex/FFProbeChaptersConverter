package by.rayden.ffprobe;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Metadata(

	@JsonProperty("chapters")
	List<ChaptersItem> chapters,

	@JsonProperty("streams")
	List<StreamsItem> streams,

	@JsonProperty("format")
	Format format
) {
}
