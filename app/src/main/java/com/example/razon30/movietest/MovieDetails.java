package com.example.razon30.movietest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mrengineer13.snackbar.SnackBar;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import dmax.dialog.SpotsDialog;

public class MovieDetails extends AppCompatActivity {

    //cast and crew
    public ArrayList<Movie> cast_and_crew = new ArrayList<Movie>();
    //similar
    public ArrayList<Movie> similar_list = new ArrayList<Movie>();
    //reviews
    public ArrayList<Movie> reviews = new ArrayList<Movie>();
    //for moreImage
    ArrayList<String> more_image_array = new ArrayList<String>();

    //links for movie details
    long id;
    String urlPreId = "http://api.themoviedb.org/3/movie/";
    String urlLaterId = "?api_key=f246d5e5105e9934d3cd4c4c181d618d";
    String image_url = "http://image.tmdb.org/t/p/w500";
    String vediopost = "/videos?api_key=f246d5e5105e9934d3cd4c4c181d618d";
    String cast_post = "/credits?api_key=f246d5e5105e9934d3cd4c4c181d618d";
    String image_post = "/images?api_key=f246d5e5105e9934d3cd4c4c181d618d";
    String similar_post = "/similar?api_key=f246d5e5105e9934d3cd4c4c181d618d";
    String reviews_post = "/reviews?api_key=f246d5e5105e9934d3cd4c4c181d618d";


    String urlMovieDetails = null;


    ImageView coverLayout;
    CircularImageView circularImageView;
    TextView    tvGenreDown, tvRevenue,
            tvImbdId, tvRating, tvBudget, tvRuntime, tvVotenumber, tvAwards, tvReleaseDate;

    BootstrapButton title_Bootstrap,releaseDateBootstrap,tvOverview,tvHomepage;
    RecyclerView listProduction;

    String trailer, homepage;

    ImageView image1, image2, image3;
    String backdrop1, backdrop2, backdrop3;

    RoundedImageView imageView;


    String w_id;
    String w_name;
    BootstrapButton watch, wish, see_more_image, reviewsButton;
  //  TextView tvGenre;

    String movie_name = "", description = "", image_link = "", movie_link = "";
    String shareUrl = "";
    String imdb_details_url_id;
    String about = " ";
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    MovieDB movieDB;


    //recyclerview for similar movie
    private RecyclerView recyclerViewSimilarMovie;
    private AdapterRecycler adapterRecylclerSimilarMovie;
    ArrayList<Movie> popularSimilarMovie;

    //recyclerview for Cast and Crew
    private RecyclerView recyclerViewCastandCrew;
    private AdapterRecycler adapterRecylclerCastandCrew;
    ArrayList<Movie> popularCastandCrew;
    AdapterProductionList adapterProductionList;


    //for loading
    View view1;
    AlertDialog.Builder builderAlertDialog;
    AlertDialog ad;

    AVLoadingIndicatorView avLoadingIndicatorViewmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        view1 = getLayoutInflater().inflate(R.layout.dialogue_element, null);
        avLoadingIndicatorViewmain = getavLoadingIndicator(view1);
        builderAlertDialog = new AlertDialog.Builder(this);
        builderAlertDialog.setView(view1);

        movieDB = new MovieDB(MovieDetails.this);

        workingOnFAB();

        final Intent intent = getIntent();
        id = Long.parseLong(intent.getStringExtra("tv"));
        String shareUrl_demo = intent.getStringExtra("url");

        if (shareUrl_demo != null && shareUrl_demo.length() != 0) {
            shareUrl = shareUrl_demo;
        }


        w_id = String.valueOf(id);

        initializing_contents();

        YoYo.with(Techniques.BounceInDown)
                .duration(1000)
                .playOn(imageView);

        boolean bool1 = movieDB.checkWatch(w_id);
        if (bool1) {

            watch.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
            watch.setBootstrapText(new BootstrapText.Builder(this).addText("Seen Movie ")
                    .addFontAwesomeIcon(FontAwesome.FA_THUMBS_O_DOWN).build() );

        }
        boolean bool2 = movieDB.checkWish(w_id);
        if (bool2) {

            wish.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
            wish.setBootstrapText(new BootstrapText.Builder(this).addText("Wished Movie ")
                    .addFontAwesomeIcon(FontAwesome.FA_HEARTBEAT).build());

        }


        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getmRequestQueue();
        MyAsyncTask task = new MyAsyncTask(MovieDetails.this);
        task.execute();

        worksOnWishAndWatch(w_id);
        worksOncolor();
        worksOnNetwork();


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

