import java.util.ArrayList;

public class Movie {

    private String name;
    private String releaseYear;
    private String director;
    private String castList;
    private String genre;
    private double rating;

    public Movie(){};

    public Movie(String name, String releaseYear, String director, String castList, String genre) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.director = director;
        this.castList = castList;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCastList() {
        return castList;
    }

    public void setCastList(String castList) {
        this.castList = castList;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
