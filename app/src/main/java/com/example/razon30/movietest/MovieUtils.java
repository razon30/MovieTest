package com.example.razon30.movietest;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by razon30 on 07-02-16.
 */
public class MovieUtils {

    static String popularMovieURL1 = "http://api.themoviedb.org/3/movie/popular?api_key=f246d5e5105e9934d3cd4c4c181d618d&page=1";
    static String popularMovieURL2 = "http://api.themoviedb" +
            ".org/3/movie/popular?api_key=f246d5e5105e9934d3cd4c4c181d618d&page=2";
    static String popularMovieURL3 = "http://api.themoviedb" +
            ".org/3/movie/popular?api_key=f246d5e5105e9934d3cd4c4c181d618d&page=3";


    public static ArrayList<Movie> loadPopularMovies(RequestQueue requestQueue) {
        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, popularMovieURL1);
        JSONObject response2 = Requestor.sendRequestBoxOfficeMovies(requestQueue,
                popularMovieURL2);
        JSONObject response3 = Requestor.sendRequestBoxOfficeMovies(requestQueue,
                popularMovieURL3);

        ArrayList<Movie> listMovies1 = Parser.parsePopularMovie(response1);
        ArrayList<Movie> listMovies2 = Parser.parsePopularMovie(response2);
        ArrayList<Movie> listMovies3 = Parser.parsePopularMovie(response3);

        listMovies1.addAll(listMovies2);
        listMovies1.addAll(listMovies3);


