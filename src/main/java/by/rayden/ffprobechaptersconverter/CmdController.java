package by.rayden.ffprobechaptersconverter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.help.HelpFormatter;
import org.apache.commons.cli.help.TextHelpAppendable;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class CmdController {

    public enum ParsedCmd {
        ERROR(""),
        HELP("help"),
        CONVERT(""),
        VERSION("version");

        private final String optionName;

        ParsedCmd(String optionName) {
            this.optionName = optionName;
        }

        /**
         * @return the option long name that corresponds to this command
         */
        public String getOptionName() {
            return this.optionName;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(CmdController.class);

    /**
     * The number of characters per line in help output: {@value}.
     */
    private static final int MAX_WIDTH = 100;

    private Options options = new Options();

    @Nullable
    private String inFileName;

    @Nullable
    private String outFileName;

    @Nullable
    private OutputFormat outputFormat;


    public void configureOptions() {
        // Create "Options" from scratch in case this method will be called multiple times
        this.options = new Options();

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
                  .argName("CUE, CSV, CMD")
                  .desc("Output format. Default is CUE.")
                  .converter(OutputFormat::valueOf)
                  .get());

        this.options.addOption(
            Option.builder("h")
                  .longOpt(ParsedCmd.HELP.getOptionName())
                  .hasArg(false)
                  .desc("Show usage help.")
                  .get());

        this.options.addOption(
            Option.builder("v")
                  .longOpt(ParsedCmd.VERSION.getOptionName())
                  .hasArg(false)
                  .desc("Show version.")
                  .get());
    }

    public ParsedCmd getParseResult(String[] args) {
        try {
            CommandLine cmd = new DefaultParser().parse(this.options, args);
            return processOptionsValues(cmd);
        } catch (ParseException e) {
            log.error("Error parsing command line arguments: {}", e.getMessage());
            return ParsedCmd.ERROR;
        }
    }

    public void printHelp() throws IOException {

        var helpFormatter = HelpFormatter
            .builder()
            .setShowSince(false)
            .setComparator((_, _) -> 0) // Keeps creation order in the configureOptions() method
            .get();
        helpFormatter.setSyntaxPrefix("Program usage:");
        setHelpWidth(helpFormatter, MAX_WIDTH);

        String header = "Convert FFProbe chaptersList from JSON to CUE, CSV or CMD format.";
        String footer = "Version " + CliApplication.APP_VERSION;
        helpFormatter.printHelp(CliApplication.APP_NAME, header, this.options, footer, true);
    }

    @SuppressWarnings("SameParameterValue")
    private void setHelpWidth(HelpFormatter helpFormatter, int maxWidth) {
        if (helpFormatter.getSerializer() instanceof TextHelpAppendable output) {
            output.setMaxWidth(maxWidth);
        } else {
            log.warn("The maximum output width cannot be set. The default value will be used.");
        }
    }

    public void printVersion() {
        System.out.println(CliApplication.APP_VERSION);
    }

    private ParsedCmd processOptionsValues(CommandLine cmd) throws ParseException {
        if (cmd.hasOption(ParsedCmd.HELP.getOptionName())) {
            return ParsedCmd.HELP;

        } else if (cmd.hasOption(ParsedCmd.VERSION.getOptionName())) {
            return ParsedCmd.VERSION;

        } else {
            this.inFileName = cmd.getOptionValue("in-file", "-");
            this.outFileName = cmd.getOptionValue("out-file", "-");
            this.outputFormat = cmd.getParsedOptionValue("format", OutputFormat.CUE);

            return ParsedCmd.CONVERT;
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
