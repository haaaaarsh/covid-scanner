package com.example.covidscanner.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.covidscanner.R;
import com.example.covidscanner.databinding.ActivityLoginBinding;
import com.example.covidscanner.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity<LoginViewModel> {

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
    }

    private void setDataBindings() {
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setViewModel(viewModel);
        activityLoginBinding.executePendingBindings();
    }
}