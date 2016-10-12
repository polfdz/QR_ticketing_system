package com.bbparty.eroc.bbp_qr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // creo el detector qr
        barcodeDetector =
                new BarcodeDetector.Builder(getContext())
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        // creo la camara fuente
        cameraSource = new CameraSource
                .Builder(getContext(), barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //case R.id.bScan:
                break;
        }
    }
}
