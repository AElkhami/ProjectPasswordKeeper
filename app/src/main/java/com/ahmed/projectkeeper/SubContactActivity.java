package com.ahmed.projectkeeper;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmed.sqlite.helper.DatabaseHelper;
import com.ahmed.sqlite.model.SubContactModel;

public class SubContactActivity extends AppCompatActivity {

    private EditText edtxtName, edtxtUsrName, edtxtUsrPass;
    private KeyListener mKeyListener1,mKeyListener2,mKeyListener3;
    private TextView txtCreated;
    private String cName,cUsrName,cPass;
    private SubContactModel subContactModel;
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
        setContentView(R.layout.activity_sub_contact);

        edtxtName = (EditText)findViewById(R.id.contactName);
        edtxtUsrName = (EditText)findViewById(R.id.userName);
        edtxtUsrPass = (EditText)findViewById(R.id.contactPassword);
        txtCreated = (TextView)findViewById(R.id.created);//insert it in the xml


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
                normalTextDec = AESHelper.decrypt(seedValue,db.getOneContact(row_id).getE_password());
            } catch (Exception e) {
                e.printStackTrace();
            }

            edtxtUsrPass.setText(normalTextDec);


            //just insert the right filed in the xml
            //txtCreated.setText("Last Updated "+db.getOneSubContact(row_id).getCreated_at());

        }

    }

    public void whenDone(){

            parentId = getIntent().getLongExtra("long",1L);
            cName = edtxtName.getText().toString();
            cUsrName = edtxtUsrName.getText().toString();
            cPass = edtxtUsrPass.getText().toString();



            db = new DatabaseHelper(getApplicationContext());
            subContactModel = new SubContactModel();


            subContactModel.setParentId(parentId);
            subContactModel.setS_name(cName);
            subContactModel.setS_user_name(cUsrName);


        try {
            normalTextEnc = AESHelper.encrypt(seedValue, cPass);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

            subContactModel.setS_password(normalTextEnc);

            db.createSubContact(subContactModel);

            finish();

    }
    public void onDelete(){


        new AlertDialog.Builder(SubContactActivity.this)
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

        try {
            db = new DatabaseHelper(getApplicationContext());
            subContactModel = new SubContactModel();

            cName = edtxtName.getText().toString();
            cUsrName = edtxtUsrName.getText().toString();
            cPass = edtxtUsrPass.getText().toString();

//            normalTextEnc = AESHelper.encrypt(seedValue, cPass);

            subContactModel.setS_row_id(row_id);
            subContactModel.setS_name(cName);
            subContactModel.setS_user_name(cUsrName);
            subContactModel.setS_password(cPass);

            db.updateSubContact(subContactModel);

            finish();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void onCancel(){
        disableEditText();
        onEditPressed = false;
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
    }
    public void enableEditText(){
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtxtName, 0);

        edtxtName.setSelection(edtxtName.getText().length());

        edtxtName.setTextColor(Color.parseColor("#000000"));
        edtxtUsrName.setTextColor(Color.parseColor("#000000"));
        edtxtUsrPass.setTextColor(Color.parseColor("#000000"));

        edtxtName.setKeyListener(mKeyListener1);
        edtxtUsrName.setKeyListener(mKeyListener2);
        edtxtUsrPass.setKeyListener(mKeyListener3);
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


}
