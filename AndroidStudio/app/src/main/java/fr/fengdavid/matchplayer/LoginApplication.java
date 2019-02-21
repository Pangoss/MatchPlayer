package fr.fengdavid.matchplayer;

import android.app.Application;

import fr.fengdavid.matchplayer.di.AppComponent;
import fr.fengdavid.matchplayer.di.DaggerAppComponent;
import io.realm.Realm;

public class LoginApplication extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().build();
        Realm.init(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
