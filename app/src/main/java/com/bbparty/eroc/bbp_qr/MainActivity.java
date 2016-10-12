package com.bbparty.eroc.bbp_qr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


/**
 * By: Pol Fernández
 * following:
 * https://www.numetriclabz.com/android-qr-code-scanner-using-zxingscanner-library-tutorial/
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bScan = (Button) findViewById(R.id.bScan);
        bScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bScan:
                Intent intent = new Intent(this, QRScan.class);
                startActivity(intent);
                break;
        }
    }
}
