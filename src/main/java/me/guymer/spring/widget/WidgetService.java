package me.guymer.spring.widget;

import java.util.List;

public interface WidgetService {
	
	public List<Widget> get();
	
	public Widget get(int id);
	
	public Widget get(Widget widget);
	
	public void create(Widget widget);
	
	public void update(Widget widget);
	
	public void delete(Widget widget);
	
	public void createOrUpdate(Widget widget);
	
}
