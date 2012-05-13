package me.guymer.spring.mybatis.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.guymer.spring.mybatis.domain.Widget;

public interface WidgetMapper {
	
	public List<Widget> getAll();
	
	public Widget get(@Param("id") int id);
	
	public void create(Widget widget);
	
	public void update(Widget widget);
	
	public void delete(Widget widget);

}
