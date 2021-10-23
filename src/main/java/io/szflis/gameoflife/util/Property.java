package io.szflis.gameoflife.util;

import io.szflis.gameoflife.viewmodel.SimpleChangeListener;

import java.util.LinkedList;
import java.util.List;

public class Property<T> {

    private T value;
    private List<SimpleChangeListener<T>> listeners = new LinkedList<>();

    public Property(T value) {
        this.value = value;
    }

    public Property() {
        this(null);
    }

    public void listen(SimpleChangeListener<T> listener) {
        this.listeners.add(listener);
    }

    public void set(T newValue) {
        this.value = newValue;
        notifyListeners();
    }

    private void notifyListeners() {
        for (SimpleChangeListener<T> listener : listeners) {
            listener.valueChanged(this.value);
        }
    }

    public T get() {
        return this.value;
    }
}
