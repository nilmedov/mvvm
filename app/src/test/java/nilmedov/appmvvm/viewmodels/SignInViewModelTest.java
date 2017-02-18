package nilmedov.appmvvm.viewmodels;

import android.support.v4.util.Pair;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Observable;
import nilmedov.appmvvm.ImmediateSchedulerRule;
import nilmedov.appmvvm.R;
import nilmedov.appmvvm.di.TestAppModule;
import nilmedov.appmvvm.di.TestLoggingModule;
import nilmedov.appmvvm.di.TestModelsModule;
import nilmedov.appmvvm.di.components.AppComponent;
import nilmedov.appmvvm.di.components.DaggerAppComponent;
import nilmedov.appmvvm.entities.User;
import nilmedov.appmvvm.network.NetworkResponse;
import nilmedov.appmvvm.views.SignInView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Nazar on 08.01.2017.
 */

public class SignInViewModelTest {
    private SignInViewModel signInViewModel;
    private SignInView signInView;

    @Rule
    public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Before
    public void setUp() {
        AppComponent testAppComponent = DaggerAppComponent.builder()
                .appModule(new TestAppModule())
                .loggingModule(new TestLoggingModule())
                .modelsModule(new TestModelsModule())
                .build();

        signInViewModel = Mockito.spy(new SignInViewModel(testAppComponent));
        signInView = Mockito.mock(SignInView.class);

        when(signInViewModel.userModel.signIn("right", "credentials")).thenReturn(Observable.just(new NetworkResponse<>(new Pair<>(new User(), ""), 200)));
        when(signInViewModel.userModel.signIn("wrong", "credentials")).thenReturn(Observable.just(new NetworkResponse<>(new Pair<>(new User(), ""), 401)));
        when(signInViewModel.userModel.signIn("internal", "error")).thenReturn(Observable.error(new Exception("Internal error")));
    }

    @Test
    public void testSignIn() {
        signInViewModel.onViewAttached(signInView);
        signInViewModel.setEmail("right");
        signInViewModel.setPassword("credentials");
        assertEquals(signInViewModel.getEmail(), "right");
        assertEquals(signInViewModel.getPassword(), "credentials");
        assertFalse(signInViewModel.isShowProgressBar());
        signInViewModel.onClickSignInButton();
        verify(signInViewModel).setShowProgressBar(true);
        assertFalse(signInViewModel.isShowProgressBar());
        verify(signInViewModel.userModel).saveAuthTokenInSharedPrefs(Mockito.any());
        verify(signInViewModel.userModel).saveUserInSharedPrefs(Mockito.any());
        verify(signInView).moveToMainView();
    }

    @Test
    public void testSignInWrongCredentialsError() {
        signInViewModel.onViewAttached(signInView);
        signInViewModel.setEmail("wrong");
        signInViewModel.setPassword("credentials");
        assertEquals(signInViewModel.getEmail(), "wrong");
        assertEquals(signInViewModel.getPassword(), "credentials");
        assertFalse(signInViewModel.isShowProgressBar());
        signInViewModel.onClickSignInButton();
        verify(signInViewModel).setShowProgressBar(true);
        assertFalse(signInViewModel.isShowProgressBar());
        verify(signInView).toast(R.string.wrong_credentials);
    }

    @Test
    public void testSignInInternalError() {
        signInViewModel.onViewAttached(signInView);
        signInViewModel.setEmail("internal");
        signInViewModel.setPassword("error");
        assertEquals(signInViewModel.getEmail(), "internal");
        assertEquals(signInViewModel.getPassword(), "error");
        assertFalse(signInViewModel.isShowProgressBar());
        signInViewModel.onClickSignInButton();
        verify(signInViewModel).setShowProgressBar(true);
        assertFalse(signInViewModel.isShowProgressBar());
        verify(signInView).toast(R.string.something_was_wrong);
    }
}
