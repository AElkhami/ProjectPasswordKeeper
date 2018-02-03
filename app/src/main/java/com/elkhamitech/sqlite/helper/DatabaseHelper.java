package com.elkhamitech.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elkhamitech.sqlite.model.EmailModel;
import com.elkhamitech.sqlite.model.SubContactModel;
import com.elkhamitech.sqlite.model.UserModel;

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
//-----------------------------------CRUD Functions-------------------------------------------------


    //insert new row

    public long createUser (UserModel user){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME, user.getUser_name());
        values.put(PASSWORD, user.getPassword());
        values.put(EMAIL, user.geteMail());
        values.put(PIN, user.getPin());

        user.setRow_id(db.insert(TABLE_USER, null, values)) ;

        return user.getRow_id();
    }

    public long createContact (EmailModel eModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(C_USER_CONTACT, eModel.getUserId());
        values.put(C_NAME, eModel.getE_name());
        values.put(C_USER_NAME, eModel.getE_user_name());
        values.put(C_PASSWORD, eModel.getE_password());
        values.put(C_WEBSITE, eModel.getE_website());
        values.put(C_NOTE, eModel.getE_note());
        values.put(C_CREATED, getDateTime());

        eModel.setE_row_id(db.insert(TABLE_CONTACT, null, values)); ;

        return eModel.getE_row_id();
    }

    public void createSubContact (SubContactModel Scontact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(S_PARENT_CONTACT, Scontact.getParentId());
        values.put(S_NAME, Scontact.getS_name());
        values.put(S_USER_NAME, Scontact.getS_user_name());
        values.put(S_PASSWORD, Scontact.getS_password());
        values.put(S_WEBSITE, Scontact.getS_website());
        values.put(S_NOTE, Scontact.getS_note());
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
                    user.seteMail(cursor.getString(cursor.getColumnIndex(EMAIL)));
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
                    user.seteMail(cursor.getString(cursor.getColumnIndex(EMAIL)));
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
                    user.seteMail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                    user.setPin(cursor.getString(cursor.getColumnIndex(PIN)));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return user;
    }

    public EmailModel getOneContact (long c_row_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACT
                + " WHERE " + C_ROW_ID + " = '" + c_row_id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

//        String[] coloumns = { EMAIL, PASSWORD };
//        Cursor c = db.query(TABLE_USER, coloumns, null, null,
//                null, null, null);
        EmailModel mail = new EmailModel();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    mail.setE_name(cursor.getString(cursor.getColumnIndex(C_NAME)));
                    mail.setE_user_name(cursor.getString(cursor.getColumnIndex(C_USER_NAME)));
                    mail.setE_password(cursor.getString(cursor.getColumnIndex(C_PASSWORD)));
                    mail.setE_website(cursor.getString(cursor.getColumnIndex(C_WEBSITE)));
                    mail.setE_note(cursor.getString(cursor.getColumnIndex(C_NOTE)));
                    mail.setCreated_at(cursor.getString(cursor.getColumnIndex(C_CREATED)));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return mail;
    }

    public SubContactModel getOneSubContact (long s_row_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SUB_CONTACT
                + " WHERE " + S_ROW_ID + " = '" + s_row_id + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

//        String[] coloumns = { EMAIL, PASSWORD };
//        Cursor c = db.query(TABLE_USER, coloumns, null, null,
//                null, null, null);
        SubContactModel SubC = new SubContactModel();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    SubC.setS_name(cursor.getString(cursor.getColumnIndex(S_NAME)));
                    SubC.setS_user_name(cursor.getString(cursor.getColumnIndex(S_USER_NAME)));
                    SubC.setS_password(cursor.getString(cursor.getColumnIndex(S_PASSWORD)));
                    SubC.setS_website(cursor.getString(cursor.getColumnIndex(S_WEBSITE)));
                    SubC.setS_note(cursor.getString(cursor.getColumnIndex(S_NOTE)));
                    SubC.setCreated_at(cursor.getString(cursor.getColumnIndex(S_CREATED)));
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
                user.seteMail(cursor.getString(cursor.getColumnIndex(EMAIL)));

                // adding to the list
                Users.add(user);
            } while (cursor.moveToNext());
        }
        return Users;
    }


    public List<EmailModel> getContacts(long userId){
        List<EmailModel> Mail = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACT + " Where "+C_USER_CONTACT+" = '"+userId+"' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EmailModel eMail = new EmailModel();
                eMail.setE_row_id((cursor.getLong(cursor.getColumnIndex(C_ROW_ID))));
                eMail.setE_name(cursor.getString((cursor.getColumnIndex(C_NAME))));
                eMail.setE_user_name((cursor.getString(cursor.getColumnIndex(C_USER_NAME))));
                eMail.setE_password(cursor.getString(cursor.getColumnIndex(C_PASSWORD)));
                eMail.setE_website(cursor.getString(cursor.getColumnIndex(C_WEBSITE)));
                eMail.setE_note(cursor.getString(cursor.getColumnIndex(C_NOTE)));
                eMail.setCreated_at(cursor.getString(cursor.getColumnIndex(C_CREATED)));
                // adding to the list
                Mail.add(eMail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return Mail;
    }


    public List<SubContactModel> getSubContacts(long parentId){
        List<SubContactModel> SubContact = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SUB_CONTACT + " Where "+S_PARENT_CONTACT+" = '"+parentId+"' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubContactModel sub = new SubContactModel();
                sub.setS_row_id((cursor.getLong(cursor.getColumnIndex(S_ROW_ID))));
                sub.setS_name(cursor.getString((cursor.getColumnIndex(S_NAME))));
                sub.setS_user_name((cursor.getString(cursor.getColumnIndex(S_USER_NAME))));
                sub.setS_password(cursor.getString(cursor.getColumnIndex(S_PASSWORD)));
                sub.setS_website(cursor.getString(cursor.getColumnIndex(S_WEBSITE)));
                sub.setS_note(cursor.getString(cursor.getColumnIndex(S_NOTE)));
                sub.setCreated_at(cursor.getString(cursor.getColumnIndex(S_CREATED)));
                // adding to the list
                SubContact.add(sub);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return SubContact;
    }

    //***Updating***--------------------------------------------------------------------------------

    public int updateContact(EmailModel eModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(C_NAME, eModel.getE_name());
        values.put(C_USER_NAME, eModel.getE_user_name());
        values.put(C_PASSWORD, eModel.getE_password());
        values.put(C_WEBSITE, eModel.getE_website());
        values.put(C_NOTE, eModel.getE_note());
        values.put(C_CREATED, getDateTime());

        // updating row
        return db.update(TABLE_CONTACT, values, C_ROW_ID + " = ?",
                new String[] { String.valueOf(eModel.getE_row_id())});
    }

    public int updateSubContact(SubContactModel sModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(S_NAME, sModel.getS_name());
        values.put(S_USER_NAME, sModel.getS_user_name());
        values.put(S_PASSWORD, sModel.getS_password());
        values.put(S_WEBSITE, sModel.getS_website());
        values.put(S_NOTE, sModel.getS_note());
        values.put(S_CREATED, getDateTime());

        // updating row
        return db.update(TABLE_SUB_CONTACT, values, S_ROW_ID + " = ?",
                new String[] { String.valueOf(sModel.getS_row_id())});
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
