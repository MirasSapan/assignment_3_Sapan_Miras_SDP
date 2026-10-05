package kz.sdp.bridge.model;

public record StudentGrade(String studentName, String course, double score) {

    public StudentGrade {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100: " + score);
        }
    }
}