    private void initializing_contents() {



        coverLayout = (ImageView) findViewById(R.id.cover);
        circularImageView = (CircularImageView) findViewById(R.id.play_trailer);
        imageView = (RoundedImageView) findViewById(R.id.postar_image_detail);
       // tvGenre = (TextView) findViewById(R.id.genre_details);
        tvOverview = (BootstrapButton) findViewById(R.id.overview_details);
        tvRuntime = (TextView) findViewById(R.id.runtime_details);
        tvHomepage = (BootstrapButton) findViewById(R.id.homepage_details);
       // tvProduction = (TextView) findViewById(R.id.production_details);
        listProduction = (RecyclerView) findViewById(R.id.listproduction);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetails.this,
                LinearLayoutManager
                        .VERTICAL, false);
        listProduction.setLayoutManager(layoutManager);
        tvGenreDown = (TextView) findViewById(R.id.genre_down_details);
        tvRevenue = (TextView) findViewById(R.id.revenue_details);
        tvBudget = (TextView) findViewById(R.id.budget_details);
       // tvTagLine = (BootstrapButton) findViewById(R.id.tagline_details);
        tvImbdId = (TextView) findViewById(R.id.imdb_details);
        tvRating = (TextView) findViewById(R.id.tv_audience);
        tvVotenumber = (TextView) findViewById(R.id.vote_number);
        tvReleaseDate = (TextView) findViewById(R.id.release_date);
        tvAwards = (TextView) findViewById(R.id.award_details);


        see_more_image = (BootstrapButton) findViewById(R.id.more_image);
        reviewsButton = (BootstrapButton) findViewById(R.id.review_details);
        watch = (BootstrapButton) findViewById(R.id.watch);
        wish = (BootstrapButton) findViewById(R.id.wish);

        image1 = (ImageView) findViewById(R.id.movie_details_image1);
        image2 = (ImageView) findViewById(R.id.movie_details_image2);
        image3 = (ImageView) findViewById(R.id.movie_details_image3);

