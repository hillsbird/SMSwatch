package com.wei.smswatch;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.wei.smswatch.IMyAidlInterface;

public class smsB extends Service {

    private MyBinder mBinder;

    public smsB() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new MyBinder();
        return mBinder;
    }

    private ServiceConnection connection = new ServiceConnection() {
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
        public void onServiceDisconnected(ComponentName name) {
            Log.i("smswatch", "reatsrt sms");
            startService(new Intent(smsB.this,sms.class));
            bindService(new Intent(smsB.this,sms.class),connection, Context.BIND_IMPORTANT);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(this,sms.class),connection,Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private class MyBinder extends IMyAidlInterface.Stub{
        @Override
        public String getServiceName() throws RemoteException {
            return smsB.class.getName();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }
    }
}

