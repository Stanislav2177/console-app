import java.util.ArrayList;
import java.util.List;

public class StudentGrades {
    private String subjectName;
    private List<Integer> grades;

    public StudentGrades(String subjectName, List<Integer> grades) {
        this.subjectName = subjectName;
        this.grades = new ArrayList<>();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }
}
