package com.hina.washmycarpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderConfirmActivity extends AppCompatActivity {

    String data1,data2,data3,data4,data5;
    TextView servicePrivderNametxt,ServieNametxt, timetxt,datetxt,totalAmounttxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1F7FB8")));
        getSupportActionBar().setTitle("Order Confimation");

        servicePrivderNametxt = findViewById(R.id.ServiceProvider);
        ServieNametxt = findViewById(R.id.serviceNametext);
        timetxt = findViewById(R.id.timetext);
        datetxt = findViewById(R.id.datetext);
        totalAmounttxt = findViewById(R.id.totalPricetext);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            data1= extras.getString("ProviderName");
           data2=extras.getString("ServiceName");
            data3 = extras.getString("Time");
            data4 = extras.getString("Date");
            data5 = extras.getString("TotalPrice");
        }

        servicePrivderNametxt.setText(data1);
        ServieNametxt.setText(data2);
        timetxt.setText(data3);
        datetxt.setText(data4);
        totalAmounttxt.setText(data5);

        Button btnhome =  findViewById(R.id.btnHome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderConfirmActivity.this,UserActivity.class));
                finish();
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(OrderConfirmActivity.this,UserActivity.class));
        finish();
    }
}
