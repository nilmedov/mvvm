package nilmedov.appmvvm.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import nilmedov.appmvvm.R;
import nilmedov.appmvvm.adapters.RepositoryAdapter;
import nilmedov.appmvvm.databinding.ActivityMainBinding;
import nilmedov.appmvvm.viewmodels.MainViewModel;
import nilmedov.appmvvm.viewmodels.base.ViewModelFactory;
import nilmedov.appmvvm.views.MainView;

public class MainActivity extends BaseViewModelActivity<MainViewModel, MainView> implements MainView {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
    }

    @Override
    protected void onViewModelPrepared(@NonNull MainViewModel viewModel) {
        binding.setViewModel(viewModel);
        RecyclerView recyclerView = binding.listRepositories;
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new RepositoryAdapter(viewModel::fetchGithubRepositories));
    }

    @Override
    protected int getViewModelType() {
        return ViewModelFactory.MAIN_VIEWMODEL;
    }

    @Override
    public void toast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToSignInView() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        binding.executePendingBindings();
        super.onDestroy();
    }
}
