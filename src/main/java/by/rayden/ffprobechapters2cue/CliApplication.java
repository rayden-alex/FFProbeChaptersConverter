package by.rayden.ffprobechapters2cue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

public class CliApplication {
    public static final String APP_NAME = "FFProbeChapters2Cue";
    private static final Logger log = LoggerFactory.getLogger(CliApplication.class);

    private final JsonMapper mapper = JsonMapper
        .builder()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
        .build();

    private final CmdController cmdController = new CmdController();
    private final FFProbeTransformer ffProbeTransformer = new FFProbeTransformer(this.mapper);
    private final CueTransformer cueTransformer = new CueTransformer();
    private final ConvertService convertService = new ConvertService(this.ffProbeTransformer, this.cueTransformer);

    public int run(String[] args) throws Exception {
        CmdController.ParsedResult parsedResult = this.cmdController.getParseResult(args);

        switch (parsedResult) {
            case HELP -> {
                this.cmdController.printHelp();
                return 0;
            }

            case CONVERT -> {
                try {
                    String inFileName = this.cmdController.getInFileName();
                    String outFileName = this.cmdController.getOutFileName();

                    this.convertService.convert(inFileName, outFileName);
                    return 0;
                } catch (Exception e) {
                    log.error("Converting error!", e);
                    return 1;
                }
            }

            case ERROR -> {
                this.cmdController.printHelp();
                return 1;
            }

            default -> throw new IllegalStateException("Unexpected ParsedResult value: " + parsedResult);
        }
    }

}
