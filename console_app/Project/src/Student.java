public class Student {
    private String name;
    private String surname;
    private String faculty;
    private int EGN;
    private int facultyNumber;
    private StudentGrades studentGrades;

    public Student(String name, String surname, String faculty,int EGN, int facultyNumber) {
        this.name = name;
        this.surname = surname;
        this.faculty = faculty;
        this.EGN = EGN;
        this.facultyNumber = facultyNumber;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getEGN() {
        return EGN;
    }

    public void setEGN(int EGN) {
        this.EGN = EGN;
    }

    public int getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(int facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", EGN=" + EGN +
                ", facultyNumber=" + facultyNumber +
                ", faculty='" + faculty + '\'';
    }
}
