package com.example.razon30.movietest;

/**
 * Created by razon30 on 01-02-16.
 */
public class Movie {

    String coverImage, profileImage,userName,profileLink;

    String birthName ;
    String actorActress  ;
    String height ;
    String birthday  ;
    String birthPlace ;
    String bio ;
    String name  ;
    String starSign;

//for other movie of a person
    public Movie(String movie_name, String movie_id, String movie_release_date, String movie_image_link, String actorActress) {
        this.movie_name = movie_name; //movie name
        this.movie_id = movie_id; //movie id
        this.movie_release_date = movie_release_date; //movie release date
        this.movie_image_link = movie_image_link; //movie image link
        this.actorActress = actorActress; //character of the person
    }

    public Movie(String birthName, String actorActress, String height, String birthday, String birthPlace, String bio, String name, String starSign) {
        this.birthName = birthName;
        this.actorActress = actorActress;
        this.height = height;
        this.birthday = birthday;
        this.birthPlace = birthPlace;
        this.bio = bio;
        this.name = name;
        this.starSign = starSign;
    }

    public String getBirthName() {
        return birthName;
    }

    public void setBirthName(String birthName) {
        this.birthName = birthName;
    }

    public String getActorActress() {
        return actorActress;
    }

    public void setActorActress(String actorActress) {
        this.actorActress = actorActress;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarSign() {
        return starSign;
    }

    public void setStarSign(String starSign) {
        this.starSign = starSign;
    }

    String movie_image_link, movie_name,movie_id, movie_release_date;
    int movie_audience_score;

    public Movie(String movie_image_link, String movie_name, String movie_id) {
        this.movie_image_link = movie_image_link;
        this.movie_name = movie_name;
        this.movie_id = movie_id;
    }

    public Movie(String movie_image_link, String movie_name, String movie_id, String
            movie_release_date, int movie_audience_score) {
        this.movie_image_link = movie_image_link;
        this.movie_name = movie_name;
        this.movie_id = movie_id;
        this.movie_release_date = movie_release_date;
        this.movie_audience_score = movie_audience_score;
    }

    //for watch
    //also for review, in this case, author == movie name and review = moview id
    public Movie(String movie_name, String movie_id) {
        this.movie_name = movie_name; //author name
        this.movie_id = movie_id; //movie review
    }
    //for wish where alarm_time= userName, alarm_date = profilelink, moviename = coverimage and
    // movieID= profileimage;...that is name,id,time,date

    //for search result
    public Movie(String coverImage, String profileImage, String userName, String profileLink) {
        this.coverImage = coverImage; //name parameter for wish //cover image for search result
        this.profileImage = profileImage; // id parameter for wish //name for search result
        this.userName = userName; //time parameter for wish //id for search result
        this.profileLink = profileLink; //date parameter for wish // type(movie or person) for search result
    }


    public String getMovie_release_date() {
        return movie_release_date;
    }

    public void setMovie_release_date(String movie_release_date) {
        this.movie_release_date = movie_release_date;
    }

    public int getMovie_audience_score() {
        return movie_audience_score;
    }

    public void setMovie_audience_score(int movie_audience_score) {
        this.movie_audience_score = movie_audience_score;
    }

    public String getMovie_image_link() {
        return movie_image_link;
    }

    public void setMovie_image_link(String movie_image_link) {
        this.movie_image_link = movie_image_link;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }
}
