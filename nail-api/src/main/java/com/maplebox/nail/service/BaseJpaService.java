package com.maplebox.nail.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, timeout = 10, rollbackFor = Throwable.class)
public abstract class BaseJpaService<T, ID extends Serializable> extends BaseService implements JpaService<T, ID> {
	static Logger log = LoggerFactory.getLogger(BaseJpaService.class);
	
	@Autowired
	private JpaRepository<T, ID> dao;
	
    @Override
	public List<T> findAll() {
		return dao.findAll();
	}

	@Override
	public List<T> findAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public List<T> findAll(Iterable<ID> ids) {
		return dao.findAll(ids);
	}

	@Override
	@Transactional(readOnly = false)
	public <S extends T> List<S> save(Iterable<S> entities) {
		return dao.save(entities);
	}
	
	@Override
	@Transactional(readOnly = false)
	public <S extends T> List<S> merge(Iterable<S> entities) {
		List<S> list = new ArrayList<>();
		for (S entity : entities) {
			list.add(save(entity));
		}
		
		return list;
	}

	@Override
	public void flush() {
		dao.flush();
	}

	@Override
	@Transactional(readOnly = false)
	public <S extends T> S saveAndFlush(S entity) {
		return dao.saveAndFlush(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false)
	public <S extends T> S mergeAndFlush(S entity) {
		try {
			EntityManager em = getEntityManager();
			Object id = getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
			if (id != null) {
				Object destEntity = em.find(entity.getClass(), id);
				nullAwareBeanCopy(destEntity, entity);
				return dao.saveAndFlush((S)destEntity);
			} else {
				return dao.saveAndFlush(entity);
			}
		}
		catch (Exception e) {
			throw new RuntimeException("merge Error!!");
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteInBatch(Iterable<T> entities) {
		dao.deleteInBatch(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAllInBatch() {
		dao.deleteAllInBatch();
	}

	@Override
	public T getOne(ID id) {
		return dao.getOne(id);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = false)
	public <S extends T> S save(S entity) {
		return dao.save(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false)
	public <S extends T> S merge(S entity) {
		try {
			EntityManager em = getEntityManager();
			Object id = getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
			if (id != null) {
				Object destEntity = em.find(entity.getClass(), id);
				nullAwareBeanCopy(destEntity, entity);
				return dao.save((S)destEntity);
			} else {
				return dao.save(entity);
			}
		}
		catch (Exception e) {
			throw new RuntimeException("merge Error!!");
		}
	}

	@Override
	public T findOne(ID id) {
		return dao.findOne(id);
	}

	@Override
	public boolean exists(ID id) {
		return dao.exists(id);
	}

	@Override
	public long count() {
		return dao.count();
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(ID id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Iterable<? extends T> entities) {
		dao.delete(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		dao.deleteAll();
	}
	
	@Override
	public <S extends T> List<S> findAll(Example<S> example) {
		return dao.findAll(example);
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		return dao.findAll(example, sort);
	}

	@Override
	public <S extends T> S findOne(Example<S> example) {
		return dao.findOne(example);
	}

	@Override
	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		return dao.findAll(example, pageable);
	}

	@Override
	public <S extends T> long count(Example<S> example) {
		return dao.count(example);
	}

	@Override
	public <S extends T> boolean exists(Example<S> example) {
		return dao.exists(example);
	}
}

