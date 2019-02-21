package fr.fengdavid.matchplayer.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import fr.fengdavid.matchplayer.R;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        Spinner spinner = (Spinner) findViewById(R.id.sp_sports);
        String[] values = {"football","basketball","tennis","baseball", "rugby"};
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.my_spinner, values));

        Button btn_create = findViewById(R.id.btn_create_event);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });
    }

    private void createEvent() {

        String URL = "http://ec2-100-27-21-188.compute-1.amazonaws.com:9000/events/create";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();

                EditText et_name = findViewById(R.id.et_name);
                Spinner sp_sport = findViewById(R.id.sp_sports);
                EditText et_place = findViewById(R.id.et_place);
                EditText et_date = findViewById(R.id.et_date);
                EditText et_players = findViewById(R.id.et_players);

                String name = et_name.getText().toString();
                TextView tv_sport = (TextView) sp_sport.getSelectedView();
                String sport= tv_sport.getText().toString();
                String place = et_place.getText().toString();
                String date= et_date.getText().toString();
                String maxPlayers = et_players.getText().toString();

                params.put("event_name", name);
                params.put("sport", sport);
                params.put("street_number", place.substring(place.lastIndexOf(",")));
                params.put("street_name", place.substring(0,place.lastIndexOf(",")));
                params.put("date", date);
                params.put("attending_number_max", maxPlayers);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
