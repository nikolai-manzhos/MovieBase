package com.defaultapps.moviebase.data.local;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;


public class Cache<K, V> {

    @NonNull
    private final CacheProvider<K, V> provider;
    @NonNull
    private final Map<K, V> cached = new HashMap<>();

    public interface CacheProvider<K, V> {

        @NonNull
        V load(@NonNull K key);
    }

    public Cache(@NonNull CacheProvider<K, V> provider) {
        this.provider = provider;
    }

    @NonNull
    public V get(@NonNull K key) {
        synchronized (cached) {
            final V value = cached.get(key);
            if (value != null) {
                return value;
            }
            final V newValue = provider.load(key);
            cached.put(key, newValue);
            return newValue;
        }
    }

    public void invalidate() {
        cached.clear();
    }
}

