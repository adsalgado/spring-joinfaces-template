package mx.sadead.spring.joinfaces.services;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import mx.sadead.spring.joinfaces.repositories.BaseRepository;

public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

	private Class<T> entityClass;

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

}
