package com.defaultapps.moviebase.data.firebase;


import com.defaultapps.moviebase.data.models.firebase.Favorite;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class FirebaseService {

    private DatabaseReference databaseReference;
    private LoggedUser loggedUser;

    @Inject
    FirebaseService(DatabaseReference databaseReference, LoggedUser loggedUser) {
        this.databaseReference = databaseReference;
        this.loggedUser = loggedUser;
    }

    public Observable<Boolean> addToFavorites(int movieId, String posterPath) {
        checkUserNotNull();
        return Observable.create(e -> {
            DatabaseReference transactionReference = databaseReference.child("users").child(loggedUser.getUserId());
            transactionReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    e.onNext(true);
                    e.onComplete();
                    transactionReference.removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    e.onError(databaseError.toException());
                    e.onComplete();
                    transactionReference.removeEventListener(this);
                }
            });
            databaseReference.push().setValue(new Favorite(movieId, posterPath));
        });
    }

    public Observable<Boolean> removeFromFavorites(String key) {
        checkUserNotNull();
        return Observable.create(e -> databaseReference.child(key).getRef().removeValue()
                .addOnCompleteListener((Task<Void> task) -> {
                    if (task.isSuccessful())
                        e.onNext(true);
                    else
                        e.onError(task.getException());
                    e.onComplete();
                })
        );
    }

    private void checkUserNotNull() {
        if (loggedUser.getFirebaseuser() == null) {
            throw new UnknownUserException("No user data provided, please supply LoggedUser instance before working with db.");
        }
    }

    private class UnknownUserException extends RuntimeException {
        UnknownUserException(String message) {
            super(message);
        }
    }
}
