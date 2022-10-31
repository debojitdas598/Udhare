package com.example.udhar_e;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;


import com.example.udhar_e.adapter.RVadapter;
import com.example.udhar_e.getsetgo.getset;
import com.example.udhar_e.handler.DBHandler;


import java.util.ArrayList;

//this class shows the data in cardview
public class ViewData extends AppCompatActivity {

    private ArrayList<getset> UdharList;
    private DBHandler dbHandler;
    private RVadapter rVadapter;
    private RecyclerView udharRV;
    ImageView del;
    ImageView add;

    //exit application tapping back twice
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);


        UdharList = new ArrayList<>();
        dbHandler = new DBHandler(ViewData.this);

        UdharList = dbHandler.read();

        rVadapter = new RVadapter(UdharList,ViewData.this);
        udharRV = findViewById(R.id.idRVCourses);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewData.this,RecyclerView.VERTICAL,false);
        udharRV.setLayoutManager(linearLayoutManager);
        udharRV.setHasFixedSize(true);

        udharRV.setAdapter(rVadapter);
        del = findViewById(R.id.deletebtn);

        //add button intent
        add =  findViewById(R.id.addbtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewData.this,MainActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflates the menu(delete-all button & seacrh button)
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem search=menu.findItem(R.id.actionSearch);
        MenuItem delall = menu.findItem(R.id.deleteall);

        //delete all records
        delall.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d("del","It works");
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewData.this);
                builder.setTitle("Confirmation!");
                builder.setMessage("Are you sure to delete ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int k) {
                        dbHandler.deleteAll();

                        int size = rVadapter.getItemCount();
                        if (size > 0) {
                            for (int i = 0; i < size; i++) {
                                rVadapter.delete(0);
                            }

                            rVadapter.notifyItemRangeRemoved(0, size);
                        }

                    }
                });
                builder.setNegativeButton("NO",null);
                builder.show();
                return false;
            }
        });

        //search tool
        SearchView searchView = (SearchView)search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                rVadapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}