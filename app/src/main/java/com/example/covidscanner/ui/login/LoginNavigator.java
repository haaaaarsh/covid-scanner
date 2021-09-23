package com.example.covidscanner.ui.login;

import android.content.Context;

public interface LoginNavigator {

    void openRegistration();

    Context getActivityContext();

    void onError(String message);

    void onLoginComplete();

    void login();
}
