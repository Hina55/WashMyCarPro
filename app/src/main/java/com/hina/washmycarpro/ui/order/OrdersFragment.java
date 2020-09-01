package com.hina.washmycarpro.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hina.washmycarpro.R;
import com.hina.washmycarpro.adapter.OrderListRecyclerViewAdapter;
import com.hina.washmycarpro.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdersFragment extends Fragment {


    FirebaseFirestore firestore;
    List<Order> orderList;
    RecyclerView orderRecyclerViewList;
    OrderListRecyclerViewAdapter adapter;
    TextView placeHolderTextView;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_order, container, false);
        firestore = FirebaseFirestore.getInstance();
        orderList = new ArrayList<>();
        orderRecyclerViewList = view.findViewById(R.id.orderList);
        progressBar = view.findViewById(R.id.progressBar);
        placeHolderTextView = view.findViewById(R.id.noOrderPlaceHolder);

        adapter = new OrderListRecyclerViewAdapter();
        orderRecyclerViewList.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

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