package com.example.razon30.movietest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mrengineer13.snackbar.SnackBar;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Random;


public class FragmentBoxOffice extends Fragment implements Sort{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    //sorting
    public MovieSorter movieSorter = new MovieSorter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                .VERTICAL, false);

        workingOnFAB(view);

        view1 = getActivity().getLayoutInflater().inflate(R.layout.dialogue_element, null);
        avLoadingIndicatorViewmain = getavLoadingIndicator(view1);
        builderAlertDialog = new AlertDialog.Builder(getActivity());
        builderAlertDialog.setView(view1);

        listMovieHits = (RecyclerView) view.findViewById(R.id.box_office);
        listMovieHits.setLayoutManager(layoutManager);
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);

        new TaskLoadMoviesBoxOffice_now().execute();
        adapterBoxOffice.setMovies(listMovies);
        adapterBoxOffice.notifyDataSetChanged();

        listMovieHits.addOnItemTouchListener(new RecyclerTOuchListener(getActivity(), listMovieHits, new ClickListener() {
            @Override
            public void onCLick(View v, int position) {

                Movie movie = listMovies.get(position);
                String id = String.valueOf(movie.getMovie_id());
                String image = image_url + movie.getMovie_image_link();

                Intent intent = new Intent(getActivity(), MovieDetails.class);

                intent.putExtra("tv", id);
                intent.putExtra("url", image);
                startActivity(intent);


            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));


        return view;
    }

    private void workingOnFAB(final View view) {
        FloatingActionMenu menuMultipleActions = (FloatingActionMenu) view.findViewById(R.id
                .multiple_actions);

        FloatingActionButton action_a = (FloatingActionButton) view.findViewById(R.id.action_a);
        final RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);

        action_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SnackBar.Builder(getActivity())
                        .withMessage("Sorting Alphabetically") // OR
                        .withTextColorId(R.color.primaryColor)
                        .withBackgroundColorId(R.color.bootstrap_brand_danger)
                        .withTypeFace(Typeface.SANS_SERIF)
                        .show();
                sortByName();

            }
        });

        FloatingActionButton action_b = (FloatingActionButton) view.findViewById(R.id.action_b);
        action_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                new SnackBar.Builder(getActivity())
                        .withMessage("Sorting By Date") // OR
                        .withTextColorId(R.color.primaryColor)
                        .withBackgroundColorId(R.color.bootstrap_brand_danger)
                        .withTypeFace(Typeface.SANS_SERIF)
                        .show();
                sortByDate();
            }
        });

        FloatingActionButton action_c = (FloatingActionButton) view.findViewById(R.id.action_c);
        action_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SnackBar.Builder(getActivity())
                        .withMessage("Sorting By Ratings") // OR
                        .withTextColorId(R.color.primaryColor)
                        .withBackgroundColorId(R.color.bootstrap_brand_danger)
                        .withTypeFace(Typeface.SANS_SERIF)
                        .show();
                sortByRatings();
            }
        });

    }

    @Override
    public void sortByName() {

        // Toast.makeText(getActivity(), "sort name box office", Toast.LENGTH_LONG).show();
        movieSorter.sortMoviesByName(listMovies);
        adapterBoxOffice.notifyDataSetChanged();

    }

    @Override
    public void sortByDate() {

        // Toast.makeText(getActivity(), "sort date box office", Toast.LENGTH_LONG).show();
        movieSorter.sortMovieByDate(listMovies);
        adapterBoxOffice.notifyDataSetChanged();

    }

    @Override
    public void sortByRatings() {

        // Toast.makeText(getActivity(), "sort rating box office", Toast.LENGTH_LONG).show();
        movieSorter.sortMoviesByRating(listMovies);
        adapterBoxOffice.notifyDataSetChanged();

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


    public class TaskLoadMoviesBoxOffice_now extends AsyncTask<Void, Void, ArrayList<Movie>> {


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
            listMovies = MovieUtils.loadBoxOfficeMovies(requestQueue);

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


    public FragmentBoxOffice() {
        // Required empty public constructor
    }


    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
