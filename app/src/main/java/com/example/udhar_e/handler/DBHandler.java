package com.example.udhar_e.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;

import com.example.udhar_e.getsetgo.getset;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

        private static final String DB_NAME = "udhar";
        private static final int DB_VERSION = 3;
        private static final String TABLE_NAME = "udhar_table";
        private static final String ID_COL = "id";
        private static final String NAME_COL = "name";
        private static final String AMOUNT_COL = "amount";
        private static final String DATE_COL = "date";
        private static final String DESC_COL = "description";
        private static final String TYPE_COL = "type";
        


        public DBHandler(Context context){
            super(context,DB_NAME,null,DB_VERSION);
        }



    @Override
    public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE " + TABLE_NAME + "("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NAME_COL + " TEXT,"
                    + AMOUNT_COL + " TEXT,"
                    + DATE_COL + " TEXT,"
                    + DESC_COL + " TEXT,"
                    + TYPE_COL + " TEXT NOT NULL" + ");";

        db.execSQL(query);
    }


    public void addNew(String name,String amount,String date,String desc, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL,name);
        values.put(AMOUNT_COL,amount);
        values.put(DATE_COL,date);
        values.put(DESC_COL,desc);
        values.put(TYPE_COL,type);

        db.insert(TABLE_NAME,null,values);
        db.close();


    }

    public ArrayList<getset> read(){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);

        ArrayList<getset> contactArrayList = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                contactArrayList.add(new getset(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return contactArrayList;
        }

        public void update(String ogname,String name,String amount,String date, String desc, String type)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(NAME_COL,name);
            values.put(AMOUNT_COL,amount);
            values.put(DESC_COL,desc);
            values.put(DATE_COL,date);
            values.put(TYPE_COL,type);




            db.update(TABLE_NAME,values,"name=?",new String[]{ogname});
            db.close();
        }


    public int deletion(String name) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "name=?", new String[]{name});
        db.close();
        return 1;
    }

    public int deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
        return 1;

    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
