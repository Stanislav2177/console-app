import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Datasource {
    //Datasource, class which will be used to execute main operations for work with Student information
    public Connection connection;
    public Scanner scanner = new Scanner(System.in);
    private static final Object lock = new Object();

    public boolean openDB(){
        try{
           connection = DriverManager.getConnection(Const.CONNECTION_STRING);
            return true;
        }catch (SQLException e){
            System.out.println("problem in DataSource and more especially in openDB()  = " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public void closeDB(){
        try{
            if(connection != null){
                connection.close();
            }
        }catch (SQLException e){
            System.out.println("Couldn't close the connection = " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addIntoDatabase() throws SQLException {
        add();
        System.out.println("Do you want to add more Students: Yes/No");
        String choice = scanner.next();

        if (connection != null){
            connection.close();
        }
        if(choice.equals("No")){
            return;
        }
    }

    public void deleteFromDatabase(){
        try(Statement statement = connection.createStatement()){
            System.out.println("Enter student faculty number by which will be removed");

            int fnum = scanner.nextInt();

            statement.execute("DELETE FROM " + Const.STUDENTS_TABLE + " WHERE facultyNumber = '" + fnum + "';");

        }catch (SQLException e){
            System.out.println("Problem in Datasource, in method deleteFromDatabase " + e.getMessage());
            e.printStackTrace();
        }

    }

    public int getCount(String table){
        String sql = "SELECT COUNT(*) AS count FROM " + table;
        try(Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql.toString())){


            int count = results.getInt("count");
            System.out.format("Count = %d\n", count);
            return count;
        }catch (SQLException e){
            System.out.println("Problem in getCount() " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public List<Student> getAllStudentsInformation(String table){
        try(Statement statement = connection.createStatement()){
            String sql = "SELECT * FROM " + Const.STUDENTS_TABLE + ";";
            ResultSet resultSet = statement.executeQuery(sql);

            List<Student> students = new ArrayList<>();

            while(resultSet.next()){
                Student student = new Student();

                student.setName(resultSet.getString(1));
                student.setSurname(resultSet.getString(2));
                student.setFaculty(resultSet.getString(3));
                student.setEGN(resultSet.getInt(4));
                student.setFacultyNumber(resultSet.getInt(5));

                students.add(student);
            }



            return students;
        }catch (SQLException e){
            System.out.println("Problem in getAllStudentsInformation " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void studentGradesMenu(){
        //Method for adding or updating student grades
        try(Statement statement = connection.createStatement()){
            queryMathGradesStudent();

            System.out.println("Do you want to update or change grades -> Yes/No");
            String choice = scanner.next();

            if(choice.equals("No")){
                return;
            }
            System.out.println("Does the student already exist, or do we need to add them? -> Yes/No");
            choice = scanner.next();
            if(choice.toLowerCase().equals("yes")){
                add(true);
            }else {
                System.out.println("Type you decision: Add / Update");
                choice = scanner.next();
                if(choice.toLowerCase().equals("add")){
                    System.out.println("Enter Faculty Number :");
                    int facultyNumber = scanner.nextInt();
                    addGradesToStudent("math",facultyNumber);

                }else if(choice.toLowerCase().equals("update")){
                    System.out.println("Enter Faculty Number :");
                    int facultyNumber = scanner.nextInt();
                    updateStudentGrades("math",facultyNumber);
                }
            }




        }catch(SQLException e ){
            System.out.println("Problem in addGradesToStudent " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addGradesToStudent(String tableSubject, int facultyNumber) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableSubject + "Grades (facultyNumber, grade1, grade2, grade3, grade4) VALUES (?, ?, ?, ?, ?)")) {
            while (true) {
                System.out.println("Enter grade1:");
                int grade1 = scanner.nextInt();

                System.out.println("Enter grade2:");
                int grade2 = scanner.nextInt();

                System.out.println("Enter grade3:");
                int grade3 = scanner.nextInt();

                System.out.println("Enter grade4:");
                int grade4 = scanner.nextInt();

                statement.setInt(1, facultyNumber);
                statement.setInt(2, grade1);
                statement.setInt(3, grade2);
                statement.setInt(4, grade3);
                statement.setInt(5, grade4);
                statement.executeUpdate();

                closeDB();
                openDB();
                studentGradesMenu();

            }
        } catch (SQLException e) {
            System.out.println("Error adding grades: " + e.getMessage());
        }
    }




    private void updateStudentGrades(String tableSubject, int facultyNumber) {
        synchronized (lock){
            try(Statement statement = connection.createStatement()){
                String correctTable = subjectConvertToTableConst(tableSubject);
                System.out.println("Which grade you want to be updated ");
                int grade = scanner.nextInt();

                StringBuilder sb = new StringBuilder();
                sb.append("UPDATE " + correctTable + " SET grade");

                System.out.println("enter the new grade = ");
                int gradeToUpdate = scanner.nextInt();
                sb.append(grade + " = " + gradeToUpdate);

                sb.append(" WHERE facultyNumber = " + facultyNumber + ";");

                statement.execute(sb.toString());
            }catch (SQLException e){
                System.out.println("Problem in updateStudentGrades " + e.getMessage());
                e.printStackTrace();
            }
        }
    }



    private int[] getSelectedSubjectGrades(String subject, int facultyNumber){
        try(Statement statement = connection.createStatement()){
            String subjectConst = subjectConvertToTableConst(subject);

            String sql = "SELECT * FROM " + subjectConst + " WHERE facultyNumber = '" + facultyNumber + "';";
            ResultSet resultSet = statement.executeQuery(sql);

            int grade1 = resultSet.getInt(1);
            int grade2 = resultSet.getInt(2);
            int grade3 = resultSet.getInt(3);
            int grade4 = resultSet.getInt(4);

            int[] array = new int[4];
            for (int i = 0; i < 4; i++) {
                //TODO: finish
            }

            System.out.format("\nFaculty Number: %d with grades: %d | %d | %d | %d", facultyNumber, grade1, grade2, grade3, grade4);
            return array;

        }catch (SQLException e) {
            System.out.println("Problem in getSelectedSubjectGrades " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private String subjectConvertToTableConst(String subject){
        if(subject.toLowerCase().equals("math")){
            return Const.MATHGRADES_TABLE;
        } else if (subject.toLowerCase().equals("geography")){
            return null;
            //TODO: will be added soon
        }

        return null;
    }

    public void queryMathGradesStudent(){
        closeDB();
        openDB();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(Const.QUERY_MATHGRADES_FOR_EACH_STUDENT);


            while (resultSet.next()){
                int facultyNumber = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                int grade1 = resultSet.getInt(4);
                int grade2 = resultSet.getInt(5);
                int grade3 = resultSet.getInt(6);
                int grade4 = resultSet.getInt(7);
                int avgGrade = resultSet.getInt(8);
                System.out.format("%d : %s %s : %d | %d | %d | %d| average: %d \n",facultyNumber, name, surname, grade1, grade2, grade3, grade4 , avgGrade);
            }
        }catch (SQLException e) {
            System.out.println("Problem in queryMathGradesStudent " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void add(){
        try(Statement statement = connection.createStatement()) {
            while(true){
                System.out.print("Name = ");
                String name = scanner.next();

                System.out.print("\nSurname = ");
                String surname = scanner.next();

                System.out.print("\nFaculty = ");
                String faculty = scanner.next();

                System.out.print("\nEGN = ");
                int EGN = scanner.nextInt();

                System.out.print("\nFaculty Number = ");
                int facultyNumber = scanner.nextInt();

                connection = DriverManager.getConnection(Const.CONNECTION_STRING);
                statement.execute("INSERT INTO " + Const.STUDENTS_TABLE +
                        "(" + Const.COLUMN_STUDENTS_NAME + ", " + Const.COLUMN_STUDENTS_SURNAME + ", "
                        + Const.COLUMN_STUDENTS_FACULTY + ", " + Const.COLUMN_STUDENTS_EGN + ", "
                        + Const.COLUMN_STUDENTS_FACULTYNUMBER + ") " +
                        "VALUES ('" + name + "', '" + surname + "', '" + faculty + "', '" + EGN + "', '" + facultyNumber + "')");

            }
        } catch (SQLException e) {
            System.out.println("Problem in DATA SOURCE, in method addIntoDatabase " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void add(Boolean yes){
        try(Statement statement = connection.createStatement()) {
            while(true){
                System.out.print("Name = ");
                String name = scanner.next();

                System.out.print("\nSurname = ");
                String surname = scanner.next();

                System.out.print("\nFaculty = ");
                String faculty = scanner.next();

                System.out.print("\nEGN = ");
                int EGN = scanner.nextInt();

                System.out.print("\nFaculty Number = ");
                int facultyNumber = scanner.nextInt();

                connection = DriverManager.getConnection(Const.CONNECTION_STRING);
                statement.execute("INSERT INTO " + Const.STUDENTS_TABLE +
                        "(" + Const.COLUMN_STUDENTS_NAME + ", " + Const.COLUMN_STUDENTS_SURNAME + ", "
                        + Const.COLUMN_STUDENTS_FACULTY + ", " + Const.COLUMN_STUDENTS_EGN + ", "
                        + Const.COLUMN_STUDENTS_FACULTYNUMBER + ") " +
                        "VALUES ('" + name + "', '" + surname + "', '" + faculty + "', '" + EGN + "', '" + facultyNumber + "')");

                addGradesToStudent("math", facultyNumber);
            }

        } catch (SQLException e) {
            System.out.println("Problem in DATA SOURCE, in method addIntoDatabase " + e.getMessage());
            e.printStackTrace();
        }
    }
}

