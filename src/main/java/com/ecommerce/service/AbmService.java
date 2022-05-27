package com.ecommerce.service;

import java.util.List;

public interface AbmService<T> {
    void save(T objectDTO);

    List<T> findAll();

    void update(T objectDTO);

    T findById(Long id);

    void delete(Long id);
}
