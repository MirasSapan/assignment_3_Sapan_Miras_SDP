package kz.sdp.bridge.format;

import java.util.List;
import java.util.stream.Collectors;

public class HtmlFormatter implements ReportFormatter {

    @Override
    public String title(String text) {
        return "<h1>" + escape(text) + "</h1>\n";
    }

    @Override
    public String tableHeader(List<String> columns) {
        return "<table border=\"1\">\n" + toHtmlRow(columns, "th");
    }

    @Override
    public String tableRow(List<String> cells) {
        return toHtmlRow(cells, "td");
    }

    @Override
    public String tableEnd() {
        return "</table>\n";
    }

    @Override
    public String summary(String text) {
        return "<p><b>" + escape(text) + "</b></p>\n";
    }

    @Override
    public String document(String body) {
        return "<html>\n<body>\n" + body + "</body>\n</html>\n";
    }

    private String toHtmlRow(List<String> cells, String cellTag) {
        String row = cells.stream()
                .map(cell -> "<" + cellTag + ">" + escape(cell) + "</" + cellTag + ">")
                .collect(Collectors.joining());
        return "  <tr>" + row + "</tr>\n";
    }

    private String escape(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
