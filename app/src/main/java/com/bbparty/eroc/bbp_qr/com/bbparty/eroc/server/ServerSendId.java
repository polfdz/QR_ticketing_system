package com.bbparty.eroc.bbp_qr.com.bbparty.eroc.server;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Pol on 08/12/2015.
 */
public class ServerSendId extends AsyncTask<String, String, JSONObject> {
    Constants constants;
    JSONObject result;
    SharedPreferences preferences;
    String email, password, s, facebook;
    public ServerSendId(){
        constants = new Constants();
        result = new JSONObject();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... userInfo) {
        while(!isCancelled()) {
            try {
                email = userInfo[0];
                password = userInfo[1];
                s = userInfo[2];
                facebook = userInfo[3];

                loginToServer(email, password, s, facebook);
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
            return result;
        }
        this.onCancelled();
        return null;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        Log.d("ANDRO_ASYNC", progress[0]);
    }

    @Override
    protected void onPostExecute(JSONObject res) {
        super.onPostExecute(res);
    }

    public JSONObject loginToServer(String _email, String _password, String _s, String _facebook) {
        AsyncHttpClient client = new SyncHttpClient();
        RequestParams rp = new RequestParams();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("Authentication",constants.AUTH_KEY);
        param.put("Function", "login");
        param.put("Email", _email);
        param.put("Password", _password);
        RequestParams params = new RequestParams(param);

        client.get(constants.BASE_URL + "webservice.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jObject) {
                //Toast.makeText(context, ""+jObject.toString(), 5000).show();
                try {
                    if (jObject.getInt("MessageCode") == 200) //OK
                    {
                        //success of login

                        JSONArray jData = jObject.getJSONArray("Data");
                        Log.d("JSON Array", jData.toString());

                        JSONArray jsonAObject = jData.getJSONArray(0);
                        JSONObject jsonObject = jsonAObject.getJSONObject(0);
                        Log.d("JSON DATA", jsonObject.toString());
                        String id = jsonObject.getString("id");
                        String email = jsonObject.getString("email");
                        String country = jsonObject.getString("country");
                        String gender = jsonObject.getString("gender");
                        String username = jsonObject.getString("username");
                        String age = jsonObject.getString("age");
                        String height = jsonObject.getString("height");
                        String token = jsonObject.getString("token");
                        String timestamp = jsonObject.getString("timestamp");
                        //check for existing race_id
                        String race_id;
                        //if(jData.length() > 1){
                        Log.d("RaceId",""+jData.getJSONArray(1));
//                        Log.d("RaceId2",""+jData.getJSONArray(1).getJSONObject(0));
                        if(!jData.getJSONArray(1).isNull(0)){
                            JSONArray jsonArrayRaceId = jData.getJSONArray(1);
                                Log.d("ServerSendId", "" + jsonArrayRaceId.getJSONObject(0).toString());
                                race_id = jsonArrayRaceId.getJSONObject(0).getString("race_id");
                        }else{
                           race_id = "-1";
                        }

                        Log.i("Serverlogin", "race_id " + race_id);
                        result.put("result", 200);
                        result.put("id", id);
                        result.put("email", email);
                        result.put("country", country);
                        result.put("gender", gender);
                        result.put("username", username);
                        result.put("age", age);
                        result.put("height", height);
                        result.put("token", token);
                        result.put("timestamp", timestamp);
                        result.put("race_id", race_id);

                    } else if (jObject.getInt("MessageCode") == 504) //Wrong Query Result
                    {
                        result.put("result", 504);
                    } else {
                result.put("result", 600);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
            @Override
            public void onFailure(int a, Header[] as, String aas, Throwable e) {
                Log.d("OnFailure Login",""+a+ "head "+as+" string" +aas+" throw"+ e.toString());
                e.printStackTrace();
            }
        });
        return result;
    }
    @Override
    protected void onCancelled(){
        super.onCancelled();
    }
}
