package com.example.razon30.movietest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Random;

public class Multi_Search_Activity extends AppCompatActivity {

    private static final String STATE_MOVIE = "multi_search_list";
    public ArrayList<Movie> listMovies = new ArrayList<Movie>();
    Adapter_KKeyword_Search adapter_kKeyword_search;
    TextView tv;
    //recycle
    private RecyclerView listMovieHits;
    //VOlley-Json
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    View view1;
    AlertDialog.Builder builderAlertDialog;
    AlertDialog ad;

    AVLoadingIndicatorView avLoadingIndicatorViewmain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi__search_);
        tv= (TextView) findViewById(R.id.tv_multi);

        Intent intent = getIntent();
        String keyword = intent.getStringExtra("tv");

        view1 = getLayoutInflater().inflate(R.layout.dialogue_element, null);
        avLoadingIndicatorViewmain = getavLoadingIndicator(view1);
        builderAlertDialog = new AlertDialog.Builder(this);
        builderAlertDialog.setView(view1);

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getmRequestQueue();

        listMovieHits = (RecyclerView) findViewById(R.id.multi_recycler);
        listMovieHits.setLayoutManager(new LinearLayoutManager(Multi_Search_Activity.this));
        adapter_kKeyword_search = new Adapter_KKeyword_Search(Multi_Search_Activity.this);
        listMovieHits.setAdapter(adapter_kKeyword_search);

        if (savedInstanceState != null) {
            // listMovies = savedInstanceState.getParcelableArrayList(STATE_MOVIE);
            // adapter_kKeyword_search.setMovies(listMovies);
        } else {
            new TaskLoadMultiSearching(Multi_Search_Activity.this,keyword).execute();


        }


        adapter_kKeyword_search.setMovies(listMovies);
        Toast.makeText(Multi_Search_Activity.this, listMovies.size() + "", Toast.LENGTH_LONG)
                .show();

        listMovieHits.addOnItemTouchListener(new RecyclerTOuchListener(Multi_Search_Activity.this,
                listMovieHits, new
                ClickListener() {
                    @Override
                    public void onCLick(View v, int position) {
                        //  Toast.makeText(Multi_Search_Activity.this, "Touched on: " + position,
                        //    Toast.LENGTH_LONG).show();

                        Movie movie = listMovies.get(position);

                        String type = movie.getProfileLink();

                        if ("person".equalsIgnoreCase(type)){

                            String id = String.valueOf(movie.getUserName());

                            Intent intent1 = new Intent(Multi_Search_Activity.this,PersonDetails
                                    .class);
                            intent1.putExtra("tv",id);
                            startActivity(intent1);

                        }else if ("movie".equalsIgnoreCase(type)){

                            String id = String.valueOf(movie.getUserName());
                            String image = "http://image.tmdb.org/t/p/w500" + movie.getCoverImage();
                            Intent intent1 = new Intent(Multi_Search_Activity.this,MovieDetails
                                    .class);
                            intent1.putExtra("url", image);
                            intent1.putExtra("tv",id);
                            startActivity(intent1);

                        }


                    }

                    @Override
                    public void onLongClick(View v, int position) {

                        // Toast.makeText(Multi_Search_Activity.this, "Long Touched on: " +
                        // position, Toast.LENGTH_LONG).show();

                    }
                }));






    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putParcelableArrayList(STATE_MOVIE, listMovies);
    }


    public interface ClickListener {

        void onCLick(View v, int position);

        void onLongClick(View v, int position);

    }

    static class RecyclerTOuchListener implements RecyclerView.OnItemTouchListener{

        GestureDetector gestureDetector;
        ClickListener clickListener;

        public  RecyclerTOuchListener(Context context, final RecyclerView rv, final ClickListener clickListener){

            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return  true;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                    View child = rv.findChildViewUnder(e.getX(),e.getY());
                    if (child != null && clickListener !=null){
                        clickListener.onLongClick(child,rv.getChildPosition(child));
                    }

                }
            });


        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if (child !=null && clickListener!=null && gestureDetector.onTouchEvent(e)){

                clickListener.onCLick(child,rv.getChildPosition(child));

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

    public class TaskLoadMultiSearching extends AsyncTask<Void, Void, ArrayList<Movie>> {

        Context context;
        String keyword;

        public TaskLoadMultiSearching(  Context context, String keyword) {
            this.context = context;
            this.keyword = keyword;
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
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            listMovies = MovieUtils.loadSearchResult(requestQueue, keyword);

            return listMovies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> aVoid) {
            super.onPostExecute(aVoid);
            adapter_kKeyword_search.setMovies(listMovies);
            adapter_kKeyword_search.notifyDataSetChanged();
            ad.cancel();

        }
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

            avLoadingIndicatorView =    visibleGone(avLoadingIndicatorView1, avLoadingIndicatorView2,
                    avLoadingIndicatorView3,
                    avLoadingIndicatorView4, avLoadingIndicatorView5, avLoadingIndicatorView6,
                    avLoadingIndicatorView7);

        }
        if (i == 2) {
            avLoadingIndicatorView =    visibleGone(avLoadingIndicatorView2, avLoadingIndicatorView1, avLoadingIndicatorView3,
                    avLoadingIndicatorView4, avLoadingIndicatorView5, avLoadingIndicatorView6,
                    avLoadingIndicatorView7);
        }
        if (i == 3) {
            avLoadingIndicatorView =     visibleGone(avLoadingIndicatorView3, avLoadingIndicatorView2, avLoadingIndicatorView1,
                    avLoadingIndicatorView4, avLoadingIndicatorView5, avLoadingIndicatorView6,
                    avLoadingIndicatorView7);
        }
        if (i == 4) {
            avLoadingIndicatorView =     visibleGone(avLoadingIndicatorView4, avLoadingIndicatorView2, avLoadingIndicatorView3,
                    avLoadingIndicatorView1, avLoadingIndicatorView5, avLoadingIndicatorView6,
                    avLoadingIndicatorView7);
        }
        if (i == 5) {
            avLoadingIndicatorView =      visibleGone(avLoadingIndicatorView5, avLoadingIndicatorView2, avLoadingIndicatorView3,
                    avLoadingIndicatorView4, avLoadingIndicatorView1, avLoadingIndicatorView6,
                    avLoadingIndicatorView7);
        }
        if (i == 6) {
            avLoadingIndicatorView =      visibleGone(avLoadingIndicatorView6, avLoadingIndicatorView2, avLoadingIndicatorView3,
                    avLoadingIndicatorView4, avLoadingIndicatorView5, avLoadingIndicatorView1,
                    avLoadingIndicatorView7);
        }
        if (i == 7) {
            avLoadingIndicatorView =      visibleGone(avLoadingIndicatorView7, avLoadingIndicatorView2, avLoadingIndicatorView3,
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



}
