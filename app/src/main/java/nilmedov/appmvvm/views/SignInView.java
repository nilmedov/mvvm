package nilmedov.appmvvm.views;

import android.support.annotation.StringRes;

/**
 * Created by Nazar on 05.01.2017.
 */

public interface SignInView {
    void toast(@StringRes int message);

    void moveToMainView();
}
