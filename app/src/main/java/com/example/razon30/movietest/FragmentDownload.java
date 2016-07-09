package com.example.razon30.movietest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class FragmentDownload extends Fragment {

    //VOlley-Json
    public VolleySingleton volleySingleton;
    public RequestQueue requestQueue;

    ListView listYears, listMenus;
    BootstrapButton otherLink;
    AdapterYearMenu adapterYear, adapterMenu;
    ArrayList<MovieDownloadLink> yearlist;
    ArrayList<MovieDownloadLink> menulist;

    String URLyears = "https://movietest-1217.firebaseio.com/YearList";
    String URLMenus = "https://movietest-1217.firebaseio.com/Menu";

    String getURLyears = "https://movietest-1217.firebaseio.com/Year/";
    String getMovieLink = "https://movietest-1217.firebaseio.com/movies";

    Firebase yearRef, menuRef;

    View view1;
    AlertDialog.Builder builderAlertDialog;
    AlertDialog ad;

    AVLoadingIndicatorView avLoadingIndicatorViewmain;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Firebase.setAndroidContext(getActivity());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        View view = inflater.inflate(R.layout.fragment_fragment_download, container, false);

        view1 = getActivity().getLayoutInflater().inflate(R.layout.dialogue_element, null);
        avLoadingIndicatorViewmain = getavLoadingIndicator(view1);
        builderAlertDialog = new AlertDialog.Builder(getActivity());
        builderAlertDialog.setView(view1);
        yearlist = new ArrayList<MovieDownloadLink>();
        menulist = new ArrayList<MovieDownloadLink>();
        listYears = (ListView) view.findViewById(R.id.listyears);
        listMenus = (ListView) view.findViewById(R.id.listmenus);
        otherLink = (BootstrapButton) view.findViewById(R.id.othermovie);

        yearRef = new Firebase(URLyears);
        menuRef = new Firebase(URLMenus);


        TaskLoadData taskLoadData = new TaskLoadData();
        taskLoadData.execute();
        TaskLoadData1 taskLoadData1 = new TaskLoadData1();
        taskLoadData1.execute();

        otherLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DownloadActivity.class);
                intent.putExtra("url", getMovieLink);
                startActivity(intent);

            }
        });

        listYears.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String year = yearlist.get(position).getMovie();

                String url = getURLyears+year;

                Intent intent = new Intent(getActivity(), DownloadActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);

            }
        });

        listMenus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = menulist.get(position).getLink();

                Intent intent = new Intent(getActivity(), DownloadActivity.class);

                intent.putExtra("url", url);
                startActivity(intent);

            }
        });


        return view;
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


    public FragmentDownload() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


    private class TaskLoadData extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ad = builderAlertDialog.create();
            ad.show();
        }


        @Override
        protected Void doInBackground(Void... params) {

            yearRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {


                    String str = snapshot.getValue().toString();

                    try {
                        JSONArray jsonArray = new JSONArray(str);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            String name = object.getString("Year");

                            MovieDownloadLink movieDownloadLink = new MovieDownloadLink(name, "");
                            yearlist.add(movieDownloadLink);


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Collections.reverse(yearlist);

                    adapterYear = new AdapterYearMenu(yearlist, getActivity());
                    listYears.setAdapter(adapterYear);

                    //   textView.setText(str);
                    // jsonObject1 = snapshot;

                //    Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getActivity(), "The read failed: " + firebaseError.getMessage()
                            , Toast.LENGTH_LONG).show();
                }
            });

//            menuRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot snapshot) {
//
//
//                    String str = snapshot.getValue().toString();
//
//                    try {
//                        JSONArray jsonArray = new JSONArray(str);
//
//                        for (int i=0;i<jsonArray.length();i++){
//
//                            JSONObject object = jsonArray.getJSONObject(i);
//
//                            String name = object.getString("Name");
//                            String link = object.getString("Link");
//                          //  Toast.makeText(getActivity(),name,Toast.LENGTH_LONG).show();
//
//                            MovieDownloadLink movieDownloadLink1 = new MovieDownloadLink(name,link);
//                            menulist.add(movieDownloadLink1);
//
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                 //   Collections.reverse(menulist);
//
//                    adapterMenu = new AdapterYearMenu(menulist,getActivity());
//                    listMenus.setAdapter(adapterMenu);
//
//                    //   textView.setText(str);
//                    // jsonObject1 = snapshot;
//
//                     Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
//
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                    Toast.makeText(getActivity(),"The read failed: " + firebaseError.getMessage()
//                            ,Toast.LENGTH_LONG).show();
//                }
//            });


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }

    private class TaskLoadData1 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        String[] strAr;


        @Override
        protected Void doInBackground(Void... params) {


            menuRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {


//                    String str = snapshot.getValue().toString();
//
//                    str = str.substring(1, str.length() - 1);
//
//                    strAr = str.split(",");
//
//
//                    for (int i = 0; i < strAr.length; i++) {
//
//                        String name = strAr[i].substring(6, strAr[i].length());
//                        if (name.contains("=")){
//                            name = name.replace("=", "");
//                        }
//                        i++;
//                        String link = strAr[i].substring(6, strAr[i].length()-1);
//
//                        MovieDownloadLink movieDownloadLink1 = new MovieDownloadLink(name,link);
//                        menulist.add(movieDownloadLink1);
//
//                    }

                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                        String name = postSnapshot.child("Name").getValue().toString();
                        String link = postSnapshot.child("Link").getValue().toString();

                        MovieDownloadLink movie = new MovieDownloadLink(name,link);

                        menulist.add(movie);
                    }


                    adapterMenu = new AdapterYearMenu(menulist, getActivity());
                    listMenus.setAdapter(adapterMenu);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getActivity(), "The read failed: " + firebaseError.getMessage()
                            , Toast.LENGTH_LONG).show();
                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ad.cancel();

        }

    }


}
