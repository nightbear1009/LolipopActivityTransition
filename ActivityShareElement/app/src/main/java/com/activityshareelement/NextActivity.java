package com.activityshareelement;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by tedliang on 15/3/7.
 */
public class NextActivity extends Activity {
    public static final String VIEW_NAME = "next.view.name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nextview);
        ImageView img = (ImageView)findViewById(R.id.nextimg);
        img.setTransitionName(VIEW_NAME);
        Picasso.with(this)
                .load("http://d1n6pk67leuy4e.cloudfront.net/webapi/images/o/400/400/SalePage/972013/0/17452")
                .noFade()
                .into(img);
    }
}
