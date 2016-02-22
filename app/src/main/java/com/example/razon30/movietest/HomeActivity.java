package com.example.razon30.movietest;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.mrengineer13.snackbar.SnackBar;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.quinny898.library.persistentsearch.SearchBox;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //toolbar, drawer and fragments
    Toolbar toolbar;
    TabLayout tabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    //worksonheader
    //for drawer header
    ImageView drawer_cover_image;
    CircularImageView drawer_profile_image;
    TextView drawer_name, drawer_profile;
    String coverImage = "1", profileImage = "1", userName = "1", profileLink = "1";

    //works for faebook on drawerHeader
    CallbackManager callbackManager;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    LoginButton loginButton;
    SharedPreferences sharedPreferences;
    MovieDB movieDB;


    //on SEarch
    private SearchBox search;


    //for alarm job
    private static final int JOB_ID = 100;
   // JobScheduler jobScheduler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_nav_drawer);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        movieDB = new MovieDB(HomeActivity.this);

        initialization();
        worksOnToolBar();
        worksOnTabAndPager();
        worksOnDrawerAndNavigation();
        worksOnSearch();

        loginButton = (LoginButton) findViewById(R.id.btn_Facebook);

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(HomeActivity.this);

        //facebookLogin();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setReadPermissions("public_profile", "email", "user_friends");

                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();
                        displayMessage(profile);

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
            }
        });


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessTokenold, AccessToken
                    accessTokennew) {

                accessToken = accessTokennew;
                Profile profile = Profile.getCurrentProfile();


                displayMessage(profile);

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {

                displayMessage(profile1);

            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        worksOnColor();


        drawer_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileLink == null || profileLink == "1") {
                    new SnackBar.Builder(HomeActivity.this)
                            .withMessage("No Link Is Available") // OR
                            .withTextColorId(R.color.primaryColor)
                            .withBackgroundColorId(R.color.accentColor)
                            .withTypeFace(Typeface.SANS_SERIF)
                            .show();
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(profileLink));
                    startActivity(i);
                }
            }
        });


        if (!isNetworkAvailable()) {

            AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(
                    HomeActivity.this);

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

        String l = sharedPreferences.getString("l", "1");

        if (userName == "1") {

            if (drawer_name.getText().toString() == null || drawer_name.getText().toString() == "" ||
                    drawer_name.getText().toString().length() == 0) {

                drawer_name.setText(sharedPreferences.getString("name", ""));
                Picasso.with(HomeActivity.this).load(sharedPreferences.getString("cover", "https://www.google.com.bd/search?q=nice+background+image+for+android&biw=1366&bih=625&source=lnms&tbm=isch&sa=X&sqi=2&ved=0ahUKEwip59vZhOTKAhUMJI4KHUFHCgAQ_AUIBigB#imgdii=INxc_Hb2fal2fM%3A%3BINxc_Hb2fal2fM%3A%3BvR2WSrkOW4vI0M%3A&imgrc=INxc_Hb2fal2fM%3A"))
                        .resize(300, 180)
                        .into(drawer_cover_image);
                Picasso.with(HomeActivity.this).load(sharedPreferences.getString("propic", "https://www.google.com.bd/search?q=nice+background+image+for+android&biw=1366&bih=625&source=lnms&tbm=isch&sa=X&sqi=2&ved=0ahUKEwip59vZhOTKAhUMJI4KHUFHCgAQ_AUIBigB#imgdii=INxc_Hb2fal2fM%3A%3BINxc_Hb2fal2fM%3A%3BrlfVzRsm_AydsM%3A&imgrc=INxc_Hb2fal2fM%3A")).into
                        (drawer_profile_image);
                profileLink = sharedPreferences.getString("profile", "");


            }

        }


    }

    private void worksOnJob() {



    }

    private void worksOnSearch() {

        search = (SearchBox) findViewById(R.id.searchbox);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                MovieDB dbMovies = new MovieDB(HomeActivity.this);

                if (item.getItemId() == R.id.action_search) {
                    // openSearch();
                    startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                }


                return true;
            }
        });


    }

    @Override
    protected void onUserLeaveHint() {

        Movie movie = new Movie(coverImage, profileImage, userName,
                profileLink);

        movieDB.insertUser(movie);

        super.onUserLeaveHint();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void initialization() {

        drawer_cover_image = (ImageView) findViewById(R.id.fb_cover_image);
        drawer_profile_image = (CircularImageView) findViewById(R.id.fb_profile_image);
        drawer_name = (TextView) findViewById(R.id.fb_profile_name);
        drawer_profile = (TextView) findViewById(R.id.fb_profile);

    }

    private void worksOnToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void worksOnTabAndPager() {
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void worksOnDrawerAndNavigation() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void displayMessage(final Profile profile1) {

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Insert your code here
                        if (object != null) {

                            drawer_name.setText(object.toString());


                            // String name = null;
                            try {
                                userName = object.getString("name");

                                drawer_name.setText(userName);
                                profileLink = object.getString("link");
                                JSONObject cover_obj = object.getJSONObject("cover");
                                coverImage = cover_obj.getString("source");
                                Picasso.with(HomeActivity.this).load(coverImage).resize(300, 180)
                                        .into(drawer_cover_image);

                                JSONObject picture_obj = object.getJSONObject("picture");
                                JSONObject data_obj = picture_obj.getJSONObject("data");
                                profileImage = data_obj.getString("url");
                                Picasso.with(HomeActivity.this).load(profileImage).into
                                        (drawer_profile_image);

                                editor.putString("name", userName);
                                editor.putString("profile", profileLink);
                                editor.putString("propic", profileImage);
                                editor.putString("cover", coverImage);
                                editor.commit();
                                editor.apply();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(HomeActivity.this, "Log in To FaceBook", Toast
                                    .LENGTH_SHORT).show();
                        }


                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,link,cover,picture");
        request.setParameters(parameters);
        request.executeAsync();

        Movie movie = new Movie(coverImage, profileImage, drawer_name.getText().toString(),
                profileLink);

        movieDB.insertUser(movie);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDetachedFromWindow() {

        super.onDetachedFromWindow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            drawer.closeDrawer(GravityCompat.START);
            mViewPager.setCurrentItem(0);

        } else if (id == R.id.nav_boxoffice) {

            drawer.closeDrawer(GravityCompat.START);
            mViewPager.setCurrentItem(1);

        } else if (id == R.id.nav_upcoming) {

            drawer.closeDrawer(GravityCompat.START);
            mViewPager.setCurrentItem(2);

        } else if (id == R.id.nav_watchlist) {

            startActivity(new Intent(this, MovieDetails.class));

        } else if (id == R.id.nav_wishlist) {

        } else if (id == R.id.nav_share) {
            //  loginButton.performClick();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                default:
                    return new FragmentHome();
                case 1:
                    return new FragmentBoxOffice();
                case 2:
                    return new FragmentUpcoming();
            }
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Home";
                case 1:
                    return "Box Office";
                case 2:
                    return "Upcoming";
            }
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        //for facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        //   new TaskLoadFBCredentials().execute();
        displayMessage(profile);
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


    private void worksOnColor() {

//        Random random = new Random();
//        int i = random.nextInt(11 - 1 + 1) + 1;
//
//        if (i == 1) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_one_toolbar));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_one_tab));
//
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_one_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color.Style_one_navigationBar));
//            }
//        }
//        if (i == 2) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_two_toolbar));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_two_tab));
//
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_two_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color
//                        .Style_two_navigationBar));
//            }
//        }
//        if (i == 3) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_three_toolbar));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_three_tab));
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_three_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color
//                        .Style_three_navigationBar));
//            }
//        }
//        if (i == 4) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_four_toolbar));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_four_tab));
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_four_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color
//                        .Style_four_navigationBar));
//            }
//        }
//        if (i == 5) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_five_toolbar));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_five_tab));
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_five_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color
//                        .Style_five_navigationBar));
//            }
//        }
//        if (i == 6) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_six_toolbar));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_six_tab));
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_six_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color
//                        .Style_six_navigationBar));
//            }
//        }
//        if (i == 7) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_seven_toolbar));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_seven_tab));
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_seven_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color
//                        .Style_seven_navigationBar));
//            }
//        }
//        if (i == 8) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_eight_toolbar));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_eight_tab));
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_eight_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color
//                        .Style_eight_navigationBar));
//            }
//        }
//        if (i == 9) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_nine_toolbar));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_nine_tab));
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_nine_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color
//                        .Style_nine_navigationBar));
//            }
//        }
//        if (i == 10) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_ten_toolbar));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_ten_tab));
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_ten_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color
//                        .Style_ten_navigationBar));
//            }
//        }
//        if (i == 11) {
//
//            toolbar.setBackgroundColor(getResources().getColor(R.color.Style_eleven_view));
//            tabLayout.setBackgroundColor(getResources().getColor(R.color.Style_eleven_view));
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.Style_eleven_view));
//                getWindow().setNavigationBarColor(getResources().getColor(R.color.Style_eleven_view));
//            }
//        }

    }
}
