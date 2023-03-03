import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    Datasource datasource = new Datasource();

    public Menu() {

    }

    public void startMenu() throws SQLException {
        if(!checkConnection()){
            return;
        }

        System.out.println("Menu:");
        printMenu();
        System.out.println("Enter your choice");
        int choice = scanner.nextInt();

        switch(choice){
            case 1:
                datasource.addIntoDatabase();
                startMenu();
                break;
            case 2:
                datasource.deleteFromDatabase();
                startMenu();
                break;
            case 3:
                List<Student> allStudentsInformation = datasource.getAllStudentsInformation(Const.STUDENTS_TABLE);
                allStudentsInformation.forEach(System.out::println);
                startMenu();
                break;
            case 4:
                datasource.queryMathGradesStudent();
                startMenu();
                break;

            case 5:
                datasource.studentGradesMenu();
                startMenu();
                break;
            case 9:
                break;
        }
        datasource.closeDB();
    }

    private void printMenu(){
        System.out.println("1 - add into database" +
                "\n2 - delete from database" +
                "\n3 - show all students" +
                "\n4 - get math grades for the students" +
                "\n5 - add or update math grades" +
                "\n6 - exit");
    }

    private boolean checkConnection(){
        if(!datasource.openDB()){
            System.out.println("Problem with opening the dataBase");
            return false;
        }
        return true;
    }
}
