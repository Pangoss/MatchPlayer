package fr.fengdavid.matchplayer.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import fr.fengdavid.matchplayer.R;
import fr.fengdavid.matchplayer.databinding.ActivityHomeBinding;
import fr.fengdavid.matchplayer.viewmodels.HomeActivityViewModel;
import fr.fengdavid.matchplayer.viewmodels.LoginViewModel;

public class HomeActivity extends AppCompatActivity implements HomeActivityViewModel.ViewListener{
    @Inject
    HomeActivityViewModel viewModel;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding;//******

        Button btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        Button btnEvents = findViewById(R.id.btn_events);
        btnEvents.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, EventsActivity.class);
                startActivity(i);
            }
        });

        Button btnSearchEvent= findViewById(R.id.btn_search_events);
        btnSearchEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, SearchEventActivity.class);
                startActivity(i);
            }
        });


    }

    public void onEvents () {
        //load Events Activity
    }

    public void onSearchEvents () {
        //load Search Event Activity
    }

    public void onEditAccount () {
        //load Edit Account Activity
    }
}
