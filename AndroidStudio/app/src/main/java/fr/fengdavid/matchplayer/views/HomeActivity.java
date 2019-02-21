package fr.fengdavid.matchplayer.views;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.fengdavid.matchplayer.R;
import fr.fengdavid.matchplayer.adapters.EventsAdapter;
import fr.fengdavid.matchplayer.structs.Event;

public class HomeActivity extends AppCompatActivity {

    private String username;
    private String sport;
    private int level;
    private boolean isEvent;
    private int nEvents;
    private ArrayList<Event> lEvents;
    private Event event;
    private ListView lvEvents;
    private EventsAdapter eventsAdapter;

    private Map<String,String> params;



    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        queue = Volley.newRequestQueue(this);

        lvEvents = findViewById(R.id.lv_myevents);

        lEvents = new ArrayList<Event>();

        isEvent();

        BottomNavigationView nav =  findViewById(R.id.nav_btn);

        nav.setOnNavigationItemSelectedListener(navListener);

        FloatingActionButton btn_create = findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, CreateEventActivity.class);
                startActivity(i);
            }
        });




    }
    private void isEvent() {
        
        getMyEvents();
        if(!lEvents.isEmpty()){
            eventsAdapter = new EventsAdapter(this, lEvents);
            lvEvents.setAdapter(eventsAdapter);
        } else {
            TextView tv_noevent = findViewById(R.id.tv_noevents);
            tv_noevent.setCursorVisible(true);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_edit_profile:
                            Intent edit = new Intent(HomeActivity.this, ProfileActivity.class);
                            startActivity(edit);
                        break;

                        case R.id.nav_home:
                            break;

                        case R.id.nav_search_event:
                            Intent search = new Intent(HomeActivity.this, SearchEventActivity.class);
                            startActivity(search);
                            break;
                    }
                    return false;
                }
            };

    private void getMyEvents () {

        String URL = "http://ec2-100-27-21-188.compute-1.amazonaws.com:9000/users/event_by_id";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Event event;
                JSONObject jsonEvent;
                lEvents.clear();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        jsonEvent = response.getJSONObject(i);
                        String name = jsonEvent.getString("event_name");
                        String sport = jsonEvent.getString("sport");
                        String place = jsonEvent.getString("street_name") + ", " + jsonEvent.getString("street_number");
                        String date = jsonEvent.getString("date");
                        int nPlayers = jsonEvent.getInt("attending_number");
                        int maxPlayers = jsonEvent.getInt("attending_number_max");
                        int id = jsonEvent.getInt("event_id");

                        event = new Event(name, sport, place, date, nPlayers, maxPlayers, id);
                        lEvents.add(event);
                    }
                }catch (JSONException e) {
                    Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

                @Override
                protected java.util.Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();

                params.put("id", "0");


                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);



    }


}
