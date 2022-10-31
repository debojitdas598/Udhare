package com.example.udhar_e;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.udhar_e.handler.DBHandler;

import java.util.Calendar;

public class UpdateUdhar extends AppCompatActivity {
    private EditText nameedt,amountedt,dateedt,descedt;
    private int id;
    private RadioGroup typeedt;
    private int checked;
    private RadioButton selected;
    private Button updatebtn;
    private DBHandler dbHandler;
    String name,amount,date,desc,type;


    //goes back to refreshed viewdata.class on pressing back button
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), ViewData.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_udhar);

        nameedt = findViewById(R.id.idEdtName);
        amountedt=findViewById(R.id.idEdtAmount);
        dateedt = findViewById(R.id.idEdtDate);
        descedt = findViewById(R.id.idEdtDescription);
        typeedt = findViewById(R.id.rgtypeedt);
        updatebtn = findViewById(R.id.idBtnUpdate);

        //getting data from handler
        dbHandler = new DBHandler(UpdateUdhar.this);
        id = getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("name");
        amount = getIntent().getStringExtra("amount");
        date = getIntent().getStringExtra("date");
        desc = getIntent().getStringExtra("desc");
        type = getIntent().getStringExtra("type");

        dateedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog =new DatePickerDialog(
                        UpdateUdhar.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateedt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });


        //adding the data to edittext boxes
        nameedt.setText(name);
        dateedt.setText(date);
        amountedt.setText(amount);
        descedt.setText(desc);
        if(type.equals("Credit"))
            typeedt.check(R.id.crededt);
        else
            typeedt.check(R.id.debtedt);

        checked = typeedt.getCheckedRadioButtonId();
        selected = findViewById(checked);
        String slct = selected.getText().toString();



        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sending updated data to dbhandler(note: it does not update the credit/debit status)

                if(nameedt.getText().toString() != "" && amountedt.getText().toString() != "")
                {
                    dbHandler.update(name, nameedt.getText().toString(), amountedt.getText().toString(), dateedt.getText().toString(), descedt.getText().toString(), slct);

                    Toast.makeText(UpdateUdhar.this, "Course Updated..", Toast.LENGTH_SHORT).show();
                    //switches to viewdata activity after update is clicked
                    Intent i = new Intent(UpdateUdhar.this, ViewData.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(UpdateUdhar.this, "Add name and amount both", Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });

    }
}