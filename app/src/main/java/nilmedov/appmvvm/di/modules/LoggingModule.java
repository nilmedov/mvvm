package nilmedov.appmvvm.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nilmedov.appmvvm.utils.log.AndroidLogger;
import nilmedov.appmvvm.utils.log.Logger;

/**
 * Created by Nazar on 28.11.2016.
 */

@Module
public class LoggingModule {

    @Provides
    @Singleton
    public Logger provideLogger() {
        return new AndroidLogger();
    }
}
