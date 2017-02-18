package nilmedov.appmvvm.di;

import android.content.Context;

import org.mockito.Mockito;

import nilmedov.appmvvm.di.modules.AppModule;


/**
 * Created by Nazar on 30.11.2016.
 */

public class TestAppModule extends AppModule {

    public TestAppModule() {
        super(Mockito.mock(Context.class));
    }
}
