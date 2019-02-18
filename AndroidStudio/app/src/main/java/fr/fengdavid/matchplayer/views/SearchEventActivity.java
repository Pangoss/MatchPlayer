package fr.fengdavid.matchplayer.views;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        lEvents = new ArrayList<Event>();

        lvEvents = findViewById(R.id.lvEvents);

        queue = Volley.newRequestQueue(this);

        Button btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEvents();
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
    }

    private void searchEvents() {

        getEvents();
        eventsAdapter = new EventsAdapter(this, lEvents);
        lvEvents.setAdapter(eventsAdapter);


    }

    private void getEvents () {

        String url = "http://ec2-100-27-21-188.compute-1.amazonaws.com:9000/events/getall";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @TargetApi(Build.VERSION_CODES.KITKAT)
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

                                    event = new Event(name, sport, place, date, nPlayers, maxPlayers);
                                    lEvents.add(event);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
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
