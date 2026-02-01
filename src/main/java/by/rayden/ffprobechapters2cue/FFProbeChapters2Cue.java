package by.rayden.ffprobechapters2cue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FFProbeChapters2Cue {
    private static final Logger log = LoggerFactory.getLogger(FFProbeChapters2Cue.class);

    static void main(String[] args) {
        try {
            int exitStatusCode = new CliApplication().run(args);
            System.exit(exitStatusCode);
        } catch (Exception e) {
            log.error("Unexpected error: ", e);
            System.exit(-1);
        }
    }

}
