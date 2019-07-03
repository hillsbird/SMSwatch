package com.wei.smswatch;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Message;
import android.util.Log;
import android.net.Uri;


public class SmsObserver extends ContentObserver {
    private ContentResolver cresolver;
    public SmsHandler smshandler;

    public SmsObserver(ContentResolver cresolver, SmsHandler handler){
        super(handler);
        this.cresolver = cresolver;
        this.smshandler = handler;
    }

    @Override
    public void onChange(boolean selfChange){

//        Log.i("SmsObserver onChange ", "SmsObserver onChange ");
        Cursor cursor = cresolver.query(Uri.parse("content://sms/inbox"), new String[] { "_id", "address", "read", "body", "thread_id" },
                "read=?", new String[] { "0" }, "date desc");

        if (cursor == null){
            return;
        }
        else{
            SmsInfo smsinfo = new SmsInfo();
            if (cursor.moveToFirst()) {
                if (smsinfo.action == 0) {
                    int idIndex = cursor.getColumnIndex("_id");
                    if (idIndex != -1) {
                        smsinfo.id = cursor.getString(idIndex);
                    }
                    int threadIndex = cursor.getColumnIndex("thread_id");
                    if (threadIndex != -1) {
                        smsinfo.thread_id = cursor.getString(threadIndex);
                    }
                    int addressIndex = cursor.getColumnIndex("address");
                    if (addressIndex != -1) {
                        smsinfo.smsAddress = cursor.getString(addressIndex);
                    }
                    int bodyIndex = cursor.getColumnIndex("body");
                    if (bodyIndex != -1) {
                        smsinfo.smsBody = cursor.getString(bodyIndex);
                    }
                    int readIndex = cursor.getColumnIndex("read");
                    if (readIndex != -1) {
                        smsinfo.read = cursor.getString(readIndex);
                    }
                    Log.i("smswatch", smsinfo.toString());
                }
            }
            /*
            while(cursor.moveToNext()){
                SmsInfo smsinfo = new SmsInfo();
                int idIndex = cursor.getColumnIndex("_id");
                if (idIndex != -1) {
                    smsinfo.id = cursor.getString(idIndex);
                }
                int threadIndex = cursor.getColumnIndex("thread_id");
                if (idIndex != -1) {
                    smsinfo.thread_id = cursor.getString(threadIndex);
                }
                int addressIndex = cursor.getColumnIndex("address");
                if (idIndex != -1) {
                    smsinfo.smsAddress = cursor.getString(addressIndex);
                }
                int bodyIndex = cursor.getColumnIndex("body");
                if (idIndex != -1) {
                    smsinfo.smsBody = cursor.getString(bodyIndex);
                }
                int readIndex = cursor.getColumnIndex("read");
                if (idIndex != -1) {
                    smsinfo.read = cursor.getString(readIndex);
                }
                Log.i("Sms",smsinfo.toString());
*/

                Message msg = smshandler.obtainMessage();
                smsinfo.action = 1;
                msg.obj = smsinfo;
                smshandler.sendMessage(msg);

        }
        if (cursor != null){
            cursor.close();
            cursor = null;
        }

    }
}
