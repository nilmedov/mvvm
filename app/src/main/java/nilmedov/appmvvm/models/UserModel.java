package nilmedov.appmvvm.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import io.reactivex.Observable;
import nilmedov.appmvvm.entities.User;
import nilmedov.appmvvm.network.NetworkResponse;

/**
 * Created by Nazar on 27.11.2016.
 */

public interface UserModel {

    Observable<NetworkResponse<Pair<User, String>>> signIn(String email, String password);

    @Nullable
    User getUserFromSharedPrefs();

    @Nullable
    String getAuthTokenFromSharedPrefs();

    void saveUserInSharedPrefs(@NonNull User user);

    void saveAuthTokenInSharedPrefs(@NonNull String token);

    void removeUserFromSharedPrefs();

    void removeAuthTokenFromSharedPrefs();
}
