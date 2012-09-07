package me.guymer.spring.jpa.widget;

import java.util.List;

import javax.inject.Inject;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jpaWidgetService")
public class WidgetService {
	
	@Inject
	private WidgetRepository widgetRepository;
	
	public List<Widget> get() {
		return widgetRepository.getAll();
	}
	
	public Widget get(int id) {
		return widgetRepository.get(id);
	}
	
	public Widget get(Widget widget) {
		int id = widget.getId();
		
		return get(id);
	}
	
	@Transactional
	public void create(Widget widget) {
		widgetRepository.create(widget);
	}
	
	@Transactional
	public void update(Widget widget) {
		widgetRepository.update(widget);
	}
	
	@Transactional
	public void delete(Widget widget) {
		widgetRepository.delete(widget);
	}
	
	@Transactional
	public void createOrUpdate(Widget widget) {
		if (widget.getId() > 0) {
			update(widget);
		} else {
			create(widget);
		}
	}
	
}
