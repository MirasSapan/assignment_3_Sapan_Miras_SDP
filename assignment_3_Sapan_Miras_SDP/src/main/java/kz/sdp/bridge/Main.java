package kz.sdp.bridge;

import kz.sdp.bridge.format.HtmlFormatter;
import kz.sdp.bridge.format.MarkdownFormatter;
import kz.sdp.bridge.format.PlainTextFormatter;
import kz.sdp.bridge.format.ReportFormatter;
import kz.sdp.bridge.model.AttendanceRecord;
import kz.sdp.bridge.model.StudentGrade;
import kz.sdp.bridge.report.AttendanceReport;
import kz.sdp.bridge.report.GradeReport;
import kz.sdp.bridge.report.Report;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<StudentGrade> grades = List.of(
                new StudentGrade("Aruzhan", "Java OOP", 91.5),
                new StudentGrade("Dias", "Java OOP", 47.0),
                new StudentGrade("Madina", "Java OOP", 78.0));

        List<AttendanceRecord> attendance = List.of(
                new AttendanceRecord("Aruzhan", 15, 15),
                new AttendanceRecord("Dias", 9, 15),
                new AttendanceRecord("Madina", 13, 15));

        List<ReportFormatter> formatters = List.of(
                new PlainTextFormatter(),
                new MarkdownFormatter(),
                new HtmlFormatter());

        for (ReportFormatter formatter : formatters) {
            printReport(formatter, new GradeReport(formatter, grades));
            printReport(formatter, new AttendanceReport(formatter, "Java OOP", attendance));
        }
    }

    private static void printReport(ReportFormatter formatter, Report report) {
        System.out.println("----- " + report.getClass().getSimpleName()
                + " + " + formatter.getClass().getSimpleName() + " -----");
        System.out.println(report.generate());
    }
}
