package com.rtayal.itunesconnect.testers;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.rtayal.itunesconnect.R;
import com.rtayal.itunesconnect.models.AppItem;
import com.rtayal.itunesconnect.models.TesterItem;

public class TesterActivity extends AppCompatActivity implements TesterFragment.OnListFragmentInteractionListener {

    private AppItem appItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        appItem = (AppItem) getIntent().getSerializableExtra("app-item");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, TesterFragment.newInstance(appItem));
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(TesterItem item) {

    }
}
