package com.elkhamitech.projectkeeper.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.elkhamitech.projectkeeper.R;
import com.elkhamitech.projectkeeper.ui.activities.MainActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        assert activity != null;

        // Set title bar
//        ((MainActivity) getActivity())
//                .setActionBarTitle("Settings");


        Button backup = activity.findViewById(R.id.backup);
        Button restore = activity.findViewById(R.id.restore);

        //creating a new folder for the database to be backuped to

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                exportDB();
            }
        });

        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                importDB();
            }
        });

    }
}
