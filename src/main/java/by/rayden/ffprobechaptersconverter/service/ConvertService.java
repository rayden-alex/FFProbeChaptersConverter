package by.rayden.ffprobechaptersconverter.service;

import by.rayden.ffprobechaptersconverter.OutputFormat;
import by.rayden.ffprobechaptersconverter.ffprobe.FFProbeChaptersMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConvertService {
    private static final Logger log = LoggerFactory.getLogger(ConvertService.class);

    private final FFProbeTransformer ffProbeTransformer;
    private final OutputTransformerFactory outputTransformerFactory;

    public ConvertService(FFProbeTransformer ffProbeTransformer, OutputTransformerFactory outputTransformerFactory) {
        this.ffProbeTransformer = ffProbeTransformer;
        this.outputTransformerFactory = outputTransformerFactory;
    }

    public void convert(String inFileName, String outFileName, OutputFormat outputFormat) throws IOException {
        FFProbeChaptersMetadata chaptersMetadata = readChapters(inFileName);
        String outputData = transformToOutputFormat(chaptersMetadata, outputFormat);
        writeOutputData(outputData, outFileName);
    }

    private FFProbeChaptersMetadata readChapters(String inFileName) throws IOException {
        final boolean isReadFromStdIn = "-".equals(inFileName);
        if (isReadFromStdIn) {
            log.debug("Reading chapters from System.in");
            // should not to close System.in
            var inReader = new BufferedReader(new InputStreamReader(System.in, System.getProperty("stdin.encoding")));
            return readChapters(inReader);

        } else {
            log.debug("Reading chapters from file: {}", inFileName);
            // try-with-resources --- need to close the file reader
            try (var inReader = Files.newBufferedReader(Path.of(inFileName))) {
                return readChapters(inReader);
            }
        }
    }

    private FFProbeChaptersMetadata readChapters(Reader reader) {
        try {
            FFProbeChaptersMetadata metadata = this.ffProbeTransformer.getMetadata(reader);
            log.info("FFProbe JSON read successfully!");
            return metadata;
        } catch (Exception e) {
            log.error("FFProbe JSON converting error!");
            throw e;
        }
    }

    private String transformToOutputFormat(FFProbeChaptersMetadata chaptersMetadata, OutputFormat outputFormat) {
        OutputTransformer transformer = this.outputTransformerFactory.getTransformer(outputFormat);
        String outputData = transformer.transform(chaptersMetadata);
        log.info("FFProbe JSON converted to output format");
        return outputData;
    }

    private void writeOutputData(String outputData, String outFileName) throws IOException {
        final boolean isWriteToStdOut = "-".equals(outFileName);
        if (isWriteToStdOut) {
            System.out.print(outputData);
        } else {
            Files.writeString(Path.of(outFileName), outputData);
        }
    }

}
