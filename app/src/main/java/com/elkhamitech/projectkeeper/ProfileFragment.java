package com.elkhamitech.projectkeeper;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView txt1,txt2,txt3;
    private Button logout;
    private SessionManager session;
    private HashMap<String, Long> rid;
    private HashMap<String, String> user;
    private Context _context;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Profile");


        txt1 = (TextView)activity.findViewById(R.id.txtv1);
        txt2 = (TextView)activity.findViewById(R.id.txtv2);

        session = new SessionManager(activity.getApplicationContext());
        rid = session.getRowDetails();
        user = session.getUserDetails();

        long id = rid.get(session.KEY_ID);
        String name = user.get(session.KEY_EMAIL);

        txt1.setText(String.valueOf(id));
        txt2.setText(name);

        logout = (Button)activity.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                activity.finish();

            }
        });
    }
}
