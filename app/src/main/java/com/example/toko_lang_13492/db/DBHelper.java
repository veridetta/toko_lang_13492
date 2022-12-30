package com.example.toko_lang_13492.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "CART";

    // Table columns
    public static final String _ID = "_id";
    public static final String NAMA = "nama";
    public static final String HARGA = "harga";
    public static final String IMG = "img";
    public static final String DESC = "deskripsi";
    public static final String QT = "qt";

    // Database Information
    static final String DB_NAME = "TOKO_LANG.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAMA + " TEXT," + DESC + " TEXT,"+ HARGA + " TEXT,"
            + IMG + " TEXT,"+ QT + " TEXT);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
