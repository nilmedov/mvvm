package nilmedov.appmvvm.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.Base64;

import javax.inject.Inject;

import io.reactivex.Observable;
import nilmedov.appmvvm.di.components.AppComponent;
import nilmedov.appmvvm.entities.User;
import nilmedov.appmvvm.network.ApiClient;
import nilmedov.appmvvm.network.NetworkResponse;
import nilmedov.appmvvm.utils.SharedPreferencesHelper;

/**
 * Created by Nazar on 27.11.2016.
 */

public class UserModelImpl implements UserModel {
    @Inject
    Context context;

    public UserModelImpl(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    public Observable<NetworkResponse<Pair<User, String>>> signIn(String email, String password) {
        final String authToken = toAuthToken(email, password);
        return ApiClient.getInstance().getGithubService().signIn(authToken)
//                .delay(10, TimeUnit.SECONDS)
                .map(response -> new NetworkResponse<>(new Pair<>(response.body(), authToken), response.code()));
    }

    @Override
    @Nullable
    public User getUserFromSharedPrefs() {
        return SharedPreferencesHelper.getObject(context, User.class);
    }

    @Override
    @Nullable
    public String getAuthTokenFromSharedPrefs() {
        return SharedPreferencesHelper.get(context, SharedPreferencesHelper.AUTH_TOKEN, null);
    }

    @Override
    public void saveUserInSharedPrefs(@NonNull User user) {
        SharedPreferencesHelper.saveObject(context, user);
    }

    @Override
    public void saveAuthTokenInSharedPrefs(@NonNull String token) {
        SharedPreferencesHelper.save(context, SharedPreferencesHelper.AUTH_TOKEN, token);
    }

    @Override
    public void removeUserFromSharedPrefs() {
        SharedPreferencesHelper.remove(context, User.class.getSimpleName());
    }

    @Override
    public void removeAuthTokenFromSharedPrefs() {
        SharedPreferencesHelper.remove(context, SharedPreferencesHelper.AUTH_TOKEN);
    }

    private String toAuthToken(String email, String password) {
        return "Basic " + Base64.encodeToString((email + ":" + password).getBytes(), Base64.NO_WRAP);
    }
}
