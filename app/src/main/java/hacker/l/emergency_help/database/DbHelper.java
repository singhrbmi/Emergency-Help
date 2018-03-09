package hacker.l.emergency_help.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;


/**
 * Created by user on 11/24/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = Contants.DATABASE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS userData");
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_UserData_TABLE = "CREATE TABLE userData(loginId INTEGER,Username TEXT,UserPhone TEXT,EmailId TEXT,Password TEXT)";
        db.execSQL(CREATE_UserData_TABLE);
    }

    //    // --------------------------user Data---------------
    public boolean upsertUserData(Result ob) {
        boolean done = false;
        Result data = null;
        if (ob.getLoginId() != 0) {
            data = getUserDataByLoginId(ob.getLoginId());
            if (data == null) {
                done = insertUserData(ob);
            } else {
                done = updateUserData(ob);
            }
        }
        return done;
    }


    //    // for user data..........
    private void populateUserData(Cursor cursor, Result ob) {
        ob.setLoginId(cursor.getInt(0));
        ob.setUsername(cursor.getString(1));
        ob.setUserPhone(cursor.getString(2));
        ob.setEmailId(cursor.getString(3));
        ob.setPassword(cursor.getString(4));
    }

    // insert userData data.............
    public boolean insertUserData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("loginId", ob.getLoginId());
        values.put("Username", ob.getUsername());
        values.put("UserPhone", ob.getUserPhone());
        values.put("EmailId", ob.getEmailId());
        values.put("Password", ob.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("userData", null, values);
        db.close();
        return i > 0;
    }

    //    user data
    public Result getUserData() {

        String query = "Select * FROM userData";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateUserData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //
//    //show  user list data
    public List<Result> getAllUserData() {
        ArrayList list = new ArrayList<>();
        String query = "Select * FROM userData";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateUserData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //  get user data
    public Result getUserDataByLoginId(int id) {

        String query = "Select * FROM userData WHERE loginId = " + id + " ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateUserData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //    update  data
    public boolean updateUserData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("loginId", ob.getLoginId());
        values.put("Username", ob.getUsername());
        values.put("UserPhone", ob.getUserPhone());
        values.put("EmailId", ob.getEmailId());
        values.put("Password", ob.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("userData", values, "loginId = " + ob.getLoginId() + " ", null);

        db.close();
        return i > 0;
    }

    // delete user Data
    public boolean deleteUserData() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("userData", null, null);
        db.close();
        return result;
    }
}
