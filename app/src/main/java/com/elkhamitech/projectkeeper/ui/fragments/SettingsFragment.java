package com.elkhamitech.projectkeeper.ui.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.ui.activities.FortressGate;
import com.elkhamitech.projectkeeper.ui.activities.MainActivity;
import com.elkhamitech.projectkeeper.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private Button backup;
    private Button restore;


    private final int REQUEST_CODE = 1;


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

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Settings");


        backup = (Button) activity.findViewById(R.id.backup);
        restore = (Button) activity.findViewById(R.id.restore);


        //creating a new folder for the database to be backuped to
        File direct = new File(Environment.getExternalStorageDirectory() + "/Password Wallet");

        if(!direct.exists())
        {
            //dir not exist
//            Toast.makeText(activity, "dir not exist", Toast.LENGTH_SHORT).show();
            if(direct.mkdir())
            {
                //dir created
//                Toast.makeText(activity, "dir created", Toast.LENGTH_SHORT).show();
            }
        }else {
            // dir exist
//            Toast.makeText(activity, "dir exist", Toast.LENGTH_SHORT).show();
        }


        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkPermissionForWriteExternalStorage()){

                }else {
                    try {
                        requestPermissionForWriteExternalStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(checkPermissionForReadExternalStorage()){

                }else {
                    try {
                        requestPermissionForReadExternalStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                exportDB();


            }
        });

        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkPermissionForWriteExternalStorage()){

                }else {
                    try {
                        requestPermissionForWriteExternalStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(checkPermissionForReadExternalStorage()){

                }else {
                    try {
                        requestPermissionForReadExternalStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                importDB();

            }
        });

    }


    public boolean checkPermissionForWriteExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionWrite = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return permissionWrite == PackageManager.PERMISSION_GRANTED;

        }
        return false;
    }

    public boolean checkPermissionForReadExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permissionRead = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            return permissionRead == PackageManager.PERMISSION_GRANTED;

        }
        return false;
    }

    public void requestPermissionForWriteExternalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void requestPermissionForReadExternalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //exporting database
    private void exportDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + getActivity().getPackageName()
                        + "//databases//" + "PassKeeper";
                String backupDBPath  = "/Password Wallet/PassKeeper";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getActivity(), "Your passwords have been backed up",
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

    //importing database
    private void importDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + getActivity().getPackageName()
                        + "//databases//" + "PassKeeper";
                String backupDBPath  = "/Password Wallet/PassKeeper";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getActivity(), "Passwords have been restored, Thanks to re-enter your pin to confirm the passwords are yours.",
                        Toast.LENGTH_LONG).show();

                Intent i = new Intent(getActivity(), FortressGate.class);
                startActivity(i);

            }
        } catch (Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }



}
