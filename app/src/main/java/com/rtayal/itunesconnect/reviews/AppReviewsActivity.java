package com.rtayal.itunesconnect.reviews;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.rtayal.itunesconnect.R;
import com.rtayal.itunesconnect.models.AppItem;
import com.rtayal.itunesconnect.models.ReviewItem;

public class AppReviewsActivity extends AppCompatActivity implements ReviewFragment.OnListFragmentInteractionListener {

    private AppItem appItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_reviews);

        appItem = (AppItem) getIntent().getSerializableExtra("app-item");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ReviewFragment.newInstance(appItem));
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(ReviewItem item) {

    }
}
