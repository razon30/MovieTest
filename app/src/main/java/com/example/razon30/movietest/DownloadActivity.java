package com.example.razon30.movietest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class DownloadActivity extends AppCompatActivity {

    RecyclerView recyclerDownload;
    AdapterRecyclerDownload adapter;
    ArrayList<MovieDownloadLink> movieList;

    Firebase refURL;
    String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Firebase.setAndroidContext(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Intent intent = getIntent();
        URL = intent.getStringExtra("url");
        Toast.makeText(this, URL,Toast.LENGTH_LONG).show();

        movieList = new ArrayList<MovieDownloadLink>();
        recyclerDownload = (RecyclerView) findViewById(R.id.recyclerDownload);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false);
        recyclerDownload.setLayoutManager(layoutManager);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference(URL);

        refURL = new Firebase(URL);


        TaskToLoadData taskToLoadData = new TaskToLoadData();
        taskToLoadData.execute();

    }

    private class TaskToLoadData extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;
        String name,link,sub = "";



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DownloadActivity.this);
            pDialog.setMessage("Loading Content ....");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            refURL.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {


                    String str = snapshot.getValue().toString();

                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                        String name = postSnapshot.child("Name").getValue().toString();
                        String link = postSnapshot.child("Link").getValue().toString();

                        if (postSnapshot.hasChild("Sub")){
                            sub = postSnapshot.child("Sub").getValue().toString();
                        }

                        MovieDownloadLink movie = new MovieDownloadLink(name,link,sub);

                        movieList.add(movie);
                    }


                    Collections.reverse(movieList);

                    adapter = new AdapterRecyclerDownload(DownloadActivity.this,movieList);

                    recyclerDownload.setAdapter(adapter);



                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(DownloadActivity.this, "The read failed: " + firebaseError
                            .getMessage()
                            , Toast.LENGTH_LONG).show();
                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
        }
    }











}
