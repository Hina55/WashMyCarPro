package com.hina.washmycarpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class OptionActivity extends AppCompatActivity implements View.OnClickListener{

    Button btninsidewash, btndryclean, btnpremium, btnpolishing;
    String service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1F7FB8")));
        getSupportActionBar().setTitle("Services");

        btninsidewash = findViewById(R.id.btnWashInside);
        btndryclean = findViewById(R.id.btnDryClean);
        btnpremium = findViewById(R.id.btnPremium);
        btnpolishing = findViewById(R.id.btnPolish);

        btninsidewash.setOnClickListener(this);
        btndryclean.setOnClickListener(this);
        btnpremium.setOnClickListener(this);
        btnpolishing.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            service= extras.getString("key");
        }
        TextView providertxt = findViewById(R.id.serviceProvidertxt);
        providertxt.setText(service);


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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


    @Override
    public void onClick(View v) {
        Intent intent;
       switch (v.getId()){
           case R.id.btnWashInside:
               intent = new Intent(OptionActivity.this, CreateOrderActivity.class);
               intent.putExtra("ServiceDescription", "Washing the Vehicle from outisde with foam gel and cleaning the inside with micro fibre and vaccum the dust.");
               intent.putExtra("AboutService", "Car Wash (Inside - Outside)");
               intent.putExtra("PriceTag","3000");
               intent.putExtra("ServiceProvider",service);
               startActivity(intent);
               break;
           case R.id.btnDryClean:
               intent = new Intent(OptionActivity.this, CreateOrderActivity.class);
               intent.putExtra("ServiceDescription", "Interior and Exterior Detailing for Cleaning the inner the Parts of Vehicles.");
               intent.putExtra("AboutService", "Dry Clean Detailing");
               intent.putExtra("PriceTag","4200");
               intent.putExtra("ServiceProvider",service);
               startActivity(intent);
               break;
           case R.id.btnPremium:
               intent = new Intent(OptionActivity.this, CreateOrderActivity.class);
               intent.putExtra("ServiceDescription", "Premium tire gloss gel, birilliant shine semi nano protection, anti bacteria for the AC.");
               intent.putExtra("AboutService", "Premium Car Wash");
               intent.putExtra("PriceTag","6000");
               intent.putExtra("ServiceProvider",service);
               startActivity(intent);
               break;
           case R.id.btnPolish:
               intent = new Intent(OptionActivity.this, CreateOrderActivity.class);
               intent.putExtra("ServiceDescription", "Polishing will eliminate surface scratches and dirt. Polish must use before Wax to restore paint for shining.");
               intent.putExtra("AboutService", "Polishing and Waxing");
               intent.putExtra("PriceTag","5000");
               intent.putExtra("ServiceProvider",service);
               startActivity(intent);
               break;
       }
    }
}
