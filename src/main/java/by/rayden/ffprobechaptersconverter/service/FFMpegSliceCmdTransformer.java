package by.rayden.ffprobechaptersconverter.service;

import by.rayden.ffprobechaptersconverter.OutputFormat;
import by.rayden.ffprobechaptersconverter.ffprobe.ChaptersItem;
import by.rayden.ffprobechaptersconverter.ffprobe.FFProbeChaptersMetadata;
import by.rayden.ffprobechaptersconverter.ffprobe.Format;
import by.rayden.ffprobechaptersconverter.ffprobe.Tags;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

public class FFMpegSliceCmdTransformer implements OutputTransformer {
    private static final Logger log = LoggerFactory.getLogger(FFMpegSliceCmdTransformer.class);

    @Override
    public OutputFormat getOutputFormat() {
        return OutputFormat.CMD;
    }

    @Override
    public String transform(FFProbeChaptersMetadata metadata) {
        if (!isFormatValid(metadata)) {
            log.error("Invalid FFProbe metadata format");
            throw new IllegalArgumentException("Invalid FFProbe metadata format");
        }

        String inFilename = getAudioFullFilename(metadata);

        StringBuilder stringBuilder = new StringBuilder();
        preProcess(stringBuilder);

        metadata.chaptersList()
                .stream()
                .sorted(Comparator.comparingInt(ChaptersItem::id))
                .forEach(chapter -> processChapter(inFilename, chapter, stringBuilder));

        postProcess(stringBuilder);
        return stringBuilder.toString();
    }

    private void preProcess(StringBuilder stringBuilder) {
        stringBuilder
            .append("chcp 65001 > nul")
            .append(System.lineSeparator())
            .append("set \"_ffmpeg=c:\\Rip\\FFMPEG\\bin\\ffmpeg.exe -hide_banner -loglevel error -nostats -nostdin \"")
            .append(System.lineSeparator())
            .append(System.lineSeparator());
    }

    private void processChapter(String inFilename, ChaptersItem chapter, StringBuilder stringBuilder) {
        String chapterOutFileName = buildChapterOutFileName(chapter, inFilename);
        stringBuilder
            .append("%_ffmpeg%")
            .append(" -ss ").append(formatTime(chapter.start()))
            .append(" -to ").append(formatTime(chapter.end()))
            .append(" -i ").append("\"").append(inFilename).append("\"")
            .append(" -c:a copy -map_chapters -1 -map_metadata 0:s:a")
            .append(" \"").append(chapterOutFileName).append("\"")
            .append(System.lineSeparator());
    }

    private void postProcess(StringBuilder stringBuilder) {
        stringBuilder
            .append(System.lineSeparator())
            .append("pause")
            .append(System.lineSeparator());
    }

    private String buildChapterOutFileName(ChaptersItem chapter, String inFilename) {
        int trackNum = chapter.id() + 1; // Cue track number starts from 1.
        String chapterTitle = Optional.ofNullable(chapter.tags()).map(Tags::title).orElse("");
        String inFileExtension = FilenameUtils.getExtension(inFilename);
        return FilenameUtils.concat(FilenameUtils.getFullPathNoEndSeparator(inFilename),
            "%02d. %s.%s".formatted(trackNum, chapterTitle, inFileExtension));
    }

    private String getAudioFullFilename(FFProbeChaptersMetadata metadata) {
        return Optional.of(metadata)
                       .map(FFProbeChaptersMetadata::format)
                       .map(Format::filename)
                       .orElseThrow(() -> new NoSuchElementException("No 'format.filename' field present in metadata"));
    }

    /**
     * @param millis Time in millis. For example 123456
     * @return String formated as "s.sss" (seconds and millis part of second). For example "123.456".
     */
    private String formatTime(int millis) {
        final double MILLIS_PER_SECOND = 1000.0;
        // Set Locale.ROOT to always use dot as decimal delimiter
        return String.format(Locale.ROOT, "%.3f", millis / MILLIS_PER_SECOND);
    }

}
