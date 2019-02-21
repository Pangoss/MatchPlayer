package fr.fengdavid.matchplayer.views;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.fengdavid.matchplayer.R;
import fr.fengdavid.matchplayer.adapters.EventsAdapter;
import fr.fengdavid.matchplayer.structs.Event;

public class SearchEventActivity extends AppCompatActivity {

    private int nEvents;
    private ArrayList<Event> lEvents;

    private ListView lvEvents;
    private EventsAdapter eventsAdapter;
    int event_id;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        lEvents = new ArrayList<Event>();

        lvEvents = findViewById(R.id.lv_events);

        queue = Volley.newRequestQueue(this);

        Button btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEvents();
            }
        });
        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchEventActivity.this, EventActivity.class);
                Event event = (Event) eventsAdapter.getItem(position);
                i.putExtra("event", event);
                startActivity(i);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.sp_sortby);
        String[] values = {"Name","Date","Sport","Place"};
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.my_spinner, values));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        BottomNavigationView nav =  findViewById(R.id.nav_btn);

        nav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_edit_profile:
                            Intent edit = new Intent(SearchEventActivity.this, ProfileActivity.class);
                            startActivity(edit);
                            break;

                        case R.id.nav_home:
                            Intent events = new Intent(SearchEventActivity.this, HomeActivity.class);
                            startActivity(events);
                            break;

                        case R.id.nav_search_event:
                            break;
                    }
                    return false;
                }
            };

    private void searchEvents() {

        getEvents();
        eventsAdapter = new EventsAdapter(this, lEvents);
        lvEvents.setAdapter(eventsAdapter);

    }

    private void getEvents () {

        String url = "http://ec2-100-27-21-188.compute-1.amazonaws.com:9000/events/getall";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Event event;
                        JSONObject jsonEvent;
                        nEvents = response.length();
                        lEvents.clear();
                            try {
                                for(int i=0; i< nEvents;i++){
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

                            } catch (JSONException e) {
                                Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
                            }

                    }
                }, new  Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        queue.add(jsonArrayRequest);

    }
}
