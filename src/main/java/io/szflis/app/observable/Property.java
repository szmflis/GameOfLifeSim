package io.szflis.app.observable;

import java.util.LinkedList;
import java.util.List;

public class Property<T> {

    private T value;
    private List<ChangeListener<T>> listeners = new LinkedList<>();

    public Property(T value) {
        this.value = value;
        System.out.println("Property initialized to value: " + value);
    }

    public Property() {
        this(null);
    }

    public void listen(ChangeListener<T> listener) {
        System.out.println("Adding change listener");
        this.listeners.add(listener);
    }

    public void set(T newValue) {
        this.value = newValue;
        notifyListeners();
    }

    private void notifyListeners() {
        for (ChangeListener<T> listener : listeners) {
            listener.valueChanged(this.value);
        }
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        return this.value;
    }
}
