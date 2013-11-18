package me.guymer.spring.mybatis.widget;

import java.util.List;

import javax.inject.Inject;

import me.guymer.spring.config.profile.MyBatis;
import me.guymer.spring.widget.Widget;
import me.guymer.spring.widget.WidgetService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@MyBatis
@Service
public class MyBatisWidgetService implements WidgetService {
	
	@Inject
	private WidgetMapper widgetMapper;
	
	@Override
	public List<Widget> get() {
		return widgetMapper.getAll();
	}
	
	@Override
	public Widget get(int id) {
		return widgetMapper.get(id);
	}
	
	@Override
	public Widget get(Widget widget) {
		int id = widget.getId();
		
		return get(id);
	}
	
	@Override
	@Transactional
	public void create(Widget widget) {
		widgetMapper.create(widget);
	}
	
	@Override
	@Transactional
	public void update(Widget widget) {
		widgetMapper.update(widget);
	}
	
	@Override
	@Transactional
	public void delete(Widget widget) {
		widgetMapper.delete(widget);
	}
	
	@Override
	@Transactional
	public void createOrUpdate(Widget widget) {
		if (widget.getId() > 0) {
			update(widget);
		} else {
			create(widget);
		}
	}
	
}
