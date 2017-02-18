package nilmedov.appmvvm.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import nilmedov.appmvvm.BR;
import nilmedov.appmvvm.entities.Repository;

/**
 * Created by Nazar on 05.02.2017.
 */

public class ItemRepositoryViewModel extends BaseObservable {
    private Repository repository;

    public ItemRepositoryViewModel(Repository repository) {
        setRepository(repository);
    }

    @Bindable
    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
        notifyPropertyChanged(BR.repository);
    }
}
