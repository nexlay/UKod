package com.example.android.ukod;




import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;


import android.view.View;



public class MainActivity extends AppCompatActivity {

    public static final String SAMPLE_FILE_TS = "wykaz_TS.pdf";
    public static final String SAMPLE_FILE_CODES = "kody_instalacji.pdf";
    public static final String SAMPLE_FILE_SERVICE = "kody_serwis.pdf";

    public static final String url3 = "http://plus.google.com/u/0/+Nick_Prigodskyi";
    public static final String url4 = "http://plus.google.com/u/0/+Nick_Prigodskyi";
    public static final String url5 = "http://plus.google.com/u/0/+Nick_Prigodskyi";
    public static final String url6 = "https://mail.google.com/mail";
    private DrawerLayout mDrawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_info:
                        mDrawerLayout.closeDrawers();
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_codes:
                        mDrawerLayout.closeDrawers();
                        Intent intent1 = new Intent(MainActivity.this, TsActivity.class);
                        intent1.putExtra("codes", SAMPLE_FILE_CODES);
                        startActivity(intent1);
                        break;
                    case R.id.action_ts:
                        mDrawerLayout.closeDrawers();
                        Intent intent2 = new Intent(MainActivity.this, TsActivity.class);
                        intent2.putExtra("ts", SAMPLE_FILE_TS);
                        startActivity(intent2);
                        break;
                    case R.id.action_service:
                        mDrawerLayout.closeDrawers();
                        Intent intent3 = new Intent(MainActivity.this, TsActivity.class);
                        intent3.putExtra("services", SAMPLE_FILE_SERVICE);
                        startActivity(intent3);
                        break;
                    case R.id.action_twitter:
                        mDrawerLayout.closeDrawers();
                        String url = "http://twitter.com/Nexlay";
                        Intent intent4 = new Intent(Intent.ACTION_VIEW);
                        intent4.setData(Uri.parse(url));
                        startActivity(intent4);
                        break;
                    case R.id.action_facebook:
                        mDrawerLayout.closeDrawers();
                        String url1 = "http://www.facebook.com/profile.php?id=100009304863041";
                        Intent intent5 = new Intent(Intent.ACTION_VIEW);
                        intent5.setData(Uri.parse(url1));
                        startActivity(intent5);
                        break;
                    case R.id.action_googleplus:
                        mDrawerLayout.closeDrawers();
                        String url2 = "http://plus.google.com/u/0/+Nick_Prigodskyi";
                        Intent intent6 = new Intent(Intent.ACTION_VIEW);
                        intent6.setData(Uri.parse(url2));
                        startActivity(intent6);
                        break;
                    case R.id.mail_jga:
                        mDrawerLayout.closeDrawers();
                        Intent intent7 = new Intent(Intent.ACTION_VIEW);
                        intent7.setData(Uri.parse(url6));
                        startActivity(intent7);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dot_menu_lenguage, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.dot_menu_locale:
                showChangeLanguageDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void linkSalary(View view) {
        Intent intent8 = new Intent(MainActivity.this,SalaryActivity.class);
        startActivity(intent8);
    }

    public void linkRfc(View view) {
        Intent intent9 = new Intent(Intent.ACTION_VIEW);
        intent9.setData(Uri.parse(url3));
        startActivity(intent9);
    }

    public void linkProvisioning(View view) {
        Intent intent10 = new Intent(Intent.ACTION_VIEW);
        intent10.setData(Uri.parse(url4));
        startActivity(intent10);
    }

    public void linkRefueling(View view) {
        Intent intent11 = new Intent(Intent.ACTION_VIEW);
        intent11.setData(Uri.parse(url5));
        startActivity(intent11);
    }
    public void showChangeLanguageDialog (){
        String[] languagesList = {"English", "Polski", "Українська" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_message_main));
        builder.setSingleChoiceItems(languagesList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
           switch (i){
               case 0:
                   LanguageHelper.changeLocale(getBaseContext().getResources(), "en");
recreate();
break;
               case 1:
                   LanguageHelper.changeLocale(getBaseContext().getResources(), "pl");
recreate();
break;
               case 2:
                   LanguageHelper.changeLocale(getBaseContext().getResources(), "uk");
                   recreate();
                   break;
           }
                    dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}



