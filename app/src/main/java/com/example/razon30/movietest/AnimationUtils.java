package com.example.razon30.movietest;

/**
 * Created by razon30 on 09-02-16.
 */
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by razon30 on 11-07-15.
 */
public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown) {

        YoYo.with(Techniques.RubberBand)
                .duration(1000)
                .playOn(holder.itemView);

    }

    public static void animateList(RecyclerView.ViewHolder holder, boolean goesDown) {

        YoYo.with(Techniques.FadeInRight)
                .duration(1500)
                .playOn(holder.itemView);

    }

}