
import java.sql.*;

public class DataSourceUser extends DataSource{

    private static final String DB_Name= "userDB.db";
    private static final String JD_DB_CONNECTION= "jdbc:sqlite:./"+DB_Name; // connection string to DB;

    // Admin
    private static final String TABLE_USER= "users";
    private static final String COL_ID= "ID";

    public DataSourceUser() {
        super(DataSourceUser.JD_DB_CONNECTION);
    }

    public User queryUser(String username){

        Statement statement= null;
        ResultSet resultSet= null;

        User user= null;
        String select= "ID";

        StringBuilder usernameBuild= new StringBuilder(username);
        usernameBuild.insert(0,"'");
        usernameBuild.insert(usernameBuild.length(),"'");

        try{
            statement= connection.createStatement();
            resultSet= statement.executeQuery("SELECT "+select+" FROM "+
                    TABLE_USER+" WHERE ID="+usernameBuild);

            user= new User();
            user.setUserId(resultSet.getString(COL_ID));

            return user;

        }catch(SQLException e){
            System.out.println("Query Failed "+e.getMessage());
            return null;
        }finally{

            if(resultSet!=null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Could not close resultSet!!! "+e.getMessage());
                }

            if(statement!=null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Couldn't close Statement!");
                    e.printStackTrace();
                }
        }
    }

    public boolean addUser(String id){
        Statement statement= null;

        // build the string, add ' ;
        StringBuilder idBuild= new StringBuilder(id);
        idBuild.insert(0,"'");
        idBuild.insert(idBuild.length(),"'");

        try{
            statement= connection.createStatement();

            statement.execute("INSERT into "+TABLE_USER+"(ID) values("+idBuild+")");

            return true;

        }catch(SQLException e){
            System.out.println("Query Failed "+e.getMessage());

            return false;
        }finally {

            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Couldn't close Statement!");
                    e.printStackTrace();
                }
        }
    }
}
