package com.platzi.platzigram2.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.platzi.platzigram2.login.interactor.LoginInteractor;
import com.platzi.platzigram2.login.interactor.LoginInteractorImpl;
import com.platzi.platzigram2.login.view.MainActivity;
import com.platzi.platzigram2.login.view.MainView;

public class LoginPresenterImpl implements LoginPresenter{

    private MainView mainView;
    private LoginInteractor interactor;

    public LoginPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void singIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        mainView.disableInputs();
        mainView.showProgressBar();
        interactor.singIn(username,password, activity,firebaseAuth);
    }

    @Override
    public void loginSuccess() {
       mainView.goContainer2Activity();
       mainView.hideProgressBar();
    }

    @Override
    public void loginError(String error) {
        mainView.enableInputs();
        mainView.hideProgressBar();
        mainView.loginError(error);
    }
}
