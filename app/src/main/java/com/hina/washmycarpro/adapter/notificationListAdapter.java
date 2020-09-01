package com.hina.washmycarpro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hina.washmycarpro.R;
import com.hina.washmycarpro.model.Order;

import java.util.ArrayList;
import java.util.List;

public class notificationListAdapter extends RecyclerView.Adapter<notificationListAdapter.OrderListViewHolder> {

    private List<Order> orderList;

    public notificationListAdapter() {
        orderList = new ArrayList<>();
    }

    public void addItem(List<Order> orders) {
        if (!orderList.isEmpty()) {
            orderList.clear();
        }
        orderList.addAll(orders);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_list, parent, false);
        return new notificationListAdapter.OrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
        Order order = orderList.get(position);
        if (order != null) {

            holder.name.setText(order.getName());
            holder.email.setText(order.getEmail());
            holder.orderDescription.setText(order.getServiceOrder());
            holder.orderTime.setText(order.getTime());
        }
    }


    @Override
    public int getItemCount() {
        return 0;
    }


    static class OrderListViewHolder extends RecyclerView.ViewHolder {

        TextView name, email, orderTime, orderDescription;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.orderUserName);
            email = itemView.findViewById(R.id.orderUserEmail);
            orderTime = itemView.findViewById(R.id.orderTime);
            orderDescription = itemView.findViewById(R.id.orderDescription);
        }
    }
}
