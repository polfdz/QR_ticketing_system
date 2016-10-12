package com.bbparty.eroc.bbp_qr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;


/**
 * By: Pol Fernández
 * following:
 * https://www.numetriclabz.com/android-qr-code-scanner-using-zxingscanner-library-tutorial/
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bScan, bAutocar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        bScan = (Button) findViewById(R.id.bScan);
        bScan.setOnClickListener(this);
        bAutocar = (Button) findViewById(R.id.bAutocar);
        bAutocar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bScan:
                Intent intent = new Intent(this, QRScan.class);
                intent.putExtra("scanner_option", "entrance"); //Entrada
                startActivity(intent);
                break;
            case R.id.bAutocar:
                Intent intent2 = new Intent(this, QRScan.class);
                intent2.putExtra("scanner_option", "autocar"); //Autocars
                startActivity(intent2);
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
