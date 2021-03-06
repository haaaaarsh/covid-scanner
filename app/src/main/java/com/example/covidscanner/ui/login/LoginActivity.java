package com.example.covidscanner.ui.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.data.model.User;
import com.example.covidscanner.databinding.ActivityLoginBinding;
import com.example.covidscanner.ui.base.BaseActivity;
import com.example.covidscanner.ui.dashboard.DashboardActivity;
import com.example.covidscanner.ui.register.RegistrationActivity;

public class LoginActivity extends BaseActivity<LoginViewModel> implements LoginNavigator {

    ActivityLoginBinding binding;

    @NonNull
    @Override
    protected LoginViewModel createViewModel() {
        LoginViewModelFactory factory = new LoginViewModelFactory();
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataBindings();
        viewModel.setNavigator(this);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    @Override
    public void openRegistration() {
        openActivity(RegistrationActivity.class);
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void onError(String message) {
        showSnackbar(message, Color.RED, Color.WHITE);
    }

    @Override
    public void onLoginComplete(User user) {
        setCurrentUser(user);
        Toast.makeText(this, getResources().getString(R.string.toast_login), Toast.LENGTH_SHORT).show();
        openActivity(DashboardActivity.class);
        finish();
    }

    @Override
    public void login() {
        hideKeyboard();
        if (viewModel.isValidated(binding.etUserName, binding.etPass)) {
            viewModel.attemptLogin(binding.etUserName.getText().toString().trim(),
                    binding.etPass.getText().toString().trim());
        }
    }
}