package nilmedov.appmvvm.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import nilmedov.appmvvm.adapters.IShowProgress;
import nilmedov.appmvvm.adapters.RepositoryAdapter;
import nilmedov.appmvvm.entities.Repository;

/**
 * Created by Nazar on 30.01.2017.
 */

public class DataBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .centerCrop()
                .into(imageView);
    }

    @BindingAdapter("bind:repositories")
    public static void setListItems(RecyclerView recyclerView, List<Repository> repositories) {
        RepositoryAdapter repositoryAdapter = (RepositoryAdapter) recyclerView.getAdapter();
        if (!repositories.isEmpty()) {
            repositoryAdapter.setRepositories(repositories);
            repositoryAdapter.notifyDataSetChanged();
        }
    }

    @BindingAdapter("bind:showProgressBar")
    public static void showProgressBar(RecyclerView recyclerView, boolean showProgressBar) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof IShowProgress) {
            ((IShowProgress) adapter).showProgressBar(showProgressBar);
        }
    }
}
