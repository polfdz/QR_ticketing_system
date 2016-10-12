package com.bbparty.eroc.bbp_qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pol on 10/10/2016.
 */
public class TicketId extends AppCompatActivity implements View.OnClickListener {

    TextView tTicketId;
    Button bBack, bInfo;
    ImageView iImage, iMode;
    String ticket_id;
    String scanner_option; //entrance , autocar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.layout_warning);

        tTicketId = (TextView) findViewById(R.id.tTicketId);
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);
        bInfo = (Button) findViewById(R.id.bInfo);
        bInfo.setOnClickListener(this);
        iImage = (ImageView) findViewById(R.id.image);
        iMode = (ImageView) findViewById(R.id.iMode);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            ticket_id = extras.getString("ticket_id");
            tTicketId.setText(ticket_id);
            scanner_option = extras.getString("scanner_option");
            Log.d("Escaner", scanner_option);
            if(scanner_option.equalsIgnoreCase("entrance")){ //ticket entrada
                boolean check = checkTicket(ticket_id);
                iMode.setImageResource(R.drawable.bb_party);
                if (check == true) {
                    iImage.setImageResource(R.drawable.accepted);
                    tTicketId.setText(R.string.accepted);
                }else {
                    iImage.setImageResource(R.drawable.rejected);
                    tTicketId.setText(R.string.not_accepted);
                }
            }else if(scanner_option.equalsIgnoreCase("autocar")){ //ticket autocar
                boolean check = checkAutocar(ticket_id);
                iMode.setImageResource(R.drawable.autocar);
                if(check == true){
                    iImage.setImageResource(R.drawable.accepted);
                    tTicketId.setText(R.string.bus_accepted);
                }else{
                    iImage.setImageResource(R.drawable.rejected);
                    tTicketId.setText(R.string.bus_rejected);
                }
            }else{
                iImage.setImageResource(R.drawable.ic_launcher);
                tTicketId.setText(R.string.contact);
            }
        }else{
            iImage.setImageResource(R.drawable.ic_launcher);
            tTicketId.setText(R.string.contact);
        }
    }

    public boolean checkTicket(String _ticket_id){
        //connexió amb servidor i retornar si el ticket ha sigut comprovat ja o no
        //COMPROVAR SI QR EXISTEIX I/O SI JA HA ENTRAT
        //modificar bd si no ha entrat --> a 1

        return true;
    }

    public boolean checkAutocar(String _ticket_id){
        //connexió amb servidor i retornar si el ticket es d'autocar o no

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bBack:
                Intent intent = new Intent(this, QRScan.class);
                intent.putExtra("scanner_option", scanner_option);
                startActivity(intent);
                break;
            case R.id.bInfo:
                if (ticket_id != null){
                    Intent intentInfo = new Intent(this, Info.class);
                    intentInfo.putExtra("ticket_id", ticket_id);
                    intentInfo.putExtra("scanner_option", scanner_option);
                    startActivity(intentInfo);
                }else{
                    //ERROR COMPROVAR VALIDESA DEL QR
                }
                break;

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, QRScan.class);
        intent.putExtra("scanner_option", scanner_option);
        startActivity(intent);
    }
}
