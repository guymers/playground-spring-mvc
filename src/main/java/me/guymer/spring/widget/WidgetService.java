package me.guymer.spring.widget;

import java.util.List;

public interface WidgetService {

	List<Widget> get();

	Widget get(int id);

	Widget get(Widget widget);

	void create(Widget widget);

	void update(Widget widget);

	void delete(Widget widget);

	void createOrUpdate(Widget widget);
}
