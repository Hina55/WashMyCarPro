package com.hina.washmycarpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hina.washmycarpro.model.Order;

public class ServiceProviderActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;
    TextView placeHolderTextView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1F7FB8")));
        getSupportActionBar().setTitle("Service Provider");

        Drawable backArrow = getResources().getDrawable(R.drawable.ic_local_car_wash_black_24dp);
        backArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);


        progressBar = findViewById(R.id.progressBar);
        placeHolderTextView = findViewById(R.id.noOrderPlaceHolder);
        firebaseFirestore=FirebaseFirestore.getInstance();
        mFirestoreList=findViewById(R.id.notificationList);

        Query query = firebaseFirestore.collection("orders");
        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query,Order.class)
                .build();

       adapter = new FirestoreRecyclerAdapter<Order, OrderNotificationViewHolder>(options) {
            @NonNull
            @Override
            public OrderNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_list,parent,false);
                return new OrderNotificationViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderNotificationViewHolder holder, int position, @NonNull Order model) {

                holder.name.setText(model.getName());
                holder.email.setText(model.getEmail());
                holder.orderDescription.setText(model.getServiceOrdered());
                holder.orderTime.setText(model.getTime());
                holder.orderDate.setText(model.getDate());
                progressBar.setVisibility(View.INVISIBLE);
            }
        };


       mFirestoreList.setHasFixedSize(true);
       mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
       mFirestoreList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/

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

    private class OrderNotificationViewHolder extends RecyclerView.ViewHolder {

        TextView name, email, orderTime, orderDescription,orderDate;
        public OrderNotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.orderUserName);
            email = itemView.findViewById(R.id.orderUserEmail);
            orderTime = itemView.findViewById(R.id.orderTime);
            orderDescription = itemView.findViewById(R.id.orderDescription);
            orderDate=itemView.findViewById(R.id.orderUserDate);

        }
    }
}
