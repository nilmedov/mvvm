package nilmedov.appmvvm.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import nilmedov.appmvvm.R;
import nilmedov.appmvvm.databinding.ActivitySignInBinding;
import nilmedov.appmvvm.viewmodels.SignInViewModel;
import nilmedov.appmvvm.viewmodels.base.ViewModelFactory;
import nilmedov.appmvvm.views.SignInView;

public class SignInActivity extends BaseViewModelActivity<SignInViewModel, SignInView> implements SignInView {
    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
    }

    @Override
    protected void onViewModelPrepared(@NonNull SignInViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    @Override
    protected int getViewModelType() {
        return ViewModelFactory.SIGN_IN_VIEWMODEL;
    }

    @Override
    public void toast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToMainView() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        binding.executePendingBindings();
        super.onDestroy();
    }
}
