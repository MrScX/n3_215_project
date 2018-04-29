
import java.sql.*;
import java.util.ArrayList;

public class DataSourceTV extends DataSource{

    private static final String DB_Name= "movieDB.db"; // file name
    private static final String JD_DB_CONNECTION= "jdbc:sqlite:./"+DB_Name; // Connection string for DB;

    private static int COUNT_RATING= 1;

    // TABLE COL NAMES;
    private static final String TABLE_TV= "series";
    private static final String COL_NAME= "name";
    private static final String COL_YEAR= "year";
    private static final String COL_CAST= "cast";
    private static final String COL_DIRECTOR= "director";
    private static final String COL_GENRE= "genre";
    private static final String COL_RATING= "rating";

    // constructor;
    public DataSourceTV() {
        super(DataSourceTV.JD_DB_CONNECTION);
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

            statement.execute("INSERT into "+TABLE_TV+"(name,year,cast,director,genre) values("+nameBuild+","+
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

    public TvSeries querySearch(String moviename){

        Statement statement= null;
        ResultSet resultSet= null;
        TvSeries tv= null;

        // build the string, add ' ;
        StringBuilder movieBuild= new StringBuilder(moviename);
        movieBuild.insert(0,"'");
        movieBuild.insert(movieBuild.length(),"'");

        try{
            statement= connection.createStatement();
            resultSet= statement.executeQuery("SELECT * FROM "+
                    TABLE_TV+" WHERE name="+movieBuild);


            tv= new TvSeries();

            // set movie object with data retrieved from DB;
            tv.setName(resultSet.getString(COL_NAME));
            tv.setCastList(resultSet.getString(COL_CAST));
            tv.setDirector(resultSet.getString(COL_DIRECTOR));
            tv.setGenre(resultSet.getString(COL_GENRE));
            tv.setReleaseYear(resultSet.getString(COL_YEAR));
            tv.setRating(resultSet.getDouble(COL_RATING));

            return tv;

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

    public ArrayList<TvSeries> showAll(){

        Statement statement= null;
        ResultSet resultSet= null;
        TvSeries tv= null;
        ArrayList<TvSeries> tvList= new ArrayList<>();

        try{
            statement= connection.createStatement();
            resultSet= statement.executeQuery("SELECT * FROM "+
                    TABLE_TV);



            while(resultSet.next()){

                tv= new TvSeries();

                tv.setName(resultSet.getString(COL_NAME));
                tv.setCastList(resultSet.getString(COL_CAST));
                tv.setDirector(resultSet.getString(COL_DIRECTOR));
                tv.setGenre(resultSet.getString(COL_GENRE));
                tv.setReleaseYear(resultSet.getString(COL_YEAR));
                tv.setRating(resultSet.getDouble(COL_RATING));

                tvList.add(tv);
            }

            return tvList;
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

    public boolean updateTV(String oldVal,String newVal,String colName){

        Statement statement= null;

        // build the string, add;
        StringBuilder newValeBuild= new StringBuilder(newVal);
        newValeBuild.insert(0,"'");
        newValeBuild.insert(newValeBuild.length(),"'");

        StringBuilder oldBuild= new StringBuilder(oldVal);
        oldBuild.insert(0,"'");
        oldBuild.insert(oldBuild.length(),"'");

        try{
            statement= connection.createStatement();

            statement.execute("UPDATE "+TABLE_TV+" SET "+colName+"="+newValeBuild+" WHERE "+colName+"="+oldBuild);

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

    public boolean updateRating(String name,double rating){

        Statement statement= null;

        // build the string, add;
        StringBuilder nameBuild= new StringBuilder(name);
        nameBuild.insert(0,"'");
        nameBuild.insert(nameBuild.length(),"'");

        double avgRating= rating/COUNT_RATING;

        StringBuilder ratingBuild= new StringBuilder(Double.toString(avgRating));
        ratingBuild.insert(0,"'");
        ratingBuild.insert(ratingBuild.length(),"'");

        try{
            statement= connection.createStatement();

            statement.execute("UPDATE "+TABLE_TV+" SET "+COL_RATING+"="+ratingBuild+" WHERE "+COL_NAME+"="+nameBuild);

            COUNT_RATING++;

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

    public boolean deleteRecord(String val){

        Statement statement= null;

        // build the string, add;
        StringBuilder newValeBuild= new StringBuilder(val);
        newValeBuild.insert(0,"'");
        newValeBuild.insert(newValeBuild.length(),"'");

        try{
            statement= connection.createStatement();

            statement.execute("DELETE FROM "+TABLE_TV+" WHERE "+COL_NAME+"="+newValeBuild);

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
