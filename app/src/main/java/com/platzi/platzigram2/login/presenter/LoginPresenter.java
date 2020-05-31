package com.platzi.platzigram2.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public interface LoginPresenter {
    void singIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth); //Interactuara con el interactor
    void loginSuccess();
    void loginError(String error);


}
