package fr.fengdavid.matchplayer.views;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import fr.fengdavid.matchplayer.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
/*
                        case R.id.nav_events:
                            Intent events = new Intent(HomeActivity.this, ProfileActivity.class);
                            startActivity(events);
                            break;
*/
                        case R.id.nav_search_event:
                            Intent search = new Intent(HomeActivity.this, SearchEventActivity.class);
                            startActivity(search);
                            break;
                    }
                    return false;
                }
            };

}
