package kz.sdp.bridge.report;

import kz.sdp.bridge.format.ReportFormatter;

import java.util.List;
import java.util.Locale;

public abstract class Report {

    private final ReportFormatter formatter;

    protected Report(ReportFormatter formatter) {
        if (formatter == null) {
            throw new IllegalArgumentException("Formatter must not be null");
        }
        this.formatter = formatter;
    }

    public final String generate() {
        StringBuilder body = new StringBuilder()
                .append(formatter.title(title()))
                .append(formatter.tableHeader(columns()));
        rows().forEach(row -> body.append(formatter.tableRow(row)));
        body.append(formatter.tableEnd())
            .append(formatter.summary(summary()));
        return formatter.document(body.toString());
    }

    protected abstract String title();

    protected abstract List<String> columns();

    protected abstract List<List<String>> rows();

    protected abstract String summary();

    protected static String formatNumber(double value) {
        return String.format(Locale.ROOT, "%.1f", value);
    }
}
