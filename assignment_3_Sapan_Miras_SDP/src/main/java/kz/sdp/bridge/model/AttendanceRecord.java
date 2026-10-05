package kz.sdp.bridge.model;

public record AttendanceRecord(String studentName, int attendedLessons, int totalLessons) {

    public AttendanceRecord {
        if (totalLessons <= 0 || attendedLessons < 0 || attendedLessons > totalLessons) {
            throw new IllegalArgumentException("Invalid attendance: " + attendedLessons + "/" + totalLessons);
        }
    }

    public double attendancePercent() {
        return attendedLessons * 100.0 / totalLessons;
    }
}
