package by.rayden.ffprobechaptersconverter.service;

import by.rayden.ffprobechaptersconverter.CliApplication;
import by.rayden.ffprobechaptersconverter.ffprobe.FFProbeChaptersMetadata;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class FFMpegSliceCmdTransformerTest {
    static JsonMapper jsonMapper;
    static FFProbeTransformer ffProbeTransformer;
    static OutputTransformer transformer;

    @BeforeAll
    static void beforeAll() {
        // Use the same mapper as in the main application.
        jsonMapper = CliApplication.getJsonMapper();

        ffProbeTransformer = new FFProbeTransformer(jsonMapper);
        transformer = new FFMpegSliceCmdTransformer();
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void testTransform() throws IOException {
        FFProbeChaptersMetadata metadata = getChaptersMetadata("FFProbeChapters_1.json");
        String result = transformer.transform(metadata);

        String expected = """
            chcp 65001 > nul
            set "_ffmpeg=c:\\Rip\\FFMPEG\\bin\\ffmpeg.exe -hide_banner -loglevel error -nostats -nostdin "
            
            %_ffmpeg% -ss 0.000 -to 280.000 -i "g:\\Melodic House Living Room Session with Live Vocals - Lynnic & ItsArius [CAGzxEDwAts].opus" -c:a copy -map_chapters -1 -map_metadata 0:s:a "g:\\01. Lynnic, ItsArius & Dinia - Maze Of Memories.opus"
            %_ffmpeg% -ss 280.000 -to 455.000 -i "g:\\Melodic House Living Room Session with Live Vocals - Lynnic & ItsArius [CAGzxEDwAts].opus" -c:a copy -map_chapters -1 -map_metadata 0:s:a "g:\\02. Lynnic, ItsArius & Dinia - Alone.opus"
            %_ffmpeg% -ss 455.000 -to 605.000 -i "g:\\Melodic House Living Room Session with Live Vocals - Lynnic & ItsArius [CAGzxEDwAts].opus" -c:a copy -map_chapters -1 -map_metadata 0:s:a "g:\\03. ItsArius & Lynnic - Cornfield Chase.opus"
            %_ffmpeg% -ss 605.000 -to 823.000 -i "g:\\Melodic House Living Room Session with Live Vocals - Lynnic & ItsArius [CAGzxEDwAts].opus" -c:a copy -map_chapters -1 -map_metadata 0:s:a "g:\\04. Lynnic & ItsArius feat. Lilly - Self Control.opus"
            %_ffmpeg% -ss 823.000 -to 996.000 -i "g:\\Melodic House Living Room Session with Live Vocals - Lynnic & ItsArius [CAGzxEDwAts].opus" -c:a copy -map_chapters -1 -map_metadata 0:s:a "g:\\05. Lynnic - Rise (feat. Lilly).opus"
            %_ffmpeg% -ss 996.000 -to 1202.000 -i "g:\\Melodic House Living Room Session with Live Vocals - Lynnic & ItsArius [CAGzxEDwAts].opus" -c:a copy -map_chapters -1 -map_metadata 0:s:a "g:\\06. Lynnic, ItsArius & Mocean - Keep Breathing.opus"
            %_ffmpeg% -ss 1202.000 -to 1470.560 -i "g:\\Melodic House Living Room Session with Live Vocals - Lynnic & ItsArius [CAGzxEDwAts].opus" -c:a copy -map_chapters -1 -map_metadata 0:s:a "g:\\07. Lynnic & Mocean - Together.opus"
            
            pause
            """;

        // Java text blocks use \n as line delimiter, so we need a normalization before comparing
        assertThat(result).isEqualToNormalizingNewlines(expected);
    }

    @SuppressWarnings("SameParameterValue")
    private FFProbeChaptersMetadata getChaptersMetadata(String fileName) throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            return ffProbeTransformer.getMetadata(reader);
        }
    }

}
