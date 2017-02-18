package nilmedov.appmvvm.viewmodels.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import nilmedov.appmvvm.App;
import nilmedov.appmvvm.viewmodels.MainViewModel;
import nilmedov.appmvvm.viewmodels.SignInViewModel;

/**
 * Created by Nazar on 05.01.2017.
 */

public class ViewModelFactory {
    @IntDef({MAIN_VIEWMODEL, SIGN_IN_VIEWMODEL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewModelType {}

    public static final int MAIN_VIEWMODEL = 0;
    public static final int SIGN_IN_VIEWMODEL = 1;


    public ViewModel create(@ViewModelType int type) {
        ViewModel viewModel = null;
        switch (type) {
            case MAIN_VIEWMODEL:
                viewModel = new MainViewModel(App.getAppComponent());
                break;
            case SIGN_IN_VIEWMODEL:
                viewModel = new SignInViewModel(App.getAppComponent());
                break;
        }

        return viewModel;
    }
}
