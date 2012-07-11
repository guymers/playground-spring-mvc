package me.guymer.spring.mybatis.persistence;

import java.util.List;

import me.guymer.spring.mybatis.domain.Widget;

import org.apache.ibatis.annotations.Param;

public interface WidgetMapper {
	
	List<Widget> getAll();
	
	Widget get(@Param("id") int id);
	
	void create(Widget widget);
	
	void update(Widget widget);
	
	void delete(Widget widget);

}
