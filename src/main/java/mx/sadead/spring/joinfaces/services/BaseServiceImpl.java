package mx.sadead.spring.joinfaces.services;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import mx.sadead.spring.joinfaces.repositories.BaseRepository;

public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

	@Autowired
    private BaseRepository<T, ID> repository;
    
	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public T findById(ID id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public T save(T object) {
		return repository.save(object);
	}

	@Override
	public T update(T object) {
		return repository.save(object);
	}

	@Override
	public void deleteById(ID id) {
		repository.deleteById(id);
	}

	@Override
	public void delete(T object) {
		repository.delete(object);
	}

	@Override
	public List<T> findAll(Example<T> example) {
		return repository.findAll(example);
	}

	@Override
	public List<T> findAllById(Iterable<ID> ids) {
		return repository.findAllById(ids);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public List<T> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	@Override
	public List<T> findAll(Example<T> example, Sort sort) {
		return findAll(example, sort);
	}

	@Override
	public Page<T> findAll(Example<T> example, Pageable pageable) {
		return repository.findAll(example, pageable);
	}

	@Override
	public T findOne(Example<T> example) {
		return repository.findOne(example).orElse(null);
	}

	@Override
	public List<T> save(Iterable<T> entities) {
		return repository.saveAll(entities);
	}

	@Override
	public List<T> update(Iterable<T> entities) {
		return repository.saveAll(entities);
	}

	@Override
	public void deleteAll(Iterable<T> entities) {
		repository.deleteAll(entities);
	}

}
