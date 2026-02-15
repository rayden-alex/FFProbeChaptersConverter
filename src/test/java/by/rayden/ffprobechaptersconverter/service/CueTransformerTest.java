package by.rayden.ffprobechaptersconverter.service;

import org.digitalmediaserver.cuelib.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CueTransformerTest {

    @Test
    void getPositionFromMillisTest() {
        // 280.9 sec = 4 min + 40 sec + (0.9*75=67) frames
        Position position = CueTransformer.getPositionFromMillis(280900);

        assertThat(position.getMinutes()).isEqualTo(4);
        assertThat(position.getSeconds()).isEqualTo(40);
        assertThat(position.getFrames()).isEqualTo(67);
    }
}
