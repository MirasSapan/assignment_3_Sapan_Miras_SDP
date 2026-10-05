package kz.sdp.bridge.format;

import java.util.Collections;
import java.util.List;

public class MarkdownFormatter implements ReportFormatter {

    @Override
    public String title(String text) {
        return "# " + text + "\n\n";
    }

    @Override
    public String tableHeader(List<String> columns) {
        List<String> separators = Collections.nCopies(columns.size(), "---");
        return toMarkdownRow(columns) + toMarkdownRow(separators);
    }

    @Override
    public String tableRow(List<String> cells) {
        return toMarkdownRow(cells);
    }

    @Override
    public String tableEnd() {
        return "\n";
    }

    @Override
    public String summary(String text) {
        return "**" + text + "**\n";
    }

    @Override
    public String document(String body) {
        return body;
    }

    private String toMarkdownRow(List<String> cells) {
        return "| " + String.join(" | ", cells) + " |\n";
    }
}
