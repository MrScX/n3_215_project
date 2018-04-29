import java.util.*;

public class Main {

    private static Scanner SC= new Scanner(System.in);
    private static boolean isAdmin= false;

    public static void main(String[] args) {

        startProgram();
    }

    private static void startProgram(){

        System.out.println("Please choose a login option:\n\n A. Admin\n B. User\n C. Exit");
        String userInput= SC.nextLine().toUpperCase();

        if(userInput.equals("A")){
            isAdmin= !isAdmin;
            adminLogin();
        }
        else if(userInput.equals("B"))
            userLogin();
        else if(userInput.equals("C"))
            System.exit(1);
        else{
            System.out.println("Wrong Input! Try again!");
            startProgram();
        }

    }

    private static void adminLogin(){

        System.out.print("Please enter your name: ");
        String userName= SC.nextLine();

        System.out.print("Please enter your password: ");
        String passwd= SC.nextLine();

        DataSourceAdmin dbQuery= new DataSourceAdmin();

        if(!dbQuery.open()){
            System.out.println("***Database not connected***");
        }

        Admin admin= dbQuery.queryAdminLogin(userName);

        System.out.println(admin.getUserName());
        System.out.println(admin.getPasswd());

        if(admin.getUserName().equals(userName) && admin.getPasswd().equals(passwd)){
            dbQuery.close();
            adminOptions();
        }else {
            System.out.println("Wrong password or username!!");
            adminLogin();
        }
    }


    private static void adminOptions(){

        System.out.println("Options are below: ");
        System.out.println(" A. Search A Movie: \n B. Add a Movie\n C. Update Movie Info \n D. Delete Movie\n E. Go back to main menu \n");

        String userInput= SC.nextLine().toUpperCase();

        switch(userInput){

            case "A":
                searchForItem();
                break;
            case "B":
                addMovie();
                break;
            case "C":
                updateMovie();
                break;
            case "D":
                deleteMovie();
                break;
            case "E":
                startProgram();
                break;
            default:
                System.out.println("Wrong input!");
                adminOptions();
        }

    }

    // admin options;

    private static void searchForItem(){

        System.out.print("Please enter a movie name: ");

        String name= SC.nextLine();

        DataSourceSearch dbQuery= new DataSourceSearch();

        if(!dbQuery.open()){
            System.out.println("***Database not connected***");
        }

        Movie movie= dbQuery.querySearch(name);

        if(!dbQuery.close()){
            System.out.println("Error closing DB!");
        }

        System.out.println("Name: "+movie.getName());
        System.out.println("Director: "+movie.getDirector());
        System.out.println("Release Year: "+movie.getReleaseYear());
        System.out.println("User Rating: "+movie.getRating());
        System.out.println("Genre: "+movie.getGenre());

        if(isAdmin)
            adminOptions();
        else
            userOptions();
    }


    private static void userLogin(){

        System.out.println("Please enter your ID: ");
        String id= SC.nextLine();

        DataSourceUser dbQuery= new DataSourceUser();

        if(!dbQuery.open()){
            System.out.println("Error not connected");
        }

        User user= dbQuery.queryUser(id);

        if(user==null){

            System.out.print("Do you wish to add the ID to DB (Y/n): ");
            String choice= SC.nextLine().toLowerCase();
            if(choice.equals("y")){
                dbQuery.addUser(id);
                dbQuery.close();
                userOptions();
            }else{
                dbQuery.close();
                startProgram();
            }
        }else if(user.getUserId().equals(id)){
            dbQuery.close();
            userOptions();
        }
    }

    private static void userOptions(){

        System.out.println("A. Search\nB. Rating\nC. Hall\n D. Main Menu");

        String choice= SC.nextLine().toLowerCase();

        switch(choice){
            case "a":
                searchForItem();
                break;

            case "b":
                updateRating();
                break;
        }

    }

    private static void updateRating(){

        System.out.println("Enter movie name: ");
        String name= SC.nextLine();

        System.out.println("Enter rating (0-5): ");
        double rating= SC.nextDouble();

        DataSourceSearch dbQuery= new DataSourceSearch();

        if(!dbQuery.open()){
            System.out.println("***Database not connected***");
        }

        Movie movie= dbQuery.querySearch(name);

        if(!dbQuery.close()){
            System.out.println("Error closing DB!");
        }

        rating+= movie.getRating();

        DataSourceModify dbRating= new DataSourceModify();

        if(!dbRating.open()){
            System.out.println("***Database not connected***");
        }

        dbRating.updateRating(name,rating);

        if(!dbRating.close()){
            System.out.println("Error closing DB!");
        }


    }

    private static void addMovie(){

        System.out.println("Please enter movie name: ");
        String name= SC.nextLine();

        System.out.println("Please enter director name: ");
        String director= SC.nextLine();

        System.out.println("Please enter release year: ");
        String releaseYear= SC.nextLine();

        System.out.println("Please enter cast names: ");
        String castList= SC.nextLine();

        System.out.println("Please enter genre names: ");
        String genre= SC.nextLine();

        DataSourceAdd dbQuery= new DataSourceAdd();

        if(!dbQuery.open()){
            System.out.println("***Error Opening DB***");
        }

        if(dbQuery.insertIntoDB(name,releaseYear,director,castList,genre)){
            System.out.println("Successfully inserted into DB");
        }else{
            System.out.println("Error inserting into DB!");
        }

        if(!dbQuery.close()){
            System.out.println("Error closing DB!");
        }else{
            System.out.println("DB Closed!");
        }

        adminOptions();
    }

    private static void updateMovie(){

        System.out.print("Please enter what you want to update: ");
        String colName= SC.nextLine();

        updateHelper(colName);

    }

    private static void updateHelper(String colName){

        System.out.print("Please enter old value: ");
        String oldVal= SC.nextLine();
        System.out.print("Please enter new value: ");
        String newVal= SC.nextLine();

        DataSourceModify dbQuery= new DataSourceModify();

        if(!dbQuery.open()){
            System.out.println("Error opening DB");
        }

        if(dbQuery.updateMovie(oldVal,newVal,colName)){
            System.out.println("Updated successfully!");
        }else{
            System.out.println("Error updating value!");
        }

        if(!dbQuery.close()){
            System.out.println("Error closing DB");
        }else{
            System.out.println("DB closed successfully!");

        }
    }

    private static void deleteMovie(){

        System.out.print("Please enter movie name to delete: ");

        String name= SC.nextLine();

        DataSourceModify dbQuery= new DataSourceModify();

        if(!dbQuery.open()){
            System.out.println("Error opening DB");
        }

        if(dbQuery.deleteRecord(name)){
            System.out.println("Deleted successfully!");
        }else{
            System.out.println("Error deleting value!");
        }

        if(!dbQuery.close()){
            System.out.println("Error closing DB");
        }else{
            System.out.println("DB closed successfully!");

        }
    }
}
