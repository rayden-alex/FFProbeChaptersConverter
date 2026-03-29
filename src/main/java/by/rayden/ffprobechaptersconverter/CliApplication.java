package by.rayden.ffprobechaptersconverter;

import by.rayden.ffprobechaptersconverter.service.ConvertService;
import by.rayden.ffprobechaptersconverter.service.CsvTransformer;
import by.rayden.ffprobechaptersconverter.service.CueTransformer;
import by.rayden.ffprobechaptersconverter.service.FFMpegSliceCmdTransformer;
import by.rayden.ffprobechaptersconverter.service.FFProbeTransformer;
import by.rayden.ffprobechaptersconverter.service.OutputTransformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

public class CliApplication {
    public static final String APP_NAME = "FFProbeChaptersConverter";
    public static final String APP_VERSION = "1.3.4 (2026-03-29 06:35:10)";

    public enum ExitStatusCode {
        OK(0),
        ERROR(1),
        UNEXPECTED_ERROR(-1);

        private final int intCode;

        ExitStatusCode(int intCode) {
            this.intCode = intCode;
        }

        public int getIntCode() {
            return this.intCode;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(CliApplication.class);

    private static final JsonMapper mapper = JsonMapper
        .builder()
        .findAndAddModules()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
        .build();

    private final FFProbeTransformer ffProbeTransformer = new FFProbeTransformer(mapper);
    private final CmdController cmdController = new CmdController();
    private final CueTransformer cueTransformer = new CueTransformer();
    private final CsvTransformer csvTransformer = new CsvTransformer();
    private final FFMpegSliceCmdTransformer ffmpegSliceCmdTransformer = new FFMpegSliceCmdTransformer();

    private final OutputTransformerFactory outputTransformerFactory =
        new OutputTransformerFactory(List.of(this.cueTransformer, this.csvTransformer, this.ffmpegSliceCmdTransformer));

    private final ConvertService convertService =
        new ConvertService(this.ffProbeTransformer, this.outputTransformerFactory);


    public ExitStatusCode run(String[] args) throws Exception {
        this.cmdController.configureOptions();
        CmdController.ParsedCmd parsedCmd = this.cmdController.getParseResult(args);

        return switch (parsedCmd) {
            case HELP -> {
                this.cmdController.printHelp();
                yield ExitStatusCode.OK;
            }

            case VERSION -> {
                this.cmdController.printVersion();
                yield ExitStatusCode.OK;
            }

            case CONVERT -> {
                try {
                    String inFileName = this.cmdController.getInFileName();
                    String outFileName = this.cmdController.getOutFileName();
                    OutputFormat outputFormat = this.cmdController.getOutputFormat();

                    this.convertService.convert(inFileName, outFileName, outputFormat);
                    yield ExitStatusCode.OK;
                } catch (Exception e) {
                    log.error("Converting error!", e);
                    yield ExitStatusCode.ERROR;
                }
            }

            case ERROR -> {
                this.cmdController.printHelp();
                yield ExitStatusCode.ERROR;
            }
        };
    }

    public static JsonMapper getJsonMapper() {
        return mapper;
    }

}
