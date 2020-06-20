package com.hina.washmycarpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateOrderActivity extends AppCompatActivity {

    String value, value2, value3,value4;
    TextView serviceDescription,aboutText, PriceTagtxt,ServiceProviderName;
    EditText datetxt,timetxt;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    String mTime,mDate; //this will put into next activity
    CheckBox checkBoxAirFreshner,checkBoxSteamCleam;
    int totalPrice=0;
    int steamCleanPrice = 500;
    int freshnerPrice = 50;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID,name,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1F7FB8")));
        getSupportActionBar().setTitle("Create Order");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        timetxt = findViewById(R.id.time);
        datetxt = findViewById(R.id.date);
        final String[] finalTime = new String[1];
        final String[] finalDate = new String[1];

        timetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               timePickerDialog = new TimePickerDialog(CreateOrderActivity.this,R.style.DateTimePickerTheme,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timetxt.setText(formatTime(hourOfDay, minute));
                        finalTime[0] = timetxt.getText().toString();
                        mTime = formatTime(hourOfDay, minute);
                    }
                },0,0,false);


                timePickerDialog.show();
            }
        });
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.MONTH, 3);

        datetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(CreateOrderActivity.this, R.style.DateTimePickerTheme,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mDate = dayOfMonth+ "/"+month+"/"+year;
                        datetxt.setText(mDate);
                        finalDate[0] = datetxt.getText().toString();

                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });



        final LinearLayout navigationlayout = findViewById(R.id.bottomNavlayout);

        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status

                        if(isOpen){
                            Log.d("TAG", "onVisibilityChanged: Keyboard is open ");
                            navigationlayout.setVisibility(View.INVISIBLE);
                            Log.d("TAG", "onVisibilityChanged: NavBar got Invisible");
                        }else{
                            Log.d("TAG", "onVisibilityChanged: Keyboard is closed");
                            navigationlayout.setVisibility(View.VISIBLE);
                            Log.d("TAG", "onVisibilityChanged: NavBar got Visible");
                        }
                    }
                });

        aboutText = findViewById(R.id.abouttxt);
        serviceDescription=findViewById(R.id.servicedesp);
        ServiceProviderName = findViewById(R.id.serviceProviderNametxt);
        PriceTagtxt = findViewById(R.id.pricetxt);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            value= extras.getString("ServiceDescription");
            value2 = extras.getString("AboutService");
            value3 = extras.getString("PriceTag");
            value4 = extras.getString("ServiceProvider");
        }

        serviceDescription.setText(value);
        aboutText.setText(value2);
        ServiceProviderName.setText(value4);
        PriceTagtxt.setText("Total Amount: "+value3 + " Rs.");
        final String[] finalTotalPrice = new String[1];
        finalTotalPrice[0] = (String) PriceTagtxt.getText();

        /*carnumbtwo = findViewById(R.id.carnumbertwo);
        carnumberfurther = findViewById(R.id.carNumberFurther);
        carnumbtwo.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2,2)});
        carnumberfurther.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});*/


        checkBoxSteamCleam = (CheckBox) findViewById(R.id.steamCleanCheck);
        checkBoxAirFreshner = (CheckBox) findViewById(R.id.airFreshnerCheck);

        final int servicePrice = Integer.parseInt(value3);
        checkBoxAirFreshner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBoxAirFreshner.isChecked()) {

                    totalPrice = servicePrice + freshnerPrice;
                }
                if (!checkBoxAirFreshner.isChecked()) {


                    totalPrice = servicePrice;
                    PriceTagtxt.setText("Total Amount: "+String.valueOf(totalPrice) + " Rs.");
                    finalTotalPrice[0] = (String) PriceTagtxt.getText();

                }
                if ((!checkBoxSteamCleam.isChecked())&&checkBoxAirFreshner.isChecked()) {


                    totalPrice = servicePrice+freshnerPrice;
                    PriceTagtxt.setText("Total Amount: "+String.valueOf(totalPrice) + " Rs.");
                    finalTotalPrice[0] = (String) PriceTagtxt.getText();


                }
                if ((!checkBoxAirFreshner.isChecked())&&checkBoxSteamCleam.isChecked()) {


                    totalPrice = servicePrice+steamCleanPrice;
                    PriceTagtxt.setText("Total Amount: "+String.valueOf(totalPrice) + " Rs.");
                    finalTotalPrice[0] = (String) PriceTagtxt.getText();


                }

                if (checkBoxAirFreshner.isChecked()&&checkBoxSteamCleam.isChecked()) {


                    totalPrice = servicePrice+freshnerPrice+steamCleanPrice;
                    PriceTagtxt.setText("Total Amount: "+String.valueOf(totalPrice) + " Rs.");
                    finalTotalPrice[0] = (String) PriceTagtxt.getText();

                }


            }



        });


        checkBoxSteamCleam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBoxSteamCleam.isChecked()) {

                    totalPrice = servicePrice+steamCleanPrice;


                }
                if (!checkBoxSteamCleam.isChecked()) {


                    totalPrice = servicePrice;
                    PriceTagtxt.setText("Total Amount: "+String.valueOf(totalPrice) + " Rs.");
                    finalTotalPrice[0] = (String) PriceTagtxt.getText();


                }
                if ((!checkBoxAirFreshner.isChecked())&&checkBoxSteamCleam.isChecked()) {


                    totalPrice = servicePrice+steamCleanPrice;
                    PriceTagtxt.setText("Total Amount: "+String.valueOf(totalPrice) + " Rs.");
                    finalTotalPrice[0] = (String) PriceTagtxt.getText();


                }

                if ((!checkBoxSteamCleam.isChecked())&&checkBoxAirFreshner.isChecked()) {


                    totalPrice = servicePrice+freshnerPrice;
                    PriceTagtxt.setText("Total Amount: "+String.valueOf(totalPrice) + " Rs.");
                    finalTotalPrice[0] = (String) PriceTagtxt.getText();


                }
                if (checkBoxAirFreshner.isChecked()&&checkBoxSteamCleam.isChecked()) {


                    totalPrice = servicePrice+freshnerPrice+steamCleanPrice;
                    PriceTagtxt.setText("Total Amount: "+String.valueOf(totalPrice) + " Rs.");
                    finalTotalPrice[0] = (String) PriceTagtxt.getText();

                }

            }

        });

        final String finalServiceProviderName;
        final String finalServiceName;

        finalServiceProviderName = value4;
        finalServiceName = value2;

        ImageButton imgB=(ImageButton)findViewById(R.id.GOTOBook);
        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(finalTime[0])){
                    Toast.makeText(CreateOrderActivity.this, "Please Specify Your Preffered Time", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(finalDate[0])){
                    Toast.makeText(CreateOrderActivity.this, "Please Select Suitable Date", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(finalTime[0]) && TextUtils.isEmpty(finalDate[0])){
                    Toast.makeText(CreateOrderActivity.this, "Please Specify Your Preffered Time and Date", Toast.LENGTH_SHORT).show();
                }
                else {

                    final AlertDialog.Builder confirmOrderDialog = new AlertDialog.Builder(v.getContext());
                    confirmOrderDialog.setTitle("Confirm Booking?");
                    confirmOrderDialog.setMessage("Select Yes to Confirm your Car Wash Booking Service.");


                    confirmOrderDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent intent = new Intent(CreateOrderActivity.this, OrderConfirmActivity.class);
                            intent.putExtra("ProviderName", finalServiceProviderName);
                            intent.putExtra("ServiceName", finalServiceName);
                            intent.putExtra("Time", finalTime[0]);
                            intent.putExtra("Date", finalDate[0]);
                            intent.putExtra("TotalPrice", finalTotalPrice[0]);

                            userID = fAuth.getCurrentUser().getUid();

                            email = "hina@gmail.com";
                            name="hina";



                            /*fStore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){

                                    }
                                }
                            });
*/

                           // progressBar.setVisibility(View.VISIBLE);
                            userID = fAuth.getCurrentUser().getUid();


                            Map<String,Object> order = new HashMap<>();
                            order.put("Name",name);
                            order.put("email",email);
                            order.put("ServiceProvider",finalServiceProviderName);
                            order.put("ServiceOredred",finalServiceName);
                            order.put("Time",finalTime[0]);
                            order.put("Date",finalDate[0]);
                            order.put("Total Amount",finalTotalPrice[0]);

                            fStore.collection("orders").add(order).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("TAG", "onSuccess: Order is  Created for "+userID);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateOrderActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                }
                            });



                        }
                    });

                    confirmOrderDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                // close the Dialog
                            //progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                    confirmOrderDialog.create().show();

                }
            }
        });

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

    public String formatTime(int hourOfDay, int minute) {

        String timeSet;
        if (hourOfDay > 12) {
            hourOfDay -= 12;
            timeSet = "PM";
        } else if (hourOfDay == 0) {
            hourOfDay += 12;
            timeSet = "AM";
        } else if (hourOfDay == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes;
        if (minute < 10)
            minutes = "0" + minute;
        else
            minutes = String.valueOf(minute);

        return String.valueOf(hourOfDay) + ':' + minutes + " " + timeSet;
    }
    /*public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
            mPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher=mPattern.matcher(dest);
            if(!matcher.matches())
                return "";
            return null;
        }

    }*/

}
