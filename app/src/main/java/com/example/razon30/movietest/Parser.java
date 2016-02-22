package com.example.razon30.movietest;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by razon30 on 07-02-16.
 */
public class Parser {
    public static ArrayList<Movie> parsePopularMovie(JSONObject response) {

        ArrayList<Movie> listMovies = new ArrayList<Movie>();

        if (response != null && response.length() > 0) {
            try {
                JSONArray arrayMovies = response.getJSONArray("results");
                for (int i = 0; i < arrayMovies.length(); i++) {
                    String id = "-1";
                    String title = "NA";
                    String urlThumbnail = "NA";

                    JSONObject currentmovie = arrayMovies.getJSONObject(i);
                    title = currentmovie.getString("title");
                    id = currentmovie.getString("id");
                    urlThumbnail = currentmovie.getString("poster_path");


                    Movie movie = new Movie(urlThumbnail, title, id);

                    if (id != "-1" && !title.equals("NA")) {
                        listMovies.add(movie);
                    }
                }

            } catch (JSONException e) {

            }

        }
        return listMovies;

    }

    public static ArrayList<Movie> parsePopularPerson(JSONObject response1) {

        ArrayList<Movie> listMovies = new ArrayList<Movie>();

        if (response1 != null && response1.length() > 0) {
            try {
                JSONArray arrayMovies = response1.getJSONArray("results");
                for (int i = 0; i < arrayMovies.length(); i++) {
                    JSONObject current_result = arrayMovies.getJSONObject(i);

                    String name = current_result.getString("name");
                    String profile_path = current_result.getString("profile_path");
                    String id = current_result.getString("id");
                    // String id_imdb = getIMDBID(id);
                    //  String job = current_result.getString("popularity");

                    //  job = job.substring(0, 4);


                    Movie movie = new Movie(profile_path, name, id);
                    listMovies.add(movie);

                }

            } catch (JSONException e) {

            }

        }
        return listMovies;


    }

    private static String getIMDBID(String id) {

        VolleySingleton volleySingleton = VolleySingleton.getsInstance();
        RequestQueue requestQueue = volleySingleton.getmRequestQueue();

        String id_imdb = MovieUtils.loadIMDBID(requestQueue, id);

        return id_imdb;
    }

    public static ArrayList<Movie> parseTopTenIMDB(JSONObject response1) {

        ArrayList<Movie> listMovies = new ArrayList<Movie>();

        if (response1 != null && response1.length() > 0) {
            try {

                JSONObject object = response1.getJSONObject("data");

                JSONArray arrayMovies = object.getJSONArray("movies");
                for (int i = 0; i < arrayMovies.length(); i++) {

                    JSONObject current = arrayMovies.getJSONObject(i);

                    String imdb_id = current.getString("idIMDB");
                    String id_tmdb = getTMDBID(imdb_id);
                    String title = current.getString("title");
                    String urlPoster = current.getString("urlPoster");

                    Movie movie = new Movie(urlPoster, title, id_tmdb);
                    listMovies.add(movie);

                }
            } catch (JSONException e) {

            }

        }
        return listMovies;


    }

    private static String getTMDBID(String imdb_id) {

        VolleySingleton volleySingleton = VolleySingleton.getsInstance();
        RequestQueue requestQueue = volleySingleton.getmRequestQueue();

        String id = MovieUtils.loadTMDBID(requestQueue, imdb_id);

        return id;
    }


    public static ArrayList<Movie> parseJSONResponse(JSONObject response1) {

        // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Movie> listMovies = new ArrayList<Movie>();
        if (response1 != null && response1.length() > 0) {
            try {
                JSONArray arrayMovies = response1.getJSONArray("results");
                for (int i = 0; i < arrayMovies.length(); i++) {
                    String id = "-1";
                    String title = "NA";
                    String releaseDate = "NA";
                    int audienceScore = -1;
                    String synopsis = "NA";
                    String urlThumbnail = "NA";

                    JSONObject currentmovie = arrayMovies.getJSONObject(i);
                    title = currentmovie.getString("title");
                    id = currentmovie.getString("id");
                    releaseDate = currentmovie.getString("release_date");
                    audienceScore = currentmovie.getInt("vote_average");
                    urlThumbnail = currentmovie.getString("backdrop_path");


                    Movie movie = new Movie(urlThumbnail, title, id, releaseDate, audienceScore);

                    if (id != "-1" && !title.equals("NA")) {
                        listMovies.add(movie);
                    }
                }

            } catch (JSONException e) {

            }

        }
        return listMovies;
    }

    public static ArrayList<Movie> parseCastandCrew(JSONObject response1) {

        ArrayList<Movie> cast_and_crew = new ArrayList<Movie>();
        if (response1 != null && response1.length() > 0) {
            try {

                JSONArray cast = response1.getJSONArray("cast");
                for (int i = 0; i < cast.length(); i++) {

                    JSONObject current_cast = cast.getJSONObject(i);
                    String name = current_cast.getString("name");
                    String profile_thumbnail = current_cast.getString("profile_path");
                    String id = current_cast.getString("id");

                    Movie movie = new Movie(profile_thumbnail, name, id);
                    cast_and_crew.add(movie);

                }

                JSONArray crew = response1.getJSONArray("crew");
                for (int i = 0; i < crew.length(); i++) {

                    JSONObject current_cast = crew.getJSONObject(i);
                    String name = current_cast.getString("name");
                    String profile_thumbnail = current_cast.getString("profile_path");
                    String id = current_cast.getString("id");
                    String id_imdb = getIMDBID(id);

                    Movie movie = new Movie(profile_thumbnail, name, id);
                    cast_and_crew.add(movie);

                }


            } catch (JSONException e) {

            }

        }

        return cast_and_crew;

    }

