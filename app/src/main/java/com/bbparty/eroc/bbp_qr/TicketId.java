package com.bbparty.eroc.bbp_qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pol on 10/10/2016.
 */
public class TicketId extends AppCompatActivity implements View.OnClickListener {

    TextView tTicketId;
    Button bBack, bInfo;
    ImageView iImage;
    String ticket_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_warning);

        tTicketId = (TextView) findViewById(R.id.tTicketId);
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);
        bInfo = (Button) findViewById(R.id.bInfo);
        bInfo.setOnClickListener(this);
        iImage = (ImageView) findViewById(R.id.image);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            ticket_id = extras.getString("ticket_id");
            tTicketId.setText(ticket_id);
            boolean check = checkTicket(ticket_id);

            if(check == true){
                iImage.setImageResource(R.drawable.accepted);
                tTicketId.setText(R.string.accepted);
            }else{
                iImage.setImageResource(R.drawable.rejected);
                tTicketId.setText(R.string.not_accepted);
            }

        }else{
            iImage.setImageResource(R.drawable.ic_launcher);
            tTicketId.setText(R.string.contact);
        }

    }

    public boolean checkTicket(String _ticket_id){
        //connexió amb servidor i retornar si el ticket ha sigut comprovat ja o no
        //COMPROVAR SI QR EXISTEIX I/O SI JA HA ENTRAT

        return true;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bBack:
                Intent intent = new Intent(this, QRScan.class);
                startActivity(intent);
                break;
            case R.id.bInfo:
                if (ticket_id != null){
                    Intent intentInfo = new Intent(this, Info.class);
                    intentInfo.putExtra("ticket_id", ticket_id);
                    startActivity(intentInfo);
                }else{
                    //ERROR COMPROVAR VALIDESA DEL QR
                }
                break;

        }
    }
}
