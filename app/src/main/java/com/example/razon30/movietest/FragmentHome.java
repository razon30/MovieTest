package com.example.razon30.movietest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;


public class FragmentHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String image_url = "http://image.tmdb.org/t/p/w500";
    String item_of_top_250_of_imdb = "http://api.themoviedb.org/3/movie/";
    String itemPost = "?external_source=imdb_id&api_key=f246d5e5105e9934d3cd4c4c181d618d";

    //VOlley-Json
    public VolleySingleton volleySingleton;
    public RequestQueue requestQueue;


    //recyclerview for popular movie
    private RecyclerView recyclerViewPopularMovies;
    private AdapterRecycler adapterRecylclerPopularMovies;
    ArrayList<Movie> popularMovieList;

    //recyclerview for popular movie
    private RecyclerView recyclerViewPopularPersons;
    private AdapterRecycler adapterRecylclerPopularpersons;
    ArrayList<Movie> popularPersonList;

    //recyclerview for topten IMDB movie
    private RecyclerView recyclerViewTopTenIMDB;
    private AdapterRecycler adapterRecylclerTopTenIMDB;
    ArrayList<Movie> popularTopTenIMDBList;

    //recyclerview for bottomten IMDB movie
    private RecyclerView recyclerViewBottomTenIMDB;
    private AdapterRecycler adapterRecylclerBottomTenIMDB;
    ArrayList<Movie> popularBottomTenIMDBList;

    //works on movie by genre
    ListView lvGenre1, lvGenre2, lvGenre3;
    String[] genreName1 = {"Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary", "Drama"};
    String[] genreName2 = {"Family", "Fantasy", "Foreign", "History", "Horror", "Music", "Mystery"};
    String[] genreName3 = {"Romance", "Science Fiction", "Tv Movie", "Thriller", "War", "Western"};
    String[] genreId1 = {"28", "12", "16", "35", "80", "99", "18"};
    String[] genreId2 = {"10751", "14", "10769", "36", "27", "10402", "9648"};
    String[] genreId3 = {"10749", "878", "10770", "53", "10752", "37"};
    Adapter_genre adapter_genre;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        worksOnPopularMovies(view);
        worksOnPopularPerson(view);
        worksOnTOpTenIMDB(view);
        worksOnBottomTenIMDB(view);
        worksOnMovieByGenre(view);

        return view;
    }


    private void worksOnPopularMovies(View view) {
        popularMovieList = new ArrayList<Movie>();
        adapterRecylclerPopularMovies = new AdapterRecycler(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false);

        recyclerViewPopularMovies = (RecyclerView) view.findViewById(R.id
                .home_movie_popular);
        recyclerViewPopularMovies.setLayoutManager(layoutManager);
        recyclerViewPopularMovies.setAdapter(adapterRecylclerPopularMovies);

        new TaskLoadMoviesPopular().execute();
        adapterRecylclerPopularMovies.setMovies(popularMovieList);
        adapterRecylclerPopularMovies.notifyDataSetChanged();

        recyclerViewPopularMovies.addOnItemTouchListener(new RecyclerTOuchListener(getActivity(), recyclerViewPopularMovies, new
                ClickListener() {
                    @Override
                    public void onCLick(View v, int position) {

                        Movie movie = popularMovieList.get(position);
                        String id = String.valueOf(movie.getMovie_id());
                        String image = image_url + movie.getMovie_image_link();

                      //  Toast.makeText(getActivity(), id, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getActivity(), MovieDetails.class);

                        intent.putExtra("tv", id);
                        intent.putExtra("url", image);
                        startActivity(intent);


                    }

                    @Override
                    public void onLongClick(View v, int position) {

                    }
                }));


    }

    private void worksOnPopularPerson(View view) {

        popularPersonList = new ArrayList<Movie>();
        adapterRecylclerPopularpersons = new AdapterRecycler(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false);

        recyclerViewPopularPersons = (RecyclerView) view.findViewById(R.id
                .home_person_popular);
        recyclerViewPopularPersons.setLayoutManager(layoutManager);
        recyclerViewPopularPersons.setAdapter(adapterRecylclerPopularpersons);

        new TaskLoadPersonPopular().execute();
        adapterRecylclerPopularpersons.setMovies(popularPersonList);
        adapterRecylclerPopularpersons.notifyDataSetChanged();

        recyclerViewPopularPersons.addOnItemTouchListener(new RecyclerTOuchListener(getActivity()
                , recyclerViewPopularPersons, new
                ClickListener() {
                    @Override
                    public void onCLick(View v, int position) {

                        Movie movie = popularPersonList.get(position);
                        String id = String.valueOf(movie.getMovie_id());
                        String image = image_url + movie.getMovie_image_link();

//                        Toast.makeText(getActivity(),id,Toast.LENGTH_LONG).show();
//
                        Intent intent = new Intent(getActivity(), PersonDetails.class);

                        intent.putExtra("tv", id);
                        startActivity(intent);


                    }

                    @Override
                    public void onLongClick(View v, int position) {

                    }
                }));


    }

    private void worksOnTOpTenIMDB(View view) {
        popularTopTenIMDBList = new ArrayList<Movie>();
        adapterRecylclerTopTenIMDB = new AdapterRecycler(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false);

        recyclerViewTopTenIMDB = (RecyclerView) view.findViewById(R.id
                .home_top_ten_imdb);
        recyclerViewTopTenIMDB.setLayoutManager(layoutManager);
        recyclerViewTopTenIMDB.setAdapter(adapterRecylclerTopTenIMDB);

        new TaskLoadTopTenIMDB().execute();
        adapterRecylclerTopTenIMDB.setMovies(popularTopTenIMDBList);
        adapterRecylclerTopTenIMDB.notifyDataSetChanged();

        recyclerViewTopTenIMDB.addOnItemTouchListener(new RecyclerTOuchListener(getActivity()
                , recyclerViewTopTenIMDB, new
                ClickListener() {
                    @Override
                    public void onCLick(View v, int position) {

                        Movie movie = popularTopTenIMDBList.get(position);
                        String id = String.valueOf(movie.getMovie_id());
                        String image = image_url + movie.getMovie_image_link();

                       // Toast.makeText(getActivity(),id,Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getActivity(), MovieDetails.class);

                        intent.putExtra("tv", id);
                        intent.putExtra("url", image);
                        startActivity(intent);


                    }

                    @Override
                    public void onLongClick(View v, int position) {

                    }
                }));

    }

    private void worksOnBottomTenIMDB(View view) {
        popularBottomTenIMDBList = new ArrayList<Movie>();
        adapterRecylclerBottomTenIMDB = new AdapterRecycler(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false);

        recyclerViewBottomTenIMDB = (RecyclerView) view.findViewById(R.id
                .home_bottom_ten_imdb);
        recyclerViewBottomTenIMDB.setLayoutManager(layoutManager);
        recyclerViewBottomTenIMDB.setAdapter(adapterRecylclerBottomTenIMDB);

        new TaskLoadBottomTenIMDB().execute();
        adapterRecylclerBottomTenIMDB.setMovies(popularBottomTenIMDBList);
        adapterRecylclerBottomTenIMDB.notifyDataSetChanged();

        recyclerViewBottomTenIMDB.addOnItemTouchListener(new RecyclerTOuchListener(getActivity()
                , recyclerViewBottomTenIMDB, new
                ClickListener() {
                    @Override
                    public void onCLick(View v, int position) {

                        Movie movie = popularBottomTenIMDBList.get(position);
                        String id = String.valueOf(movie.getMovie_id());
                        String image = image_url + movie.getMovie_image_link();

                        Toast.makeText(getActivity(),id,Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getActivity(), MovieDetails.class);

                        intent.putExtra("tv", id);
                        intent.putExtra("url", image);
                        startActivity(intent);


                    }

                    @Override
                    public void onLongClick(View v, int position) {

                    }
                }));

    }


    private void worksOnMovieByGenre(View view) {

        lvGenre1 = (ListView) view.findViewById(R.id.search_genre_list1);
        lvGenre2 = (ListView) view.findViewById(R.id.search_genre_list2);
        lvGenre3 = (ListView) view.findViewById(R.id.search_genre_list3);

        adapter_genre = new Adapter_genre(getActivity(), genreName1);
        lvGenre1.setAdapter(adapter_genre);

        lvGenre1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String idd = genreId1[position];

      //          Toast.makeText(getActivity(),idd,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), MoviesByGenre.class);
                intent.putExtra("tv", idd);
                startActivity(intent);


            }
        });

        adapter_genre = new Adapter_genre(getActivity(), genreName2);
        lvGenre2.setAdapter(adapter_genre);

        lvGenre2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String idd = genreId2[position];
   //             Toast.makeText(getActivity(),idd,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), MoviesByGenre.class);
                intent.putExtra("tv", idd);
                startActivity(intent);


            }
        });

        adapter_genre = new Adapter_genre(getActivity(), genreName3);
        lvGenre3.setAdapter(adapter_genre);

        lvGenre3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String idd = genreId3[position];
    //            Toast.makeText(getActivity(),idd,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), MoviesByGenre.class);
                intent.putExtra("tv", idd);
                startActivity(intent);


            }
        });



    }



    public class TaskLoadMoviesPopular extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            volleySingleton = VolleySingleton.getsInstance();
            requestQueue = volleySingleton.getmRequestQueue();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            popularMovieList = MovieUtils.loadPopularMovies(requestQueue);
            return popularMovieList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> aVoid) {
            super.onPostExecute(aVoid);

            recyclerViewPopularMovies.setAdapter(adapterRecylclerPopularMovies);
            adapterRecylclerPopularMovies.setMovies(popularMovieList);
            adapterRecylclerPopularMovies.notifyDataSetChanged();

        }
    }

    public class TaskLoadPersonPopular extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            volleySingleton = VolleySingleton.getsInstance();
            requestQueue = volleySingleton.getmRequestQueue();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            popularPersonList = MovieUtils.loadPopularPerson(requestQueue);
            return popularPersonList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> aVoid) {
            super.onPostExecute(aVoid);

            recyclerViewPopularPersons.setAdapter(adapterRecylclerPopularpersons);
            adapterRecylclerPopularpersons.setMovies(popularPersonList);
            adapterRecylclerPopularpersons.notifyDataSetChanged();

        }
    }

    public class TaskLoadTopTenIMDB extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            volleySingleton = VolleySingleton.getsInstance();
            requestQueue = volleySingleton.getmRequestQueue();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            popularTopTenIMDBList = MovieUtils.loadTopTenIMDB(requestQueue);
            return popularTopTenIMDBList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> aVoid) {
            super.onPostExecute(aVoid);

            recyclerViewTopTenIMDB.setAdapter(adapterRecylclerTopTenIMDB);
            adapterRecylclerTopTenIMDB.setMovies(popularTopTenIMDBList);
            adapterRecylclerTopTenIMDB.notifyDataSetChanged();

        }
    }

    public class TaskLoadBottomTenIMDB extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            volleySingleton = VolleySingleton.getsInstance();
            requestQueue = volleySingleton.getmRequestQueue();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            popularBottomTenIMDBList = MovieUtils.loadBottomTenIMDB(requestQueue);
            return popularBottomTenIMDBList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> aVoid) {
            super.onPostExecute(aVoid);

            recyclerViewBottomTenIMDB.setAdapter(adapterRecylclerBottomTenIMDB);
            adapterRecylclerBottomTenIMDB.setMovies(popularBottomTenIMDBList);
            adapterRecylclerBottomTenIMDB.notifyDataSetChanged();

        }
    }




    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public interface ClickListener {

        void onCLick(View v, int position);

        void onLongClick(View v, int position);

    }


    static class RecyclerTOuchListener implements RecyclerView.OnItemTouchListener {

        GestureDetector gestureDetector;
        ClickListener clickListener;

        public RecyclerTOuchListener(Context context, final RecyclerView rv, final ClickListener clickListener) {

            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                    View child = rv.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, rv.getChildPosition(child));
                    }

                }
            });


        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {

                clickListener.onCLick(child, rv.getChildPosition(child));

            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

}
