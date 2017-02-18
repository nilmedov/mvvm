package nilmedov.appmvvm.viewmodels.base;

import android.databinding.BaseObservable;

/**
 * Created by Nazar on 05.01.2017.
 */

public abstract class ViewModel<V> extends BaseObservable {

    abstract public void onViewAttached(V view);

    abstract public void onViewDetached();

    abstract public void onDestroyed();
}
