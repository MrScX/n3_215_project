
import java.sql.*;
import java.util.ArrayList;

public class DataSourceSearch extends DataSource{

    private static final String DB_Name= "movieDB.db"; // file name
    private static final String JD_DB_CONNECTION= "jdbc:sqlite:./"+DB_Name; // Connection string for DB;

    // TABLE COL NAMES;
    private static final String TABLE_MOVIE= "movie";
    private static final String TABLE_TV= "series";
    private static final String COL_NAME= "name";
    private static final String COL_YEAR= "year";
    private static final String COL_CAST= "cast";
    private static final String COL_DIRECTOR= "director";
    private static final String COL_GENRE= "genre";
    private static final String COL_RATING= "rating";

    // constructor;
    public DataSourceSearch() {
        super(DataSourceSearch.JD_DB_CONNECTION);
    }

    // method for searching the movie by name
    public Movie querySearch(String moviename){

        Statement statement= null;
        ResultSet resultSet= null;
        Movie movie= null;

        // build the string, add ' ;
        StringBuilder movieBuild= new StringBuilder(moviename);
        movieBuild.insert(0,"'");
        movieBuild.insert(movieBuild.length(),"'");

        try{
            statement= connection.createStatement();
            resultSet= statement.executeQuery("SELECT * FROM "+
                    TABLE_MOVIE+" WHERE name="+movieBuild);


            movie= new Movie();

            // set movie object with data retrieved from DB;
            movie.setName(resultSet.getString(COL_NAME));
            movie.setCastList(resultSet.getString(COL_CAST));
            movie.setDirector(resultSet.getString(COL_DIRECTOR));
            movie.setGenre(resultSet.getString(COL_GENRE));
            movie.setReleaseYear(resultSet.getString(COL_YEAR));
            movie.setRating(resultSet.getDouble(COL_RATING));

            return movie;

            // close everything;
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

    public ArrayList<Movie> showAll(){

        Statement statement= null;
        ResultSet resultSet= null;
        Movie movie= null;
        ArrayList<Movie> movieList= new ArrayList<>();

        try{
            statement= connection.createStatement();
            resultSet= statement.executeQuery("SELECT * FROM "+
                    TABLE_MOVIE);



            while(resultSet.next()){

                movie= new Movie();

                movie.setName(resultSet.getString(COL_NAME));
                movie.setCastList(resultSet.getString(COL_CAST));
                movie.setDirector(resultSet.getString(COL_DIRECTOR));
                movie.setGenre(resultSet.getString(COL_GENRE));
                movie.setReleaseYear(resultSet.getString(COL_YEAR));
                movie.setRating(resultSet.getDouble(COL_RATING));

                movieList.add(movie);
            }

            return movieList;
            // close everything;
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
