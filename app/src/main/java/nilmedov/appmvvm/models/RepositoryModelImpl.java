package nilmedov.appmvvm.models;

import java.util.List;

import io.reactivex.Observable;
import nilmedov.appmvvm.entities.Repository;
import nilmedov.appmvvm.network.ApiClient;
import nilmedov.appmvvm.network.NetworkResponse;

/**
 * Created by Nazar on 08.02.2017.
 */

public class RepositoryModelImpl implements RepositoryModel {
    @Override
    public Observable<NetworkResponse<List<Repository>>> getRepositoriesFromNetwork(String username, int page) {
        return ApiClient.getInstance().getGithubService().publicRepositories(username, page)
                .map(response -> new NetworkResponse<>(response.body(), response.code()));
    }
}
