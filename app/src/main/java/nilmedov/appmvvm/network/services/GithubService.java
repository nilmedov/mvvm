package nilmedov.appmvvm.network.services;

import java.util.List;

import io.reactivex.Observable;
import nilmedov.appmvvm.entities.Repository;
import nilmedov.appmvvm.entities.User;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nazar on 26.11.2016.
 */

public interface GithubService {

    @GET("users/{username}/repos?per_page=5")
    Observable<Response<List<Repository>>> publicRepositories(@Path("username") String username, @Query("page") int page);

    @GET("user")
    Observable<Response<User>> signIn(@Header("Authorization") String token);
}
