package com.time.chakrirkhoborjokhontokhon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class GovtJobActivity extends AppCompatActivity {

    
    private RecyclerView govtRecyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govt_job);

        firebaseFirestore = FirebaseFirestore.getInstance();

        govtRecyclerView = findViewById(R.id.govtRecyclerviewId);
        govtRecyclerView.setHasFixedSize(true);
        govtRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Query

        Query query = firebaseFirestore.collection("Products").orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ProductsModel>options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query,ProductsModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_sample_layout,parent,false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
                holder.nameText.setText(model.getName());
                holder.priceText.setText("আবেদনের শুরুর তারিখ : "+model.getPrice());
                holder.lastDate.setText("আবেদনের শেষ তারিখ : "+model.getLastD());
                holder.jobQuantity.setText("পদ সংখ্যা : "+model.getQuan());
                holder.jobDescription.setText("বর্ণনা : "+model.getDes());
                holder.jobSource.setText("তথ্য সুত্র : "+model.getSource());
                Picasso.get().load(model.getLink()).into(holder.imageView);


                boolean isExpanded = model.isExpanded();
                holder.expRelativeLayout.setVisibility(isExpanded ? View.VISIBLE :View.GONE);


            }
        };
        govtRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView priceText;
        private Button moreButton;
        private RelativeLayout expRelativeLayout;
        private TextView lastDate;
        private TextView jobQuantity;
        private TextView jobDescription;
        private TextView jobSource;
        private PhotoView imageView;



        private Button collapse;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.product_sample_nameId);
            priceText = itemView.findViewById(R.id.product_sample_priceId);
            moreButton = itemView.findViewById(R.id.product_sample_moreId);
            expRelativeLayout = itemView.findViewById(R.id.expandable_relative_layout);
            lastDate =itemView.findViewById(R.id.product_sample_lastDateId);
            jobQuantity =itemView.findViewById(R.id.product_sample_quantityId);
            jobDescription =itemView.findViewById(R.id.product_sample_descriptionId);
            jobSource =itemView.findViewById(R.id.product_sample_sourceId);
            imageView = itemView.findViewById(R.id.product_sample_image_jobId);



            collapse = itemView.findViewById(R.id.collapseId);

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expRelativeLayout.setVisibility(View.VISIBLE);

                }
            });
            collapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expRelativeLayout.setVisibility(View.GONE);

                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
