package by.rayden.ffprobechaptersconverter.service;

import by.rayden.ffprobechaptersconverter.ffprobe.FFProbeChaptersMetadata;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.json.JsonClasspathSource;

import static org.assertj.core.api.Assertions.assertThat;

class CsvTransformerTest {
    CsvTransformer csvTransformer = new CsvTransformer();

    @ParameterizedTest
    @JsonClasspathSource("FFProbeChapters_1.json")
    void testTransform(FFProbeChaptersMetadata metadata) {
        String result = this.csvTransformer.transform(metadata);

        String expected = """
            0.000,280.000,1,"Lynnic, ItsArius & Dinia - Maze Of Memories"
            280.000,455.000,2,"Lynnic, ItsArius & Dinia - Alone"
            455.000,605.000,3,ItsArius & Lynnic - Cornfield Chase
            605.000,823.000,4,Lynnic & ItsArius feat. Lilly - Self Control
            823.000,996.000,5,Lynnic - Rise (feat. Lilly)
            996.000,1202.000,6,"Lynnic, ItsArius & Mocean - Keep Breathing"
            1202.000,1470.560,7,Lynnic & Mocean - Together
            """;

        // Java text blocks use \n as line delimiter, so we need a normalization before comparing
        assertThat(result).isEqualToNormalizingNewlines(expected);
    }

}
