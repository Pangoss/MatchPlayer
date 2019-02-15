package fr.fengdavid.matchplayer.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.fengdavid.matchplayer.R;

public class SearchEventActivity extends AppCompatActivity {

    int nEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        Button btn_setting = findViewById(R.id.btn_refresh);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEvents();

            }
        });
    }

    private void searchEvents() {

    }
}
