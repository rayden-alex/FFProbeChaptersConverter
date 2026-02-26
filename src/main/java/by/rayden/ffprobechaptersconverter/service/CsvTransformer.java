package by.rayden.ffprobechaptersconverter.service;

import by.rayden.ffprobechaptersconverter.OutputFormat;
import by.rayden.ffprobechaptersconverter.ffprobe.ChaptersItem;
import by.rayden.ffprobechaptersconverter.ffprobe.FFProbeChaptersMetadata;
import by.rayden.ffprobechaptersconverter.ffprobe.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

public class CsvTransformer implements OutputTransformer {
    private static final Logger log = LoggerFactory.getLogger(CsvTransformer.class);

    @Override
    public OutputFormat getOutputFormat() {
        return OutputFormat.CSV;
    }

    @Override
    public String transform(FFProbeChaptersMetadata metadata) {
        if (!isFormatValid(metadata)) {
            log.error("Invalid FFProbe metadata format");
            throw new IllegalArgumentException("Invalid FFProbe metadata format");
        }

        StringBuilder stringBuilder = new StringBuilder();

        metadata.chaptersList()
                .stream()
                .sorted(Comparator.comparingInt(ChaptersItem::id))
                .forEach(chapter -> processChapter(chapter, stringBuilder));

        return stringBuilder.toString();
    }

    private void processChapter(ChaptersItem chapter, StringBuilder stringBuilder) {
        stringBuilder.append(formatTime(chapter.start())).append(',');
        stringBuilder.append(formatTime(chapter.end())).append(',');
        stringBuilder.append(chapter.id() + 1).append(','); // Track number starts from 1.

        String chapterTitle = Optional.ofNullable(chapter.tags())
                                      .map(Tags::title)
                                      .map(this::escapeCsv)
                                      .orElse("");

        stringBuilder.append(chapterTitle).append(System.lineSeparator());
    }

    private String escapeCsv(String value) {
        // Doubling all existing quotas
        String escaped = value.replace("\"", "\"\"");

        // Wrap string in quotas if it contains a commas or quotas
        if (escaped.contains(",") || escaped.contains("\"")) {
            return "\"" + escaped + "\"";
        }

        return escaped;
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
