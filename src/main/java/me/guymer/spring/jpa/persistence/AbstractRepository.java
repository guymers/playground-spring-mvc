package me.guymer.spring.jpa.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractRepository<T extends Serializable> {
	
	@PersistenceContext
	EntityManager entityManager;
	
	private Class<T> clazz;
	
	public AbstractRepository(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public T get(int id) {
		return entityManager.find(clazz, id);
	}
	
	public List<T> getAll() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		
		Root<T> root = cq.from(clazz);
		cq.select(root);
		
		TypedQuery<T> query = entityManager.createQuery(cq); 
		
		return query.getResultList();
	}
	
	public void create(final T entity) {
		entityManager.persist(entity);
	}
	
	public void update(final T entity) {
		entityManager.merge(entity);
	}
	
	public void delete(final T entity) {
		// got to merge so we have an entity in the current persistence context
		final T newEntity = entityManager.merge(entity);
		
		entityManager.remove(newEntity);
	}

}
