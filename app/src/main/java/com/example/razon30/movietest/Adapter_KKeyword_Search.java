package com.example.razon30.movietest;

/**
 * Created by razon30 on 18-02-16.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class Adapter_KKeyword_Search extends RecyclerView.Adapter<Adapter_KKeyword_Search
        .ViewHolderBoxOffice> {

    String image_url = "http://image.tmdb.org/t/p/w342";
    Context context;
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private int previousPosition = 0;


    public Adapter_KKeyword_Search(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
    }

    public void setMovies(ArrayList<Movie> listMovies) {
        this.listMovies = listMovies;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_search_result_list, parent, false);
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderBoxOffice holder, int position) {

        if (position == 0) {
            holder.layout.setBackgroundResource(R.color.background2);
        } else if (position == 1) {
            holder.layout.setBackgroundResource(R.color.teal_800);
        } else if (position % 2 == 0) {
            holder.layout.setBackgroundResource(R.color.primaryColorDark);
        } else if (position % 3 == 0) {
            holder.layout.setBackgroundResource(R.color.background5);
        } else if (position % 4 == 0) {
            holder.layout.setBackgroundResource(R.color.background2);
        } else if (position % 5 == 0) {
            holder.layout.setBackgroundResource(R.color.background8);
        } else {
            holder.layout.setBackgroundResource(R.color.teal_A400);
        }


        Movie currentMovie = listMovies.get(position);
        holder.movieTitle.setText(currentMovie.getProfileImage());
        String movieReleaseDate = currentMovie.getProfileLink();
        if (movieReleaseDate != null || movieReleaseDate.length() > 0) {
            holder.movieReleaseDate.setText(movieReleaseDate);
        } else {
            holder.movieReleaseDate.setText("NA");
        }



        if (position > previousPosition) {
            AnimationUtils.animate(holder, true);
        } else {
            AnimationUtils.animate(holder, false);
        }
        previousPosition = position;


        String urlThumnail = currentMovie.getCoverImage();

        Picasso.with(context).load(image_url+urlThumnail).into(holder.movieThumbnail);

    }


    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolderBoxOffice extends RecyclerView.ViewHolder {
        RoundedImageView movieThumbnail;
        TextView movieTitle;
        TextView movieReleaseDate;
        RelativeLayout layout;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            movieThumbnail = (RoundedImageView) itemView.findViewById(R.id.cast_thumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.name_cast);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.cast_job);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout_relative);
        }
    }


}
