package by.rayden.ffprobe;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Tags(

	@JsonProperty("title")
	String title,

	@JsonProperty("comment")
	String comment,

	@JsonProperty("date")
	String date,

	@JsonProperty("artist")
	String artist,

	@JsonProperty("genre")
	String genre,

	@JsonProperty("language")
	String language,

	@JsonProperty("purl")
	String purl,

	@JsonProperty("encoder")
	String encoder,

	@JsonProperty("Url")
	String url
) {
}
