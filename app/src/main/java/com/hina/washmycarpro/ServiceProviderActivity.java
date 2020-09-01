package com.hina.washmycarpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hina.washmycarpro.adapter.OrderListRecyclerViewAdapter;
import com.hina.washmycarpro.adapter.notificationListAdapter;
import com.hina.washmycarpro.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceProviderActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    List<Order> orderList;
    RecyclerView orderRecyclerViewList;
    notificationListAdapter adapter;
    TextView placeHolderTextView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);

        firestore = FirebaseFirestore.getInstance();
        orderList = new ArrayList<>();
        orderRecyclerViewList = findViewById(R.id.notificationList);
        progressBar = findViewById(R.id.progressBar);
        placeHolderTextView = findViewById(R.id.noOrderPlaceHolder);
        adapter = new notificationListAdapter();
        orderRecyclerViewList.setAdapter(adapter);


        firestore.collection("orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    orderRecyclerViewList.setVisibility(View.VISIBLE);
                    placeHolderTextView.setVisibility(View.GONE);

                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        Order order = new Order();

                            order.setDate(documentSnapshot.getString("Date"));
                            order.setName(documentSnapshot.getString("Name"));
                            order.setServiceOrder(documentSnapshot.getString("ServiceOrdered"));
                            order.setServiceProvider(documentSnapshot.getString("ServiceProvider"));
                            order.setTime(documentSnapshot.getString("Time"));
                            order.setTotalAmount(documentSnapshot.getString("Total Amount"));
                            order.setEmail(documentSnapshot.getString("email"));
                            orderList.add(order);


                    }
                    adapter.addItem(orderList);
                } else {
                    orderRecyclerViewList.setVisibility(View.GONE);
                    placeHolderTextView.setVisibility(View.VISIBLE);
                }
            }
        });


    }
}
