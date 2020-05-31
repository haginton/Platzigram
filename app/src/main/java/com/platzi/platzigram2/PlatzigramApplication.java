package com.platzi.platzigram2;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PlatzigramApplication extends Application {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseStorage firebaseStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w("PlatzigramApplication", "Usuario logeado " + firebaseUser.getEmail());
                }else{
                    Log.w("PlatzigramApplication", "Usuario no logueado" );
                }
            }
        };

        firebaseStorage = FirebaseStorage.getInstance();

    }

    public StorageReference getStorageReference(){
        return firebaseStorage.getReference();
    }
}
