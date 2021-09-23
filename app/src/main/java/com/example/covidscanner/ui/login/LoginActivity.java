package com.example.covidscanner.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.covidscanner.R;
import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.data.db.dao.UserDao;
import com.example.covidscanner.data.model.User;
import com.example.covidscanner.databinding.ActivityLoginBinding;
import com.example.covidscanner.ui.base.BaseActivity;
import com.example.covidscanner.ui.register.RegistrationActivity;

import java.util.List;

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



        AppDatabase db = AppDatabase.getInstance();
        UserDao userDao = db.userDao();
        class InsertUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                List<User> user = userDao.getUsers();
                return null;
            }
        }
        InsertUser insertUser = new InsertUser();
        insertUser.execute();
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
}