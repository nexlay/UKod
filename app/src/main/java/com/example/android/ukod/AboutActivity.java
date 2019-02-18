package com.example.android.ukod;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;




public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.information));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }


        TextView infoTextView = findViewById(R.id.info_text_view);
        int versionCode = BuildConfig.VERSION_CODE;
        String information = getString(R.string.app_version) + " " + BuildConfig.VERSION_NAME + "." + versionCode;
        information += "\n" + getString(R.string.made_by);
        infoTextView.setText(information);

        TextView textViewInfo = findViewById(R.id.text_view_info);
        String changeLogInfo = "Ostatnie zmiany:";
        changeLogInfo += "\n" + "1. Drobne poprawki.";
        textViewInfo.setText(changeLogInfo);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
