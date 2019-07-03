package com.wei.smswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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

        /*if (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_SMS))
            {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_SMS}, 1);
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_SMS}, 1);
            }

        }
        else
        {
        }
        final int REQUEST_CODE_ASK_PERMISSIONS = 123;
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermissions();
        getContact.getAllContacts(this);

        Intent myintent = new Intent(this, sms.class);
        Log.i("Sms Start ","Sms Start ");
        startService(myintent);
    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_SMS) ==  PackageManager.PERMISSION_GRANTED)
                    {
                        Log.i("Sms", "permission granted");
                    }
                }
                else
                {
                    Log.i("Sms", " no permission granted");
                }
                break;
        }
    }
*/
    private void getPermissions(){
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_SMS);
        }
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_CONTACTS);
        }

        if (!permissionList.isEmpty()){
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1002);
        }else{
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1002:
                if (grantResults.length > 0){
                    for (int i = 0; i < grantResults.length; i++){
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                            Toast.makeText(MainActivity.this, permissions[i] + "权限被拒绝了,无法使用", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
}
