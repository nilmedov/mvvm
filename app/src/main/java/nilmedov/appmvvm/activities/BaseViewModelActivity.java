package nilmedov.appmvvm.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import nilmedov.appmvvm.viewmodels.base.ViewModel;
import nilmedov.appmvvm.viewmodels.base.ViewModelFactory;
import nilmedov.appmvvm.viewmodels.base.ViewModelLoader;

import static nilmedov.appmvvm.viewmodels.base.ViewModelLoader.LOADER_ID;

/**
 * Created by Nazar on 05.01.2017.
 */

public abstract class BaseViewModelActivity<VM extends ViewModel<V>, V> extends AppCompatActivity {
    private static final String TAG = BaseViewModelActivity.class.getSimpleName();
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // LoaderCallbacks as an object, so no hint regarding Loader will be leak to the subclasses.
        getSupportLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<VM>() {
            @Override
            public final Loader<VM> onCreateLoader(int id, Bundle args) {
                Log.i(TAG, "onCreateLoader");
                return new ViewModelLoader<>(BaseViewModelActivity.this, getViewModelType());
            }

            @Override
            public final void onLoadFinished(Loader<VM> loader, VM viewModel) {
                Log.i(TAG, "onLoadFinished");
                BaseViewModelActivity.this.viewModel = viewModel;
                onViewModelPrepared(viewModel);
            }

            @Override
            public final void onLoaderReset(Loader<VM> loader) {
                Log.i(TAG, "onLoaderReset");
                BaseViewModelActivity.this.viewModel = null;
                onViewModelDestroyed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.onViewAttached(getView());
    }

    @Override
    protected void onStop() {
        viewModel.onViewDetached();
        super.onStop();
    }

    protected abstract void onViewModelPrepared(@NonNull VM viewModel);

    /**
     * Hook for subclasses before the screen gets destroyed.
     */
    protected void onViewModelDestroyed() {
    }

    @NonNull
    protected V getView() {
        return (V) this;
    }

    protected abstract @ViewModelFactory.ViewModelType int getViewModelType();
}
