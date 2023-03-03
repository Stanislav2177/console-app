public class Const {
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\StanislavYankov\\Desktop\\Java Practicing\\console_app\\Project\\studentInformation.db?busy_timeout=5000" ;

    //Constants used for tables/columns
    public static final String STUDENTS_TABLE = "students";
    public static final String COLUMN_STUDENTS_NAME = "name";
    public static final String COLUMN_STUDENTS_SURNAME = "surname";
    public static final String COLUMN_STUDENTS_FACULTY = "faculty";
    public static final String COLUMN_STUDENTS_EGN = "EGN";
    public static final String COLUMN_STUDENTS_FACULTYNUMBER = "facultyNumber";

    public static final String MATHGRADES_TABLE = "mathGrades";
    public static final String COLUMN_MATHGRADES_FN = "facultyNumber";
    public static final String COLUMN_MATHGRADES_GRADE1 = "grade1";
    public static final String COLUMN_MATHGRADES_GRADE2 = "grade2";
    public static final String COLUMN_MATHGRADES_GRADE3 = "grade3";
    public static final String COLUMN_MATHGRADES_GRADE4 = "grade4";


    //Constants used for queries

    public static final String QUERY_MATHGRADES_FOR_EACH_STUDENT =
            "SELECT " + STUDENTS_TABLE + "." + COLUMN_STUDENTS_FACULTYNUMBER + ", "
                    + STUDENTS_TABLE + "." + COLUMN_STUDENTS_NAME + ", " +
                    STUDENTS_TABLE + "." + COLUMN_STUDENTS_SURNAME + ", " +
                    MATHGRADES_TABLE + "." + COLUMN_MATHGRADES_GRADE1 + ", " +
                    MATHGRADES_TABLE + "." + COLUMN_MATHGRADES_GRADE2 + ", " +
                    MATHGRADES_TABLE + "." + COLUMN_MATHGRADES_GRADE3 + ", " +
                    MATHGRADES_TABLE + "." + COLUMN_MATHGRADES_GRADE4 + ", " +
                    "( " + MATHGRADES_TABLE + "." + COLUMN_MATHGRADES_GRADE1 + " + " +
                    MATHGRADES_TABLE + "." + COLUMN_MATHGRADES_GRADE2 + " + " +
                    MATHGRADES_TABLE + "." + COLUMN_MATHGRADES_GRADE3 + " + " +
                    MATHGRADES_TABLE + "." + COLUMN_MATHGRADES_GRADE4 + " ) / 4 AS avg_grade " +
                    "FROM " + STUDENTS_TABLE + " " +
                    "INNER JOIN " + MATHGRADES_TABLE + " " +
                    "ON " + STUDENTS_TABLE + "." + COLUMN_STUDENTS_FACULTYNUMBER + " = " +
                    MATHGRADES_TABLE + "." + COLUMN_MATHGRADES_FN + ";";


}
