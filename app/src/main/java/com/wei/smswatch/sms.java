package com.wei.smswatch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.net.Uri;
import android.content.ContentResolver;
import android.os.Process;
import android.util.Log;


public class sms extends Service {

    private SmsObserver sobr;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        Log.i("smswatch", "Sms Service Create  ");

        ContentResolver resolver = getContentResolver();
        sobr = new SmsObserver(resolver, new SmsHandler(this));
        resolver.registerContentObserver(Uri.parse("content://sms"), true,sobr);
    }


    @Override
    public void onDestroy(){
        Log.i("smswatch", "Sms Service Destroy ");
        super.onDestroy();
        this.getContentResolver().unregisterContentObserver(sobr);
        Process.killProcess(Process.myPid());

    }


}
