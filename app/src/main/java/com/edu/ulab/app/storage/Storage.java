package com.edu.ulab.app.storage;

public interface Storage<ID, V> {
    V save(V obj);

    V getById(ID id);

    V update(V obj);

    void deleteById(ID id);
}
