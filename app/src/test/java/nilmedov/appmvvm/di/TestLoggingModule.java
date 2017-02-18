package nilmedov.appmvvm.di;


import nilmedov.appmvvm.di.modules.LoggingModule;
import nilmedov.appmvvm.utils.TestLogger;
import nilmedov.appmvvm.utils.log.Logger;

/**
 * Created by Nazar on 29.11.2016.
 */

public class TestLoggingModule extends LoggingModule {

    @Override
    public Logger provideLogger() {
        return new TestLogger();
    }
}
