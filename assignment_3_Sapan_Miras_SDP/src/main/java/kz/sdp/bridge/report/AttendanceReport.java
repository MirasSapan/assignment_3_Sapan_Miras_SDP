package kz.sdp.bridge.report;

import kz.sdp.bridge.format.ReportFormatter;
import kz.sdp.bridge.model.AttendanceRecord;

import java.util.List;

public class AttendanceReport extends Report {

    private static final double MIN_ATTENDANCE_PERCENT = 70.0;

    private final String courseName;
    private final List<AttendanceRecord> records;

    public AttendanceReport(ReportFormatter formatter, String courseName, List<AttendanceRecord> records) {
        super(formatter);
        this.courseName = courseName;
        this.records = List.copyOf(records);
    }

    @Override
    protected String title() {
        return "Attendance Report: " + courseName;
    }

    @Override
    protected List<String> columns() {
        return List.of("Student", "Attended", "Percent", "Warning");
    }

    @Override
    protected List<List<String>> rows() {
        return records.stream()
                .map(record -> List.of(
                        record.studentName(),
                        record.attendedLessons() + "/" + record.totalLessons(),
                        formatNumber(record.attendancePercent()) + "%",
                        hasLowAttendance(record) ? "YES" : "no"))
                .toList();
    }

    @Override
    protected String summary() {
        return "Students with low attendance: " + countLowAttendance() + " of " + records.size();
    }

    private boolean hasLowAttendance(AttendanceRecord record) {
        return record.attendancePercent() < MIN_ATTENDANCE_PERCENT;
    }

    private long countLowAttendance() {
        return records.stream().filter(this::hasLowAttendance).count();
    }
}
