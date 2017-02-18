package nilmedov.appmvvm.viewmodels.base;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

/**
 * Created by Nazar on 05.01.2017.
 */

public class ViewModelLoader <T extends ViewModel> extends Loader<T> {
    private static final String TAG = ViewModelLoader.class.getSimpleName();
    public static final int LOADER_ID = 101;
    private final int viewModelType;
    private T viewModel;

    public ViewModelLoader(Context context, @ViewModelFactory.ViewModelType int viewModelType) {
        super(context);
        this.viewModelType = viewModelType;
    }

    @Override
    protected void onStartLoading() {
        Log.i(TAG, "onStartLoading");
        // if we already own a presenter instance, simply deliver it.
        if (viewModel != null) {
            deliverResult(viewModel);
            return;
        }

        // Otherwise, force a load
        forceLoad();
    }

    @Override
    protected void onForceLoad() {

        // Create the Presenter using the Factory
        viewModel = (T) new ViewModelFactory().create(viewModelType);

        // Deliver the result
        deliverResult(viewModel);
    }

    @Override
    public void deliverResult(T data) {
        super.deliverResult(data);
        Log.i(TAG, "deliverResult:" + viewModel.getClass().getSimpleName());
    }

    @Override
    protected void onStopLoading() {
        Log.i(TAG, "onStopLoading: " + viewModel.getClass().getSimpleName());
    }

    @Override
    protected void onReset() {
        Log.i(TAG, "onReset: " + viewModel.getClass().getSimpleName());
        if (viewModel != null) {
            viewModel.onDestroyed();
            viewModel = null;
        }
    }
}
