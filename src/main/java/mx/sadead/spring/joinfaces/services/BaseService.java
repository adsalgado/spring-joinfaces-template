package mx.sadead.spring.joinfaces.services;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public interface BaseService<T, ID> {

	List<T> findAll();
    List<T> findAll(Example<T> example);
    List<T> findAllById(Iterable<ID> id);
    Page<T> findAll(Pageable pageable);
    List<T> findAll(Sort sort);
    List<T> findAll(Example<T> example, Sort sort);
    Page<T> findAll(Example<T> example, Pageable pageable);
    T findById(ID id);
    T findOne(Example<T> example);
	T save(T object);
    List<T> save(Iterable<T> itrbl);
	T update(T object);
    List<T> update(Iterable<T> itrbl);
	void deleteById(ID id);
	void delete(T object);
    void deleteAll(Iterable<T> itrbl);
	
}
