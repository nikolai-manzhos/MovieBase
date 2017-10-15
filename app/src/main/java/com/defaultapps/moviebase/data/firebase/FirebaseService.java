package com.defaultapps.moviebase.data.firebase;


import com.defaultapps.moviebase.data.models.firebase.Favorite;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import io.reactivex.Observable;

@SuppressWarnings("WeakerAccess")
@Singleton
public class FirebaseService {

    private final Provider<DatabaseReference> databaseProvider;

    @Inject
    FirebaseService(Provider<DatabaseReference> databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public Observable<Boolean> addToFavorites(int movieId, String posterPath) {
        final DatabaseReference dbReference = checkDbNotNull();
        return Observable.create(e -> dbReference.push().setValue(new Favorite(movieId, posterPath))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        e.onNext(true);
                    } else {
                        e.onError(task.getException());
                    }
                    e.onComplete();
                })
        );
    }

    public Observable<Boolean> removeFromFavorites(int movieId) throws Exception {
        final DatabaseReference dbReference = checkDbNotNull();
        return Observable.create(e ->  {
            Query query = dbReference.orderByChild("favoriteMovieId").equalTo(movieId);
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    for (DataSnapshot favoriteSnapshot : dataSnapshot.getChildren()) {
                        favoriteSnapshot.getRef().removeValue();
                    }
                    e.onNext(true);
                    e.onComplete();
                    query.removeEventListener(this);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    e.onComplete();
                    query.removeEventListener(this);
                }
            });
        }
        );
    }

    public Observable<List<Integer>> fetchAllFavorites() {
        final DatabaseReference dbReference = checkDbNotNull();
        return Observable.create(e -> {
            Query query = dbReference.orderByKey();
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Observable.fromIterable(dataSnapshot.getChildren())
                                .map(favSnapshot -> {
                                    Favorite favorite = favSnapshot.getValue(Favorite.class);
                                    return favorite.getFavoriteMovieId();
                                })
                                .toList()
                                .subscribe(
                                        e::onNext,
                                        err -> {}
                                );
                    } else {
                        e.onComplete();
                    }
                    e.onComplete();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    e.onComplete();
                }
            });
        });
    }

    private DatabaseReference checkDbNotNull() {
        DatabaseReference dbReference = databaseProvider.get();
        if (dbReference == null) {
            throw new UnknownUserException("Database reference is NULL, check db initialization.");
        }
        return dbReference;
    }

    private class UnknownUserException extends RuntimeException {
        UnknownUserException(String message) {
            super(message);
        }
    }
}