    public static String parseTMDBID(JSONObject response1) {

        String id = null;

        if (response1 != null && response1.length() > 0) {
            try {

                id = response1.getString("id");


            } catch (JSONException e) {

            }

        }

        return id;
    }


    public static String parseIMDBID(JSONObject response1) {
        String imdbId = null;
        try {

            imdbId = response1.getString("imdb_id");

        } catch (Exception e) {

        }
        return imdbId;
    }

    public static Movie parsePersonDetails(JSONObject response1) {

        Movie movie1 = null, movie2;
        try {
            String imdbId = response1.getString("imdb_id");
            // String poterImage = response1.getString("profile_path");
            movie1 = getMoreDetails(imdbId);


        } catch (Exception e) {

        }
        return movie1;
    }

    private static Movie getMoreDetails(String imdbId) {
        VolleySingleton volleySingleton = VolleySingleton.getsInstance();
        RequestQueue requestQueue = volleySingleton.getmRequestQueue();
        Movie movie = MovieUtils.loadMorePersonDetails(requestQueue, imdbId);
        return movie;
    }

    public static Movie parseMorePersonDetails(JSONObject response1) {

        Movie movie = null;

        try {

            String birthName = "NA";
            String actorActress = "NA";
            String height = "NA";
            String birthday = "NA";
            String birthPlace = "NA";
            String bio = "NA";
            String name = "NA";
            String starSign = "NA";


            JSONObject dataObject = response1.getJSONObject("data");
            JSONArray jsonNameArray = dataObject.getJSONArray("names");
            JSONObject dataDetails = jsonNameArray.getJSONObject(0);

            if (dataDetails.has("birthName")) {
                birthName = dataDetails.getString("birthName");
            }else {
                 birthName = "NA";
            }

            if (dataDetails.has("actorActress")) {
                actorActress = dataDetails.getString("actorActress");
            }else {
                actorActress = "NA";
            }

            if (dataDetails.has("height")) {
                height = dataDetails.getString("height");
            }else {
                height = "NA";
            }

            if (dataDetails.has("dateOfBirth")) {
                birthday = dataDetails.getString("dateOfBirth");
            }else {
                birthday = "NA";
            }

            if (dataDetails.has("placeOfBirth")) {
                birthPlace = dataDetails.getString("placeOfBirth");
            }else {
                birthPlace = "NA";
            }

            if (dataDetails.has("bio")) {
                bio = dataDetails.getString("bio");
            }else {
                bio = "NA";
            }

            if (dataDetails.has("name")) {
                name = dataDetails.getString("name");
            }else {
                name = "NA";
            }

            if (dataDetails.has("starSign")) {
                starSign = dataDetails.getString("starSign");
            }else {
                starSign  = "NA";
            }

//            if (birthName == null) birthName = "";
//            if (actorActress == null) birthName = "";
//            if (height == null) birthName = "";
//            if (birthday == null) birthName = "";
//            if (birthPlace == null) birthName = "";
//            if (bio == null) birthName = "";
//            if (name == null) birthName = "";
//            if (starSign == null) birthName = "";


            movie = new Movie(birthName, actorActress, height, birthday, birthPlace, bio, name, starSign);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie;

    }

    public static ArrayList<Movie> parseOtherMovies(JSONObject response1) {

        ArrayList<Movie> person_more_movie_list = new ArrayList<Movie>();

        try {

            JSONArray movies = response1.getJSONArray("cast");


            for (int i = 0; i < movies.length(); i++) {

                JSONObject object = movies.getJSONObject(i);

                String title = object.getString("title");
                String character = object.getString("character");
                String p_character = "as " + character;
                String releaseDate = object.getString("release_date");
                String poster = object.getString("poster_path");
                String id = object.getString("id");

                Movie movie = new Movie(title,id,releaseDate,poster,p_character);
                person_more_movie_list.add(movie);

            }

            JSONArray movies1 = response1.getJSONArray("crew");

            for (int i = 0; i < movies1.length(); i++) {

                JSONObject object = movies1.getJSONObject(i);

                String title = object.getString("title");
                String character = object.getString("character");
                String p_character = "as " + character;
                String releaseDate = object.getString("release_date");
                String poster = object.getString("poster_path");
                String id = object.getString("id");

                Movie movie = new Movie(title,id,releaseDate,poster,p_character);
                person_more_movie_list.add(movie);

            }


        } catch (Exception e) {
//                            Toast.makeText(Person_Details.this, e.toString(), Toast.LENGTH_LONG)
//                                    .show();


        }

        return person_more_movie_list;

    }

    public static ArrayList<Movie> parseSearchReult(JSONObject response1) {
        ArrayList<Movie> listMovies = new ArrayList<Movie>();
        if (response1 != null && response1.length() > 0) {
            try {

                JSONArray resultArray = response1.getJSONArray("results");

                for (int i=0;i<resultArray.length();i++){

                    JSONObject current_result = resultArray.getJSONObject(i);

                    String type = current_result.getString("media_type");

                    if ("person".equalsIgnoreCase(type)){

                        String id = current_result.getString("id");
                        String name = current_result.getString("name");
                        String profile = current_result.getString("profile_path");

                        Movie movie = new Movie(profile,name,id,type);
                        listMovies.add(movie);

                    }else if ("movie".equalsIgnoreCase(type)){

                        String id = current_result.getString("id");
                        String name = current_result.getString("title");
                        String profile = current_result.getString("poster_path");

                        Movie movie = new Movie(profile,name,id,type);
                        listMovies.add(movie);

                    }



                }



            } catch (JSONException e) {

            }

        }
        return listMovies;
    }
}

