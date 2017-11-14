package com.compscitutorials.basigarcia.navigationdrawervideotutorial;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Activity extends RunTimePermission {
    private static int time_out=2000;
    private static final int REQUEST_PERMISSION = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);


//        b1=(Button) findViewById(R.id.bmain);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(MainActivity.this, Signup.class);
//                startActivity(i);
//
//            }
//        });

        requestAppPermissions(new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.CALL_PHONE
                },
                R.string.msg, REQUEST_PERMISSION);
    }







    @Override
    public void onPermissionsGranted(int requestCode) {
        //Do anything when permisson granted
        Snackbar.make(findViewById(android.R.id.content), "Permissions Granted", Snackbar.LENGTH_INDEFINITE).show();
        ConnectivityManager con=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=con.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnected())
        {
            //  new getdataonline().execute();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent pass = new Intent(Login_Activity.this, LoginActivity.class);
                    startActivity(pass);
                    finish();
                }
            }, time_out);
        }
        else{
            Toast.makeText(getApplicationContext(), "Internet not Connected", Toast.LENGTH_LONG);

        }

    }

}
