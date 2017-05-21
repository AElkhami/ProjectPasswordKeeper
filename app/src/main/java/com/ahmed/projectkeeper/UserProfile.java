package com.ahmed.projectkeeper;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity {

    private TextView txt1,txt2,txt3;
    private Button logout;
    private SessionManager session;
    private HashMap<String, Long> rid;
    private HashMap<String, String> user;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        txt1 = (TextView)findViewById(R.id.txtv1);
        txt2 = (TextView)findViewById(R.id.txtv2);

        session = new SessionManager(getApplicationContext());
        rid = session.getRowDetails();
        user = session.getUserDetails();

        long id = rid.get(session.KEY_ID);
        String name = user.get(session.KEY_EMAIL);

        txt1.setText(String.valueOf(id));
        txt2.setText(name);

        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                finish();
            }
        });

    }
}
