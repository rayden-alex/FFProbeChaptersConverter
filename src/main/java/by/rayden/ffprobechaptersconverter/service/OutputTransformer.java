package by.rayden.ffprobechaptersconverter.service;

import by.rayden.ffprobechaptersconverter.ffprobe.FFProbeChaptersMetadata;
import by.rayden.ffprobechaptersconverter.ffprobe.StreamsItem;
import by.rayden.ffprobechaptersconverter.ffprobe.Tags;

import java.util.List;
import java.util.Optional;

public interface OutputTransformer {

    String transform(final FFProbeChaptersMetadata metadata);

    default Optional<Tags> getFirstAudioTags(List<StreamsItem> streamsList) {
        return streamsList
            .stream()
            .filter(item -> "audio".equalsIgnoreCase(item.codecType()))
            .findFirst()
            .map(StreamsItem::tags);
    }

    default boolean isFormatValid(FFProbeChaptersMetadata metadata) {
        return !metadata.streamsList().isEmpty() && !metadata.chaptersList().isEmpty();
    }
}
