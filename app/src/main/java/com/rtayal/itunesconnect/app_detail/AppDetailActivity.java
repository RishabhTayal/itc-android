package com.rtayal.itunesconnect.app_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rtayal.itunesconnect.R;
import com.rtayal.itunesconnect.testers.TesterActivity;
import com.rtayal.itunesconnect.models.AppItem;
import com.rtayal.itunesconnect.models.ReviewItem;
import com.rtayal.itunesconnect.reviews.AppReviewsActivity;
import com.rtayal.itunesconnect.reviews.ReviewFragment;

public class AppDetailActivity extends AppCompatActivity implements ReviewFragment.OnListFragmentInteractionListener {

    private AppItem appItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        appItem = (AppItem) getIntent().getSerializableExtra("app-item");

        findViewById(R.id.testersButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppDetailActivity.this, TesterActivity.class);
                intent.putExtra("app-item", appItem);
                startActivity(intent);
            }
        });

        findViewById(R.id.reviewsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppDetailActivity.this, AppReviewsActivity.class);
                intent.putExtra("app-item", appItem);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onListFragmentInteraction(ReviewItem item) {

    }
}
