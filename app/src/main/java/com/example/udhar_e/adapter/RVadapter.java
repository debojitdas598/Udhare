package com.example.udhar_e.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udhar_e.R;
import com.example.udhar_e.UpdateUdhar;
import com.example.udhar_e.getsetgo.getset;
import com.example.udhar_e.handler.DBHandler;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Locale;
//adapter class for recyclerview

public class RVadapter extends RecyclerView.Adapter<RVadapter.ViewHolder> implements Filterable {

    private ArrayList<getset> udharArrayList;
    ArrayList<getset> backup;
    private Context context;


    public RVadapter(ArrayList<getset> udharArrayList,Context context) {
        this.udharArrayList=udharArrayList;
        this.context=context;
        backup = new ArrayList<>(udharArrayList);
    }

    public void filterList(ArrayList<getset> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        udharArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.udhar_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        getset modal = udharArrayList.get(position);
        holder.NameTV.setText(modal.getName());
        holder.AmountTV.setText("Amount : ₹"+ modal.getAmount());
        holder.DateTV.setText("Date : "+ modal.getDate());
        holder.DescTV.setText("Details : "+ modal.getDesc());

        if(modal.getType().equals("Credit")==true)
        {
            holder.type.setImageResource(R.drawable.cred);
        }

        else if(modal.getType().equals("Debt")==false)
        {
            holder.type.setImageResource(R.drawable.debt);
        }

        else
        {
            holder.type.setImageResource(R.drawable.debt);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, UpdateUdhar.class);

                i.putExtra("id",modal.getId());
                i.putExtra("name",modal.getName());
                i.putExtra("amount",modal.getAmount());
                i.putExtra("date",modal.getDate());
                i.putExtra("desc",modal.getDesc());
                i.putExtra("type",modal.getType());


                context.startActivity(i);
            }




        });

        //delete single record from recyclerview
       holder.del.setOnClickListener(view -> {

           AlertDialog.Builder builder = new AlertDialog.Builder(context);
           builder.setTitle("Confirmation!");
           builder.setMessage("Are you sure to delete "+modal.getName()+" ?");
           builder.setCancelable(false);
           builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   DBHandler dbHandler = new DBHandler(context);
                   int result = dbHandler.deletion(modal.getName());

                   if(result>0){
                       Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                       udharArrayList.remove(modal);
                       notifyDataSetChanged();
                   }
                   else
                   {
                       Toast.makeText(context,"Failed to delete",Toast.LENGTH_SHORT).show();
                   }
               }
           });
           builder.setNegativeButton("No",null);
           builder.show();

       });


    }

    //function to delete one record from database
    public void delete(int position){
        udharArrayList.remove(position);
        notifyItemRemoved(position);
    }





    @Override
    public int getItemCount() {
        return udharArrayList.size();
    }


    //background operation for search funtion
    @Override
    public Filter getFilter() {
        return filter;
    }
    //background thread
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<getset> filtereddata = new ArrayList<>();
            if(keyword.toString().isEmpty())
                filtereddata.addAll(backup);
            else{
                for(getset obj : backup){
                    if(obj.getName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }
            FilterResults results = new FilterResults();
            results.values=filtereddata;
            return results;
        }
        //main ui thread
        @Override
        protected void publishResults(CharSequence keyword, FilterResults results) {
            udharArrayList.clear();
            udharArrayList.addAll((ArrayList<getset>)results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView  NameTV,AmountTV,DescTV,DateTV;
        private ImageView del,type;
        private RVadapter rVadapter;
        private CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            NameTV = itemView.findViewById(R.id.nameId);
            AmountTV = itemView.findViewById(R.id.amountId);
            DateTV = itemView.findViewById(R.id.dateId);
            DescTV = itemView.findViewById(R.id.descId);
            del = itemView.findViewById(R.id.deletebtn);
            type = itemView.findViewById(R.id.typeindi);


        }
    }




}
