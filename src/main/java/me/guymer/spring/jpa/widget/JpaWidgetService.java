package me.guymer.spring.jpa.widget;

import java.util.List;

import javax.inject.Inject;

import me.guymer.spring.config.profile.Jpa;
import me.guymer.spring.widget.Widget;
import me.guymer.spring.widget.WidgetService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Jpa
@Service
public class JpaWidgetService implements WidgetService {

	@Inject
	private WidgetRepository widgetRepository;

	@Override
	public List<Widget> get() {
		return widgetRepository.getAll();
	}

	@Override
	public Widget get(int id) {
		return widgetRepository.get(id);
	}

	@Override
	public Widget get(Widget widget) {
		int id = widget.getId();

		return get(id);
	}

	@Override
	@Transactional
	public void create(Widget widget) {
		widgetRepository.create(widget);
	}

	@Override
	@Transactional
	public void update(Widget widget) {
		widgetRepository.update(widget);
	}

	@Override
	@Transactional
	public void delete(Widget widget) {
		widgetRepository.delete(widget);
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
