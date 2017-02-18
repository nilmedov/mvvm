package nilmedov.appmvvm.adapters;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nilmedov.appmvvm.R;
import nilmedov.appmvvm.databinding.ItemRepositoryBinding;
import nilmedov.appmvvm.entities.Repository;
import nilmedov.appmvvm.viewmodels.ItemRepositoryViewModel;

/**
 * Created by Nazar on 05.02.2017.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IShowProgress {
    private static final String TAG = RepositoryAdapter.class.getSimpleName();

    private static final int TYPE_REPOSITORY = 0;
    private static final int TYPE_LOADING = 1;

    private List<Repository> repositories;
    private Pagination paginationCallback;
    private boolean showProgressBar;

    public RepositoryAdapter(Pagination paginationCallback) {
        this.paginationCallback = paginationCallback;
        this.repositories = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_LOADING) {
            return new LoadingHolder(layoutInflater.inflate(R.layout.item_repository_loading,
                    parent,
                    false));
        }
        return new RepositoryHolder(DataBindingUtil.inflate(layoutInflater,
                R.layout.item_repository,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepositoryHolder) {
            ((RepositoryHolder) holder).onBind(repositories.get(position));
            if (position == repositories.size() - 1 && !showProgressBar) {
                paginationCallback.loadMoreItems();
            }
        }
    }

    @Override
    public int getItemCount() {
        return repositories.size() + (showProgressBar ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == repositories.size() && showProgressBar) {
            return TYPE_LOADING;
        }
        return TYPE_REPOSITORY;
    }

    @Override
    public void showProgressBar(boolean showProgressBar) {
        new Handler().post(() -> {
            this.showProgressBar = showProgressBar;
            if (showProgressBar) {
                notifyItemInserted(repositories.size());
            } else {
                notifyItemRemoved(repositories.size());
            }
        });
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    private class RepositoryHolder extends RecyclerView.ViewHolder {
        private ItemRepositoryBinding binding;

        public RepositoryHolder(ItemRepositoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(Repository repository) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemRepositoryViewModel(repository));
            } else {
                binding.getViewModel().setRepository(repository);
            }
            binding.executePendingBindings();
        }
    }

    private class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(View itemView) {
            super(itemView);
        }
    }

    public interface Pagination {
        void loadMoreItems();
    }
}
