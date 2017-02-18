package nilmedov.appmvvm.di;

import org.mockito.Mockito;

import nilmedov.appmvvm.di.modules.ModelsModule;
import nilmedov.appmvvm.models.RepositoryModel;
import nilmedov.appmvvm.models.UserModel;


/**
 * Created by Nazar on 29.11.2016.
 */

public class TestModelsModule extends ModelsModule {

    @Override
    public UserModel provideUserModel() {
        return Mockito.mock(UserModel.class);
    }

    @Override
    public RepositoryModel provideRepositoryModel() {
        return Mockito.mock(RepositoryModel.class);
    }
}
