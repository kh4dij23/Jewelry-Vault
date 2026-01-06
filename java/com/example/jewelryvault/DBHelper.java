package com.example.jewelryvault;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "JewelryVault.db";
    private static final int DATABASE_VERSION = 2;

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_JEWELRY = "jewelry";

    // User table columns
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    // Jewelry table columns
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_TYPE = "type";
    private static final String COL_VALUE = "value";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COL_USERNAME + " TEXT PRIMARY KEY,"
                + COL_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        // Create jewelry table
        String createJewelryTable = "CREATE TABLE " + TABLE_JEWELRY + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT NOT NULL,"
                + COL_TYPE + " TEXT NOT NULL,"
                + COL_VALUE + " TEXT NOT NULL)";
        db.execSQL(createJewelryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JEWELRY);
        onCreate(db);
    }

    // ---------- USER OPERATIONS ----------
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_PASSWORD, password);
        return db.insert(TABLE_USERS, null, cv) != -1;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_USERNAME};
        String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean resetPassword(String username, String newPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PASSWORD, newPass);

        String whereClause = COL_USERNAME + " = ?";
        String[] whereArgs = {username};

        return db.update(TABLE_USERS, cv, whereClause, whereArgs) > 0;
    }

    // ---------- JEWELRY OPERATIONS ----------
    public boolean addJewelry(String name, String type, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_TYPE, type);
        cv.put(COL_VALUE, value);
        return db.insert(TABLE_JEWELRY, null, cv) != -1;
    }

    public Cursor getAllJewelry() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_JEWELRY + " ORDER BY " + COL_NAME, null);
    }

    // New method to get jewelry count
    public int getJewelryCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_JEWELRY, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    // Close database properly
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}