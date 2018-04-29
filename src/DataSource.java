import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    // Connection Database
    protected Connection connection;
    private String JD_DB_CONNECTION;

    public DataSource(String JD_DB_CONNECTION){
        this.JD_DB_CONNECTION= JD_DB_CONNECTION;
    }

    // Open DB
    public boolean open(){
        try{
            connection= DriverManager.getConnection(JD_DB_CONNECTION);
            return true;
        }catch (SQLException e){
            System.out.println("Database Connection Error! "+e.getMessage());
            return false;
        }
    }

    // Close DB
    public boolean close(){
        try{
            if(connection!=null)
                connection.close();
            return true;
        }catch (SQLException e){
            System.out.println("Unable to close Connection! "+e.getMessage());
            return false;
        }
    }
}
