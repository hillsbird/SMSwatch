package com.wei.smswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("smswatch","111 ");
        getPermissions();
        Log.i("smswatch","222 ");

        Intent myintent = new Intent(this, sms.class);
        Log.i("smswatch","Sms Start ");
        startService(myintent);
    }


    String[] permissions = new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS};
    List<String> permissionList = new ArrayList<>();

    private void getPermissions(){
        permissionList.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for (int i =0; i < permissions.length; i++) {
                if (checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permissions[i]);
                }
            }
            if (permissionList.size() > 0) {
                requestPermissions(permissions, 100);
                Log.i("smswatch", "Get Permission");
            } else {
                getContact.getAllContacts(this);
                Log.i("smswatch","333 ");
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;
        if (requestCode == 100){
            for (int i = 0; i < grantResults.length; i++){
                if (grantResults[i] == -1){
                    hasPermissionDismiss = true;
                }
            }
            if (hasPermissionDismiss){
                Log.i("smswatch", " no permission granted");
            }
            else{
                getPermissions();
            }
        }

    }

}
