package com.example.toko_lang_13492.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.toko_lang_13492.Cart;
import com.example.toko_lang_13492.Produk;

import java.util.ArrayList;

public class DBManager {

    private DBHelper dbHelper;

    private Context context;

    private SQLiteDatabase database,dbopen;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        dbopen = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String nama, String harga, String img, String qt, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.NAMA, nama);
        contentValue.put(DBHelper.HARGA, harga);
        contentValue.put(DBHelper.IMG, img);
        contentValue.put(DBHelper.QT, qt);
        contentValue.put(DBHelper.DESC, desc);
        database.insert(DBHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DBHelper._ID, DBHelper.NAMA, DBHelper.DESC, DBHelper.HARGA, DBHelper.QT, DBHelper.IMG };
        Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public ArrayList<Cart> readCart() {
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = dbopen.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<Cart> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new Cart(cursorCourses.getString(1),"",cursorCourses.getString(3),
                        cursorCourses.getString(2),cursorCourses.getString(4),cursorCourses.getString(5),cursorCourses.getString(0)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }
    public int update(long _id, String qt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.QT, qt);
        int i = database.update(DBHelper.TABLE_NAME, contentValues, DBHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DBHelper.TABLE_NAME, DBHelper._ID + "=" + _id, null);
    }

}
