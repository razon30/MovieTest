package com.example.razon30.movietest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.RequestQueue;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Random;

public class MoviesByGenre extends AppCompatActivity {

    String image_url = "http://image.tmdb.org/t/p/w500";
    private RecyclerView listMovieHits;
    private AdapterBoxOffice adapterBoxOffice;
    public ArrayList<Movie> listMovies = new ArrayList<Movie>();
    //VOlley-Json
    public VolleySingleton volleySingleton;
    public RequestQueue requestQueue;

    View view1;
    AlertDialog.Builder builderAlertDialog;
    AlertDialog ad;

    AVLoadingIndicatorView avLoadingIndicatorViewmain;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_by_genre);
        Intent intent = getIntent();
        id = intent.getStringExtra("tv");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false);

        view1 = getLayoutInflater().inflate(R.layout.dialogue_element, null);
        avLoadingIndicatorViewmain = getavLoadingIndicator(view1);
        builderAlertDialog = new AlertDialog.Builder(this);
        builderAlertDialog.setView(view1);

        listMovieHits = (RecyclerView) findViewById(R.id.movies_by_genre);
        listMovieHits.setLayoutManager(layoutManager);
        adapterBoxOffice = new AdapterBoxOffice(this);
        listMovieHits.setAdapter(adapterBoxOffice);

        new TaskLoadMoviesByGenre(id).execute();
        adapterBoxOffice.setMovies(listMovies);
        adapterBoxOffice.notifyDataSetChanged();

        listMovieHits.addOnItemTouchListener(new RecyclerTOuchListener(this, listMovieHits, new
                ClickListener() {
                    @Override
                    public void onCLick(View v, int position) {

                        Movie movie = listMovies.get(position);
                        String id = String.valueOf(movie.getMovie_id());
                        String image = image_url + movie.getMovie_image_link();

                        Intent intent = new Intent(MoviesByGenre.this, MovieDetails.class);

                        intent.putExtra("tv", id);
                        intent.putExtra("url", image);
                        startActivity(intent);


                    }

                    @Override
                    public void onLongClick(View v, int position) {

                    }
                }));


    }

    private AVLoadingIndicatorView getavLoadingIndicator(View view) {

        AVLoadingIndicatorView avLoadingIndicatorView = null;

        AVLoadingIndicatorView avLoadingIndicatorView1 = (AVLoadingIndicatorView) view
                .findViewById(R.id
                        .avloadingIndicatorView1);
        AVLoadingIndicatorView avLoadingIndicatorView2 = (AVLoadingIndicatorView) view
                .findViewById(R.id
                        .avloadingIndicatorView2);
        AVLoadingIndicatorView avLoadingIndicatorView3 = (AVLoadingIndicatorView) view
                .findViewById(R.id
                        .avloadingIndicatorView3);
        AVLoadingIndicatorView avLoadingIndicatorView4 = (AVLoadingIndicatorView) view
                .findViewById(R.id
                        .avloadingIndicatorView4);
        AVLoadingIndicatorView avLoadingIndicatorView5 = (AVLoadingIndicatorView) view
                .findViewById(R.id
                        .avloadingIndicatorView5);
        AVLoadingIndicatorView avLoadingIndicatorView6 = (AVLoadingIndicatorView) view
                .findViewById(R.id
                        .avloadingIndicatorView6);
        AVLoadingIndicatorView avLoadingIndicatorView7 = (AVLoadingIndicatorView) view
                .findViewById(R.id
                        .avloadingIndicatorView7);

        Random random = new Random();
        int i = random.nextInt(7 - 1 + 1) + 1;
        if (i == 1) {

            avLoadingIndicatorView = visibleGone(avLoadingIndicatorView1, avLoadingIndicatorView2,
                    avLoadingIndicatorView3,
                    avLoadingIndicatorView4, avLoadingIndicatorView5, avLoadingIndicatorView6,
                    avLoadingIndicatorView7);

        }
        if (i == 2) {
            avLoadingIndicatorView = visibleGone(avLoadingIndicatorView2, avLoadingIndicatorView1, avLoadingIndicatorView3,
                    avLoadingIndicatorView4, avLoadingIndicatorView5, avLoadingIndicatorView6,
                    avLoadingIndicatorView7);
        }
        if (i == 3) {
            avLoadingIndicatorView = visibleGone(avLoadingIndicatorView3, avLoadingIndicatorView2, avLoadingIndicatorView1,
                    avLoadingIndicatorView4, avLoadingIndicatorView5, avLoadingIndicatorView6,
                    avLoadingIndicatorView7);
        }
        if (i == 4) {
            avLoadingIndicatorView = visibleGone(avLoadingIndicatorView4, avLoadingIndicatorView2, avLoadingIndicatorView3,
                    avLoadingIndicatorView1, avLoadingIndicatorView5, avLoadingIndicatorView6,
                    avLoadingIndicatorView7);
        }
        if (i == 5) {
            avLoadingIndicatorView = visibleGone(avLoadingIndicatorView5, avLoadingIndicatorView2, avLoadingIndicatorView3,
                    avLoadingIndicatorView4, avLoadingIndicatorView1, avLoadingIndicatorView6,
                    avLoadingIndicatorView7);
        }
        if (i == 6) {
            avLoadingIndicatorView = visibleGone(avLoadingIndicatorView6, avLoadingIndicatorView2, avLoadingIndicatorView3,
                    avLoadingIndicatorView4, avLoadingIndicatorView5, avLoadingIndicatorView1,
                    avLoadingIndicatorView7);
        }
        if (i == 7) {
            avLoadingIndicatorView = visibleGone(avLoadingIndicatorView7, avLoadingIndicatorView2, avLoadingIndicatorView3,
                    avLoadingIndicatorView4, avLoadingIndicatorView5, avLoadingIndicatorView6,
                    avLoadingIndicatorView1);
        }

        return avLoadingIndicatorView;

    }

    private AVLoadingIndicatorView visibleGone(AVLoadingIndicatorView avLoadingIndicatorView1, AVLoadingIndicatorView
            avLoadingIndicatorView2, AVLoadingIndicatorView avLoadingIndicatorView3, AVLoadingIndicatorView avLoadingIndicatorView4, AVLoadingIndicatorView avLoadingIndicatorView5, AVLoadingIndicatorView avLoadingIndicatorView6, AVLoadingIndicatorView avLoadingIndicatorView7) {

        avLoadingIndicatorView1.setVisibility(View.VISIBLE);
        avLoadingIndicatorView2.setVisibility(View.GONE);
        avLoadingIndicatorView3.setVisibility(View.GONE);
        avLoadingIndicatorView4.setVisibility(View.GONE);
        avLoadingIndicatorView5.setVisibility(View.GONE);
        avLoadingIndicatorView6.setVisibility(View.GONE);
        avLoadingIndicatorView7.setVisibility(View.GONE);

        return avLoadingIndicatorView1;

    }


    public class TaskLoadMoviesByGenre extends AsyncTask<Void, Void, ArrayList<Movie>> {

        String id;

        public TaskLoadMoviesByGenre(String id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            volleySingleton = VolleySingleton.getsInstance();
            requestQueue = volleySingleton.getmRequestQueue();

            ad = builderAlertDialog.create();
            ad.show();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            listMovies = MovieUtils.loadMoviesByGenre(requestQueue, id);

            return listMovies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> aVoid) {
            super.onPostExecute(aVoid);
            adapterBoxOffice.setMovies(listMovies);
            adapterBoxOffice.notifyDataSetChanged();
            ad.cancel();

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
