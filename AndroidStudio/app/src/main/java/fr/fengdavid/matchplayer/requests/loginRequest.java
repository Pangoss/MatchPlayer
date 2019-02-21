package fr.fengdavid.matchplayer.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class loginRequest {

    private String URL_POST = "http://ec2-100-27-21-188.compute-1.amazonaws.com:9000/users/login";

    public String LoginPassword, LoginEmail;
    private RequestQueue requestQueue;

    public String auth="";
    public String token;
    public int Id;

    public loginRequest(String loginPassword, String loginEmail) {
        this.LoginPassword = loginPassword;
        this.LoginEmail = loginEmail;
    }

    public String getAuth(){
        return auth;
    }
    public void setAuth(String mAuth){
        this.auth = mAuth;
    }
    public String getToken() {
        return token;
    }
    public int getId() {
        return Id;
    }

    public void sendLoginDataToEc2(Context mContext, String Email, String Password) {

        requestQueue = Volley.newRequestQueue(mContext);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("password", Password);
            jsonBody.put("email", Email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.i("LOG_VOLLEY", response);
                // 200 : registration done successfully
                // Process the JSON
                try{
                    // Loop through the array elements
                    JSONObject loginResponse = new JSONObject(response);
                    // Get the current loginResponse (json object) data
                    auth = loginResponse.getString("auth");
                    token = loginResponse.getString("token");
                    Id = loginResponse.getInt("id");
                    setAuth(auth);
                    Log.i("DATA", "ID: " + Id + " | auth: " + auth + " | token: " + token);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody(){
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            /*@Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }*/
        };
        requestQueue.add(stringRequest);
    }
}
