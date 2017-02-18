package nilmedov.appmvvm.di.modules;

import dagger.Module;
import dagger.Provides;
import nilmedov.appmvvm.App;
import nilmedov.appmvvm.models.RepositoryModel;
import nilmedov.appmvvm.models.RepositoryModelImpl;
import nilmedov.appmvvm.models.UserModel;
import nilmedov.appmvvm.models.UserModelImpl;

/**
 * Created by Nazar on 29.11.2016.
 */

@Module
public class ModelsModule {

    @Provides
    public UserModel provideUserModel() {
        return new UserModelImpl(App.getAppComponent());
    }

    @Provides
    public RepositoryModel provideRepositoryModel() {
        return new RepositoryModelImpl();
    }
}
