package nilmedov.appmvvm.views;

import android.support.annotation.StringRes;

/**
 * Created by Nazar on 04.12.2016.
 */

public interface MainView {

    void toast(@StringRes int message);

    void moveToSignInView();
}
