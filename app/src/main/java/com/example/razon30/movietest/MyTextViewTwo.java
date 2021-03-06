package com.example.razon30.movietest;

/**
 * Created by razon30 on 07-02-16.
 */
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextViewTwo extends TextView {
    public MyTextViewTwo(Context context) {
        super(context);
    }

    public MyTextViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyTextViewTwo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "roboto_condensed_light.ttf");
        setTypeface(tf);
    }

}