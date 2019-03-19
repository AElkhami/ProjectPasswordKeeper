package com.elkhamitech.data.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elkhamitech.data.model.EntryModel;
import com.elkhamitech.data.model.SubEntryModel;
import com.elkhamitech.data.model.UserModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ahmed on 1/5/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    Context context;

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PassKeeper";
    //-----------------------------------------------------------------
    // Table Name
    private static final String TABLE_USER= "user";

    // Column names
    private static final String ROW_ID = "row_id";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String PIN = "pin";

    // Table Name
    private static final String TABLE_CONTACT = "contact_email";

    // Column names
    private static final String C_ROW_ID = "c_row_id";
    private static final String C_NAME = "c_name";
    private static final String C_USER_NAME = "c_user_name";
    private static final String C_PASSWORD = "c_password";
    private static final String C_WEBSITE = "c_website";
    private static final String C_NOTE = "c_note";
    private static final String C_CREATED = "c_created";
    private static final String C_USER_CONTACT = "c_user_contact";


    // Table Name
    private static final String TABLE_SUB_CONTACT = "sub_contact";

    // Column names
    private static final String S_ROW_ID = "s_row_id";
    private static final String S_NAME = "s_name";
    private static final String S_USER_NAME = "s_user_name";
    private static final String S_PASSWORD = "s_password";
    private static final String S_WEBSITE = "s_website";
    private static final String S_NOTE = "s_note";
    private static final String S_CREATED = "s_created";
    private static final String S_PARENT_CONTACT = "s_sub_contact";

//-----------------------------------------------------------------
// Table Create Statements
// USER table create statement
private static final String CREATE_TABLE_USER = "CREATE TABLE "
        + TABLE_USER + "("
        + ROW_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
        + USER_NAME + " TEXT,"
        + PASSWORD + " TEXT,"
        + EMAIL + " TEXT,"
        + PIN + " TEXT"+ ")";
// USER table create statement
    private static final String CREATE_TABLE_CONTACT = "CREATE TABLE "
        + TABLE_CONTACT + "("
        + C_ROW_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
        + C_NAME + " TEXT,"
        + C_USER_NAME + " TEXT,"
        + C_PASSWORD + " TEXT,"
        + C_WEBSITE + " TEXT,"
        + C_NOTE + " TEXT,"
        + C_CREATED + " DATETIME,"
        + C_USER_CONTACT + " INTEGER" +")";
    // USER table create statement
    private static final String CREATE_TABLE_SUB_CONTACT = "CREATE TABLE "
            + TABLE_SUB_CONTACT + "("
            + S_ROW_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
            + S_NAME + " TEXT,"
            + S_USER_NAME + " TEXT,"
            + S_PASSWORD + " TEXT,"
            + S_WEBSITE + " TEXT,"
            + S_NOTE + " TEXT,"
            + S_CREATED + " DATETIME,"
            + S_PARENT_CONTACT + " INTEGER" +")";



//==================================================================================================

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating the tables

        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CONTACT);
        db.execSQL(CREATE_TABLE_SUB_CONTACT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CONTACT);

        // create new tables
        onCreate(db);
    }

