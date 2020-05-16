package com.time.chakrirkhoborjokhontokhon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AddNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        this.setTitle("Add New Activity");
    }

    public void imgOne(View view) {
        Intent govtJobAddIntent = new Intent(AddNewActivity.this,GovtJobAddActivity.class);
        startActivity(govtJobAddIntent);
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
    }

    public void imgTwo(View view) {
        Intent nonGovtJobAddIntent = new Intent(AddNewActivity.this,NonGovtJobAddActivity.class);
        startActivity(nonGovtJobAddIntent);
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
    }

    public void imgThree(View view) {
        Toast.makeText(this, "This is img3", Toast.LENGTH_SHORT).show();

    }

    public void imgFour(View view) {
        Toast.makeText(this, "This is img4", Toast.LENGTH_SHORT).show();

    }

    public void imgFive(View view) {
        Toast.makeText(this, "This is img5", Toast.LENGTH_SHORT).show();

    }

    public void imgSix(View view) {
        Toast.makeText(this, "This is img6", Toast.LENGTH_SHORT).show();

    }

    public void imgSeven(View view) {
        Toast.makeText(this, "This is img7", Toast.LENGTH_SHORT).show();

    }

    public void imgEight(View view) {
        Toast.makeText(this, "This is img8", Toast.LENGTH_SHORT).show();

    }

    public void logOutButton(View view) {
        Toast.makeText(this, "Log Out Is Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddNewActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);

    }
}
