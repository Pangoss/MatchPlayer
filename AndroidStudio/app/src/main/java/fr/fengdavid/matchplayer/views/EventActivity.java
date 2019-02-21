package fr.fengdavid.matchplayer.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import fr.fengdavid.matchplayer.structs.Event;

public class EventActivity extends AppCompatActivity {

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        showEvent();

        Button btn_join = findViewById(R.id.btn_join_event);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(event.getnPlayers()<event.getMaxPlayers()) {
                    joinEvent();
                }else {
                    Context context = getApplicationContext();
                    CharSequence text = "Players limit reached";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });


    }

    private void showEvent() {
        Bundle data = getIntent().getExtras();
        event =(Event) data.getParcelable("event");

        TextView tv_name = findViewById(R.id.tv_name_ev);
        TextView tv_sport = findViewById(R.id.tv_sport_ev);
        TextView tv_place = findViewById(R.id.tv_place_ev);
        TextView tv_date = findViewById(R.id.tv_date_ev);
        TextView tv_hour = findViewById(R.id.tv_hour_ev);
        TextView tv_players = findViewById(R.id.tv_players_ev);

        tv_name.append(event.getName());
        tv_sport.append(event.getSport());
        tv_place.append(event.getPlace());
        tv_date.append(event.getDate().substring(0, 10));
        tv_hour.append(event.getDate().substring(11,16));
        tv_players.append(event.getnPlayers() + "/" + event.getMaxPlayers());
    }

    private void joinEvent() {

        String URL = "http://ec2-100-27-21-188.compute-1.amazonaws.com:9000/users/update/join";

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
                String id = String.valueOf(event.getId());

                params.put("id_event", id);
                params.put("id_user", "1");


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
