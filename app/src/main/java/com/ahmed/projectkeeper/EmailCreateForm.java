package com.ahmed.projectkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmed.sqlite.helper.DatabaseHelper;
import com.ahmed.sqlite.model.EmailModel;

import java.util.HashMap;

public class EmailCreateForm extends AppCompatActivity {

    private DatabaseHelper db;
    private EmailModel eMail;
    private EditText edtxtName, edtxtUsrName, edtxtUsrPass;
    private TextView txtCreated;
    private String cName,cUsrName,cPass;
    private SessionManager session;
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
        setContentView(R.layout.activity_create_mail_or_contact);

        edtxtName = (EditText)findViewById(R.id.contactName);
        edtxtUsrName = (EditText)findViewById(R.id.userName);
        edtxtUsrPass = (EditText)findViewById(R.id.contactPassword);
        txtCreated = (TextView)findViewById(R.id.created);

    }


    public void createEmail(){
        cName = edtxtName.getText().toString();
        cUsrName = edtxtUsrName.getText().toString();
        cPass = edtxtUsrPass.getText().toString();



        session = new SessionManager(getApplicationContext());
        db = new DatabaseHelper(getApplicationContext());
        eMail = new EmailModel();
        rid = session.getRowDetails();
        UserId = rid.get(session.KEY_ID);

        eMail.setUserId(UserId);
        eMail.setE_name(cName);
        eMail.setE_user_name(cUsrName);
        try {
            normalTextEnc = AESHelper.encrypt(seedValue, cPass);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        eMail.setE_password(normalTextEnc);
        db.createContact(eMail);
        row_id = eMail.getE_row_id();
        fromListView = true;

        Intent mIntent = new Intent (EmailCreateForm.this,EmailMainActivity.class);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
