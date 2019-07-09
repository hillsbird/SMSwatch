package com.wei.smswatch;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.net.Uri;
import android.content.ContentResolver;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import com.wei.smswatch.IMyAidlInterface;


public class sms extends Service {

    private MyBinder mBinder;
    private SmsObserver sobr;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        mBinder = new MyBinder();
        return mBinder;
    }

    @Override
    public void onCreate(){
        Log.i("smswatch", "Sms Service Create  ");
        ContentResolver resolver = getContentResolver();
        sobr = new SmsObserver(resolver, new SmsHandler(this));
        resolver.registerContentObserver(Uri.parse("content://sms"), true,sobr);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        startService(new Intent(sms.this,smsB.class));
        bindService(new Intent(this,smsB.class),connection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        Log.i("smswatch", "Sms Service Destroy ");
        super.onDestroy();
        this.getContentResolver().unregisterContentObserver(sobr);
        Process.killProcess(Process.myPid());

    }


    private class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return sms.class.getName();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }

    private ServiceConnection connection  = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                Log.i("smswatch", "connected with " + iMyAidlInterface.getServiceName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("smswatch", "restart smsB");
            startService(new Intent(sms.this,smsB.class));
            bindService(new Intent(sms.this,smsB.class),connection, Context.BIND_IMPORTANT);

        }
    };
}
