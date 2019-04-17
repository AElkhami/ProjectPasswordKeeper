package com.elkhamitech.projectkeeper.utils.accesshandler;

import android.app.Activity;
import android.content.Intent;

import com.elkhamitech.projectkeeper.ui.activities.FortressGateActivity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ahmed on 5/31/2017.
 */

public class SecurityModerator {

    private static Date appPauseTime;
    private static Date appLastPause;


    public static void lockAppStoreTime() {
        appPauseTime = new Date();
    }

    public static void lockAppCheck(Activity act) {
        boolean bLock = false;

        // Check to see if this is the first time through app
        if (appPauseTime==null) {
            bLock = true;
        } else {
            Date currTime = new Date();
            long diffMillis = currTime.getTime() - appPauseTime.getTime();
            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffMillis);

            // Lock app if 120 seconds (2 minutes) has lapsed
            if (diffInSec > 3) {
                bLock=true;
            }
        }


        appLastPause = new Date();
        if (bLock) {
            Intent j = new Intent(act, FortressGateActivity.class);
            act.startActivity(j);
        }
    }
}
