package by.rayden.ffprobe;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Disposition(

	@JsonProperty("metadata")
	int metadata,

	@JsonProperty("original")
	int original,

	@JsonProperty("visual_impaired")
	int visualImpaired,

	@JsonProperty("forced")
	int forced,

	@JsonProperty("attached_pic")
	int attachedPic,

	@JsonProperty("non_diegetic")
	int nonDiegetic,

	@JsonProperty("still_image")
	int stillImage,

	@JsonProperty("descriptions")
	int descriptions,

	@JsonProperty("captions")
	int captions,

	@JsonProperty("dub")
	int dub,

	@JsonProperty("karaoke")
	int karaoke,

	@JsonProperty("default")
	int jsonMemberDefault,

	@JsonProperty("multilayer")
	int multilayer,

	@JsonProperty("timed_thumbnails")
	int timedThumbnails,

	@JsonProperty("comment")
	int comment,

	@JsonProperty("hearing_impaired")
	int hearingImpaired,

	@JsonProperty("lyrics")
	int lyrics,

	@JsonProperty("dependent")
	int dependent,

	@JsonProperty("clean_effects")
	int cleanEffects
) {
}
