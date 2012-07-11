package me.guymer.spring.mybatis.service;

import java.util.List;

import javax.inject.Inject;

import me.guymer.spring.mybatis.domain.Widget;
import me.guymer.spring.mybatis.persistence.WidgetMapper;

import org.springframework.stereotype.Service;

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
		return get(widget.getId());
	}
	
	public void create(Widget widget) {
		widgetMapper.create(widget);
	}
	
	public void update(Widget widget) {
		widgetMapper.update(widget);
	}

	public void delete(Widget widget) {
		widgetMapper.delete(widget);
	}
}
