package com.elkhamitech.projectkeeper.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.elkhamitech.projectkeeper.utils.AccessHandler.AESHelper;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SecurityModerator;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SessionManager;
import com.elkhamitech.data.helper.DatabaseHelper;
import com.elkhamitech.data.model.EntryModel;

import java.util.HashMap;

public class CreateNewEntry extends AppCompatActivity {

    private DatabaseHelper db;
    private EntryModel eMail;
    private EditText edtxtName, edtxtUsrName, edtxtUsrPass,edtxtWebsite,edtxtNote;
    private TextView txtCreated;
    private String cName,cUsrName,cPass,cWebsite,cNote;
    private long UserId, row_id;
    private HashMap<String, Long> rid;
    private Boolean fromListView = false;

    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        edtxtName = (EditText)findViewById(R.id.contactName);
        edtxtUsrName = (EditText)findViewById(R.id.userName);
        edtxtUsrPass = (EditText)findViewById(R.id.contactPassword);
        edtxtWebsite = (EditText)findViewById(R.id.contactwebsite);
        edtxtNote = (EditText)findViewById(R.id.contactnotes);
        txtCreated = (TextView)findViewById(R.id.created);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close_white_24dp);

        setTitle("Passwords");


    }


    public void createEmail(){
        cName = edtxtName.getText().toString();
        cUsrName = edtxtUsrName.getText().toString();
        cPass = edtxtUsrPass.getText().toString();
        cWebsite = edtxtWebsite.getText().toString();
        cNote = edtxtNote.getText().toString();


        db = new DatabaseHelper(getApplicationContext());
        eMail = new EntryModel();
        rid = SessionManager.getRowDetails();
        UserId = rid.get(SessionManager.KEY_ID);

        eMail.setUserId(UserId);
        eMail.setName(cName);
        eMail.setUserName(cUsrName);
        try {
            normalTextEnc = AESHelper.encrypt(seedValue, cPass);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        eMail.setPassword(normalTextEnc);
        eMail.setWebsite(cWebsite);
        eMail.setNote(cNote);
        db.createContact(eMail);
        row_id = eMail.getRowId();
        fromListView = true;

        Intent mIntent = new Intent (CreateNewEntry.this,EntryMainActivity.class);
        mIntent.putExtra("boolean",fromListView);
        mIntent.putExtra("long", row_id);
        startActivity(mIntent);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_done_btn, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_done:
                createEmail();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SecurityModerator.lockAppStoreTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SecurityModerator.lockAppCheck(this);
    }
}
