package nilmedov.appmvvm.viewmodels;

import android.databinding.Bindable;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nilmedov.appmvvm.BR;
import nilmedov.appmvvm.R;
import nilmedov.appmvvm.di.components.AppComponent;
import nilmedov.appmvvm.entities.User;
import nilmedov.appmvvm.models.UserModel;
import nilmedov.appmvvm.network.NetworkResponse;
import nilmedov.appmvvm.utils.log.Logger;
import nilmedov.appmvvm.viewmodels.base.ViewModel;
import nilmedov.appmvvm.views.SignInView;

/**
 * Created by Nazar on 05.01.2017.
 */

public class SignInViewModel extends ViewModel<SignInView> {
    private static final String TAG = SignInViewModel.class.getSimpleName();

    @Nullable
    private Disposable signInSubscription;
    @Nullable
    private Observable<NetworkResponse<Pair<User, String>>> signInObservable;
    @Nullable
    private SignInView view;

    @Inject
    UserModel userModel;
    @Inject
    Logger log;

    private String email;
    private String password;
    private boolean showProgressBar;

    public SignInViewModel(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    public void onViewAttached(SignInView view) {
        this.view = view;
        if (signInObservable != null) {
            setShowProgressBar(true);
            signInSubscription = subscribe(signInObservable);
        }
    }

    @Override
    public void onViewDetached() {
        if (signInSubscription != null) {
            signInSubscription.dispose();
        }
        view = null;
    }

    @Override
    public void onDestroyed() {
        signInSubscription = null;
        signInObservable = null;
    }

    public void onClickSignInButton() {
        setShowProgressBar(true);
        signInObservable = userModel.signIn(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
        signInSubscription = subscribe(signInObservable);
    }

    private Disposable subscribe(Observable<NetworkResponse<Pair<User, String>>> observable) {
        return observable.subscribe(response -> {
                    if (response.isSuccessful()) {
                        log.i(TAG, response.getBody().toString());
                        userModel.saveUserInSharedPrefs(response.getBody().first);
                        userModel.saveAuthTokenInSharedPrefs(response.getBody().second);
                        view.moveToMainView();
                    } else if (response.getCode() == 401) {
                        view.toast(R.string.wrong_credentials);
                    } else {
                        view.toast(R.string.something_was_wrong);
                    }
                },
                e -> {
                    log.e(TAG, "signIn error", e);
                    setShowProgressBar(false);
                    view.toast(R.string.something_was_wrong);
                    signInObservable = null;
                },
                () -> {
                    setShowProgressBar(false);
                    signInObservable = null;
                });
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public boolean isShowProgressBar() {
        return showProgressBar;
    }

    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
        notifyPropertyChanged(BR.showProgressBar);
    }
}
