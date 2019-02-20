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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class registerRequest {

    private String URL_POST = "http://ec2-100-27-21-188.compute-1.amazonaws.com:9000/users/register";

    public String RequestRegisterName, RequestRegisterPassword, RequestRegisterPhone, RequestRegisterEmail;
    private RequestQueue requestQueue;
    private String data = "{"+
            "\"name\":" + "\"" + RequestRegisterName + "\","+
            "\"password\":" + "\"" + RequestRegisterPassword + "\","+
            "\"email\":" + "\"" + RequestRegisterEmail + "\","+
            "\"phonenumber\":" + "\"" + RequestRegisterPhone + "\""+
            "}";

    public registerRequest(String RegisterName, String RegisterPassword, String RegisterPhone, String RegisterEmail) {
        this.RequestRegisterName = RegisterName;
        this.RequestRegisterPassword = RegisterPassword;
        this.RequestRegisterPhone = RegisterPhone;
        this.RequestRegisterEmail = RegisterEmail;
    }

    public void sendRegisterDataToEc2(Context mContext,String Name, String Password, String Phone, String Email) {

        requestQueue = Volley.newRequestQueue(mContext);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", Name);
            jsonBody.put("password", Password);
            jsonBody.put("email", Email);
            jsonBody.put("phonenumber", Phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                    // 200 : registration done successfully
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

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);
    }
}
