
import java.sql.*;

public class DataSourceAdd extends DataSource{

    private static final String DB_Name= "movieDB.db"; // file name
    private static final String JD_DB_CONNECTION= "jdbc:sqlite:./"+DB_Name; // Connection string for DB;

    // TABLE COL NAMES;
    private static final String TABLE_MOVIE= "movie";
    private static final String COL_NAME= "name";
    private static final String COL_YEAR= "year";
    private static final String COL_CAST= "cast";
    private static final String COL_DIRECTOR= "director";
    private static final String COL_GENRE= "genre";
    private static final String COL_RATING= "rating";

    // constructor;
    public DataSourceAdd() {
        super(DataSourceAdd.JD_DB_CONNECTION);
    }


    // method for searching the movie by name
    public boolean insertIntoDB(String name, String releaseYear, String director, String castList, String genre){

        Statement statement= null;

        // build the string, add ' ;
        StringBuilder nameBuild= new StringBuilder(name);
        nameBuild.insert(0,"'");
        nameBuild.insert(nameBuild.length(),"'");

        StringBuilder releaseYearBuild= new StringBuilder(releaseYear);
        releaseYearBuild.insert(0,"'");
        releaseYearBuild.insert(releaseYearBuild.length(),"'");

        StringBuilder directorBuild= new StringBuilder(director);
        directorBuild.insert(0,"'");
        directorBuild.insert(directorBuild.length(),"'");

        StringBuilder castListBuild= new StringBuilder(castList);
        castListBuild.insert(0,"'");
        castListBuild.insert(castListBuild.length(),"'");

        StringBuilder genreBuild= new StringBuilder(genre);
        genreBuild.insert(0,"'");
        genreBuild.insert(genreBuild.length(),"'");

        try{
            statement= connection.createStatement();

            statement.execute("INSERT into "+TABLE_MOVIE+"(name,year,cast,director,genre) values("+nameBuild+","+
                    releaseYearBuild+","+directorBuild+","+castListBuild+","+genreBuild+")");

            return true;

        }catch(SQLException e){
            System.out.println("Query Failed "+e.getMessage());

            return false;
        }finally{

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
