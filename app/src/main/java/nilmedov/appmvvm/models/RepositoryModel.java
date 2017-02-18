package nilmedov.appmvvm.models;

import java.util.List;

import io.reactivex.Observable;
import nilmedov.appmvvm.entities.Repository;
import nilmedov.appmvvm.network.NetworkResponse;

/**
 * Created by Nazar on 08.02.2017.
 */

public interface RepositoryModel {

    Observable<NetworkResponse<List<Repository>>> getRepositoriesFromNetwork(String username, int page);
}
