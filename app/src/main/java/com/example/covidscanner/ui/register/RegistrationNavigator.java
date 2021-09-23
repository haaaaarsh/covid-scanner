package com.example.covidscanner.ui.register;

import android.content.Context;

public interface RegistrationNavigator {

    void signUp();

    void onRegistrationComplete();

    void onError(String message);

    Context getActivityContext();

}
