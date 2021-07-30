package mx.sadead.spring.joinfaces.services;

import java.util.List;

public interface BaseService<T, ID> {

	List<T> findAll();
	T findById(ID id);
	T save(T object);
	T update(T object);
	void deleteById(ID id);
	void delete(T object);
	
}
