package fr.fengdavid.matchplayer.di;

import fr.fengdavid.matchplayer.views.ForgotPasswordActivity;
import fr.fengdavid.matchplayer.views.LoginActivity;
import fr.fengdavid.matchplayer.views.ProfileActivity;
import fr.fengdavid.matchplayer.views.RegisterActivity;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ViewModelModules.class, ValidatorModule.class})

public interface AppComponent {

    void inject(RegisterActivity registerActivity);
    void inject(LoginActivity loginActivity);
    void inject(ForgotPasswordActivity forgotPasswordActivity);
    void inject(ProfileActivity profileActivity);

}
