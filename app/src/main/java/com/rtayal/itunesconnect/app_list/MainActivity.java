package com.rtayal.itunesconnect.app_list;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rtayal.itunesconnect.R;
import com.rtayal.itunesconnect.app_detail.AppDetailActivity;
import com.rtayal.itunesconnect.helper.Helper;
import com.rtayal.itunesconnect.helper.MyApplication;
import com.rtayal.itunesconnect.helper.ServiceCaller;
import com.rtayal.itunesconnect.models.AppItem;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ItemFragment.newInstance(1));
        transaction.commit();

        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(2)
                .setRemindInterval(2)
                .setOnClickButtonListener(new OnClickButtonListener() {
                    @Override
                    public void onClickButton(int which) {

                    }
                })
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);

//        BottomNavigationView navigation = findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ServiceCaller.getBaseUrl().length() == 0) {
            ServiceCaller.askForBaseUrl(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_rate) {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
            }
        }
        if (id == R.id.action_share) {
//            Answers.getInstance().logInvite(new InviteEvent());
            String shareBody = "Check out iTunes Connect for your smart phone. Download now from "; //+ Helper.AppDownloadLink;
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        }
        if (id == R.id.action_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            SharedPreferences.Editor editor = MyApplication.sharedPreferences().edit();
                            editor.remove(Helper.SharedPrefUserNameKey);
                            editor.remove(Helper.SharedPrefPasswordKey);
                            editor.remove(Helper.SharedPrefLoggedInKey);
                            editor.apply();
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Cancel", null).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(AppItem item) {
        Log.i("Item", item.toString());
        Intent intent = new Intent(MainActivity.this, AppDetailActivity.class);
        intent.putExtra("app-item", item);
        startActivity(intent);
    }
}
