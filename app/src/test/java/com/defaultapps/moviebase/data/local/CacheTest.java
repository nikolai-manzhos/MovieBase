package com.defaultapps.moviebase.data.local;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class CacheTest {

    private static final int FAKE_ID = 1422;
    private static final boolean FAKE_FAVORITE_STATUS = true;
    private static final boolean FAKE_DEFAULT_VALUE = false;

    private Cache<Integer, BehaviorSubject<Boolean>> cache;

    @Before
    public void setup() {
        cache = new Cache<>(postId -> BehaviorSubject.createDefault(FAKE_DEFAULT_VALUE));
    }

    @Test
    public void shouldHaveDefaultItem() {
        cache.get(FAKE_ID)
                .test()
                .assertNoErrors()
                .assertValue(FAKE_DEFAULT_VALUE);
    }

    @Test
    public void shouldPutFavoriteItem() {
        cache.get(FAKE_ID)
                .onNext(FAKE_FAVORITE_STATUS);
    }

    @Test
    public void shouldReturnFavoriteStatus() {
        cache.get(FAKE_ID)
                .onNext(FAKE_FAVORITE_STATUS);

        cache.get(FAKE_ID)
                .observeOn(Schedulers.trampoline())
                .subscribeOn(Schedulers.trampoline())
                .test()
                .assertNoErrors()
                .assertValue(FAKE_FAVORITE_STATUS);
    }

    @Test
    public void shouldClearCache() {
        cache.get(FAKE_ID)
                .onNext(FAKE_FAVORITE_STATUS);

        cache.invalidate();
        cache.get(FAKE_ID)
                .test()
                .assertNoErrors()
                .assertValue(FAKE_DEFAULT_VALUE);
    }
}
