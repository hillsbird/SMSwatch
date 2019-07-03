package com.wei.smswatch;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


public class getContact {
    public static Map<String, String> getAllContacts(Context context){
        Map<String, String> contactMap = new HashMap<String, String>();
        ContentResolver contentresolver = context.getContentResolver();
        Cursor cursor = contentresolver.query(Uri.parse("content://com.android.contacts/contacts"),null,null,null,null);
        if (cursor.moveToFirst()){
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int nameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            do {
                String contactId = cursor.getString(idColumn);
                String displayName = cursor.getString(nameColumn);
                Log.i("smswatch",contactId+":"+displayName);
                int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0){
                    Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                    if (phoneCursor.moveToFirst()){
                        do {
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Log.i("smswatch",phoneNumber);
                            if(!contactMap.containsKey(phoneNumber)){
                                contactMap.put(handlePhoneNum(phoneNumber), displayName);
                            }

                        }while (cursor.moveToNext());
                    }
                }

            }while (cursor.moveToNext());
        }
        return contactMap;
    }

    private static String handlePhoneNum(String phoneNum){
        if(!TextUtils.isEmpty(phoneNum)){
            phoneNum = phoneNum.replaceAll("\\D", "");
        }
        if(phoneNum.length() == 13 && phoneNum.startsWith("86")){
            return phoneNum.substring(2);
        }
        return phoneNum;
    }
}
