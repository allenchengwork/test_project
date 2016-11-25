package com.maplebox.nail.service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaService<T, ID extends Serializable> extends Service, JpaRepository<T, ID> {
	<S extends T> List<S> merge(Iterable<S> entities) throws IllegalAccessException, InvocationTargetException;
	<S extends T> S mergeAndFlush(S entity) throws IllegalAccessException, InvocationTargetException;
	<S extends T> S merge(S entity) throws IllegalAccessException, InvocationTargetException;
}