        return listMovies1;
    }

    static String popular_person = "http://api.themoviedb.org/3/person/popular?api_key=f246d5e5105e9934d3cd4c4c181d618d";

    public static ArrayList<Movie> loadPopularPerson(RequestQueue requestQueue) {

        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, popular_person);
        ArrayList<Movie> listMovies1 = Parser.parsePopularPerson(response1);

        return listMovies1;
    }

    static String imdb_top_250 = "http://www.myapifilms.com/imdb/top?token=764e7262-e813-4448-b1ba-52cfb7aed311&format=json&data=0";

    public static ArrayList<Movie> loadTopTenIMDB(RequestQueue requestQueue) {

        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, imdb_top_250);
        ArrayList<Movie> listMovies1 = Parser.parseTopTenIMDB(response1);

        return listMovies1;

    }

    static String imdb_bottom_250 = "http://www.myapifilms.com/imdb/bottom?token=764e7262-e813-4448-b1ba-52cfb7aed311&format=json&data=0";

    public static ArrayList<Movie> loadBottomTenIMDB(RequestQueue requestQueue) {
        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, imdb_bottom_250);
        ArrayList<Movie> listMovies1 = Parser.parseTopTenIMDB(response1);

        return listMovies1;
    }

    static String URL1 = "http://api.themoviedb" +
            ".org/3/movie/now_playing?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response=images&include_image_language=en,null";

    static String URL2 = "http://api.themoviedb" +
            ".org/3/movie/now_playing?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null&page=2";

    static String URL3 = "http://api.themoviedb" +
            ".org/3/movie/now_playing?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null&page=3";

    static String URL4 = "http://api.themoviedb" +
            ".org/3/movie/now_playing?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null&page=4";

    static String URL5 = "http://api.themoviedb" +
            ".org/3/movie/now_playing?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null&page=5";


    public static ArrayList<Movie> loadBoxOfficeMovies(RequestQueue requestQueue) {

        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, URL1);
        JSONObject response2 = Requestor.sendRequestBoxOfficeMovies(requestQueue, URL2);
        JSONObject response3 = Requestor.sendRequestBoxOfficeMovies(requestQueue, URL3);
        JSONObject response4 = Requestor.sendRequestBoxOfficeMovies(requestQueue, URL4);
        JSONObject response5 = Requestor.sendRequestBoxOfficeMovies(requestQueue, URL5);

        ArrayList<Movie> listMovies1 = Parser.parseJSONResponse(response1);
        ArrayList<Movie> listMovies2 = Parser.parseJSONResponse(response2);
        ArrayList<Movie> listMovies3 = Parser.parseJSONResponse(response3);
        ArrayList<Movie> listMovies4 = Parser.parseJSONResponse(response4);
        ArrayList<Movie> listMovies5 = Parser.parseJSONResponse(response5);

        //listMovies1.remove(listMovies2);
        listMovies1.addAll(listMovies2);
        listMovies1.addAll(listMovies3);
        listMovies1.addAll(listMovies4);
        listMovies1.addAll(listMovies5);

        return listMovies1;

    }

    static String URLUpcoming1 = "http://api.themoviedb" +
            ".org/3/movie/upcoming?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null";

    static String URLUpcoming2 = "http://api.themoviedb" +
            ".org/3/movie/upcoming?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null&page=2";

    static String URLUpcoming3 = "http://api.themoviedb" +
            ".org/3/movie/upcoming?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null&page=3";

    static String URLUpcoming4 = "http://api.themoviedb" +
            ".org/3/movie/upcoming?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null&page=4";

    static String URLUpcoming5 = "http://api.themoviedb" +
            ".org/3/movie/upcoming?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null&page=5";


    public static ArrayList<Movie> loadUpcomingMovies(RequestQueue requestQueue) {

        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, URLUpcoming1);
        JSONObject response2 = Requestor.sendRequestBoxOfficeMovies(requestQueue, URLUpcoming2);
        JSONObject response3 = Requestor.sendRequestBoxOfficeMovies(requestQueue, URLUpcoming3);
        JSONObject response4 = Requestor.sendRequestBoxOfficeMovies(requestQueue, URLUpcoming4);
        JSONObject response5 = Requestor.sendRequestBoxOfficeMovies(requestQueue, URLUpcoming5);

        ArrayList<Movie> listMovies1 = Parser.parseJSONResponse(response1);
        ArrayList<Movie> listMovies2 = Parser.parseJSONResponse(response2);
        ArrayList<Movie> listMovies3 = Parser.parseJSONResponse(response3);
        ArrayList<Movie> listMovies4 = Parser.parseJSONResponse(response4);
        ArrayList<Movie> listMovies5 = Parser.parseJSONResponse(response5);

        //listMovies1.remove(listMovies2);
        listMovies1.addAll(listMovies2);
        listMovies1.addAll(listMovies3);
        listMovies1.addAll(listMovies4);
        listMovies1.addAll(listMovies5);

        return listMovies1;

    }

    public static ArrayList<Movie> loadSimilarMovie(RequestQueue requestQueue, String urlSimilarMovies) {

        String urlSimilar1 = urlSimilarMovies+"&page=1";
        String urlSimilar2 = urlSimilarMovies+"&page=2";

        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, urlSimilar1);
        JSONObject response2 = Requestor.sendRequestBoxOfficeMovies(requestQueue, urlSimilar2);

        ArrayList<Movie> listMovies1 = Parser.parsePopularMovie(response1);
        ArrayList<Movie> listMovies2 = Parser.parsePopularMovie(response2);

        listMovies1.addAll(listMovies2);

        return listMovies1;

    }

    public static ArrayList<Movie> loadCastandCrew(RequestQueue requestQueue, String urlCastandCrew) {
        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, urlCastandCrew);
        ArrayList<Movie> listMovies1 = Parser.parseCastandCrew(response1);

        return listMovies1;
    }

    public static String loadTMDBID(RequestQueue requestQueue, String imdb_id) {

        String url = "http://api.themoviedb" +
                ".org/3/movie/"+imdb_id+"?external_source=imdb_id&api_key=f246d5e5105e9934d3cd4c4c181d618d";

        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, url);
        String id = Parser.parseTMDBID(response1);

        return id;


    }

    static  String urlPreId = "http://api.themoviedb.org/3/genre/";

    static   String urlPostId1 = "/movies?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null";
    static   String urlPostId2 = "/movies?api_key=f246d5e5105e9934d3cd4c4c181d618d&append_to_response" +
            "=images&include_image_language=en,null&page=2";


    public static ArrayList<Movie> loadMoviesByGenre(RequestQueue requestQueue, String id) {
        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue,urlPreId+id+urlPostId1 );
        JSONObject response2 = Requestor.sendRequestBoxOfficeMovies(requestQueue,
                urlPreId+id+urlPostId2 );

        ArrayList<Movie> listMovies1 = Parser.parseJSONResponse(response1);
        ArrayList<Movie> listMovies2 = Parser.parseJSONResponse(response2);

        listMovies1.addAll(listMovies2);

        return listMovies1;
    }

    public static String loadIMDBID(RequestQueue requestQueue, String id) {
        String url = "http://api.themoviedb.org/3/person/"+id+"?api_key=f246d5e5105e9934d3cd4c4c181d618d";

        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, url);
        String id_imdb = Parser.parseIMDBID(response1);

        return id_imdb;
    }

    public static Movie loadpersonDetailsMovies(RequestQueue requestQueue, String s) {

        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, s);
        Movie movie = Parser.parsePersonDetails(response1);

        return movie;

    }

    public static Movie loadMorePersonDetails(RequestQueue requestQueue, String imdbId) {

        String urlPrePerson = "http://www.myapifilms.com/imdb/idIMDB?idName=";
        String urlPostPerson = "&token=764e7262-e813-4448-b1ba-52cfb7aed311&format=json&language=en-us&bornDied=1&starSign=1&uniqueName=1&actorActress=1";

        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue,
                urlPrePerson+imdbId+urlPostPerson);
        Movie movie = Parser.parseMorePersonDetails(response1);

        return movie;


    }

    public static ArrayList<Movie> loadOthereMovies(RequestQueue requestQueue, String s) {

        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue, s);
        ArrayList<Movie> listMovies1 = Parser.parseOtherMovies(response1);

        return listMovies1;
    }

    public static ArrayList<Movie> loadSearchResult(RequestQueue requestQueue, String keyword) {
        JSONObject response1 = Requestor.sendRequestBoxOfficeMovies(requestQueue,keyword );

        ArrayList<Movie> listMovies1 = Parser.parseSearchReult(response1);

        return listMovies1;
    }
}
