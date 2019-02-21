package fr.fengdavid.matchplayer;

import android.app.Application;

import fr.fengdavid.matchplayer.di.AppComponent;
import fr.fengdavid.matchplayer.di.DaggerAppComponent;
import io.realm.Realm;

public class LoginApplication extends Application {

    AppComponent mAppComponent;
    private String token;
    private int id;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().build();
        Realm.init(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public void setId(int ID){
        this.id = ID;
    }
    public int getId() {
        return id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
