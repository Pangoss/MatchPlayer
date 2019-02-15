package fr.fengdavid.matchplayer.requests;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class registerRequest extends AppCompatActivity {

    private String URL_POST = "https://ec2-100-27-21-188.compute-1.amazonaws.com:9000/users/register";
    private String RequestRegisterName, RequestRegisterPassword, RequestRegisterPhone, RequestRegisterEmail;
    private String data;

    public registerRequest(String RegisterName, String RegisterPassword, String RegisterPhone, String RegisterEmail){
        this.RequestRegisterName = RegisterName;
        this.RequestRegisterPassword = RegisterPassword;
        this.RequestRegisterPhone = RegisterPhone;
        this.RequestRegisterEmail = RegisterEmail;
        this.data= "{"+
                "\"name\":" + "\"" + RequestRegisterName + "\","+
                "\"password\":" + "\"" + RequestRegisterPassword + "\","+
                "\"email\":" + "\"" + RequestRegisterEmail + "\","+
                "\"phonenumber\":" + "\"" + RequestRegisterPhone + "\""+
                "}";
    }

    public void InsertRegisterDataToDb() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj_res=new JSONObject(response);
                            Toast.makeText(getApplicationContext(),obj_res.toString(),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
                        }
                        //Log.d("Response", response);
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                //Log.d("Error.Response", error.getMessage());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            /*protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", RequestRegisterName);
                params.put("password", RequestRegisterPassword);
                params.put("email",RequestRegisterEmail);
                params.put("phonenumber",RequestRegisterPhone);
                params.put("Content-Type", "application/json");
                return params;
            }*/
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data == null ? null : data.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