//==================================================================================================
//-----------------------------------UserCrud Functions-------------------------------------------------


    //insert new row

    public long createUser (UserModel user){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME, user.getUser_name());
        values.put(PASSWORD, user.getPassword());
        values.put(EMAIL, user.getEmail());
        values.put(PIN, user.getPin());

        user.setRow_id(db.insert(TABLE_USER, null, values)) ;

        return user.getRow_id();
    }

    public long createContact (EntryModel eModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(C_USER_CONTACT, eModel.getUserId());
        values.put(C_NAME, eModel.getName());
        values.put(C_USER_NAME, eModel.getUserName());
        values.put(C_PASSWORD, eModel.getPassword());
        values.put(C_WEBSITE, eModel.getWebsite());
        values.put(C_NOTE, eModel.getNote());
        values.put(C_CREATED, getDateTime());

        eModel.setRowId(db.insert(TABLE_CONTACT, null, values)); ;

        return eModel.getRowId();
    }

    public void createSubContact (SubEntryModel Scontact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(S_PARENT_CONTACT, Scontact.getParentId());
        values.put(S_NAME, Scontact.getName());
        values.put(S_USER_NAME, Scontact.getUserName());
        values.put(S_PASSWORD, Scontact.getPassword());
        values.put(S_WEBSITE, Scontact.getWebsite());
        values.put(S_NOTE, Scontact.getNote());
        values.put(S_CREATED, getDateTime());

        db.insert(TABLE_SUB_CONTACT, null, values) ;
    }



    //Retrieving one Column--------------------------------------------------------------------------------

    public UserModel getRegisteredUser(String Email, String Password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER
                + " WHERE " + EMAIL + " = '" + Email + "'"
                + " AND " + PASSWORD + " = '" + Password + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

//        String[] coloumns = { EMAIL, PASSWORD };
//        Cursor c = db.query(TABLE_USER, coloumns, null, null,
//                null, null, null);
        UserModel user = new UserModel();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    user.setRow_id(cursor.getLong(cursor.getColumnIndex(ROW_ID)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return user;
    }

    public UserModel getRegisteredUser(String Email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER
                + " WHERE " + EMAIL + " = '" + Email + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

//        String[] coloumns = { EMAIL, PASSWORD };
//        Cursor c = db.query(TABLE_USER, coloumns, null, null,
//                null, null, null);
        UserModel user = new UserModel();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    user.setRow_id(cursor.getLong(cursor.getColumnIndex(ROW_ID)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return user;
    }

    public UserModel getPinUser (String pin) {
        SQLiteDatabase db = this.getReadableDatabase();

//        String selectQuery = "SELECT * FROM " + TABLE_USER
//                + " WHERE " + PIN + " = '" + pin + "'";
//
//        Cursor cursor = db.rawQuery(selectQuery, null);

        String[] columns = { ROW_ID, EMAIL, PASSWORD, PIN };
        Cursor cursor = db.query(TABLE_USER, columns, "pin=?", new String[]{pin},
                null, null, null);
        UserModel user = new UserModel();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    user.setRow_id(cursor.getLong(cursor.getColumnIndex(ROW_ID)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                    user.setPin(cursor.getString(cursor.getColumnIndex(PIN)));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return user;
    }

    public EntryModel getOneContact (long c_row_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACT
                + " WHERE " + C_ROW_ID + " = '" + c_row_id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

//        String[] coloumns = { EMAIL, PASSWORD };
//        Cursor c = db.query(TABLE_USER, coloumns, null, null,
//                null, null, null);
        EntryModel mail = new EntryModel();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    mail.setName(cursor.getString(cursor.getColumnIndex(C_NAME)));
                    mail.setUserName(cursor.getString(cursor.getColumnIndex(C_USER_NAME)));
                    mail.setPassword(cursor.getString(cursor.getColumnIndex(C_PASSWORD)));
                    mail.setWebsite(cursor.getString(cursor.getColumnIndex(C_WEBSITE)));
                    mail.setNote(cursor.getString(cursor.getColumnIndex(C_NOTE)));
                    mail.setCreatedAt(cursor.getString(cursor.getColumnIndex(C_CREATED)));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return mail;
    }

    public SubEntryModel getOneSubContact (long s_row_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SUB_CONTACT
                + " WHERE " + S_ROW_ID + " = '" + s_row_id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

//        String[] coloumns = { EMAIL, PASSWORD };
//        Cursor c = db.query(TABLE_USER, coloumns, null, null,
//                null, null, null);
        SubEntryModel SubC = new SubEntryModel();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    SubC.setName(cursor.getString(cursor.getColumnIndex(S_NAME)));
                    SubC.setUserName(cursor.getString(cursor.getColumnIndex(S_USER_NAME)));
                    SubC.setPassword(cursor.getString(cursor.getColumnIndex(S_PASSWORD)));
                    SubC.setWebsite(cursor.getString(cursor.getColumnIndex(S_WEBSITE)));
                    SubC.setNote(cursor.getString(cursor.getColumnIndex(S_NOTE)));
                    SubC.setCreatedAt(cursor.getString(cursor.getColumnIndex(S_CREATED)));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return SubC;
    }

    //retriving all columns--------------------------------------------------------------------------------

    public List<UserModel> getAllUsers(){
        List<UserModel> Users = new ArrayList<UserModel>();
        String selectQuery = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel();
                user.setRow_id(cursor.getInt((cursor.getColumnIndex(ROW_ID))));
                user.setUser_name((cursor.getString(cursor.getColumnIndex(USER_NAME))));
                user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));

                // adding to the list
                Users.add(user);
            } while (cursor.moveToNext());
        }
        return Users;
    }


    public List<EntryModel> getContacts(long userId){
        List<EntryModel> Mail = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACT + " Where "+C_USER_CONTACT+" = '"+userId+"' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EntryModel eMail = new EntryModel();
                eMail.setRowId((cursor.getLong(cursor.getColumnIndex(C_ROW_ID))));
                eMail.setName(cursor.getString((cursor.getColumnIndex(C_NAME))));
                eMail.setUserName((cursor.getString(cursor.getColumnIndex(C_USER_NAME))));
                eMail.setPassword(cursor.getString(cursor.getColumnIndex(C_PASSWORD)));
                eMail.setWebsite(cursor.getString(cursor.getColumnIndex(C_WEBSITE)));
                eMail.setNote(cursor.getString(cursor.getColumnIndex(C_NOTE)));
                eMail.setCreatedAt(cursor.getString(cursor.getColumnIndex(C_CREATED)));
                // adding to the list
                Mail.add(eMail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return Mail;
    }


    public List<SubEntryModel> getSubContacts(long parentId){
        List<SubEntryModel> SubContact = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SUB_CONTACT + " Where "+S_PARENT_CONTACT+" = '"+parentId+"' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubEntryModel sub = new SubEntryModel();
                sub.setRowId((cursor.getLong(cursor.getColumnIndex(S_ROW_ID))));
                sub.setName(cursor.getString((cursor.getColumnIndex(S_NAME))));
                sub.setUserName((cursor.getString(cursor.getColumnIndex(S_USER_NAME))));
                sub.setPassword(cursor.getString(cursor.getColumnIndex(S_PASSWORD)));
                sub.setWebsite(cursor.getString(cursor.getColumnIndex(S_WEBSITE)));
                sub.setNote(cursor.getString(cursor.getColumnIndex(S_NOTE)));
                sub.setCreatedAt(cursor.getString(cursor.getColumnIndex(S_CREATED)));
                // adding to the list
                SubContact.add(sub);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return SubContact;
    }

    //***Updating***--------------------------------------------------------------------------------

    public int updateContact(EntryModel eModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(C_NAME, eModel.getName());
        values.put(C_USER_NAME, eModel.getUserName());
        values.put(C_PASSWORD, eModel.getPassword());
        values.put(C_WEBSITE, eModel.getWebsite());
        values.put(C_NOTE, eModel.getNote());
        values.put(C_CREATED, getDateTime());

        // updating row
        return db.update(TABLE_CONTACT, values, C_ROW_ID + " = ?",
                new String[] { String.valueOf(eModel.getRowId())});
    }

    public int updateSubContact(SubEntryModel sModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(S_NAME, sModel.getName());
        values.put(S_USER_NAME, sModel.getUserName());
        values.put(S_PASSWORD, sModel.getPassword());
        values.put(S_WEBSITE, sModel.getWebsite());
        values.put(S_NOTE, sModel.getNote());
        values.put(S_CREATED, getDateTime());

        // updating row
        return db.update(TABLE_SUB_CONTACT, values, S_ROW_ID + " = ?",
                new String[] { String.valueOf(sModel.getRowId())});
    }
    //***Deleting***--------------------------------------------------------------------------------
    public long deleteContact(long row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT, C_ROW_ID + " = ?",
                new String[] { String.valueOf(row_id) });
        return row_id;
    }

    public void deleteSubContact(long row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SUB_CONTACT, S_ROW_ID + " = ?",
                new String[] { String.valueOf(row_id) });
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm dd-MMM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
