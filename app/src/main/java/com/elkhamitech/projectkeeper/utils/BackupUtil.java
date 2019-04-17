package com.elkhamitech.projectkeeper.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import com.elkhamitech.projectkeeper.ui.activities.FortressGateActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import androidx.core.app.ActivityCompat;

/**
 * Created by A.Elkhami on 16,April,2019
 */
public class BackupUtil {

    private Context context;
    private final int REQUEST_CODE = 1;

    public BackupUtil(Context context) {
        this.context = context;
    }

    private boolean checkPermissionForWriteExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionWrite = context
                    .checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return permissionWrite == PackageManager.PERMISSION_GRANTED;

        }
        return false;
    }

    private boolean checkPermissionForReadExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permissionRead = context
                    .checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            return permissionRead == PackageManager.PERMISSION_GRANTED;

        }
        return false;
    }

    private void requestPermissionForWriteExternalStorage()  {
        try {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void requestPermissionForReadExternalStorage()  {
        try {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void checkForPermissions() {
        if (!checkPermissionForWriteExternalStorage()) {
            try {
                requestPermissionForWriteExternalStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (checkPermissionForReadExternalStorage()) {
            try {
                requestPermissionForReadExternalStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createBackupFolder() {

        //creating a new folder for the database to backup to
        File direct = new File(Environment.getExternalStorageDirectory() + "/Password Wallet");

        if (!direct.exists()) {
            //dir not exist
            Toast.makeText(context, "dir not exist", Toast.LENGTH_SHORT).show();
            if (direct.mkdir()) {
                //dir created
                Toast.makeText(context, "dir created", Toast.LENGTH_SHORT).show();
            }
        } else {
            // dir exist
            Toast.makeText(context, "dir exist", Toast.LENGTH_SHORT).show();
        }
    }

    //exporting database
    public void exportDB() {

        createBackupFolder();

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + context.getPackageName()
                        + "//databases//" + "PassKeeper";
                String backupDBPath = "/Password Wallet/PassKeeper";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(context, "Your passwords have been backed up",
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

    //importing database
    public void importDB() {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + context.getPackageName()
                        + "//databases//" + "PassKeeper";
                String backupDBPath = "/Password Wallet/PassKeeper";
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(context, "Passwords have been restored, Thanks to re-enter your pin to confirm the passwords are yours.",
                        Toast.LENGTH_LONG).show();

                Intent i = new Intent(context, FortressGateActivity.class);
                context.startActivity(i);

            }
        } catch (Exception e) {

            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

}
