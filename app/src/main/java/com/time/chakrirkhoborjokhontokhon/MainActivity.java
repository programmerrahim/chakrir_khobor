package com.time.chakrirkhoborjokhontokhon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private CardView govtCardView;


  //main method starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerId);
        navigationView = findViewById(R.id.navigationView_Id);
        navigationView.setNavigationItemSelectedListener(this);


        govtCardView = findViewById(R.id.govt_card_ViewId);
        govtCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gintent = new Intent(MainActivity.this,GovtJobActivity.class);
                startActivity(gintent);

                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });


        //Navigation Drawer in main method starts

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Navigation Drawer in main method ends


        checkConnection();


    }
    //main method ends


    //Navigation Drawer in out method starts

    @Override
    public void onBackPressed() {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to Exit?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final ProgressDialog progressBar = new ProgressDialog(MainActivity.this);
                    progressBar.setMessage("Welcome back again");
                    progressBar.show();


                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            progressBar.dismiss();

                        }
                    },1000);

                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                    finish();
                }
            }).show();

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_gov_Id:
                Toast.makeText(this, "This is Gov", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu__non_gov_Id:
                Toast.makeText(this, "This is non Gov", Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_company_Id:
                Toast.makeText(this, "This is company", Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_accounting_Id:
                Toast.makeText(this, "This is accounting", Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_marketing_Id:
                Toast.makeText(this, "This is marketing", Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_walkInInterView_Id:
                Toast.makeText(this, "This is walk in interview", Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu_about_Id:
                Toast.makeText(this, "This is about", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_admin_Id:
                 Intent adminIntent = new Intent(MainActivity.this,AdminActivity.class);
                 startActivity(adminIntent);

                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                break;
        }
        return false;
    }
    //Navigation Drawer in out method ends

    //Check Connection Start
    public void checkConnection(){

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null!=activeNetwork){

            if (activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
                Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show();
            }
            if (activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show();
            }


        }

        else{
            Toast.makeText(this, "No Network Connections", Toast.LENGTH_LONG).show();
        }
    }
    //Check Connection Ends




}
