package nilmedov.appmvvm.viewmodels;

import android.databinding.Bindable;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nilmedov.appmvvm.BR;
import nilmedov.appmvvm.R;
import nilmedov.appmvvm.di.components.AppComponent;
import nilmedov.appmvvm.entities.Repository;
import nilmedov.appmvvm.entities.User;
import nilmedov.appmvvm.models.RepositoryModel;
import nilmedov.appmvvm.models.UserModel;
import nilmedov.appmvvm.network.NetworkResponse;
import nilmedov.appmvvm.utils.log.Logger;
import nilmedov.appmvvm.viewmodels.base.ViewModel;
import nilmedov.appmvvm.views.MainView;

/**
 * Created by Nazar on 10.01.2017.
 */

public class MainViewModel extends ViewModel<MainView> {
    private static final String TAG = MainViewModel.class.getSimpleName();

    @Nullable
    private Disposable fetchReposSubsription;
    @Nullable
    private Observable<NetworkResponse<List<Repository>>> fetchReposObservable;
    @Nullable
    private MainView view;

    @Inject
    UserModel userModel;
    @Inject
    RepositoryModel repositoryModel;
    @Inject
    Logger log;

    private User user;
    private boolean showProgressBar;
    private List<Repository> repositories = new ArrayList<>();

    private int repositoriesPage = 1;

    public MainViewModel(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    public void onViewAttached(MainView view) {
        this.view = view;
        String authToken = userModel.getAuthTokenFromSharedPrefs();
        user = userModel.getUserFromSharedPrefs();
        if (user != null && authToken != null && !authToken.isEmpty()) {
            log.i(TAG, authToken);
            log.i(TAG, user.toString());
            setUser(user);
            if (fetchReposObservable != null) {
                fetchReposSubsription = subscribe(fetchReposObservable);
            }
            if (repositories != null && !repositories.isEmpty()) {
                setRepositories(repositories);
            } else {
                fetchGithubRepositories();
            }
        } else {
            view.moveToSignInView();
        }
    }

    @Override
    public void onViewDetached() {
        if (fetchReposSubsription != null) {
            fetchReposSubsription.dispose();
        }
        view = null;
    }

    @Override
    public void onDestroyed() {
        fetchReposSubsription = null;
        fetchReposObservable = null;
    }

    public void fetchGithubRepositories() {
        setShowProgressBar(true);
        fetchReposObservable = repositoryModel.getRepositoriesFromNetwork(user.getLogin(), repositoriesPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
        fetchReposSubsription = subscribe(fetchReposObservable);

    }

    public void logOut() {
        userModel.removeAuthTokenFromSharedPrefs();
        userModel.removeUserFromSharedPrefs();
        view.moveToSignInView();
    }

    private Disposable subscribe(Observable<NetworkResponse<List<Repository>>> observable) {
        return observable.subscribe(response -> {
                    if (response.isSuccessful()) {
                        if (!response.getBody().isEmpty()) {
                            addRepositories(response.getBody());
                        }
                    } else if (response.getCode() == 401) {
                        view.moveToSignInView();
                    } else {
                        view.toast(R.string.something_was_wrong);
                    }
                },
                e -> {
                    log.e(TAG, "fetchReposError", e);
                    setShowProgressBar(false);
                    view.toast(R.string.something_was_wrong);
                    fetchReposObservable = null;
                },
                () -> {
                    repositoriesPage++;
                    setShowProgressBar(false);
                    fetchReposObservable = null;
                });
    }

    @Bindable
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        notifyPropertyChanged(BR.user);
    }

    @Bindable
    public boolean isShowProgressBar() {
        return showProgressBar;
    }

    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
        notifyPropertyChanged(BR.showProgressBar);
    }

    @Bindable
    public List<Repository> getRepositories() {
        return repositories;
    }

    public void addRepositories(List<Repository> repositories) {
        this.repositories.addAll(repositories);
        notifyPropertyChanged(BR.repositories);
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
        notifyPropertyChanged(BR.repositories);
    }
}