        title_Bootstrap = (BootstrapButton) findViewById(R.id.tvtitle);
        releaseDateBootstrap = (BootstrapButton) findViewById(R.id.tvreleasedate);

    }

    private void workingOnFAB() {
        FloatingActionMenu menuMultipleActions = (FloatingActionMenu) findViewById(R.id
                .multiple_actions);

        FloatingActionButton FABShare = (FloatingActionButton) findViewById(R.id.action_a);
      //  final RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);

        FABShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Watching " + movie_name + "\n" + movie_link);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Watching " + movie_name));

            }
        });

        FloatingActionButton FABRefresh = (FloatingActionButton) findViewById(R.id.action_b);
        FABRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MovieDetails.this, MovieDetails.class);
                intent.putExtra("tv", w_id);
                intent.putExtra("url", movie_link);
                startActivity(intent);

            }
        });

        FloatingActionButton FABSetting = (FloatingActionButton) findViewById(R.id.action_c);
        FABSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MovieDetails.this,Setting.class));

            }
        });

    }





    private void worksOnWishAndWatch(final String w_id) {



        final String movie_trailer = "https://www.youtube.com/watch?v=" + trailer;

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MovieDetails.this, PlayingYoutube.class).putExtra
                        ("trailer", trailer));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        worksOnCastAndCrew(w_id);

        tvHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homepage != null && homepage != "" && homepage.length() != 0) {
                    Intent i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(homepage));
                    startActivity(i);
                } else {
                    new SnackBar.Builder(MovieDetails.this)
                            .withMessage("No Link is Available") // OR
                            .withTextColorId(R.color.accentColor)
                            .withBackgroundColorId(R.color.primaryColor)
                            .withTypeFace(Typeface.SANS_SERIF)
                            .show();
                }
            }
        });

        see_more_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (more_image_array == null || more_image_array.size() == 0) {
                    new SnackBar.Builder(MovieDetails.this)
                            .withMessage("No Image Found or Network Error") // OR
                            .withTextColorId(R.color.primaryColor)
                            .withBackgroundColorId(R.color.accentColor)
                            .withTypeFace(Typeface.SANS_SERIF)
                            .show();
                    return;
                } else {


                    Intent intent = new Intent(MovieDetails.this, ImageGalleryActivity.class);
                    intent.putStringArrayListExtra("images", more_image_array);
                    intent.putExtra("palette_color_type", PaletteColorType.VIBRANT);
                    startActivity(intent);

                }

            }
        });


        worksOnSimilarMovies(w_id);


        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view1 = getLayoutInflater().inflate(R.layout.review, null);
                ListView oddView = (ListView) view1.findViewById(R.id.review_list);
                Review_Adapter review_adapter = new Review_Adapter(reviews, MovieDetails.this);
                oddView.setAdapter(review_adapter);

                AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(
                        MovieDetails.this);

                if (reviews == null || reviews.size() == 0) {
                    new SnackBar.Builder(MovieDetails.this)
                            .withMessage("No Reviews Found") // OR
                            .withTextColorId(R.color.accentColor)
                            .withBackgroundColorId(R.color.primaryColor)
                            .withTypeFace(Typeface.SANS_SERIF)
                            .show();
                    return;
                } else {

                    builderAlertDialog
                            .setView(view1)
                            .show();

                }

            }
        });

        final View view = getLayoutInflater().inflate(R.layout.aboutshare, null);
        final TextView remove = (TextView) view.findViewById(R.id.remove);
        final ShareButton shareButton = (ShareButton) view.findViewById(R.id.okShare);
        final TextView tag = (TextView) view.findViewById(R.id.fbAbout);

        final ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(shareUrl))
                .setContentTitle("Watching")
                .setContentDescription("Amazing")
                .build();



        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareUrl == null || shareUrl.length() == 0) {

                    new SnackBar.Builder(MovieDetails.this)
                            .withMessage("Sorry,No link is Available to share But Added to Your " +
                                    "Watch List") // OR
                            .withTextColorId(R.color.accentColor)
                            .withBackgroundColorId(R.color.primaryColor)
                            .show();
                }

                boolean bool = movieDB.checkWatch(w_id);
               // final boolean boo2 = movieDB.checkWish(w_id);
                if (bool) {


                    tag.setText("Already in Watch List");
                    remove.setVisibility(View.VISIBLE);
                    final DialogPlus dialog = DialogPlus.newDialog(MovieDetails.this)
                                                        .setContentHolder(new ViewHolder(view))
                                                        .setExpanded(true)  // This will enable the expand feature, (similar to
                                                                // android L share dialog)
                                                        .setMargin(100, 5, 100, 5)
                                                        .setGravity(Gravity.CENTER)
                                                        .setCancelable(true)
                                                        .setInAnimation(R.anim.slide_in_bottom)
                                                        .setOutAnimation(R.anim.slide_out_bottom)
                                                        .create();
                    dialog.show();
                    shareButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareButton.setShareContent(content);
                        }
                    });

                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            watch.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                            watch.setBootstrapText(new BootstrapText.Builder(MovieDetails.this)
                                    .addText("Add to Watch")
                                    .addFontAwesomeIcon(FontAwesome.FA_THUMBS_O_UP).build());
                            movieDB.deleteWatch(w_id);
                            dialog.dismiss();
                            new SnackBar.Builder(MovieDetails.this)
                                    .withMessage("Removed Successfully") // OR
                                    .withTextColorId(R.color.accentColor)
                                    .withBackgroundColorId(R.color.primaryColor)
                                    .show();
                        }
                    });


                } else {
                    Movie movie = new Movie(w_name, w_id);
                    long fact = movieDB.insertWatch(movie);
                    if (fact != -1) {
                        watch.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                        watch.setBootstrapText(new BootstrapText.Builder(MovieDetails.this).addText
                                ("Seen Movie")
                                .addFontAwesomeIcon(FontAwesome.FA_THUMBS_O_DOWN).build());
                        remove.setVisibility(View.GONE);

                        // watch.setShareContent(content);
                        tag.setText("Share On Facebook?");

                        final DialogPlus dialog = DialogPlus.newDialog(MovieDetails.this)

                                .setContentHolder(new ViewHolder(view))
                                .setExpanded(true)  // This will enable the expand feature, (similar to
                                        // android L share dialog)
                                .setMargin(100, 5, 100, 5)
                                .setGravity(Gravity.CENTER)
                                .setCancelable(true)
                                .setInAnimation(R.anim.slide_in_bottom)
                                .setOutAnimation(R.anim.slide_out_bottom)
                                .create();
                        dialog.show();
                        shareButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareButton.setShareContent(content);
                            }
                        });


                    }
                }
            }
        });


        wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean boo2 = movieDB.checkWatch(w_id);
                boolean bool = movieDB.checkWish(w_id);
                if (bool) {

                    new SnackBar.Builder(MovieDetails.this)
                            .withMessage("Already in Wish List") // OR
                            .withActionMessage("Remove") // OR
                            .withTextColorId(R.color.accentColor)
                            .withBackgroundColorId(R.color.primaryColor)
                            .withOnClickListener(new SnackBar.OnMessageClickListener() {
                                @Override
                                public void onMessageClick(Parcelable parcelable) {
                                    movieDB.deleteWish(w_id);
                                    wish.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
                                    wish.setBootstrapText(new BootstrapText.Builder(MovieDetails
                                            .this).addText
                                            ("Add To Wish")
                                            .addFontAwesomeIcon(FontAwesome.FA_HEART).build());
                                }
                            })
                            .show();


                } else {
                    Movie movie = new Movie(w_name, w_id, "null", "null");
                    long fact = movieDB.insertWish(movie);
                    if (fact != -1) {
                        wish.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                        wish.setBootstrapText(new BootstrapText.Builder(MovieDetails
                                .this).addText
                                ("Wished Movie")
                                .addFontAwesomeIcon(FontAwesome.FA_HEARTBEAT).build());
                        new SnackBar.Builder(MovieDetails.this)
                                .withMessage("Added to Wish List") // OR
                                .withTextColorId(R.color.accentColor)
                                .withBackgroundColorId(R.color.primaryColor)
                                .withActionMessage("Set Alarm")
                                .withOnClickListener(new SnackBar.OnMessageClickListener() {
                                    @Override
                                    public void onMessageClick(Parcelable token) {

                                        makeAlarm(w_id, w_name);

                                    }
                                })
                                .show();

                    }
                }
            }
        });


    }

    private void makeAlarm(String w_id, String w_name) {

        final String movie_ID = w_id;
        final String movie_NAME = w_name;

        View view1 = getLayoutInflater().inflate(R.layout.close_alarm,
                null);

        final DatePicker datePicker = (DatePicker) view1.findViewById(R.id.datePicker);
        final TimePicker timePicker = (TimePicker) view1.findViewById(R.id.timePicker);
        com.example.razon30.movietest.MyTextViewOne ok = (MyTextViewOne) view1.findViewById(R.id.setAlarm);
        final com.example.razon30.movietest.MyTextViewOne dateTime = (MyTextViewOne) view1.findViewById(R.id.timeDate);

        if (datePicker.getVisibility() == View.GONE) {

            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            dateTime.setText(date);

        }

        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datePicker.getVisibility() == View.GONE) {
                    timePicker.setVisibility(View.GONE);
                    datePicker.setVisibility(View.VISIBLE);


                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
                    Date currentLocalTime = cal.getTime();
                    DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
                    date.setTimeZone(TimeZone.getTimeZone("GMT+6:00"));
                    String localTime = date.format(currentLocalTime);
                    dateTime.setText(localTime);

                } else if (timePicker.getVisibility() == View.GONE) {
                    timePicker.setVisibility(View.VISIBLE);
                    datePicker.setVisibility(View.GONE);
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    dateTime.setText(date);

                }
            }
        });

        final AlertDialog builder = new AlertDialog.Builder(MovieDetails.this).create();
        builder.setView(view1);
        builder.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDB.deleteWish(movie_ID);

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                String monthStr = chooseMonth(month);
                String date = day + " " + monthStr + " " + year;

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                String time = setTime(hour, minute);

                Movie movie = new Movie(movie_name, movie_ID, time, date);
                movieDB.insertWish(movie);
                builder.cancel();
                new SnackBar.Builder(MovieDetails.this)
                        .withMessage("Alarm is set") // OR
                        .withTextColorId(R.color.accentColor)
                        .withBackgroundColorId(R.color.primaryColor)
                        .show();
            }
        });


    }

    private String setTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        return aTime;

    }

    private String chooseMonth(int month) {

        if (month == 01 || month == 1) {
            return "Jan";
        } else if (month == 02 || month == 2) {
            return "Feb";
        } else if (month == 03 || month == 3) {
            return "March";
        } else if (month == 04 || month == 4) {
            return "April";
        } else if (month == 05 || month == 5) {
            return "May";
        } else if (month == 06 || month == 6) {
            return "June";
        } else if (month == 07 || month == 7) {
            return "July";
        } else if (month == 8) {
            return "Aug";
        } else if (month == 9) {
            return "Sep";
        } else if (month == 10) {
            return "Oct";
        } else if (month == 11) {
            return "Nov";
        } else if (month == 12) {
            return "Dec";
        }
        return "null";

    }

    private void worksOnSimilarMovies(String w_id) {

        popularSimilarMovie = new ArrayList<Movie>();
        adapterRecylclerSimilarMovie = new AdapterRecycler(MovieDetails.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetails.this,
                LinearLayoutManager
                .HORIZONTAL, false);

        recyclerViewSimilarMovie = (RecyclerView)findViewById(R.id.movie_details_similar_movies);
        recyclerViewSimilarMovie.setLayoutManager(layoutManager);
        recyclerViewSimilarMovie.setAdapter(adapterRecylclerSimilarMovie);

        new TaskLoadSimilarMovie(w_id).execute();
        adapterRecylclerSimilarMovie.setMovies(popularSimilarMovie);
        adapterRecylclerSimilarMovie.notifyDataSetChanged();

        recyclerViewSimilarMovie.addOnItemTouchListener(new RecyclerTOuchListener(MovieDetails.this
                , recyclerViewSimilarMovie, new
                ClickListener() {
                    @Override
                    public void onCLick(View v, int position) {

                        Movie movie = popularSimilarMovie.get(position);
                        String id = String.valueOf(movie.getMovie_id());
                        String image = image_url + movie.getMovie_image_link();

                        Intent intent = new Intent(MovieDetails.this, MovieDetails.class);

                        intent.putExtra("tv", id);
                        intent.putExtra("url", image);
                        startActivity(intent);


                    }

                    @Override
                    public void onLongClick(View v, int position) {

                    }
                }));



    }

    private void worksOnCastAndCrew(String w_id) {

        popularCastandCrew = new ArrayList<Movie>();
        adapterRecylclerCastandCrew = new AdapterRecycler(MovieDetails.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetails.this,
                LinearLayoutManager
                        .HORIZONTAL, false);

        recyclerViewCastandCrew = (RecyclerView)findViewById(R.id.movie_details_cast_and_crew);
        recyclerViewCastandCrew.setLayoutManager(layoutManager);
        recyclerViewCastandCrew.setAdapter(adapterRecylclerCastandCrew);

        new TaskLoadCastandCrew(w_id).execute();
        adapterRecylclerCastandCrew.setMovies(popularCastandCrew);
        adapterRecylclerCastandCrew.notifyDataSetChanged();

        recyclerViewCastandCrew.addOnItemTouchListener(new RecyclerTOuchListener(MovieDetails.this
                , recyclerViewCastandCrew, new
                ClickListener() {
                    @Override
                    public void onCLick(View v, int position) {

                        Movie movie = popularCastandCrew.get(position);
                        String id = String.valueOf(movie.getMovie_id());
                        String image = image_url + movie.getMovie_image_link();

  //                      Toast.makeText(MovieDetails.this,id,Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MovieDetails.this, PersonDetails.class);

                        intent.putExtra("tv", id);

                        startActivity(intent);


                    }

                    @Override
                    public void onLongClick(View v, int position) {

                    }
                }));




    }

    public class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {

        Context context;
        public MyAsyncTask(Context context) {

            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ad = builderAlertDialog.create();
            ad.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            LoadingKajKarbar();

            try {
                Thread.sleep(4000);
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid) {
                ad.cancel();

            }


        }
    }

    private void LoadingKajKarbar() {

        //fetching primary data
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlPreId + id + urlLaterId, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        if (jsonObject == null || jsonObject.length() == 0) {
                            Toast.makeText(MovieDetails.this, "Problem to load", Toast.LENGTH_LONG)
                                    .show();

                        }

                        try {

                            String backdrop_path = jsonObject.getString("backdrop_path");

                            if (backdrop_path != "" && backdrop_path != null) {

                                Picasso.with(MovieDetails.this).load(image_url + backdrop_path).into
                                        (coverLayout);

                            }
                            image_link = image_url + backdrop_path;


                            JSONArray genr = jsonObject.getJSONArray("genres");
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < genr.length(); i++) {
                                JSONObject g = genr.getJSONObject(i);
                                sb.append(g.getString("name"));
                                if (i < genr.length() - 1) {
                                    sb.append(",");
                                }
                            }
                            //tvGenre.setText(sb);
                            tvGenreDown.setText(sb);

                            description = String.valueOf(sb);

                            homepage = "";
                            homepage = jsonObject.getString("homepage");
                            if (homepage != "" && homepage != null) {

                                tvHomepage.setVisibility(View.VISIBLE);
                                tvHomepage.setBootstrapText(new BootstrapText.Builder(MovieDetails.this)
                                        .addFontAwesomeIcon(FontAwesome.FA_DATABASE)
                                        .addText("    Homepage").build());

                            }

                            movie_link = homepage;

                            String imdb_id = "";
                            imdb_details_url_id = jsonObject.getString("imdb_id");
                            if (imdb_details_url_id != "" && imdb_details_url_id != null) {
                                tvImbdId.setText(imdb_details_url_id);

                            } else {
                                tvImbdId.setText("NA");
                            }

                            worksOnRating(imdb_details_url_id);
                            String runtime = "";
                            runtime = jsonObject.getString("runtime");
                            if (runtime != null) {
                                tvRuntime.setText(runtime + " minute");
                            } else {
                                tvRuntime.setText("Sorry,unknown");
                            }
                            String budget = "";
                            budget = jsonObject.getString("budget");

                            if (budget != null && budget.length() != 0 && budget != "" &&
                                    budget != "0") {
                                tvBudget.setText("$" + budget);
                            } else {
                                tvBudget.setText("Budget Unknown");
                            }

                            String overview = "";
                            overview = jsonObject.getString("overview");
                            if (overview != "" && overview != null) {
                                tvOverview.setBootstrapText(new BootstrapText.Builder(MovieDetails.this)
                                        .addText("           " + overview).build());

                            } else {

                            }


                            String poster_path = jsonObject.getString("poster_path");
                            if (poster_path != "" && poster_path != null) {

                                Picasso.with(MovieDetails.this).load(image_url + poster_path).into
                                        (imageView);

                            } else {
                                Picasso.with(MovieDetails.this).load(R.drawable.nav_back).into
                                        (imageView);
                            }
                            JSONArray produc = jsonObject.getJSONArray("production_companies");
                            StringBuilder production = new StringBuilder();

                            ArrayList<String> proList = new ArrayList<String>();


                            for (int i = 0; i < produc.length(); i++) {

                                JSONObject p = produc.getJSONObject(i);
//                                production.append(p.getString("name"));
//                                if (i < produc.length() - 1) {
//                                    production.append("\n");
//                                }
                                proList.add(p.getString("name").toString());

                            }

                           // tvProduction.setText(production);
                            adapterProductionList = new AdapterProductionList(proList,
                                    MovieDetails.this);

                            listProduction.setVisibility(View.VISIBLE);
                            listProduction.setAdapter(adapterProductionList);

                            String release_Date = jsonObject.getString("release_date");
                            String[] date = release_Date.split("-");
                            String title = jsonObject.getString("title");
                            // tvTitle.setText(title + "  (" + date[0] + ")");
                            w_name = title + "  (" + date[0] + ")";
                            //tvTitle.setText(w_name);
                            movie_name = title + "  (" + date[0] + ")";

                            tvReleaseDate.setText(release_Date);

                            CollapsingToolbarLayout collapsingToolbarLayout;
                            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
                            collapsingToolbarLayout.setTitle(movie_name);

                            collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style
                                    .CollapsedAppBar);
                            collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style
                                    .ExpandedAppBarPlus1);
                            collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style
                                    .CollapsedAppBarPlus1);



                            String revenue = "";
                            revenue = jsonObject.getString("revenue");

                            if (revenue != null && revenue.length() != 0 && revenue != "" &&
                                    revenue != "0") {
                                tvRevenue.setText("$" + revenue);
                            } else if (revenue == "0") {
                                tvRevenue.setText("Revenue Unknown");
                            } else {

                                tvRevenue.setText("Still Running, NO total Revenue");
                            }

                            String tagLine = "";

                            tagLine = jsonObject.getString("tagline");

                            title_Bootstrap.setBootstrapText(new BootstrapText.Builder(MovieDetails.this)
                                    .addFontAwesomeIcon(FontAwesome.FA_FILE_MOVIE_O)
                                    .addText("   " + tagLine).build());

                            releaseDateBootstrap.setBootstrapText(new BootstrapText.Builder(MovieDetails.this)
                                    .addFontAwesomeIcon(FontAwesome.FA_FILE_ARCHIVE_O)
                                    .addText("   " + sb.toString()).build());

