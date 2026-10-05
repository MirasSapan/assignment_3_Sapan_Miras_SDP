package kz.sdp.bridge.format;

import java.util.List;
import java.util.stream.Collectors;

public class PlainTextFormatter implements ReportFormatter {

    private static final int COLUMN_WIDTH = 18;

    @Override
    public String title(String text) {
        return text.toUpperCase() + "\n" + "=".repeat(text.length()) + "\n";
    }

    @Override
    public String tableHeader(List<String> columns) {
        String header = toFixedWidthLine(columns);
        return header + "-".repeat(header.length() - 1) + "\n";
    }

    @Override
    public String tableRow(List<String> cells) {
        return toFixedWidthLine(cells);
    }

    @Override
    public String tableEnd() {
        return "";
    }

    @Override
    public String summary(String text) {
        return "\n> " + text + "\n";
    }

    @Override
    public String document(String body) {
        return body;
    }

    private String toFixedWidthLine(List<String> cells) {
        return cells.stream()
                .map(cell -> String.format("%-" + COLUMN_WIDTH + "s", cell))
                .collect(Collectors.joining()) + "\n";
    }
}
