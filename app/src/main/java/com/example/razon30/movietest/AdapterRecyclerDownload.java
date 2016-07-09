package com.example.razon30.movietest;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;

/**
 * Created by razon30 on 09-07-16.
 */
public class AdapterRecyclerDownload extends RecyclerView.Adapter<AdapterRecyclerDownload.ViewHolderAdapterRecyclerDownload> {

    Context context;
    ArrayList<MovieDownloadLink> movieList = new ArrayList<MovieDownloadLink>();
    private LayoutInflater layoutInflater;
    private ClipboardManager myClipboard;
    private ClipData myClip;


    public AdapterRecyclerDownload(Context context, ArrayList<MovieDownloadLink> movieList) {
        this.context = context;
        this.movieList = movieList;
        layoutInflater = LayoutInflater.from(context);
        myClipboard =(ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
    }

    @Override
    public AdapterRecyclerDownload.ViewHolderAdapterRecyclerDownload onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_recycler_download, parent, false);
        ViewHolderAdapterRecyclerDownload viewHolder = new ViewHolderAdapterRecyclerDownload(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerDownload.ViewHolderAdapterRecyclerDownload holder, int position) {

        String name = movieList.get(position).getMovie();
        final String link = movieList.get(position).getLink();
        final String sub = movieList.get(position).getSubLink();



        if (name.contains(".")){
            name = name.replace("."," ");
        }

        holder.name.setText(name);

        if (!sub.equals("") && !sub.equals("0")){

            holder.subPart.setVisibility(View.VISIBLE);
            if (sub.equals("1")){
                holder.sharesub.setVisibility(View.GONE);
                holder.copysub.setText("Subtitile is attached with movie link");
                holder.copysub.setEnabled(false);
            }else {


                holder.copysub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myClip = ClipData.newPlainText("text", sub);
                        myClipboard.setPrimaryClip(myClip);

                        Toast.makeText(context, "Text Copied", Toast.LENGTH_SHORT).show();
                    }
                });

                holder.sharesub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, sub);
                        sendIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(sendIntent, "Share Subtitle"));
                    }
                });
            }

        }

        if (link.contains("http://www.dailymotion.com/video/") || link.contains("https://www.youtube.com/watch")){
            holder.sharemovie.setVisibility(View.GONE);
            holder.copymovie.setText("Watch Movie");
            if (link.contains("https://www.youtube.com/watch")){
                holder.copymovie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = link.substring(link.indexOf("=")+1,link.length());
                        context.startActivity(new Intent(context, PlayingYoutube.class)
                                .putExtra
                                ("trailer", str));
                    }
                });
            }else {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    context.startActivity(browserIntent);

            }
        }else {


            holder.copymovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClip = ClipData.newPlainText("text", link);
                    myClipboard.setPrimaryClip(myClip);

                    Toast.makeText(context, "Text Copied",Toast.LENGTH_SHORT).show();
                }
            });

            holder.sharemovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, link);
                    sendIntent.setType("text/plain");
                    context.startActivity(Intent.createChooser(sendIntent,"Share Link"));
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolderAdapterRecyclerDownload extends RecyclerView.ViewHolder {

        TextView name;
        BootstrapButton copymovie,copysub,sharemovie,sharesub;
        LinearLayout subPart;


        public ViewHolderAdapterRecyclerDownload(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            copymovie = (BootstrapButton) itemView.findViewById(R.id.copyMovieLink);
            copysub = (BootstrapButton) itemView.findViewById(R.id.copySubLink);
            sharemovie = (BootstrapButton) itemView.findViewById(R.id.sharemovielink);
            sharesub = (BootstrapButton) itemView.findViewById(R.id.shareSublink);
            subPart = (LinearLayout) itemView.findViewById(R.id.subtitlePart);

        }
    }
}
