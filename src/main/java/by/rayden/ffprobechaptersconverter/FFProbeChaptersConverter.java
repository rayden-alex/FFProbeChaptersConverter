package by.rayden.ffprobechaptersconverter;

import by.rayden.ffprobechaptersconverter.CliApplication.ExitStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FFProbeChaptersConverter {
    private static final Logger log = LoggerFactory.getLogger(FFProbeChaptersConverter.class);

    static void main(String[] args) {
        try {
            ExitStatusCode exitCode = new CliApplication().run(args);
            System.exit(exitCode.getIntCode());
        } catch (Exception e) {
            log.error("Unexpected error: ", e);
            System.exit(ExitStatusCode.UNEXPECTED_ERROR.getIntCode());
        }
    }

}
