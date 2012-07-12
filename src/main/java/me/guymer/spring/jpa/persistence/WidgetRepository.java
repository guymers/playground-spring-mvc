package me.guymer.spring.jpa.persistence;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import me.guymer.spring.jpa.domain.Widget;
import me.guymer.spring.jpa.domain.Widget_;

import org.springframework.stereotype.Repository;

@Repository
public class WidgetRepository extends AbstractRepository<Widget> {
	
	public WidgetRepository() {
		super(Widget.class);
	}
	
	@Override
	public List<Widget> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Widget> criteriaQuery = criteriaBuilder.createQuery(Widget.class);
		
		Root<Widget> widget = criteriaQuery.from(Widget.class);
		
		criteriaQuery.select(widget);
		criteriaQuery.where(criteriaBuilder.isTrue(widget.get(Widget_.active)));
		
		TypedQuery<Widget> query = entityManager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}
	
}
