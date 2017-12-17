package com.elkhamitech.projectkeeper.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.elkhamitech.projectkeeper.Activities.MainActivity;
import com.elkhamitech.projectkeeper.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private Button backup;
    private Button restore;


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

//        File direct = new File(Environment.getExternalStorageDirectory() + "/Exam Creator");
//
//        if(!direct.exists())
//        {
//            if(direct.mkdir())
//            {
//                //directory is created;
//            }
//        }
//
        backup = (Button)activity.findViewById(R.id.backup);
//        restore = (Button)activity.findViewById(R.id.restore);
//
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                exportDB();
                try {
                    copyAppDbToDownloadFolder();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
//
//        restore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                importDB();
//            }
//        });
    }

    public void copyAppDbToDownloadFolder() throws IOException {

        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        try {
            File backupDB = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "PassKeeper_bkp");
            File currentDB = getActivity().getApplicationContext().getDatabasePath("PassKeeper"); //databaseName=your current application database name, for example "my_data.db"
            if (currentDB.exists()) {
                FileInputStream fis = new FileInputStream(currentDB);
                FileOutputStream fos = new FileOutputStream(backupDB);
                fos.getChannel().transferFrom(fis.getChannel(), 0, fis.getChannel().size());
                // or fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                fis.close();
                fos.close();
                Log.i("Database successfully", " copied to download folder");

            } else Log.i("Copying Database", " fail, database not found");
        } catch (IOException e) {
            Log.d("Copying Database", "fail, reason:", e);
        }
            }
        }
    }

//    //importing database
//    private void importDB() {
//        // TODO Auto-generated method stub
//
//        try {
//            File sd = Environment.getExternalStorageDirectory();
//            File data  = Environment.getDataDirectory();
//
//            if (sd.canWrite()) {
//                String  currentDBPath= "//data//" + "com.elkhamitech.projectkeeper"
//                        + "//databases//" + "PassKeeper";
//                String backupDBPath  = "/mnt/sdcard/BackupFolder/PassKeeper";
//                File  backupDB= new File(data, currentDBPath);
//                File currentDB  = new File(sd, backupDBPath);
//
//                FileChannel src = new FileInputStream(currentDB).getChannel();
//                FileChannel dst = new FileOutputStream(backupDB).getChannel();
//                dst.transferFrom(src, 0, src.size());
//                src.close();
//                dst.close();
//                Toast.makeText(getActivity(), backupDB.toString(),
//                        Toast.LENGTH_LONG).show();
//
//            }
//        } catch (Exception e) {
//
//            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
//                    .show();
//
//        }
//    }
//    //exporting database
//    private void exportDB() {
//        // TODO Auto-generated method stub
//
//        try {
//            File sd = Environment.getExternalStorageDirectory();
//            File data = Environment.getDataDirectory();
//
//            if (sd.canWrite()) {
//                String  currentDBPath= "/data/" + "com.elkhamitech.projectkeeper"
//                        + "/databases/" + "PassKeeper";
//                String backupDBPath  = "/storage/sdcard0/";
//                File currentDB = new File(data, currentDBPath);
//                File backupDB = new File(sd, backupDBPath);
//
//                FileChannel src = new FileInputStream(currentDB).getChannel();
//                FileChannel dst = new FileOutputStream(backupDB).getChannel();
//                dst.transferFrom(src, 0, src.size());
//                src.close();
//                dst.close();
//                Toast.makeText(getActivity(),"done"+ backupDB.toString(),
//                        Toast.LENGTH_LONG).show();
//
//            }
//        } catch (Exception e) {
//
//            Toast.makeText(getActivity(), "not done"+e.toString(), Toast.LENGTH_LONG)
//                    .show();
//
//        }
//    }
}
