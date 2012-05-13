package me.guymer.spring.mybatis.controller;

import java.util.List;

import javax.inject.Inject;

import me.guymer.spring.mybatis.domain.Widget;
import me.guymer.spring.mybatis.service.WidgetService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/mybatis/widget")
public class WidgetController {

	private static final Logger logger = LoggerFactory.getLogger(WidgetController.class);
	
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
	public @ResponseBody Widget get(@PathVariable("id") int id) {
		Widget widget = widgetService.get(id);
		
		logger.info("retrieved {}", widget);
		
		return widget;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Widget create(@RequestBody Widget widget) {
		widgetService.create(widget);
		
		logger.info("created {}", widget);
		
		return widget;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Widget update(@PathVariable("id") int id, @RequestBody Widget widget) {
		widget.setId(id);
		widgetService.update(widget);
		
		logger.info("updated {}", widget);
		
		return widget;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Widget delete(@PathVariable("id") int id, @RequestBody Widget widget) {
		widget.setId(id);
		widgetService.delete(widget);
		
		logger.info("deleted {}", widget);
		
		return widget;
	}
}
