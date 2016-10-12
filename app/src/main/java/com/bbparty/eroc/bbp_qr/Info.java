package com.bbparty.eroc.bbp_qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Pol on 11/10/2016.
 */
public class Info extends AppCompatActivity implements View.OnClickListener {

    String ticket_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ticket_id = extras.getString("ticket_id");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bBack:
                Intent intent = new Intent(this, QRScan.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TicketId.class);
        intent.putExtra("ticket_id", ticket_id);
        startActivity(intent);
    }
}
