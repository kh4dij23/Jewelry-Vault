package com.example.jewelryvault;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "JewelryVault.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(username TEXT PRIMARY KEY, password TEXT)");
        db.execSQL("CREATE TABLE jewelry(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type TEXT, value TEXT, date_added TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS jewelry");
        onCreate(db);
    }

    // ---------- USER ----------
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        return db.insert("users", null, cv) != -1;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM users WHERE username=? AND password=?",
                new String[]{username, password}
        );
        return c.getCount() > 0;
    }

    public boolean resetPassword(String username, String newPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("password", newPass);
        return db.update("users", cv, "username=?", new String[]{username}) > 0;
    }

    // ---------- JEWELRY ----------
    public boolean addJewelry(String name, String type, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("type", type);
        cv.put("value", value);
        cv.put("date_added", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        return db.insert("jewelry", null, cv) != -1;
    }

    public Cursor getAllJewelry() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM jewelry ORDER BY id DESC", null);
    }

    public int getJewelryCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM jewelry", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public boolean deleteJewelry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("jewelry", "id=?", new String[]{String.valueOf(id)}) > 0;
    }
}