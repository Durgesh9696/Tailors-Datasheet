package com.durgeshparekh.tailorsdatasheet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.durgeshparekh.tailorsdatasheet.model.Bean_Customer;
import com.durgeshparekh.tailorsdatasheet.model.Bean_Pent;
import com.durgeshparekh.tailorsdatasheet.model.Bean_Shirt;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String Lock = "dblock";
    private static final int DATABASE_VERSION = 1; // play store version is 13 upgrade to 14

    // Put your Database name
    private static final String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/TailorsDataSheet/" + "tailorsdatasheet.db";
    private final Context context;

    //Table names
    private static final String TABLE_CUSTOMER = "Customer";
    private static final String TABLE_PENT = "Pent";
    private static final String TABLE_SHIRT = "Shirt";

    //define column of TABLE_CUSTOMER here
    private static final String CUSTOMER_ID = "Id";
    private static final String CUSTOMER_NAME = "Name";
    private static final String CUSTOMER_GENDER = "Gender";
    private static final String CUSTOMER_ADDRESS = "Address";
    private static final String CUSTOMER_CONTACT = "Contact";

    //define column of TABLE_PENT here
    private static final String PENT_ID = "Id";
    private static final String PENT_CUSTOMER_ID = "CustomerId";
    private static final String PENT_LENGTH = "Length";
    private static final String PENT_BOTTOM = "Bottom";
    private static final String PENT_WEST = "West";
    private static final String PENT_HIP = "Hip";
    private static final String PENT_FLY = "Fly";
    private static final String PENT_THIGH = "Thigh";
    private static final String PENT_THIGH_READY = "ThighReady";

    //define column of TABLE_SHIRT here
    private static final String SHIRT_ID = "Id";
    private static final String SHIRT_CUSTOMER_ID = "CustomerId";
    private static final String SHIRT_LENGTH = "Length";
    private static final String SHIRT_CHEST = "Chest";
    private static final String SHIRT_STOMACH = "Stomach";
    private static final String SHIRT_LOOSING = "Loosing";
    private static final String SHIRT_NECK = "Neck";
    private static final String SHIRT_SHOULDER = "Shoulder";
    private static final String SHIRT_SLEEVES = "Sleeves";

    //create customer data
    private static final String createTableCustomer = "CREATE TABLE " + TABLE_CUSTOMER + "(" +
            CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CUSTOMER_NAME + " TEXT," + CUSTOMER_GENDER +
            " TEXT," + CUSTOMER_ADDRESS + " TEXT," + CUSTOMER_CONTACT + " TEXT" + ")";

    //create pent data
    private static final String createTablePent = "CREATE TABLE " + TABLE_PENT + "(" +
            PENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PENT_CUSTOMER_ID + " INT," + PENT_LENGTH + " TEXT,"
            + PENT_BOTTOM + " TEXT," + PENT_WEST + " TEXT," + PENT_HIP + " TEXT,"+ PENT_FLY + " TEXT,"
            + PENT_THIGH + " TEXT,"+ PENT_THIGH_READY + " TEXT" + ")";

    //create shirt data
    private static final String createTableShirt = "CREATE TABLE " + TABLE_SHIRT + "(" +
            SHIRT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SHIRT_CUSTOMER_ID + " INT," + SHIRT_LENGTH + " TEXT,"
            + SHIRT_CHEST + " TEXT," + SHIRT_STOMACH + " TEXT," + SHIRT_LOOSING + " TEXT,"+ SHIRT_NECK + " TEXT,"
            + SHIRT_SHOULDER + " TEXT,"+ SHIRT_SLEEVES + " TEXT" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("11111", "onCreate: Db Called" );
        db.setLocale(Locale.ENGLISH);

        //CREATE TABLES
        db.execSQL(createTableCustomer);
        db.execSQL(createTablePent);
        db.execSQL(createTableShirt);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("2222", "onUpgrade: Called" );
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PENT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SHIRT);

        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL(context.getString(R.string.foreign_key_status));
            Cursor c = db.rawQuery("PRAGMA foreign_keys", null);
            c.moveToFirst();
            if (!c.isClosed()) {
                c.close();
            }
        }
    }

    void addCustomer(Bean_Customer beanCustomer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cvCustomer = new ContentValues();
        cvCustomer.put(CUSTOMER_NAME, beanCustomer.getName());
        cvCustomer.put(CUSTOMER_ADDRESS, beanCustomer.getAddress());
        cvCustomer.put(CUSTOMER_CONTACT, beanCustomer.getContact());
        cvCustomer.put(CUSTOMER_GENDER, beanCustomer.getGender());

        db.insert(TABLE_CUSTOMER, null, cvCustomer);

    }

    int getCustomerID(String contact) {
        int customerId = 0;

        synchronized (Lock) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_CUSTOMER, new String[]{CUSTOMER_ID}, CUSTOMER_CONTACT + "=?",
                    new String[]{contact}, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    customerId = cursor.getInt(0);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        }
        return customerId;
    }

    void addSizeData(Bean_Pent beanPent, Bean_Shirt beanShirt) {
        SQLiteDatabase db = this.getWritableDatabase();

        //insert pent data
        ContentValues cvPent = new ContentValues();
        cvPent.put(PENT_CUSTOMER_ID, beanPent.getCustomerID());
        cvPent.put(PENT_LENGTH, beanPent.getPent_length());
        cvPent.put(PENT_BOTTOM, beanPent.getBottom());
        cvPent.put(PENT_WEST, beanPent.getWest());
        cvPent.put(PENT_HIP, beanPent.getHip());
        cvPent.put(PENT_FLY, beanPent.getFly());
        cvPent.put(PENT_THIGH, beanPent.getThigh());
        cvPent.put(PENT_THIGH_READY, beanPent.getThighReady());

        db.insert(TABLE_PENT, null, cvPent);

        // insert shirt data
        ContentValues cvShirt = new ContentValues();
        cvShirt.put(SHIRT_CUSTOMER_ID, beanShirt.getCustomerID());
        cvShirt.put(SHIRT_LENGTH, beanShirt.getShirt_length());
        cvShirt.put(SHIRT_CHEST, beanShirt.getChest());
        cvShirt.put(SHIRT_STOMACH, beanShirt.getStomach());
        cvShirt.put(SHIRT_LOOSING, beanShirt.getLoosing());
        cvShirt.put(SHIRT_NECK, beanShirt.getNeck());
        cvShirt.put(SHIRT_SHOULDER, beanShirt.getShoulder());
        cvShirt.put(SHIRT_SLEEVES, beanShirt.getSleves());

        db.insert(TABLE_SHIRT, null, cvShirt);

        db.close();

    }

    List<Bean_Customer> getAllCustomerList() {
        List<Bean_Customer> customerList = new ArrayList<>();

        synchronized (Lock) {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectMaster = "SELECT * FROM " + TABLE_CUSTOMER;
            Cursor cursor = db.rawQuery(selectMaster, null);

            if (cursor.moveToFirst()) {
                do {
                    Bean_Customer beanCustomer = new Bean_Customer();
                    beanCustomer.setCustomer_id(cursor.getInt(0));
                    beanCustomer.setName(cursor.getString(1));
                    beanCustomer.setGender(cursor.getString(2));
                    beanCustomer.setAddress(cursor.getString(3));
                    beanCustomer.setContact(cursor.getString(4));

                    customerList.add(beanCustomer);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return customerList;
    }

    void deleteData(String customer_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //delete customer from customer table
        db.delete(TABLE_CUSTOMER, CUSTOMER_ID + "=?", new String[]{customer_id});
        //delete pent data of customer from pent table
        db.delete(TABLE_PENT, PENT_CUSTOMER_ID + "=?", new String[]{customer_id});
        //delete shirt data of customer from shirt table
        db.delete(TABLE_SHIRT, SHIRT_CUSTOMER_ID + "=?", new String[]{customer_id});

        db.close();
    }

    public void updateCustomerData(Bean_Customer beanCustomer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_NAME, beanCustomer.getName());
        cv.put(CUSTOMER_CONTACT, beanCustomer.getContact());
        cv.put(CUSTOMER_GENDER, beanCustomer.getGender());
        cv.put(CUSTOMER_ADDRESS, beanCustomer.getAddress());

        db.update(TABLE_CUSTOMER, cv,CUSTOMER_ID + "=?", new String[]{String.valueOf(beanCustomer.getCustomer_id())});

    }

    public Bean_Pent getPentDetailById(int customerId) {
        Bean_Pent beanPent = new Bean_Pent();

        synchronized (Lock) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_PENT, new String[]{PENT_LENGTH, PENT_BOTTOM, PENT_WEST, PENT_HIP, PENT_FLY,
                    PENT_THIGH, PENT_THIGH_READY}, PENT_CUSTOMER_ID + "=?",
                    new String[]{String.valueOf(customerId)}, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    beanPent.setPent_length(cursor.getString(0));
                    beanPent.setBottom(cursor.getString(1));
                    beanPent.setWest(cursor.getString(2));
                    beanPent.setHip(cursor.getString(3));
                    beanPent.setFly(cursor.getString(4));
                    beanPent.setThigh(cursor.getString(5));
                    beanPent.setThighReady(cursor.getString(6));

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        }
        return beanPent;
    }

    void updatePentDetails(Bean_Pent bean_pent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PENT_LENGTH, bean_pent.getPent_length());
        cv.put(PENT_BOTTOM, bean_pent.getBottom());
        cv.put(PENT_WEST, bean_pent.getWest());
        cv.put(PENT_HIP, bean_pent.getHip());
        cv.put(PENT_FLY, bean_pent.getFly());
        cv.put(PENT_THIGH, bean_pent.getThigh());
        cv.put(PENT_THIGH_READY, bean_pent.getThighReady());

        db.update(TABLE_PENT, cv,PENT_CUSTOMER_ID + "=?",
                    new String[]{
                        String.valueOf(bean_pent.getCustomerID())
                    });
    }

    Bean_Shirt getShirtDetailById(int customerId) {
        Bean_Shirt beanShirt = new Bean_Shirt();

        synchronized (Lock) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_SHIRT, new String[]{SHIRT_LENGTH, SHIRT_CHEST, SHIRT_STOMACH, SHIRT_LOOSING,
                            SHIRT_NECK, SHIRT_SHOULDER, SHIRT_SLEEVES}, SHIRT_CUSTOMER_ID + "=?",
                    new String[]{String.valueOf(customerId)}, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    beanShirt.setShirt_length(cursor.getString(0));
                    beanShirt.setChest(cursor.getString(1));
                    beanShirt.setStomach(cursor.getString(2));
                    beanShirt.setLoosing(cursor.getString(3));
                    beanShirt.setNeck(cursor.getString(4));
                    beanShirt.setShoulder(cursor.getString(5));
                    beanShirt.setSleves(cursor.getString(6));

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        }
        return beanShirt;
    }

    public void updateShirtDetails(Bean_Shirt bean_shirt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(SHIRT_LENGTH, bean_shirt.getShirt_length());
        cv.put(SHIRT_CHEST, bean_shirt.getChest());
        cv.put(SHIRT_STOMACH, bean_shirt.getStomach());
        cv.put(SHIRT_LOOSING, bean_shirt.getLoosing());
        cv.put(SHIRT_NECK, bean_shirt.getNeck());
        cv.put(SHIRT_SHOULDER, bean_shirt.getShoulder());
        cv.put(SHIRT_SLEEVES, bean_shirt.getSleves());

        db.update(TABLE_SHIRT, cv,SHIRT_CUSTOMER_ID + "=?",
                new String[]{
                        String.valueOf(bean_shirt.getCustomerID())
                });

    }
}
