package by.rayden.ffprobechaptersconverter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.help.HelpFormatter;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class CmdController {
    public enum ParsedResult {ERROR, HELP, CONVERT}

    private static final Logger log = LoggerFactory.getLogger(CmdController.class);

    private final Options options = new Options();

    @Nullable
    private String inFileName;

    @Nullable
    private String outFileName;

    @Nullable
    private OutputFormat outputFormat;


    public ParsedResult getParseResult(String[] args) {
        configureOptions();

        var parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(this.options, args);
            return processOptionsValues(cmd);
        } catch (ParseException e) {
            log.error("Error parsing command line arguments: {}", e.getMessage());
            return ParsedResult.ERROR;
        }
    }

    private void configureOptions() {
        this.options.addOption(
            Option.builder("i")
                  .longOpt("in-file")
                  .hasArg()
                  .argName("FILE")
                  .desc("Input file. Default is '-' for STDIN.")
                  .get());

        this.options.addOption(
            Option.builder("o")
                  .longOpt("out-file")
                  .hasArg()
                  .argName("FILE")
                  .desc("Output file. Default is '-' for STDOUT.")
                  .get());

        this.options.addOption(
            Option.builder("f")
                  .longOpt("format")
                  .hasArg()
                  .argName("CUE, CSV")
                  .desc("Output format. Default is CUE.")
                  .converter(OutputFormat::valueOf)
                  .get());

        this.options.addOption(
            Option.builder("h")
                  .longOpt("help")
                  .hasArg(false)
                  .desc("Show usage help.")
                  .get());
    }

    public void printHelp() throws IOException {
        var  helpFormatter = HelpFormatter.builder().setShowSince(false).get();
        helpFormatter.setSyntaxPrefix("Program usage:");
        String header = "Convert FFProbe chaptersList from JSON to CUE or CSV format.";
        String footer = "Version 1.2.1 (2026-02-21 07:07:17)";

        helpFormatter.printHelp(CliApplication.APP_NAME, header, this.options, footer, true);
    }

    private ParsedResult processOptionsValues(CommandLine cmd) throws ParseException {
        if (cmd.hasOption("help")) {
            return ParsedResult.HELP;

        } else {
            this.inFileName = cmd.getOptionValue("in-file", "-");
            this.outFileName = cmd.getOptionValue("out-file", "-");
            this.outputFormat = cmd.getParsedOptionValue("format", OutputFormat.CUE);

            return ParsedResult.CONVERT;
        }
    }

    public String getInFileName() {
        return Objects.requireNonNull(this.inFileName);
    }

    public String getOutFileName() {
        return Objects.requireNonNull(this.outFileName);
    }

    public OutputFormat getOutputFormat() {
        return Objects.requireNonNull(this.outputFormat);
    }

}
