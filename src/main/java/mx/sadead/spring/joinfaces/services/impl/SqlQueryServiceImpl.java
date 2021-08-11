package mx.sadead.spring.joinfaces.services.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import mx.sadead.spring.joinfaces.services.SqlQueryService;

@Service
public class SqlQueryServiceImpl implements SqlQueryService {

	@PersistenceContext
	private final EntityManager entityManager;

	public SqlQueryServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public <TEntity> List<TEntity> findAllByQueryNativeToEntity(Class<TEntity> eClazz, String queryNative,
			Object... params) {
		Query query = entityManager.createNativeQuery(queryNative, eClazz);
		int noParam = 1;
		for (Object param : params) {
			query.setParameter(noParam, param);
			noParam++;
		}
		return query.getResultList();
	}

	@Override
	public List<Object[]> findAllByQueryNative(String queryNative, Object... params) {
		Query query = entityManager.createNativeQuery(queryNative);
		int noParam = 1;
		for (Object param : params) {
			query.setParameter(noParam, param);
			noParam++;
		}
		return query.getResultList();
	}

	@Override
	public List<Map<String, Object>> findAllByQueryNativeToMap(String queryNative, Object... params) {
		Session session = entityManager.unwrap(Session.class);
		SQLQuery<Map<String, Object>> query = session.createSQLQuery(queryNative);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		int noParam = 0;
		for (Object param : params) {
			query.setParameter(noParam, param);
			noParam++;
		}

		return query.list();
	}

	@Override
	public <TEntity> List<TEntity> findByCriteria(DetachedCriteria criteria) {
		return findByCriteria(criteria, null, null);
	}

	@Override
	public <TEntity> List<TEntity> findByCriteria(DetachedCriteria criteria, Integer firstResult, Integer maxResults) {
		Session session = entityManager.unwrap(Session.class);
		Criteria executableCriteria = criteria.getExecutableCriteria(session);
		if (firstResult != null) {
			executableCriteria.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			executableCriteria.setMaxResults(maxResults);
		}
		return executableCriteria.list();
	}

}
