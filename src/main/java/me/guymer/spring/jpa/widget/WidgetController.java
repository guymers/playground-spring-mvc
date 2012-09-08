package me.guymer.spring.jpa.widget;

import java.util.List;

import javax.inject.Inject;

import me.guymer.spring.web.ext.ExtJsonResponseBody;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("jpaWidgetController")
@RequestMapping(value = "/jpa/widget")
public class WidgetController {
	
	@Inject
	private WidgetService widgetService;
	
	@RequestMapping(value = "widgets", method = RequestMethod.GET)
	public String widget(Model model) {
		return "widget";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ExtJsonResponseBody
	public List<Widget> list() {
		List<Widget> widgets = widgetService.get();
		
		return widgets;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ExtJsonResponseBody
	public Widget get(@PathVariable int id) {
		Widget widget = widgetService.get(id);
		
		return widget;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ExtJsonResponseBody
	public Widget create(@RequestBody Widget widget) {
		widgetService.create(widget);
		
		return widget;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ExtJsonResponseBody
	public Widget update(@PathVariable int id, @RequestBody Widget widget) {
		widget.setId(id);
		widgetService.update(widget);
		
		return widget;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ExtJsonResponseBody
	public Widget delete(@PathVariable int id, @RequestBody Widget widget) {
		widget.setId(id);
		widgetService.delete(widget);
		
		return widget;
	}
	
}
