package me.guymer.spring.mybatis.widget;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("mybatisWidgetController")
@RequestMapping(value = "/mybatis/widget")
public class WidgetController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WidgetController.class);
	
	@Inject
	private WidgetService widgetService;
	
	@RequestMapping(value = "widgets", method = RequestMethod.GET)
	public String widget(Model model) {
		return "widget";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Widget> list() {
		List<Widget> widgets = widgetService.get();
		
		return widgets;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Widget get(@PathVariable int id) {
		Widget widget = widgetService.get(id);
		
		LOGGER.info("retrieved {}", widget);
		
		return widget;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Widget create(@RequestBody Widget widget) {
		widgetService.create(widget);
		
		LOGGER.info("created {}", widget);
		
		return widget;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Widget update(@PathVariable int id, @RequestBody Widget widget) {
		widget.setId(id);
		widgetService.update(widget);
		
		LOGGER.info("updated {}", widget);
		
		return widget;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Widget delete(@PathVariable int id, @RequestBody Widget widget) {
		widget.setId(id);
		widgetService.delete(widget);
		
		LOGGER.info("deleted {}", widget);
		
		return widget;
	}
}
