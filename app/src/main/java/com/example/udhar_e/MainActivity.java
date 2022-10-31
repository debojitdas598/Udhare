package com.example.udhar_e;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.udhar_e.getsetgo.getset;
import com.example.udhar_e.handler.DBHandler;

import java.util.ArrayList;
import java.util.Calendar;
//this class takes input for the data

public class MainActivity extends AppCompatActivity {

    private EditText name, amount, date, desc;
    private Button addbtn, viewbtn;
    private RadioGroup rgtyp;
    private RadioButton selected;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), ViewData.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHandler dbHandler = new DBHandler(MainActivity.this);
        name = findViewById(R.id.idName);
        amount = findViewById(R.id.idAmount);
        date = findViewById(R.id.idDate);
        desc = findViewById(R.id.idDetails);
        addbtn = findViewById(R.id.idAdd);
        viewbtn = findViewById(R.id.idView);
        rgtyp = findViewById(R.id.rgtype);
        RadioButton inicheck = findViewById(R.id.cred);
//        rgtyp.check(R.id.cred);



        //action when add udhar button is clicked
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name1 = name.getText().toString();
                String amount1 = amount.getText().toString();
                String date1 = date.getText().toString();
                String desc1 = desc.getText().toString();
                int checkedid = rgtyp.getCheckedRadioButtonId();
                String checkbtn ="";
                if(checkedid != -1) {
                    selected = findViewById(checkedid);

                     checkbtn = selected.getText().toString();
                }


                if (name1.isEmpty() || amount1.isEmpty()|| checkedid <0) {

                    Toast.makeText(MainActivity.this, "Please enter name, amount, credit/debt", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    dbHandler.addNew(name1, amount1, date1, desc1, checkbtn);
                    Toast.makeText(MainActivity.this, "The Udhaar has been added", Toast.LENGTH_SHORT).show();

                    name.setText("");
                    amount.setText("");
                    date.setText("");
                    desc.setText("");
                    rgtyp.clearCheck();
                }

            }


        });



        //intent to go to viewdata activity
        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity via a intent.
                Intent i = new Intent(getApplicationContext(), ViewData.class);
                startActivity(i);


            }
        });


        //opens dialog box in date section
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog =new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });



// //        log cat
//        ArrayList<getset> a = dbHandler.read();
//        for (getset contact : a) {
//            Log.d("dbharry",
//                    "Name: " + contact.getName() + "\n" +
//                    "Amount:  " + contact.getAmount() + "\n"+
//                    "Date: " + contact.getDate() + "\n"+
//                    "Description" + contact.getDesc());
        }
    }







