package com.elkhamitech.projectkeeper.ui.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.elkhamitech.projectkeeper.ui.Activities.MainActivity;
import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.ui.Activities.WelcomeActivity;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SessionManager;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView txt1,txt2,txt3;
    private Button logout;
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


        txt1 = activity.findViewById(R.id.txtv1);
        txt2 = activity.findViewById(R.id.txtv2);

        rid = SessionManager.getRowDetails();
        user = SessionManager.getUserDetails();

        long id = rid.get(SessionManager.KEY_ID);
        String name = user.get(SessionManager.KEY_EMAIL);

        txt1.setText(String.valueOf(id));
        txt2.setText(name);

        logout = activity.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
                activity.finish();

            }
        });
    }


    public void logoutUser(){

        SessionManager.logoutUser();

        // After logout redirect user to Loing Activity
        Intent w = new Intent(getContext(), WelcomeActivity.class);
        // Closing all the Activities
        w.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        w.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        startActivity(w);

    }
}
