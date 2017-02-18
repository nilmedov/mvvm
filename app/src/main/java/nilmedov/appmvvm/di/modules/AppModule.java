package nilmedov.appmvvm.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nazar on 28.11.2016.
 */

@Module
public class AppModule {
    private Context applicationContext;

    public AppModule(@NonNull Context context) {
        this.applicationContext = context.getApplicationContext();
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return applicationContext;
    }
}
