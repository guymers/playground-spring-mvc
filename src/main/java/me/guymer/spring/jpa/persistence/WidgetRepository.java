package me.guymer.spring.jpa.persistence;

import me.guymer.spring.jpa.domain.Widget;

import org.springframework.stereotype.Repository;

@Repository
public class WidgetRepository extends AbstractRepository<Widget> {
	
	public WidgetRepository() {
		super(Widget.class);
	}
	
}
