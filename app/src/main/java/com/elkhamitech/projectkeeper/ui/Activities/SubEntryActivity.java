package com.elkhamitech.projectkeeper.ui.Activities;

import android.app.AlertDialog;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.utils.AccessHandler.AESHelper;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SecurityModerator;
import com.elkhamitech.data.helper.DatabaseHelper;
import com.elkhamitech.data.model.SubEntryModel;

public class SubEntryActivity extends AppCompatActivity {

    private EditText edtxtName, edtxtUsrName, edtxtUsrPass,edtxtWebsite,edtxtNotes;
    private KeyListener mKeyListener1,mKeyListener2,mKeyListener3,mKeyListener4,mKeyListener5;
    private TextView txtCreated;
    private String cName,cUsrName,cPass,cWebsite,cNote;
    private SubEntryModel subEntryModel;
    private DatabaseHelper db;
    private long parentId;
    private boolean fromEmail , onEditPressed= false;
    private long row_id;

    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        setTitle("Passwords");

        edtxtName = (EditText)findViewById(R.id.contactName);
        edtxtUsrName = (EditText)findViewById(R.id.userName);
        edtxtUsrPass = (EditText)findViewById(R.id.contactPassword);
        edtxtWebsite = (EditText)findViewById(R.id.contactwebsite);
        edtxtNotes = (EditText)findViewById(R.id.contactnotes);
        txtCreated = (TextView)findViewById(R.id.supCreated);


        row_id = getIntent().getLongExtra("long",1L);
        fromEmail = getIntent().getBooleanExtra("boolean",false);


        db = new DatabaseHelper(getApplicationContext());

