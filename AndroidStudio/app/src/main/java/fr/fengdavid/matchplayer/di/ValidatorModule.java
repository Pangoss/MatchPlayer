package fr.fengdavid.matchplayer.di;

import fr.fengdavid.matchplayer.validators.EmailValidator;
import fr.fengdavid.matchplayer.validators.NameValidator;
import fr.fengdavid.matchplayer.validators.PasswordValidator;
import fr.fengdavid.matchplayer.validators.PhoneValidator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ValidatorModule {

    @Provides
    @Singleton
    PhoneValidator providePhoneValidator() {
        return new PhoneValidator("Invalid Phone number");
    }

    @Provides
    @Singleton
    EmailValidator provideEmailValidator() {
        return new EmailValidator("Invalid Email");
    }

    @Provides
    @Singleton
    NameValidator provideNameValidator() {
        return new NameValidator("Name cannot be empty");
    }

    @Provides
    @Singleton
    PasswordValidator providePasswordValidator() {
        return new PasswordValidator("Password length between 6 to 15 characters !");
    }
}
