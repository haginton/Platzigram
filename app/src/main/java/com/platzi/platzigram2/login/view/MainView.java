package com.platzi.platzigram2.login.view;

public interface MainView {
    void enableInputs();
    void disableInputs();

    void showProgressBar();
    void hideProgressBar();

    void loginError(String error);

    void goCreateAccount();
    void goContainer2Activity();
}
