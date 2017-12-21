package com.rtayal.itunesconnect;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.rtayal.itunesconnect.dummy.ReviewItem;

public class AppDetailActivity extends AppCompatActivity implements ReviewFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ReviewFragment.newInstance(1));
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(ReviewItem item) {

    }
}
