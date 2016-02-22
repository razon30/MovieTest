package com.example.razon30.movietest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mrengineer13.snackbar.SnackBar;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class PersonDetails extends AppCompatActivity {

    //url for imdb


    //url for tmdb
    String urlPreId = "http://api.themoviedb.org/3/person/";
    String id;
    String urlLaterId = "?api_key=f246d5e5105e9934d3cd4c4c181d618d";
    String image_url = "http://image.tmdb.org/t/p/w500";
    String image_post = "/images?api_key=f246d5e5105e9934d3cd4c4c181d618d";
    String more_movie_post = "/movie_credits?api_key=f246d5e5105e9934d3cd4c4c181d618d";


    //variables of expanding layout
    private ExpandableRelativeLayout mExpandLayout;
    BootstrapButton mExpandButton;
    int b = 1;


    //basic architectural variables
    ImageView personCover, image1, image2, image3;
    RoundedImageView personPoster;
    BootstrapButton personBirthName, personActorActress, personBio, personBirthDay, personBirthPlace,
            personHeight, personStarsign,personMoreImage;
    RecyclerView personSalaryandMovie;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    ArrayList<String> more_image_array;

    private AdapterOtherMovies adapterOtherMovies;
    public ArrayList<Movie> listMovies = new ArrayList<Movie>();


    //for loading
    View view1;
    AlertDialog.Builder builderAlertDialog;
    AlertDialog ad;

    AVLoadingIndicatorView avLoadingIndicatorViewmain;

    Movie movie1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        more_image_array = new ArrayList<String>();
        Intent intent = getIntent();
        id = intent.getStringExtra("tv");
        //imdbId = intent.getStringExtra("im");
        String image = intent.getStringExtra("img");

        workingOnFAB();

        Initialization();
        view1 = getLayoutInflater().inflate(R.layout.dialogue_element, null);
        avLoadingIndicatorViewmain = getavLoadingIndicator(view1);
        builderAlertDialog = new AlertDialog.Builder(this);
        builderAlertDialog.setView(view1);

        worksOnOtherMovies();

        worksonExpandingLayout();

        new TaskLoadPersonDetails(id).execute();

//        Picasso.with(PersonDetails.this).load(image).into(personPoster);


        personMoreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (more_image_array == null || more_image_array.size() == 0) {
                    new SnackBar.Builder(PersonDetails.this)
                            .withMessage("No Image Found or Network Error") // OR
                            .withTextColorId(R.color.primaryColor)
                            .withBackgroundColorId(R.color.accentColor)
                            .withTypeFace(Typeface.SANS_SERIF)
                            .show();
                    return;
                } else {


                    Intent intent = new Intent(PersonDetails.this, ImageGalleryActivity.class);
                    intent.putStringArrayListExtra("images", more_image_array);
                    intent.putExtra("palette_color_type", PaletteColorType.VIBRANT);
                    startActivity(intent);

                }

            }
        });


    }

    private void workingOnFAB() {
        FloatingActionMenu menuMultipleActions = (FloatingActionMenu) findViewById(R.id
                .multiple_actions);

        com.github.clans.fab.FloatingActionButton FABShare = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.action_a);
        //  final RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);

        FABShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "My Favourite " + movie1.getName());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent , "From ToTaL MoViE"));

            }
        });

        com.github.clans.fab.FloatingActionButton FABRefresh = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.action_b);
        FABRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PersonDetails.this, PersonDetails.class);
                intent.putExtra("tv", id);
                startActivity(intent);

            }
        });

        com.github.clans.fab.FloatingActionButton FABSetting = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.action_c);
        FABSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PersonDetails.this,Setting.class));

            }
        });

    }


    private void worksOnOtherMovies() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(PersonDetails.this,
                LinearLayoutManager
                        .HORIZONTAL, false);

        personSalaryandMovie.setLayoutManager(layoutManager);
        adapterOtherMovies = new AdapterOtherMovies(PersonDetails.this);
        personSalaryandMovie.setAdapter(adapterOtherMovies);

        new TaskLoadOtherMovies(id).execute();
        adapterOtherMovies.setMovies(listMovies);
        adapterOtherMovies.notifyDataSetChanged();

        personSalaryandMovie.addOnItemTouchListener(new RecyclerTOuchListener(this, personSalaryandMovie,
                new
                        ClickListener() {
                            @Override
                            public void onCLick(View v, int position) {

                                Movie movie = listMovies.get(position);
                                String id = String.valueOf(movie.getMovie_id());
                                String image = image_url + movie.getMovie_image_link();

                                Intent intent = new Intent(PersonDetails.this, MovieDetails.class);

                                intent.putExtra("tv", id);
                                intent.putExtra("url", image);
                                startActivity(intent);


                            }

                            @Override
                            public void onLongClick(View v, int position) {

                            }
                        }));


    }

    public class TaskLoadOtherMovies extends AsyncTask<Void, Void, ArrayList<Movie>> {


        String id;
        public TaskLoadOtherMovies(String id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            volleySingleton = VolleySingleton.getsInstance();
            requestQueue = volleySingleton.getmRequestQueue();

        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            listMovies = MovieUtils.loadOthereMovies(requestQueue, urlPreId+id+more_movie_post );

            return listMovies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> aVoid) {
            super.onPostExecute(aVoid);
            adapterOtherMovies.setMovies(listMovies);
            adapterOtherMovies.notifyDataSetChanged();


        }
    }


    public class TaskLoadPersonDetails extends AsyncTask<Void, Void, Movie> {


        String id;

        public TaskLoadPersonDetails(String id) {
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
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected Movie doInBackground(Void... params) {
            Movie movie = MovieUtils.loadpersonDetailsMovies(requestQueue, urlPreId + id + urlLaterId);
            worksOnImage();
            return movie;
        }

        @Override
        protected void onPostExecute(Movie aVoid) {
            super.onPostExecute(aVoid);

            movie1 = aVoid;

            if (aVoid.getBirthName() == null || aVoid.getBirthName() == "") {
                personBirthName.setVisibility(View.GONE);
            } else {

                personBirthName.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                        .addFontAwesomeIcon(FontAwesome.FA_GRADUATION_CAP)
                        .addText("  " + aVoid.getBirthName()).build());
            }


            if (aVoid.getActorActress() == null || aVoid.getActorActress() == "") {
                personActorActress.setVisibility(View.GONE);
            } else {
                if (aVoid.getActorActress().compareToIgnoreCase("Actor") == 0) {
                    personActorActress.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                            .addFontAwesomeIcon(FontAwesome.FA_MALE)
                            .addText("  " + aVoid.getActorActress()).build());
                } else {
                    personActorActress.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                            .addFontAwesomeIcon(FontAwesome.FA_FEMALE)
                            .addText("  " + aVoid.getActorActress()).build());
                }
            }


            if (aVoid.getHeight() == null || aVoid.getHeight() == "") {
                personHeight.setVisibility(View.GONE);
            } else {

                personHeight.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                        .addText(aVoid.getHeight()).build());

            }

            if (aVoid.getBirthday() == null || aVoid.getBirthday() == "") {
                personBirthDay.setVisibility(View.GONE);
            } else {
                personBirthDay.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                        .addText(aVoid.getBirthday()).build());
            }

            if (aVoid.getBirthPlace() == null || aVoid.getBirthPlace() == "") {
                personBirthPlace.setVisibility(View.GONE);
            } else {
                personBirthPlace.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                        .addText(aVoid.getBirthPlace()).build());
            }
            if (aVoid.getBio() == null || aVoid.getBio() == "") {
                personBio.setVisibility(View.GONE);
            } else {

                personBio.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                        .addText(aVoid.getBio()).build());
            }

            CollapsingToolbarLayout collapsingToolbarLayout;
            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
            collapsingToolbarLayout.setTitle(aVoid.getName());

            collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style
                    .CollapsedAppBar);
            collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style
                    .ExpandedAppBarPlus1);
            collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style
                    .CollapsedAppBarPlus1);

            if (aVoid.getStarSign() == null || aVoid.getStarSign() == "") {
                personStarsign.setVisibility(View.GONE);
            } else {

                personStarsign.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                        .addText(aVoid.getStarSign()).build());
            }
            ad.cancel();

        }
    }

    private void worksOnImage() {

        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, urlPreId + id +
                image_post, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        if (jsonObject == null || jsonObject.length() == 0) {
                            new SnackBar.Builder(PersonDetails.this)
                                    .withMessage("Problem to Load") // OR
                                    .withTextColorId(R.color.primaryColor)
                                    .withBackgroundColorId(R.color.accentColor)
                                    .withTypeFace(Typeface.SANS_SERIF)
                                    .show();

                        }

                        try {


                            JSONArray image = jsonObject.getJSONArray("profiles");

                            String profile1 = image.getJSONObject(0).getString("file_path");
                            if (profile1 != null && profile1.length() != 0) {
                                image1.setVisibility(View.VISIBLE);
                                Picasso.with(PersonDetails.this).load(image_url + profile1).into
                                        (image1);
                            }
                            String profile2 = image.getJSONObject(1).getString("file_path");
                            if (profile2 != null && profile2.length() != 0) {

                                image2.setVisibility(View.VISIBLE);
                                Picasso.with(PersonDetails.this).load(image_url + profile2).into
                                        (image2);
                            }


                            String profile3 = image.getJSONObject(2).getString("file_path");
                            if (profile3 != null && profile3.length() != 0) {
                                image3.setVisibility(View.VISIBLE);
                                Picasso.with(PersonDetails.this).load(image_url + profile3).into
                                        (image3);
                            }

                            for (int i = 0; i < image.length(); i++) {

                                JSONObject obj = image.getJSONObject(i);
                                String im = image_url + obj.getString("file_path");
                                more_image_array.add(im);

                            }

                            Picasso.with(PersonDetails.this).load(image_url + more_image_array.get
                                    (0))
                                    .into(personCover);

                            Picasso.with(PersonDetails.this).load(image_url + more_image_array
                                    .get(1))
                                    .into(personPoster);


                        } catch (Exception e) {
//                            Toast.makeText(Person_Details.this, e.toString(), Toast.LENGTH_LONG)
//                                    .show();


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(Person_Details.this, volleyError.toString(), Toast.LENGTH_LONG)
//                                .show();

                    }
                });

        requestQueue.add(request1);


    }


