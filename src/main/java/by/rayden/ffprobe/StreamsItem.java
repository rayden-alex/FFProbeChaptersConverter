package by.rayden.ffprobe;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StreamsItem(

	@JsonProperty("color_range")
	String colorRange,

	@JsonProperty("pix_fmt")
	String pixFmt,

	@JsonProperty("r_frame_rate")
	String rFrameRate,

	@JsonProperty("start_pts")
	int startPts,

	@JsonProperty("duration_ts")
	int durationTs,

	@JsonProperty("duration")
	String duration,

	@JsonProperty("codec_tag_string")
	String codecTagString,

	@JsonProperty("avg_frame_rate")
	String avgFrameRate,

	@JsonProperty("color_space")
	String colorSpace,

	@JsonProperty("codec_long_name")
	String codecLongName,

	@JsonProperty("height")
	int height,

	@JsonProperty("chroma_location")
	String chromaLocation,

	@JsonProperty("time_base")
	String timeBase,

	@JsonProperty("coded_height")
	int codedHeight,

	@JsonProperty("level")
	int level,

	@JsonProperty("profile")
	String profile,

	@JsonProperty("bits_per_raw_sample")
	String bitsPerRawSample,

	@JsonProperty("index")
	int index,

	@JsonProperty("codec_name")
	String codecName,

	@JsonProperty("tags")
	Tags tags,

	@JsonProperty("start_time")
	String startTime,

	@JsonProperty("disposition")
	Disposition disposition,

	@JsonProperty("codec_tag")
	String codecTag,

	@JsonProperty("has_b_frames")
	int hasBFrames,

	@JsonProperty("refs")
	int refs,

	@JsonProperty("width")
	int width,

	@JsonProperty("coded_width")
	int codedWidth,

	@JsonProperty("codec_type")
	String codecType,

	@JsonProperty("channel_layout")
	String channelLayout,

	@JsonProperty("extradata_size")
	int extradataSize,

	@JsonProperty("initial_padding")
	int initialPadding,

	@JsonProperty("sample_rate")
	String sampleRate,

	@JsonProperty("channels")
	int channels,

	@JsonProperty("sample_fmt")
	String sampleFmt,

	@JsonProperty("bits_per_sample")
	int bitsPerSample,

	@JsonProperty("mime_codec_string")
	String mimeCodecString
) {
}
