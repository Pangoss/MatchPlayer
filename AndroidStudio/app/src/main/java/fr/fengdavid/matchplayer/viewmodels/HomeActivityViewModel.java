package fr.fengdavid.matchplayer.viewmodels;

import android.databinding.BaseObservable;

import javax.inject.Inject;

public class HomeActivityViewModel extends BaseObservable {
    private String userName;

    @Inject
    public HomeActivityViewModel (String userName) {

        userName=userName;


    }


    public interface ViewListener {

    }
}
