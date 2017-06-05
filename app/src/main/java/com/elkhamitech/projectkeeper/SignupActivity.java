package com.elkhamitech.projectkeeper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elkhamitech.sqlite.helper.DatabaseHelper;
import com.elkhamitech.sqlite.model.UserModel;


public class SignupActivity extends AppCompatActivity  {

    // Database Helper
    private DatabaseHelper db;
    private UserModel user;
    private String user_name,eMail,password;
    private EditText  edtxt_user, edtxt_pass, edtxt_email;
    private SessionManager session;
    private Long id;
    private boolean newUser = true;

    //for encryption and decryption
    private String seedValue = "I don't know what is this";
    private String normalTextEnc;
    private String normalTextDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        edtxt_user = (EditText)findViewById(R.id.editTextName);
        edtxt_pass = (EditText)findViewById(R.id.editTextPass);
        edtxt_email = (EditText)findViewById(R.id.editTextMail);

        Button SignInButton = (Button) findViewById(R.id.btn_signin);
        SignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                user_name= edtxt_user.getText().toString();
                eMail = edtxt_email.getText().toString();
                password = edtxt_pass.getText().toString();

                if ( user_name.equals("") || eMail.equals("")
                        || password.equals("")) {

                    Toast.makeText(getBaseContext(),
                            "Please Fill all the Required Fileds.",
                            Toast.LENGTH_LONG).show();

                } else if (emailExist() == false) {
                    Toast.makeText(getBaseContext(),
                            "The email already exist.",
                            Toast.LENGTH_LONG).show();
                }else {
                    session = new SessionManager(getApplicationContext());
                    db = new DatabaseHelper(getApplicationContext());
                    user = new UserModel();

                    user.setUser_name(user_name);

                    try {
                        normalTextEnc = AESHelper.encrypt(seedValue, password);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    user.setPassword(normalTextEnc);
                    user.seteMail(eMail);

                    db.createUser(user);

                    id =user.getRow_id();

                    session.createLoginSession(id,eMail);

                    Intent gotoMain = new Intent(SignupActivity.this, MainActivity.class);
                    gotoMain.putExtra("boolean", newUser);
                    startActivity(gotoMain);
                    finish();
                }

            }
        });

    }

    public boolean emailExist() {

        db = new DatabaseHelper(getApplicationContext());
        user = new UserModel();

        if (eMail.equals(db.getRegisteredUser(eMail))) {

            return false;
        }
        return true;
    }



    public void onDBinsertion(int row_id){

        AsyncTask<Integer,Void,Void> mAsyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

        };
        mAsyncTask.execute(row_id);
    }

}

