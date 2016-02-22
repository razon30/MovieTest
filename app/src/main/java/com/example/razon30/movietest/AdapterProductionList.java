package com.example.razon30.movietest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.font.FontAwesome;

import java.util.ArrayList;

/**
 * Created by razon30 on 14-02-16.
 */
public class AdapterProductionList extends RecyclerView.Adapter<AdapterProductionList
        .ViewHolderProduction> {

    ArrayList<String> list;
    LayoutInflater layoutInflater;
    Context context;

    public AdapterProductionList(ArrayList<String> list, Context context) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);;
        this.context = context;
    }


    @Override
    public AdapterProductionList.ViewHolderProduction onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list_production, parent, false);
        ViewHolderProduction viewHolder = new ViewHolderProduction(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterProductionList.ViewHolderProduction holder, int position) {

        holder.button.setBootstrapText(new BootstrapText.Builder(context)
                .addFontAwesomeIcon(FontAwesome.FA_FILE_MOVIE_O)
                .addText("  " + list.get(position)).build());;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolderProduction extends RecyclerView.ViewHolder {
        BootstrapButton button ;

        public ViewHolderProduction(View itemView) {
            super(itemView);
            button = (BootstrapButton) itemView.findViewById(R.id.item_production_list);
        }
    }
}
