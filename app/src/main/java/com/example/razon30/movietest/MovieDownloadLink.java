package com.example.razon30.movietest;

/**
 * Created by razon30 on 09-07-16.
 */
public class MovieDownloadLink {

    String movie, link,SubLink;

    public MovieDownloadLink(String movie, String link, String subLink) {
        this.movie = movie;
        this.link = link;
        SubLink = subLink;
    }

    public MovieDownloadLink(String movie, String link) {
        this.movie = movie;
        this.link = link;
    }

    public String getSubLink() {
        return SubLink;
    }

    public void setSubLink(String subLink) {
        SubLink = subLink;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
