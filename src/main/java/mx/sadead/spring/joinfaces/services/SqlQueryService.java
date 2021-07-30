package mx.sadead.spring.joinfaces.services;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

public interface SqlQueryService {

	<TEntity> List<TEntity> findAllByQueryNativeToEntity(final Class<TEntity> eClazz, final String queryNative, final Object... params);
    List<Object[]> findAllByQueryNative(final String queryNative, final Object... params);
    List<Map<String, Object>> findAllByQueryNativeToMap(final String queryNative, final Object... params);
    <TEntity> List<TEntity> findByCriteria(final DetachedCriteria criteria);
    <TEntity> List<TEntity> findByCriteria(final DetachedCriteria criteria, final Integer firstResult, final Integer maxResults);
    
}
