package com.bbparty.eroc.bbp_qr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbparty.eroc.bbp_qr.com.bbparty.eroc.server.CheckInternetConnection;
import com.bbparty.eroc.bbp_qr.com.bbparty.eroc.server.ServerValidateTicket_Bus;
import com.bbparty.eroc.bbp_qr.com.bbparty.eroc.server.ServerValidateTicket_Entrance;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Pol on 10/10/2016.
 */
public class TicketId extends AppCompatActivity implements View.OnClickListener {
    Context context;
    TextView tTicketId;
    Button bBack, bInfo;
    ImageView iImage, iMode;
    String ticket_id;
    String scanner_option; //entrance , autocar
    ServerValidateTicket_Entrance serverValidateTicket_Entrance;
    ServerValidateTicket_Bus serverValidateTicket_Bus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.layout_warning);
        context = getApplicationContext();

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
            Log.d("Escaner", ticket_id);
            if(scanner_option.equalsIgnoreCase("entrance")){ //ticket entrada
                int check = checkTicket_Entrance(ticket_id);
                iMode.setImageResource(R.drawable.bb_party);
                if (check == 0) { //ticket valid
                    iImage.setImageResource(R.drawable.accepted);
                    tTicketId.setText(R.string.accepted);
                }else if(check == 1) { //ticket already in
                    iImage.setImageResource(R.drawable.rejected);
                    tTicketId.setText(R.string.ticket_in);
                }else if(check == 2){ //error
                    iImage.setImageResource(R.drawable.rejected);
                    tTicketId.setText(R.string.not_accepted);
                }else if (check == 3){
                    iImage.setImageResource(R.drawable.failed);
                    tTicketId.setText(R.string.internet_error);
                }
            }else if(scanner_option.equalsIgnoreCase("autocar")){ //ticket autocar
                int check = checkTicket_Autocar(ticket_id);
                iMode.setImageResource(R.drawable.autocar);
                if(check == 1){ //bus 1
                    iImage.setImageResource(R.drawable.bus1);
                    tTicketId.setText(R.string.bus1_accepted);
                }else if(check == 2){ //bus 2
                    iImage.setImageResource(R.drawable.bus2);
                    tTicketId.setText(R.string.bus2_accepted);
                }else if(check == 4){ //not valid for bus
                    iImage.setImageResource(R.drawable.rejected);
                    tTicketId.setText(R.string.bus_rejected);
                }else if (check == 5) {
                    iImage.setImageResource(R.drawable.rejected);
                    tTicketId.setText(R.string.not_accepted);
                }else{
                    iImage.setImageResource(R.drawable.failed);
                    tTicketId.setText(R.string.internet_error_info);
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

    public int checkTicket_Entrance(String _ticket_id){
        //connexió amb servidor i retornar si el ticket ha sigut comprovat ja o no
        //COMPROVAR SI QR EXISTEIX I/O SI JA HA ENTRAT
        //modificar bd si no ha entrat --> a 1
        Log.d("Track_ticket", _ticket_id);
        serverValidateTicket_Entrance = new ServerValidateTicket_Entrance();
        try {
            JSONObject result = serverValidateTicket_Entrance.execute(_ticket_id).get();
            int conn = result.getInt("MessageCode");
            if(conn == 200){
                //ticket vàlid
                return 0;
            }else if(conn == 505){
                //ticket already IN
                return 1;
            }else if(conn == 504){
                //ticket not vàlid
                return 2;
            }else{ //internet error
                return 3;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 3;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return 3;
        } catch (JSONException e) {
            e.printStackTrace();
            return 3;
        }finally {
            serverValidateTicket_Entrance.cancel(true);
        }
    }

    public int checkTicket_Autocar(String _ticket_id){
        //connexió amb servidor i retornar si el ticket es d'autocar o no
        serverValidateTicket_Bus = new ServerValidateTicket_Bus();
        try {
            JSONObject result = serverValidateTicket_Bus.execute(_ticket_id).get();
            int conn = result.getInt("MessageCode");
            int type = 0;
            if(result.has("bus")){
                type = result.getInt("bus");
            }
            if(conn == 200){ //valid for bus
                return type;
            }else if(conn == 505){ //not valid for bus
                return 4;
            }else if(conn == 504){ //ticket not valid
                return 5;
            }else{
                return 6;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 6;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return 6;
        } catch (JSONException e) {
            e.printStackTrace();
            return 6;
        }finally {
            serverValidateTicket_Bus.cancel(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bBack:
                goBack();
                finish();
                break;
            case R.id.bInfo:
                //check internet
                if(CheckInternetConnection.isNetworkAvailable(this)){
                    if (ticket_id != null) {
                        Intent intentInfo = new Intent(this, Info.class);
                        intentInfo.putExtra("ticket_id", ticket_id);
                        intentInfo.putExtra("scanner_option", scanner_option);
                        startActivity(intentInfo);
                        finish();
                    }else{
                        //ERROR COMPROVAR VALIDESA DEL QR
                        popUpToast(getResources().getString(R.string.escaner_error));
                    }
                }else{
                    popUpToast(getResources().getString(R.string.internet_error_info));
                }
                break;

        }
    }
    private void popUpToast(String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }
    public void goBack(){
        Intent intent = new Intent(this, QRScan.class);
        intent.putExtra("scanner_option", scanner_option);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goBack();
        finish();
    }
}
