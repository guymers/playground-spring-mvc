package me.guymer.spring.mybatis.widget;

import java.util.List;

import me.guymer.spring.widget.Widget;

import org.apache.ibatis.annotations.Param;

interface WidgetMapper {
	
	List<Widget> getAll();
	
	Widget get(@Param("id") int id);
	
	void create(Widget widget);
	
	void update(Widget widget);
	
	void delete(Widget widget);
	
}
