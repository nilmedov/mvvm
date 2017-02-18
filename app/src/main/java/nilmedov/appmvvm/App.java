package nilmedov.appmvvm;

import android.app.Application;

import com.google.gson.Gson;

import nilmedov.appmvvm.di.components.AppComponent;
import nilmedov.appmvvm.di.components.DaggerAppComponent;
import nilmedov.appmvvm.di.modules.AppModule;
import nilmedov.appmvvm.di.modules.LoggingModule;
import nilmedov.appmvvm.di.modules.ModelsModule;

/**
 * Created by Nazar on 28.11.2016.
 */

public class App extends Application {
    public static Gson sGson = new Gson();
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .loggingModule(new LoggingModule())
                .modelsModule(new ModelsModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
