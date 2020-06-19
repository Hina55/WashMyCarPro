package com.hina.washmycarpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.firebase.auth.FirebaseAuth;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.Calendar;

public class CreateOrderActivity extends AppCompatActivity {

    String value, value2, value3;
    TextView serviceDescription,aboutText, PriceTagtxt;
    EditText datetxt,timetxt;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    String mTime,mDate;
    CheckBox checkBoxAirFreshner,checkBoxSteamCleam;
    int totalPrice;
    int steamCleanPrice = 500;
    int freshnerPrice = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1F7FB8")));
        getSupportActionBar().setTitle("Create Order");

        timetxt = findViewById(R.id.time);
        datetxt = findViewById(R.id.date);

        timetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               timePickerDialog = new TimePickerDialog(CreateOrderActivity.this,R.style.DateTimePickerTheme,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timetxt.setText(formatTime(hourOfDay, minute));
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
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        /*datetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(CreateOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        datetxt.setText(dayOfMonth+ "/"+month+"/"+year);
                    }
                },0,0,0);

                datePickerDialog.show();
            }
        });*/

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


        serviceDescription=findViewById(R.id.servicedesp);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            value= extras.getString("ServiceDescription");
            value2 = extras.getString("AboutService");
            value3 = extras.getString("PriceTag");
        }
        serviceDescription.setText(value);
        aboutText = findViewById(R.id.abouttxt);
        aboutText.setText(value2);
        /*carnumbtwo = findViewById(R.id.carnumbertwo);
        carnumberfurther = findViewById(R.id.carNumberFurther);
        carnumbtwo.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2,2)});
        carnumberfurther.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});*/
        PriceTagtxt = findViewById(R.id.pricetxt);
        PriceTagtxt.setText(value3 + " Rs.");

        checkBoxAirFreshner = (CheckBox) findViewById(R.id.airFreshnerCheck);
        checkBoxSteamCleam = (CheckBox) findViewById(R.id.steamCleanCheck);
        final int servicePrice = Integer.parseInt(value3);

        checkBoxAirFreshner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxAirFreshner.isChecked()){

                    totalPrice = servicePrice+freshnerPrice;
                    PriceTagtxt.setText(String.valueOf(totalPrice)+" Rs.");
                }
                else if(!checkBoxAirFreshner.isChecked()){

                    PriceTagtxt.setText(String.valueOf(value3)+ " Rs.");
                }

            }

        });
        checkBoxSteamCleam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxSteamCleam.isChecked()){
                    totalPrice = servicePrice+steamCleanPrice;
                    PriceTagtxt.setText(String.valueOf(totalPrice) + " Rs.");
                }
                else if(!checkBoxAirFreshner.isChecked()){

                    PriceTagtxt.setText(String.valueOf(value3) + " Rs.");
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
