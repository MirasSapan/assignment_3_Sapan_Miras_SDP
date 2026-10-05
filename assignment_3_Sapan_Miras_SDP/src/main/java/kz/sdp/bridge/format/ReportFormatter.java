package kz.sdp.bridge.format;

import java.util.List;

public interface ReportFormatter {

    String title(String text);

    String tableHeader(List<String> columns);

    String tableRow(List<String> cells);

    String tableEnd();

    String summary(String text);

    String document(String body);
}
