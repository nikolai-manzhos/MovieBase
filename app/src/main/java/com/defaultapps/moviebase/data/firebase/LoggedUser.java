package com.defaultapps.moviebase.data.firebase;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoggedUser {

    private FirebaseUser firebaseuser;

    @Inject
    public LoggedUser() {}

    public FirebaseUser getFirebaseuser() {
        return firebaseuser;
    }

    public void setFirebaseuser(FirebaseUser firebaseuser) {
        this.firebaseuser = firebaseuser;
    }

    public String getUserId() {
        return firebaseuser.getUid();
    }
}
