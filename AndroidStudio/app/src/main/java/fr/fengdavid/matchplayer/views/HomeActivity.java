package fr.fengdavid.matchplayer.views;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import fr.fengdavid.matchplayer.R;

public class HomeActivity extends AppCompatActivity {

    private String username;
    private String sport;
    private int level;
    private int nEvents = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btn_setting = findViewById(R.id.btn_settings);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(i);
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
                            Intent edit = new Intent(HomeActivity.this, ProfileActivity.class);
                            startActivity(edit);
                        break;

                        case R.id.nav_events:
                            Intent events = new Intent(HomeActivity.this, EventActivity.class);
                            startActivity(events);
                            break;

                        case R.id.nav_search_event:
                            Intent search = new Intent(HomeActivity.this, SearchEventActivity.class);
                            startActivity(search);
                            break;
                    }
                    return false;
                }
            };

    //Test icons
    public void onSetIcon (View view) {

        ImageView icon = findViewById(R.id.iv_icon);
        if(nEvents<4) {
            nEvents++;
        } else nEvents = 0;

        RatingBar rb = findViewById(R.id.rb_level);
        rb.setRating(nEvents+1);


        switch (nEvents) {
            case 0:
                icon.setImageResource(R.drawable.ic_sport_basket);
                break;

            case 1:
                icon.setImageResource(R.drawable.ic_sport_baseball);
                break;

            case 2:
                icon.setImageResource(R.drawable.ic_sport_football);
                break;

            case 3:
                icon.setImageResource(R.drawable.ic_sport_rugby);
                break;

            case 4:
                icon.setImageResource(R.drawable.ic_sport_tennis);
                break;
        }

    }


}
