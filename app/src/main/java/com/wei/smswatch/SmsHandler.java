package com.wei.smswatch;


import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

public class SmsHandler extends Handler {
    private Context context;
    public SmsHandler(sms context ) {
        this.context = (Context) context;
    }

    @Override
    public void handleMessage(Message message){
        SmsInfo smsinfo = (SmsInfo)message.obj;

        if (smsinfo.action == 1){
            ContentValues values = new ContentValues();
            values.put("read", "1");
            context.getContentResolver().update(Uri.parse("content://sms/inbox"), values, "thread_id=?", new String[] { smsinfo.thread_id });
        }else if (smsinfo.action == 2) {
            Uri uri = Uri.parse("content://sms/");
            context.getContentResolver().delete(uri, "_id=?", new String[] { smsinfo.id });
        }
    }
}
