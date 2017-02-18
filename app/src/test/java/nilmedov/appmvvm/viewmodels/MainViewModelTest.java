package nilmedov.appmvvm.viewmodels;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import io.reactivex.Observable;
import nilmedov.appmvvm.ImmediateSchedulerRule;
import nilmedov.appmvvm.di.TestAppModule;
import nilmedov.appmvvm.di.TestLoggingModule;
import nilmedov.appmvvm.di.TestModelsModule;
import nilmedov.appmvvm.di.components.AppComponent;
import nilmedov.appmvvm.di.components.DaggerAppComponent;
import nilmedov.appmvvm.entities.User;
import nilmedov.appmvvm.network.NetworkResponse;
import nilmedov.appmvvm.views.MainView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Nazar on 18.02.2017.
 */

public class MainViewModelTest {
    private MainViewModel mainViewModel;
    private MainView mainView;

    @Rule
    public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Before
    public void setUp() {
        AppComponent testAppComponent = DaggerAppComponent.builder()
                .appModule(new TestAppModule())
                .loggingModule(new TestLoggingModule())
                .modelsModule(new TestModelsModule())
                .build();

        mainViewModel = Mockito.spy(new MainViewModel(testAppComponent));
        mainView = Mockito.mock(MainView.class);
    }

    @Test
    public void testUserLoggedInSuccess() {
        User user = new User(123, "name", "url", "email", "login", "location", "avatarUrl");
        int firstPage = 1;
        when(mainViewModel.userModel.getAuthTokenFromSharedPrefs()).thenReturn("auth_token");
        when(mainViewModel.userModel.getUserFromSharedPrefs()).thenReturn(user);
        when(mainViewModel.repositoryModel.getRepositoriesFromNetwork(user.getLogin(), firstPage))
                .thenReturn(Observable.just(new NetworkResponse<>(new ArrayList<>(), 200)));

        mainViewModel.onViewAttached(mainView);
        verify(mainViewModel.userModel).getUserFromSharedPrefs();
        verify(mainViewModel.userModel).getAuthTokenFromSharedPrefs();
        verify(mainViewModel).setUser(user);
        verify(mainViewModel.repositoryModel).getRepositoriesFromNetwork(user.getLogin(), firstPage);
    }

    @Test
    public void testUserLoggedInFailed() {
        when(mainViewModel.userModel.getAuthTokenFromSharedPrefs()).thenReturn(null);
        when(mainViewModel.userModel.getUserFromSharedPrefs()).thenReturn(null);
        when(mainViewModel.repositoryModel.getRepositoriesFromNetwork("login", 1))
                .thenReturn(Observable.just(new NetworkResponse<>(new ArrayList<>(), 200)));

        mainViewModel.onViewAttached(mainView);
        verify(mainViewModel.userModel).getUserFromSharedPrefs();
        verify(mainViewModel.userModel).getAuthTokenFromSharedPrefs();
        verify(mainView).moveToSignInView();
        verify(mainViewModel, Mockito.never()).setUser(Mockito.any());
        verify(mainViewModel.repositoryModel, Mockito.never()).getRepositoriesFromNetwork("login", 1);
}
}
