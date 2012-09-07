package me.guymer.spring.mybatis.widget;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface WidgetMapper {
	
	List<Widget> getAll();
	
	Widget get(@Param("id") int id);
	
	void create(Widget widget);
	
	void update(Widget widget);
	
	void delete(Widget widget);

}
