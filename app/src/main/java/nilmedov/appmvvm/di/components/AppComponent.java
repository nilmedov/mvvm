package nilmedov.appmvvm.di.components;

import javax.inject.Singleton;

import dagger.Component;
import nilmedov.appmvvm.di.modules.AppModule;
import nilmedov.appmvvm.di.modules.LoggingModule;
import nilmedov.appmvvm.di.modules.ModelsModule;
import nilmedov.appmvvm.models.UserModelImpl;
import nilmedov.appmvvm.viewmodels.MainViewModel;
import nilmedov.appmvvm.viewmodels.SignInViewModel;

/**
 * Created by Nazar on 28.11.2016.
 */

@Component(modules = {AppModule.class, LoggingModule.class, ModelsModule.class})
@Singleton
public interface AppComponent {

    void inject(UserModelImpl userModelImpl);

    void inject(SignInViewModel signInViewModel);

    void inject(MainViewModel mainViewModel);
}
