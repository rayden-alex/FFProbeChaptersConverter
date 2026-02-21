package by.rayden.ffprobechaptersconverter.service;

import by.rayden.ffprobechaptersconverter.OutputFormat;
import by.rayden.ffprobechaptersconverter.ffprobe.ChaptersItem;
import by.rayden.ffprobechaptersconverter.ffprobe.FFProbeChaptersMetadata;
import by.rayden.ffprobechaptersconverter.ffprobe.Tags;
import org.digitalmediaserver.cuelib.CueSheet;
import org.digitalmediaserver.cuelib.CueSheetSerializer;
import org.digitalmediaserver.cuelib.FileData;
import org.digitalmediaserver.cuelib.Index;
import org.digitalmediaserver.cuelib.Position;
import org.digitalmediaserver.cuelib.TrackData;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CueTransformer implements OutputTransformer {
    private static final Logger log = LoggerFactory.getLogger(CueTransformer.class);
    private static final long NANOS_PER_SECOND = 1000_000_000L;
    private static final long NANOS_PER_MILLI = 1000_000L;

    /**
     * CD Audio (Red Book) has 75 frames per second
     */
    private static final int FRAMES_PER_SECOND = 75;
    private static final long NANOS_PER_FRAME = NANOS_PER_SECOND / FRAMES_PER_SECOND;

    private static final int START_OF_TRACK_IDX = 1;


    @Override
    public OutputFormat getOutputFormat() {
        return OutputFormat.CUE;
    }

    @Override
    public String transform(final FFProbeChaptersMetadata metadata) {
        if (!isFormatValid(metadata)) {
            log.error("Invalid FFProbe metadata format");
            throw new IllegalArgumentException("Invalid FFProbe metadata format");
        }

        Optional<Tags> audioTags = getFirstAudioTags(metadata.streamsList());

        CueSheet cueSheet = new CueSheet();
        audioTags.map(Tags::artist).ifPresent(cueSheet::setPerformer);
        audioTags.map(Tags::genre).ifPresent(cueSheet::setGenre);
        audioTags.map(Tags::title).ifPresent(cueSheet::setTitle);
        audioTags.map(Tags::date).map(this::getYearFromDateString).ifPresent(cueSheet::setYear);
//        audioTags.map(Tags::url).ifPresent(cueSheet::setComment);

        String filename = getAudioFilename(metadata.format().filename());
        FileData fileData = new FileData(cueSheet, filename, "WAVE");
        cueSheet.getFileData().add(fileData);

        metadata.chaptersList()
                .stream()
                .sorted(Comparator.comparingInt(ChaptersItem::id))
                .forEach(chapter -> processChapter(chapter, fileData));

        String cueStr = new CueSheetSerializer().serializeCueSheet(cueSheet);
        return cueStr.replace("\n", System.lineSeparator()); // CueSheetSerializer always use '\n' as lineSeparator
    }

    /**
     * @param dateStr A date string like "20261130"
     * @return Year from the date
     */
    private int getYearFromDateString(final String dateStr) {
        return Integer.parseInt(dateStr.substring(0, 4));
    }

    private String getAudioFilename(@Nullable final String filename) {
        return Optional.ofNullable(filename)
                       .map(Path::of)
                       .map(Path::getFileName)
                       .map(Path::toString)
                       .orElseThrow(() -> new NoSuchElementException("No 'format.filename' field present in metadata"));
    }

    private void processChapter(final ChaptersItem chaptersItem, final FileData fileData) {
        int trackNum = chaptersItem.id() + 1; // Cue track number starts from 1.
        TrackData track = new TrackData(fileData, trackNum, "AUDIO");

        String chapterTitle = Optional.ofNullable(chaptersItem.tags()).map(Tags::title).orElse("");
        // TODO The standard allows no more than 80 characters.
        //  But for my purposes, maybe it's acceptable to not have this limit?
        track.setTitle(chapterTitle); // .substring(0, 80)
        fileData.getTrackData().add(track);

        Position position = CueTransformer.getPositionFromMillis(chaptersItem.start());

        Index index = new Index(START_OF_TRACK_IDX, position); // The StartIndex of the track is always "01".
        track.getIndices().add(index);

    }

    @VisibleForTesting
    static Position getPositionFromMillis(final int startTimeMillis) {
        LocalTime startTime = LocalTime.ofNanoOfDay(startTimeMillis * NANOS_PER_MILLI);

        return new Position(startTime.get(ChronoField.MINUTE_OF_DAY),
            startTime.getSecond(),
            (int) (startTime.getNano() / NANOS_PER_FRAME));
    }

}
