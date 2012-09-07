package me.guymer.spring.mybatis.widget;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("mybatisWidgetService")
public class WidgetService {

	@Inject
	private WidgetMapper widgetMapper;
	
	public List<Widget> get() {
		return widgetMapper.getAll();
	}
	
	public Widget get(int id) {
		return widgetMapper.get(id);
	}
	
	public Widget get(Widget widget) {
		int id = widget.getId();
		
		return get(id);
	}
	
	@Transactional
	public void create(Widget widget) {
		widgetMapper.create(widget);
	}
	
	@Transactional
	public void update(Widget widget) {
		widgetMapper.update(widget);
	}
	
	@Transactional
	public void delete(Widget widget) {
		widgetMapper.delete(widget);
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
