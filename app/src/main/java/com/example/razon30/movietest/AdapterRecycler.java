package com.example.razon30.movietest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by razon30 on 07-02-16.
 */
public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolderPopularMovies> {

    String image_url = "http://image.tmdb.org/t/p/w500";
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    Context context;
    private int previousPosition=0;

    public AdapterRecycler(Context context) {

        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setMovies(ArrayList<Movie> listMovies) {
        this.listMovies = listMovies;
        notifyDataSetChanged();
    }


    @Override
    public AdapterRecycler.ViewHolderPopularMovies onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_item_tv_iv, parent, false);
        ViewHolderPopularMovies viewHolder = new ViewHolderPopularMovies(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRecycler.ViewHolderPopularMovies holder, int position) {

        Movie currentMovie = listMovies.get(position);

        String name = currentMovie.getMovie_name();
        String imageLink = currentMovie.getMovie_image_link();

        holder.textView.setText(name);

        if (imageLink.contains("http:")) {
            Picasso.with(context).load(imageLink).into(holder.imageView);
        } else {
            Picasso.with(context).load(image_url + imageLink).into(holder.imageView);
        }

        if(position>previousPosition)
        {
            AnimationUtils.animateList(holder, true);
        }
        else{
            AnimationUtils.animateList(holder, false);
        }
        previousPosition=position;


    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolderPopularMovies extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolderPopularMovies(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tv_recycler_item_tv_iv);
            imageView = (ImageView) itemView.findViewById(R.id.iv_recycler_item_tv_iv);

        }
    }
}