//                            if (tagLine != null && tagLine.length() != 0 && tagLine != "") {
//                                tvTagLine.setVisibility(View.VISIBLE);
//                                tvTagLine.setBootstrapText(new BootstrapText.Builder(MovieDetails.this)
//                                        .addFontAwesomeIcon(FontAwesome.FA_FILE_ARCHIVE_O)
//                                        .addText("   " + tagLine).build());
//                            } else {
//                                tvTagLine.setVisibility(View.GONE);
//                            }


                        } catch (Exception e) {


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        requestQueue.add(request);


        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET,
                urlPreId + id + vediopost, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        if (jsonObject == null || jsonObject.length() == 0) {
                            Toast.makeText(MovieDetails.this, "Problem to load", Toast.LENGTH_LONG)
                                    .show();

                        }

                        try {

                            JSONArray ved = jsonObject.getJSONArray("results");

                            JSONObject v = ved.getJSONObject(0);
                            trailer = v.getString("key");

                        } catch (Exception e) {
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });

        requestQueue.add(request1);


        JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET,
                urlPreId + id + image_post, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        if (jsonObject == null || jsonObject.length() == 0) {
                            new SnackBar.Builder(MovieDetails.this)
                                    .withMessage("Problem to Load") // OR
                                    .withTextColorId(R.color.primaryColor)
                                    .withBackgroundColorId(R.color.accentColor)
                                    .withTypeFace(Typeface.SANS_SERIF)
                                    .show();
                        }

                        try {


                            JSONArray image_slide = jsonObject.getJSONArray("backdrops");

                            String[] arrayList = new String[image_slide.length()];

                            for (int i = 0; i < image_slide.length(); i++) {

                                JSONObject obj = image_slide.getJSONObject(i);
                                String im = image_url + obj.getString("file_path");
                                more_image_array.add(im);

                            }


                            JSONArray image = jsonObject.getJSONArray("posters");

                            String profile1 = image.getJSONObject(1).getString("file_path");
                            if (profile1 != null && profile1.length() != 0) {
                                image1.setVisibility(View.VISIBLE);
                                Picasso.with(MovieDetails.this).load(image_url + profile1).into
                                        (image1);
                            } else {

                                image1.setVisibility(View.GONE);
                            }

                            String profile2 = image.getJSONObject(2).getString("file_path");
                            if (profile2 != null && profile2.length() != 0) {
                                image2.setVisibility(View.VISIBLE);
                                Picasso.with(MovieDetails.this).load(image_url + profile2).into
                                        (image2);
                            } else {

                                image2.setVisibility(View.GONE);
                            }

                            String profile3 = image.getJSONObject(0).getString("file_path");
                            if (profile3 != null && profile3.length() != 0) {
                                image3.setVisibility(View.VISIBLE);
                                Picasso.with(MovieDetails.this).load(image_url + profile3).into
                                        (image3);
                            } else {

                                image3.setVisibility(View.GONE);
                            }

                            for (int i = 0; i < image.length(); i++) {

                                JSONObject obj = image.getJSONObject(i);
                                String im = image_url + obj.getString("file_path");
                                more_image_array.add(im);

                            }

                        } catch (Exception e) {

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        requestQueue.add(request3);

        JsonObjectRequest request5 = new JsonObjectRequest(Request.Method.GET,
                urlPreId + id + reviews_post, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        if (jsonObject == null || jsonObject.length() == 0) {
                            new SnackBar.Builder(MovieDetails.this)
                                    .withMessage("Problem to Load") // OR
                                    .withTextColorId(R.color.primaryColor)
                                    .withBackgroundColorId(R.color.accentColor)
                                    .withTypeFace(Typeface.SANS_SERIF)
                                    .show();
                        }

                        try {


                            JSONArray rvw = jsonObject.getJSONArray("results");

                            for (int i = 0; i < rvw.length(); i++) {

                                JSONObject current = rvw.getJSONObject(i);
                                String author = current.getString("author");
                                String text = current.getString("content");

                                Movie movie = new Movie(author, text);
                                reviews.add(movie);

                            }


                        } catch (Exception e) {
                            Toast.makeText(MovieDetails.this, e.toString(), Toast.LENGTH_LONG)
                                    .show();


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MovieDetails.this, volleyError.toString(), Toast.LENGTH_LONG)
                                .show();

                    }
                });

        requestQueue.add(request5);


    }

    private void worksOnRating(String imdb_details_url_id) {


        JsonObjectRequest request_imdb = new JsonObjectRequest(Request
                .Method.GET, "http://www.omdbapi.com/?i=" + imdb_details_url_id + "&plot=full&r=json", null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        if (jsonObject == null || jsonObject.length() == 0) {
                            new SnackBar.Builder(MovieDetails.this)
                                    .withMessage("Problem to Load") // OR
                                    .withTextColorId(R.color.primaryColor)
                                    .withBackgroundColorId(R.color.accentColor)
                                    .withTypeFace(Typeface.SANS_SERIF)
                                    .show();

                        }

                        try {

                            String vote = "";
                            vote = jsonObject.getString("imdbVotes");
                            if (vote != null) {
                                if (vote.length() > 5) {
                                    tvVotenumber.setTextSize(30);
                                }
                                tvVotenumber.setText(vote);
                            } else {
                                tvVotenumber.setText("");
                            }
                            String audience_score = "-1";

                            audience_score = jsonObject.getString("imdbRating");

                            if (audience_score == "-1") {
                                tvRating.setText(audience_score + "");

                            } else {
                                if (audience_score.length() > 1) {
                                    tvRating.setTextSize(30);
                                }
                                tvRating.setText(audience_score);

                            }

                            String award = jsonObject.getString("Awards");
                            tvAwards.setText(award);


                        } catch (Exception e) {


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        requestQueue.add(request_imdb);

    }

    private void worksOnNetwork() {

        if (!isNetworkAvailable()) {

            AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(
                    MovieDetails.this);

            builderAlertDialog.setTitle("Connection Failed")
                    .setMessage("Try for connecting?")
                    .setIcon(R.drawable.ic_action_warning)
                    .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));

                        }
                    })
                    .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }

    }

    public class TaskLoadSimilarMovie extends AsyncTask<Void, Void, ArrayList<Movie>> {

        String w_id;
        String urlSimilarMovies;
        public TaskLoadSimilarMovie(String w_id) {
            this.w_id = w_id;
            urlSimilarMovies = urlPreId+w_id+similar_post;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            volleySingleton = VolleySingleton.getsInstance();
            requestQueue = volleySingleton.getmRequestQueue();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            popularSimilarMovie = MovieUtils.loadSimilarMovie(requestQueue, urlSimilarMovies);
            return popularSimilarMovie;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> aVoid) {
            super.onPostExecute(aVoid);

            recyclerViewSimilarMovie.setAdapter(adapterRecylclerSimilarMovie);
            adapterRecylclerSimilarMovie.setMovies(popularSimilarMovie);
            adapterRecylclerSimilarMovie.notifyDataSetChanged();

        }
    }

    public class TaskLoadCastandCrew extends AsyncTask<Void, Void, ArrayList<Movie>> {

        String w_id;
        String urlCastandCrew;
        public TaskLoadCastandCrew(String w_id) {
            this.w_id = w_id;
            urlCastandCrew = urlPreId+w_id+cast_post;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            volleySingleton = VolleySingleton.getsInstance();
            requestQueue = volleySingleton.getmRequestQueue();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {
            popularCastandCrew = MovieUtils.loadCastandCrew(requestQueue, urlCastandCrew);
            return popularCastandCrew;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> aVoid) {
            super.onPostExecute(aVoid);

            recyclerViewCastandCrew.setAdapter(adapterRecylclerCastandCrew);
            adapterRecylclerCastandCrew.setMovies(popularCastandCrew);
            adapterRecylclerCastandCrew.notifyDataSetChanged();

        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void worksOncolor() {

        Random random = new Random();
        int i = random.nextInt(11 - 1 + 1) + 1;

        if (i == 1) {

         //   tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_one_toolbar));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_one_navigationBar));
                getWindow().setNavigationBarColor(getResources().getColor(R.color.Style_one_navigationBar));
            }

        }
        if (i == 2) {

           // tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_two_toolbar));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color
                        .Style_two_navigationBar));
                getWindow().setNavigationBarColor(getResources().getColor(R.color
                        .Style_two_navigationBar));
            }
        }
        if (i == 3) {

//            tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_three_toolbar));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color
                        .Style_three_navigationBar));
                getWindow().setNavigationBarColor(getResources().getColor(R.color
                        .Style_three_navigationBar));
            }
        }
        if (i == 4) {

          //  tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_four_toolbar));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color
                        .Style_four_navigationBar));
                getWindow().setNavigationBarColor(getResources().getColor(R.color
                        .Style_four_navigationBar));
            }
        }
        if (i == 5) {

          //  tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_five_toolbar));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color
                        .Style_five_navigationBar));
                getWindow().setNavigationBarColor(getResources().getColor(R.color
                        .Style_five_navigationBar));
            }
        }
        if (i == 6) {

         //   tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_six_toolbar));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color
                        .Style_six_navigationBar));
                getWindow().setNavigationBarColor(getResources().getColor(R.color
                        .Style_six_navigationBar));
            }
        }
        if (i == 7) {

          //  tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_seven_toolbar));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color
                        .Style_seven_navigationBar));
                getWindow().setNavigationBarColor(getResources().getColor(R.color
                        .Style_seven_navigationBar));
            }
        }
        if (i == 8) {

          //  tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_eight_toolbar));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color
                        .Style_eight_navigationBar));
                getWindow().setNavigationBarColor(getResources().getColor(R.color
                        .Style_eight_navigationBar));
            }
        }
        if (i == 9) {

         //   tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_nine_toolbar));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color
                        .Style_nine_navigationBar));
                getWindow().setNavigationBarColor(getResources().getColor(R.color
                        .Style_nine_navigationBar));
            }
        }
        if (i == 10) {

          //  tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_ten_toolbar));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color
                        .Style_ten_navigationBar));
                getWindow().setNavigationBarColor(getResources().getColor(R.color
                        .Style_ten_navigationBar));
            }
        }
        if (i == 11) {

          //  tvGenre.setBackgroundColor(getResources().getColor(R.color.Style_eleven_view));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_eleven_view));
                getWindow().setNavigationBarColor(getResources().getColor(R.color.Style_eleven_view));
            }

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