//    private String worksOnParsingData() {
//
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlPreId + id + urlLaterId, null,
//
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//
//                        if (jsonObject == null || jsonObject.length() == 0) {
//                            new SnackBar.Builder(PersonDetails.this)
//                                    .withMessage("Problem to Load") // OR
//                                    .withTextColorId(R.color.background1)
//                                    .withBackgroundColorId(R.color.primaryColor)
//                                    .withTypeFace(Typeface.SANS_SERIF)
//                                    .show();
//                        }
//
//                        try {
//
//                            imdbId = jsonObject.getString("imdb_id");
//                            String poterImage = jsonObject.getString("profile_path");
//                            Picasso.with(PersonDetails.this).load(image_url + poterImage).into(personPoster);
//
//                           // worksOnparsingImdbData(imdbId);
//
//                        } catch (Exception e) {
//
//                        }
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//
//                    }
//                });
//
//        requestQueue.add(request);
//
//
//        return imdbId;
//    }
//
//    private void worksOnparsingImdbData(final String imdbId) {
//
//
//    }


    private void worksonExpandingLayout() {

        mExpandButton.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                .addText("Biography ")
                .addFontAwesomeIcon(FontAwesome.FA_ANGLE_DOWN)
                .build());

        mExpandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b == 1) {
                    mExpandButton.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                            .addText("Biography ")
                            .addFontAwesomeIcon(FontAwesome.FA_ANGLE_UP)
                            .build());
                    b = 2;
                } else if (b == 2) {
                    mExpandButton.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
                            .addText("Biography ")
                            .addFontAwesomeIcon(FontAwesome.FA_ANGLE_DOWN)
                            .build());
                    b = 1;
                }
                mExpandLayout.toggle();
            }
        });

    }

    private void Initialization() {

        mExpandButton = (BootstrapButton) findViewById(R.id.moreorless);
        mExpandLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
        personCover = (ImageView) findViewById(R.id.coverPerson);
        personPoster = (RoundedImageView) findViewById(R.id.person_postar_image_detail);
        personBirthName = (BootstrapButton) findViewById(R.id.tvBirthName);
        personActorActress = (BootstrapButton) findViewById(R.id.tvActororActress);
        personBio = (BootstrapButton) findViewById(R.id.tvBiography);
        personBirthDay = (BootstrapButton) findViewById(R.id.personBirthday);
        personBirthPlace = (BootstrapButton) findViewById(R.id.personBirthPlace);
        personHeight = (BootstrapButton) findViewById(R.id.personHeight);
        personStarsign = (BootstrapButton) findViewById(R.id.personStarSign);
        personSalaryandMovie = (RecyclerView) findViewById(R.id.personMovieandSallary);
        image1 = (ImageView) findViewById(R.id.movie_details_image1);
        image2 = (ImageView) findViewById(R.id.movie_details_image2);
        image3 = (ImageView) findViewById(R.id.movie_details_image3);
        personMoreImage = (BootstrapButton) findViewById(R.id.more_image);
        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getmRequestQueue();

    }

    private void worksOnLoading() {

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


//    JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, "http://www.myapifilms.com/imdb/idIMDB?idName=nm0425005&token=764e7262-e813-4448-b1ba-52cfb7aed311&format=json&language=en-us&bornDied=1&starSign=1&uniqueName=1&actorActress=1", null,
//
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject jsonObject) {
//
//                            if (jsonObject == null || jsonObject.length() == 0) {
//                                new SnackBar.Builder(PersonDetails.this)
//                                        .withMessage("Problem to Load") // OR
//                                        .withTextColorId(R.color.background1)
//                                        .withBackgroundColorId(R.color.primaryColor)
//                                        .withTypeFace(Typeface.SANS_SERIF)
//                                        .show();
//                            }
//
//                            try {
//
//
//                                JSONObject dataObject = jsonObject.getJSONObject("data");
//                                JSONArray jsonNameArray = dataObject.getJSONArray("names");
//                                JSONObject dataDetails = jsonNameArray.getJSONObject(0);
//
//                                String birthName = dataDetails.getString("birthName");
//                                String actorActress = dataDetails.getString("actorActress");
//                                String height = dataDetails.getString("height");
//                                String birthday = dataDetails.getString("dateOfBirth");
//                                String birthPlace = dataDetails.getString("placeOfBirth");
//                                String bio = dataDetails.getString("bio");
//                                String name = dataDetails.getString("name");
//                                String starSign = dataDetails.getString("starSign");
//
//                                Toast.makeText(PersonDetails.this,
//                                        birthday+birthName+actorActress+starSign + " " +
//                                                imdbId,
//                                        Toast
//                                                .LENGTH_SHORT)
//                                        .show();
//
//                                personBirthName.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
//                                        .addFontAwesomeIcon(FontAwesome.FA_GRADUATION_CAP)
//                                        .addText("  " + birthName).build());
//                                if (actorActress.compareToIgnoreCase("Actor") == 0) {
//                                    personActorActress.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
//                                            .addFontAwesomeIcon(FontAwesome.FA_MALE)
//                                            .addText("  " + actorActress).build());
//                                } else {
//                                    personActorActress.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
//                                            .addFontAwesomeIcon(FontAwesome.FA_FEMALE)
//                                            .addText("  " + actorActress).build());
//                                }
//
//                                personHeight.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
//                                        .addText(height).build());
//                                personBirthDay.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
//                                        .addText(birthday).build());
//                                personBirthPlace.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
//                                        .addText(birthPlace).build());
//                                personBio.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
//                                        .addText(bio).build());
//
//                                CollapsingToolbarLayout collapsingToolbarLayout;
//                                collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
//                                collapsingToolbarLayout.setTitle(name);
//
//                                collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style
//                                        .CollapsedAppBar);
//                                collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style
//                                        .ExpandedAppBarPlus1);
//                                collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style
//                                        .CollapsedAppBarPlus1);
//
//                                personStarsign.setBootstrapText(new BootstrapText.Builder(PersonDetails.this)
//                                        .addText(starSign).build());
//
//
//                            } catch (Exception e) {
//
//                            }
//
//
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//
//                        }
//                    });
//
//            requestQueue.add(request1);


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
