package fr.fengdavid.matchplayer.viewmodels;

import android.databinding.BaseObservable;

import javax.inject.Inject;

public class HomeActivityViewModel extends BaseObservable {

    private String userName;
    private ViewListener mListener;

    @Inject
    public HomeActivityViewModel (String userName) {

        userName=userName;


    }

    public void setViewListener(ViewListener listener) {
        this.mListener = listener;
    }


    public interface ViewListener {

    }
}
