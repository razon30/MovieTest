package com.example.razon30.movietest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by razon30 on 01-02-16.
 */
public class MovieDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Database_Movie_";
    public static final int DB_VERSION = 1;

    //for user
    public static final String DB_TABLE_NAME_USER_DETAILS = "user_details";
    public static final String UserName = "username";
    public static final String UserProfilePicture = "userprofilepicture";
    public static final String UserCOverPicture = "usercoverpicture";
    public static final String UserProfileLink = "userprofilelink";

    public static final String MOVIES_TABLE_SQL_USER = "CREATE TABLE "
            + DB_TABLE_NAME_USER_DETAILS + " ( " + UserName
            + " TEXT, " + UserProfilePicture + " TEXT, " + UserCOverPicture + " TEXT, " + UserProfileLink + " " +
            "TEXT)";

    //watch and wish List
    public static final String DB_TABLE_NAME_WATCH = "Watch_Movies";
    public static final String DB_TABLE_NAME_WISH = "Wish_Movies";
    public static final String W_ID = "w_id";
    public static final String W_NAME = "w_name";
    public static final String W_TIME = "w_time";
    public static final String W_DATE = "w_date";

    public static final String MOVIES_TABLE_SQL_WATCH = "CREATE TABLE "
            + DB_TABLE_NAME_WATCH + " ( " + W_ID
            + " TEXT, " + W_DATE + " TEXT, " + W_TIME + " TEXT, " + W_NAME + " TEXT)";

    public static final String MOVIES_TABLE_SQL_WISH = "CREATE TABLE "
            + DB_TABLE_NAME_WISH + " ( " + W_ID
            + " TEXT, " + W_DATE + " TEXT, " + W_TIME + " TEXT, " + W_NAME + " TEXT)";


    public static final String DB_TABLE_NAME_SEARCH_KEY = "Search_key";
    public static final String Search_Key = "search_key";
    public static final String MOVIES_TABLE_SEARCH_KEY = "CREATE TABLE "
            + DB_TABLE_NAME_SEARCH_KEY + " ( " + Search_Key + " TEXT)";


    SQLiteDatabase db;
    Context context;

    public MovieDB(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MOVIES_TABLE_SQL_USER);
        Log.e("TABLE CREAT", MOVIES_TABLE_SQL_USER);

        db.execSQL(MOVIES_TABLE_SQL_WATCH);
        Log.e("TABLE CREAT", MOVIES_TABLE_SQL_WATCH);

        db.execSQL(MOVIES_TABLE_SQL_WISH);
        Log.e("TABLE CREAT", MOVIES_TABLE_SQL_WISH);

        db.execSQL(MOVIES_TABLE_SEARCH_KEY);
        Log.e("TABLE CREAT", MOVIES_TABLE_SEARCH_KEY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long insertUser(Movie movie) {
        // TODO Auto-generated method stub

        if (movie.getUserName().length() == 0 || movie.getUserName() == null || movie.getUserName().compareTo
                ("")
                == 0) {

            return -1;

        }

        deleteUser();

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserName, movie.getUserName());
        values.put(UserProfileLink, movie.getProfileImage());
        values.put(UserCOverPicture, movie.getCoverImage());
        values.put(UserProfileLink, movie.getProfileLink());
        long insert = db.insert(DB_TABLE_NAME_USER_DETAILS, null, values);
        db.close();
        return insert;

    }


    public Movie getUser() {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DB_TABLE_NAME_USER_DETAILS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Movie movie = null;

        if (cursor!=null && cursor.getCount()>0) {

            String name = cursor.getString(cursor
                    .getColumnIndex(UserName));
            String profilePic = cursor.getString(cursor
                    .getColumnIndex(UserProfilePicture));
            String coverPic = cursor.getString(cursor
                    .getColumnIndex(UserCOverPicture));
            String profileLink = cursor.getString(cursor
                    .getColumnIndex(UserProfileLink));

            movie = new Movie(coverPic, profilePic, name, profileLink);
        }else {

        }
        cursor.close();
        db.close();
        return movie;
    }


    public int deleteUser() {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getReadableDatabase();

        int dlt = db.delete(DB_TABLE_NAME_USER_DETAILS, null,null);

        db.close();
        return dlt;

    }




    public long insertWatch(Movie movie) {
        // TODO Auto-generated method stub

        if (movie.getMovie_id().length() == 0 || movie.getMovie_id() == null || movie.getMovie_id().compareTo("")
                == 0) {

            return -1;

        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(W_ID, movie.getMovie_id());
        // values.put(PASSWORD, data.getPassword());
        values.put(W_NAME, movie.getMovie_name());
        long insert = db.insert(DB_TABLE_NAME_WATCH, null, values);
        db.close();
        return insert;

    }

    public ArrayList<Movie> searchWatch() {
        // TODO Auto-generated method stub

        ArrayList<Movie> dataArrayList = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DB_TABLE_NAME_WATCH;
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                String name = cursor.getString(cursor
                        .getColumnIndex(W_ID));
                String link = cursor.getString(cursor
                        .getColumnIndex(W_NAME));

                Movie movie = new Movie(name, link);

                dataArrayList.add(movie);
                cursor.moveToNext();

            }

        }

        cursor.close();
        db.close();
        return dataArrayList;
    }

    public boolean checkWatch(String w_id) {
        boolean bool = false;
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DB_TABLE_NAME_WATCH + " WHERE " + w_id + "=" + W_ID;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {


            cursor.close();
            db.close();
            return true;
        } else {
            return false;
        }


        // return bool;
    }

    public int deleteWatch(String address) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getReadableDatabase();

        int dlt = db.delete(DB_TABLE_NAME_WATCH, W_ID + "=?", new String[]{""
                + address});

        db.close();
        return dlt;

    }

    public int deleteAllWatch() {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getReadableDatabase();

        int dlt = db.delete(DB_TABLE_NAME_WATCH, null, null);

        db.close();
        return dlt;

    }

    public long insertWish(Movie movie) {
        // TODO Auto-generated method stub

        if (movie.getProfileImage().length() == 0 || movie.getProfileImage() == null || movie.getProfileImage()
                .compareTo
                ("")
                == 0) {

            return -1;

        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(W_ID, movie.getProfileImage());
        // values.put(PASSWORD, data.getPassword());
        values.put(W_NAME, movie.getCoverImage());
        values.put(W_DATE, movie.getProfileLink());
        values.put(W_TIME, movie.getUserName());
        long insert = db.insert(DB_TABLE_NAME_WISH, null, values);
        db.close();
        return insert;

    }

    public ArrayList<Movie> searchWish() {
        // TODO Auto-generated method stub

        ArrayList<Movie> dataArrayList = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DB_TABLE_NAME_WISH;
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                String name = cursor.getString(cursor
                        .getColumnIndex(W_ID));
                String link = cursor.getString(cursor
                        .getColumnIndex(W_NAME));
                String time = cursor.getString(cursor.getColumnIndex(W_TIME));
                String date = cursor.getString(cursor.getColumnIndex(W_DATE));

                Movie movie = new Movie(name, link, time, date);

                dataArrayList.add(movie);
                cursor.moveToNext();

            }

        }

        cursor.close();
        db.close();
        return dataArrayList;
    }

    public boolean checkWish(String w_id) {
        boolean bool = false;
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DB_TABLE_NAME_WISH + " WHERE " + w_id + "=" + W_ID;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;

        } else {
            return false;
        }

    }


    public int deleteWish(String address) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getReadableDatabase();

        int dlt = db.delete(DB_TABLE_NAME_WISH, W_ID + "=?", new String[]{""
                + address});

        db.close();
        return dlt;

    }

    public int deleteAllWish() {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getReadableDatabase();
        int dlt = db.delete(DB_TABLE_NAME_WISH, null, null);

        db.close();
        return dlt;

    }





    public long insertSearchKey(String search_Key) {
        // TODO Auto-generated method stub

        if (search_Key.length() == 0 || search_Key == null || search_Key.compareTo("")
                == 0) {

            return -1;

        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Search_Key, search_Key);
        long insert = db.insert(DB_TABLE_NAME_SEARCH_KEY, null, values);
        db.close();
        return insert;

    }

    public ArrayList<String> getAllSearchKey() {
        // TODO Auto-generated method stub

        ArrayList<String> dataArrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DB_TABLE_NAME_SEARCH_KEY;
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                String name = cursor.getString(cursor
                        .getColumnIndex(Search_Key));

                dataArrayList.add(name);
                cursor.moveToNext();

            }

        }

        cursor.close();
        db.close();
        return dataArrayList;
    }

    public void deleateAll_SearchKey() {

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(DB_TABLE_NAME_SEARCH_KEY, null, null);

    }




}
