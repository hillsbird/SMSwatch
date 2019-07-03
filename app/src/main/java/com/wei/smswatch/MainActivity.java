package com.wei.smswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
        getContact.getAllContacts(this);
        Log.i("smswatch","333 ");

        Intent myintent = new Intent(this, sms.class);
        Log.i("smswatch","Sms Start ");
        startService(myintent);
    }


    String[] permissions = new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS};
    List<String> permissionList = new ArrayList<>();

    private void getPermissions(){
        permissionList.clear();
        for (int i =0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(getBaseContext(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permissions[i]);
            }
        }
        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, permissions, 100);
            Log.i("smswatch", "Get Permission");
        } else {
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
            }
        }

    }

}
