package kz.sdp.bridge.report;

import kz.sdp.bridge.format.ReportFormatter;
import kz.sdp.bridge.model.StudentGrade;

import java.util.List;

public class GradeReport extends Report {

    private static final double PASSING_SCORE = 50.0;

    private final List<StudentGrade> grades;

    public GradeReport(ReportFormatter formatter, List<StudentGrade> grades) {
        super(formatter);
        this.grades = List.copyOf(grades);
    }

    @Override
    protected String title() {
        return "Student Grade Report";
    }

    @Override
    protected List<String> columns() {
        return List.of("Student", "Course", "Score", "Status");
    }

    @Override
    protected List<List<String>> rows() {
        return grades.stream()
                .map(grade -> List.of(
                        grade.studentName(),
                        grade.course(),
                        formatNumber(grade.score()),
                        isPassed(grade) ? "PASSED" : "FAILED"))
                .toList();
    }

    @Override
    protected String summary() {
        return "Average score: " + formatNumber(averageScore());
    }

    private boolean isPassed(StudentGrade grade) {
        return grade.score() >= PASSING_SCORE;
    }

    private double averageScore() {
        return grades.stream().mapToDouble(StudentGrade::score).average().orElse(0);
    }
}
