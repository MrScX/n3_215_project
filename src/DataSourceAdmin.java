
import java.sql.*;

public class DataSourceAdmin extends DataSource{

    private static final String DB_Name= "admin.db";
    private static final String JD_DB_CONNECTION= "jdbc:sqlite:./"+DB_Name; // connection string to DB;

    // Admin
    private static final String TABLE_ADMIN= "adminLogin";
    private static final String COL_ADMIN_NAME= "username";
    private static final String COL_ADMIN_PASSWD= "password";

    public DataSourceAdmin() {
        super(DataSourceAdmin.JD_DB_CONNECTION);
    }

    public Admin queryAdminLogin(String username){

        Statement statement= null;
        ResultSet resultSet= null;
        Admin admin= null;
        String select= "username,password";
        StringBuilder usernameBuild= new StringBuilder(username);
        usernameBuild.insert(0,"'");
        usernameBuild.insert(usernameBuild.length(),"'");

        try{
            statement= connection.createStatement();
            resultSet= statement.executeQuery("SELECT "+select+" FROM "+
                    TABLE_ADMIN+" WHERE username="+usernameBuild);


            admin= new Admin();
            admin.setUserName(resultSet.getString(COL_ADMIN_NAME));
            admin.setPasswd(resultSet.getString(COL_ADMIN_PASSWD));

            return admin;

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

}