        if (fromEmail){

            disableEditText();

            db = new DatabaseHelper(getApplicationContext());
            db.getOneSubContact(row_id);
            edtxtName.setText(db.getOneSubContact(row_id).getS_name());
            edtxtUsrName.setText(db.getOneSubContact(row_id).getS_user_name());
            try {
                normalTextDec = AESHelper.decrypt(seedValue,db.getOneSubContact(row_id).getS_password());
            } catch (Exception e) {
                e.printStackTrace();
            }

            edtxtUsrPass.setText(normalTextDec);
            edtxtWebsite.setText(db.getOneSubContact(row_id).getS_website());
            edtxtNotes.setText(db.getOneSubContact(row_id).getS_note());

            txtCreated.setVisibility(View.VISIBLE);
            txtCreated.setText("Last Updated "+db.getOneSubContact(row_id).getCreated_at());

            onCopy();

        }
    }

    public void copyText(final EditText tdtxt, View v) {

        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_copy, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.id_Copy:

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tdtxt.getText().toString());
                        Toast.makeText(SubEntryActivity.this, "Text Copied", Toast.LENGTH_SHORT).show();

                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();

    }

    public void onCopy() {
        edtxtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(edtxtName, v);

            }
        });
        edtxtUsrName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(edtxtUsrName, v);
            }
        });
        edtxtUsrPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(edtxtUsrPass, v);
            }
        });
        edtxtWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(edtxtWebsite, v);
            }
        });
        edtxtNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(edtxtNotes, v);
            }
        });
    }

    public void whenDone(){

            parentId = getIntent().getLongExtra("long",1L);
            cName = edtxtName.getText().toString();
            cUsrName = edtxtUsrName.getText().toString();
            cPass = edtxtUsrPass.getText().toString();
            cWebsite = edtxtWebsite.getText().toString();
            cNote = edtxtNotes.getText().toString();

            db = new DatabaseHelper(getApplicationContext());
            subEntryModel = new SubEntryModel();

            subEntryModel.setParentId(parentId);
            subEntryModel.setS_name(cName);
            subEntryModel.setS_user_name(cUsrName);
        try {
            normalTextEnc = AESHelper.encrypt(seedValue, cPass);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            subEntryModel.setS_password(normalTextEnc);
            subEntryModel.setS_website(cWebsite);
            subEntryModel.setS_note(cNote);

            db.createSubContact(subEntryModel);

            finish();
    }

    public void onDelete(){


        new AlertDialog.Builder(SubEntryActivity.this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        db = new DatabaseHelper(getApplicationContext());
                        row_id = getIntent().getLongExtra("long",1L);
                        db.deleteSubContact(row_id);

                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
//                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }

    public void onUpdate(){
        enableEditText();
        onEditPressed = true;

    }

    public void onUpdateDone(){

        row_id = getIntent().getLongExtra("long",1L);

            db = new DatabaseHelper(getApplicationContext());
            subEntryModel = new SubEntryModel();

            cName = edtxtName.getText().toString();
            cUsrName = edtxtUsrName.getText().toString();
            cPass = edtxtUsrPass.getText().toString();
            cWebsite = edtxtWebsite.getText().toString();
            cNote = edtxtNotes.getText().toString();

            subEntryModel.setS_row_id(row_id);
            subEntryModel.setS_name(cName);
            subEntryModel.setS_user_name(cUsrName);
            try {
                normalTextEnc = AESHelper.encrypt(seedValue, cPass);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            subEntryModel.setS_password(normalTextEnc);
            subEntryModel.setS_website(cWebsite);
            subEntryModel.setS_note(cNote);

            db.updateSubContact(subEntryModel);
            disableEditText();
            onEditPressed = false;

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtxtName.getWindowToken(), 0);
    }

    public void onCancel(){
        disableEditText();
        onEditPressed = false;
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtxtName.getWindowToken(), 0);
    }

    public void disableEditText(){
//        edtxtName.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener1 = edtxtName.getKeyListener();
        edtxtName.setKeyListener(null);

//        edtxtUsrName.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener2 = edtxtUsrName.getKeyListener();
        edtxtUsrName.setKeyListener(null);

//        edtxtUsrPass.setTextColor(Color.parseColor("#FFFFFF"));
        mKeyListener3 = edtxtUsrPass.getKeyListener();
        edtxtUsrPass.setKeyListener(null);

        mKeyListener4 = edtxtWebsite.getKeyListener();
        edtxtWebsite.setKeyListener(null);

        mKeyListener5 = edtxtNotes.getKeyListener();
        edtxtNotes.setKeyListener(null);
    }

    public void enableEditText(){
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtxtName, 0);

        edtxtName.setSelection(edtxtName.getText().length());

        edtxtName.setTextColor(Color.parseColor("#000000"));
        edtxtUsrName.setTextColor(Color.parseColor("#000000"));
        edtxtUsrPass.setTextColor(Color.parseColor("#000000"));
        edtxtWebsite.setTextColor(Color.parseColor("#000000"));
        edtxtNotes.setTextColor(Color.parseColor("#000000"));

        edtxtName.setKeyListener(mKeyListener1);
        edtxtUsrName.setKeyListener(mKeyListener2);
        edtxtUsrPass.setKeyListener(mKeyListener3);
        edtxtWebsite.setKeyListener(mKeyListener4);
        edtxtNotes.setKeyListener(mKeyListener5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        fromEmail = getIntent().getBooleanExtra("boolean",false);

        if (onEditPressed){
            inflater.inflate(R.menu.actionbar_updone_btn, menu);
        } else if (fromEmail) {
            inflater.inflate(R.menu.actionbar_edt_delt, menu);
        } else {
            inflater.inflate(R.menu.actionbar_done_btn, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.action_done:
                whenDone();
                return true;
            case R.id.action_delete:
                onDelete();
                return true;
            case R.id.action_edit:
                invalidateOptionsMenu();
                onUpdate();
                return true;
            case R.id.action_update_done:
                invalidateOptionsMenu();
                onUpdateDone();
                return true;
            case R.id.action_update_cancel:
                invalidateOptionsMenu();
                onCancel();
                return true;
            // work around to handel on resume on EntryMainActivity.class
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem editDone = menu.findItem(R.id.action_update_done);

        if (onEditPressed) {
            editDone.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
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
