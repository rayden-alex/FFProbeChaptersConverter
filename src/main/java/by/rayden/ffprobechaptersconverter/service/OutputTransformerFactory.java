package by.rayden.ffprobechaptersconverter.service;

import by.rayden.ffprobechaptersconverter.OutputFormat;

import java.util.List;

public class OutputTransformerFactory {
    private final List<OutputTransformer> transformers;

    public OutputTransformerFactory(List<OutputTransformer> transformers) {
        this.transformers = transformers;
    }

    public OutputTransformer getTransformer(OutputFormat outputFormat) {
        return this.transformers
            .stream()
            .filter(transformer -> transformer.getOutputFormat() == outputFormat)
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("Unsupported output format: " + outputFormat));
    }

}
