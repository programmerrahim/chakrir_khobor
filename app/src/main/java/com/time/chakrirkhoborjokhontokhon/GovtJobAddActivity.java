package com.time.chakrirkhoborjokhontokhon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GovtJobAddActivity extends AppCompatActivity {

    private static final String TAG = "GovtJobAddActivity";
    public static final int ImageBack = 1001;


    private Uri ImageUri;
    private StorageReference JobImageRef;
    private String downloadImageUrl;
    private String name,price,lastD,quan,desc,source,cdate;

    private EditText nameEditTxt;
    private EditText priceEditTxt;
    private EditText lastDate;
    private EditText jobQuantity;
    private EditText jobDescription;
    private EditText jobSource;
    private ImageView jobImage;
    private Button addButton;



    private ProgressDialog loadingBar;

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govt_job_add);
        this.setTitle("Govt Job Add Activity");

        JobImageRef = FirebaseStorage.getInstance().getReference().child("Job Images");

        mFirestore = FirebaseFirestore.getInstance();






        nameEditTxt = findViewById(R.id.govtJobAddNameId);
        priceEditTxt = findViewById(R.id.govtJobAddPriceId);
        addButton = findViewById(R.id.govtJobAddButtonId);
        lastDate = findViewById(R.id.govtJobAddLastDateId);
        jobQuantity = findViewById(R.id.govtJobAddQuantityid);
        jobDescription = findViewById(R.id.govtJobAddDescriptionId);
        jobSource = findViewById(R.id.govtJobAddSourceId);
        jobImage = findViewById(R.id.govtJobAdd_imageId);



        loadingBar = new ProgressDialog(this);

        jobImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openGallery();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              validateGovtJobAddData();
            }
        });

    }

    //open gallery method start
    private void openGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, ImageBack);
    }
    //open gallery ends
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImageBack && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            jobImage.setImageURI(ImageUri);
        }
    }
    //validateGovtJobAddData method start
    private void validateGovtJobAddData() {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        String currentDate = dateFormat.format(date);

         name = nameEditTxt.getText().toString();
         price = priceEditTxt.getText().toString();
         lastD = lastDate.getText().toString();
         quan = jobQuantity.getText().toString();
         desc = jobDescription.getText().toString();
         source = jobSource.getText().toString();
         cdate = currentDate;



        if (ImageUri==null){
            Toast.makeText(this, "Image is mandatory.", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please write Job name.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Please write Job first date.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(lastD)) {
            Toast.makeText(this, "Please write Job last date.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(quan)) {
            Toast.makeText(this, "Please write job quantity.", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(desc)) {
            Toast.makeText(this, "Please write job description.", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(source)) {
            Toast.makeText(this, "Please write job source.", Toast.LENGTH_SHORT).show();
        }
        else{
            StoreJobInformation();
        }
    }
    //validateGovtJobAddData method ends


    //StoreJobInformation method starts
    private void StoreJobInformation() {
        loadingBar.setTitle("Add new Job");
        loadingBar.setMessage("Please wait while adding the new job.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final StorageReference filePath = JobImageRef.child(ImageUri.getLastPathSegment()+".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(GovtJobAddActivity.this, "Error "+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(GovtJobAddActivity.this, "Product Image uploaded successfully.", Toast.LENGTH_SHORT).show();
                Task<Uri>urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                           throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(GovtJobAddActivity.this, "Getting Product image Url successfully.", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    //StoreJobInformation method ends


    //SaveProductInfoToDatabase method starts
    private void SaveProductInfoToDatabase() {



        Map<String,Object>productMap=new HashMap<>();
        productMap.put("name",name);
        productMap.put("price",price);
        productMap.put("lastD",lastD);
        productMap.put("quan",quan);
        productMap.put("des",desc);
        productMap.put("source",source);
        productMap.put("link",downloadImageUrl);
        productMap.put("date",cdate);

        mFirestore.collection("Products").add(productMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(GovtJobAddActivity.this,AddNewActivity.class);
                    startActivity(intent);
                    Log.d(TAG, "onComplete: ");
                    loadingBar.dismiss();
                    Toast.makeText(GovtJobAddActivity.this, "Product is added successfully.", Toast.LENGTH_SHORT).show();
                }else{
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(GovtJobAddActivity.this, "Error :" +message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //SaveProductInfoToDatabase method ends



}
