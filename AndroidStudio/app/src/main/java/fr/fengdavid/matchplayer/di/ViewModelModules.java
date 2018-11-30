package fr.fengdavid.matchplayer.di;

import fr.fengdavid.matchplayer.viewmodels.ForgotPasswordViewModel;
import fr.fengdavid.matchplayer.viewmodels.LoginViewModel;
import fr.fengdavid.matchplayer.viewmodels.ProfileViewModel;
import fr.fengdavid.matchplayer.viewmodels.RegisterViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModules {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    abstract RegisterViewModel provideRegisterViewModel(RegisterViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract LoginViewModel provideLoginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel.class)
    abstract ForgotPasswordViewModel provideForgotPasswordViewModel(ForgotPasswordViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ProfileViewModel provideProfileViewModel(ProfileViewModel viewModel);
}
