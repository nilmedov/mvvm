package nilmedov.appmvvm.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import nilmedov.appmvvm.App;


/**
 * Created by Nazar on 27.11.2016.
 */

public class SharedPreferencesHelper {
    private static final String PREFERENCES_FILE_KEY = "app_shared_prefs";

    public static final String AUTH_TOKEN = "auth_token";

    private SharedPreferencesHelper() {

    }

    public static synchronized void save(@NonNull Context context, @NonNull String key, @Nullable String value) {
        context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE).edit()
                .putString(key, value)
                .apply();
    }

    public static synchronized String get(@NonNull Context context, @NonNull String key, @Nullable String defaultValue) {
        return context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public static synchronized void remove(@NonNull Context context, @NonNull String key) {
        context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE).edit()
                .remove(key)
                .apply();
    }

    public static synchronized void saveObject(@NonNull Context context, @NonNull Object object) {
        String json = App.sGson.toJson(object);
        save(context, object.getClass().getSimpleName(), json);
    }

    @Nullable
    public static synchronized <T> T getObject(@NonNull Context context, Class<T> clazz) {
        String json = get(context, clazz.getSimpleName(), "");
        if (json.isEmpty()) {
            return null;
        }
        return App.sGson.fromJson(json, clazz);
    }
}
