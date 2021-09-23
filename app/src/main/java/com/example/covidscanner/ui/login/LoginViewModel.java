package com.example.covidscanner.ui.login;

import com.example.covidscanner.ui.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public void openRegistration() {
        getNavigator().openRegistration();
    }
}
