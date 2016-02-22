package com.example.razon30.movietest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by razon30 on 15-02-16.
 */
public class AdapterOtherMovies extends RecyclerView.Adapter<AdapterOtherMovies
        .ViewHolderOtherMovie> {

    String image_url = "http://image.tmdb.org/t/p/w500";
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private int previousPosition=0;
    Context context;


    public  AdapterOtherMovies(Context context) {

        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setMovies(ArrayList<Movie> listMovies) {
        this.listMovies = listMovies;
        notifyDataSetChanged();
    }


    @Override
    public AdapterOtherMovies.ViewHolderOtherMovie onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.itemmovieandsalaryrecycler, parent, false);
        ViewHolderOtherMovie viewHolder = new ViewHolderOtherMovie(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterOtherMovies.ViewHolderOtherMovie holder, int position) {

        Movie currentMovie = listMovies.get(position);

        Picasso.with(context).load(image_url+currentMovie.getMovie_image_link()).into(holder
                .imageView);
        holder.releaseYear.setBootstrapText(new BootstrapText.Builder(context)
                .addFontAwesomeIcon(FontAwesome.FA_GRADUATION_CAP)
                .addText("  " + currentMovie.getMovie_release_date()).build());

        holder.personCharacter.setBootstrapText(new BootstrapText.Builder(context)
                .addFontAwesomeIcon(FontAwesome.FA_WRENCH)
                .addText("  " + currentMovie.getActorActress()).build());

        holder.movieName.setBootstrapText(new BootstrapText.Builder(context)
                .addFontAwesomeIcon(FontAwesome.FA_FILE_MOVIE_O)
                .addText("  " + currentMovie.getMovie_name()).build());



    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolderOtherMovie extends RecyclerView.ViewHolder {

        RoundedImageView imageView;
        BootstrapButton movieName,personCharacter,releaseYear;

        public ViewHolderOtherMovie(View itemView) {
            super(itemView);

            imageView = (RoundedImageView) itemView.findViewById(R.id.imageView);
            movieName = (BootstrapButton) itemView.findViewById(R.id.movieName);
            personCharacter = (BootstrapButton) itemView.findViewById(R.id.character);
            releaseYear = (BootstrapButton) itemView.findViewById(R.id.releaseDate);

        }
    }
}
