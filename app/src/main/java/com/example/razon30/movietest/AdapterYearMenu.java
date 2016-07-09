package com.example.razon30.movietest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by razon30 on 09-07-16.
 */
public class AdapterYearMenu extends BaseAdapter{

    ArrayList<MovieDownloadLink> downloadLinks;
    Context context;
    private static LayoutInflater inflater = null;

    public AdapterYearMenu(ArrayList<MovieDownloadLink> downloadLinks, Context context) {
        this.downloadLinks = downloadLinks;
        this.context = context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.item_list_years_menus, null);

        TextView textView = (TextView) view.findViewById(R.id.itemyearmenu);

        String name = downloadLinks.get(position).getMovie();

        if (name.contains(".")){
            name = name.replace("."," ");
        }

        textView.setText(name);

        return view;
    }

    @Override
    public int getCount() {
        return downloadLinks.size();
    }

    @Override
    public Object getItem(int position) {
        return downloadLinks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
