package fr.fengdavid.matchplayer.di;

import fr.fengdavid.matchplayer.repositories.RealmUserRepository;
import fr.fengdavid.matchplayer.repositories.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    @Singleton
    UserRepository provideIssueRepository(RealmUserRepository repository) {
        return repository;
    }
}
