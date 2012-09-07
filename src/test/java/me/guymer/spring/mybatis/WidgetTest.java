package me.guymer.spring.mybatis;

import java.util.Date;

import javax.inject.Inject;

import junit.framework.Assert;
import me.guymer.spring.mybatis.domain.Widget;
import me.guymer.spring.mybatis.service.WidgetService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
@Transactional
public class WidgetTest {
	
	@Configuration
	@ComponentScan(basePackages = "me.guymer.spring", excludeFilters = @ComponentScan.Filter({ComponentScan.class, EnableWebMvc.class}))
	static class ContextConfiguration {}
	
	@Inject
	private WidgetService widgetService;
	
	@Test(expected = MyBatisSystemException.class)
	public void testSqlError() {
		String nameToLong = "012345678901234567890123456789012345678901234567890";
		
		Widget widget = new Widget();
		widget.setName(nameToLong);
		widget.setCreateDate(new Date());
		widget.setActive(true);
		
		widgetService.create(widget);
	}
	
	@Test
	public void testCreateOrUpdateCreate() {
		Assert.assertEquals(5, widgetService.get().size());
		
		Widget widget = new Widget();
		widget.setName("name");
		widget.setCreateDate(new Date());
		widget.setActive(true);
		
		widgetService.createOrUpdate(widget);
		
		Assert.assertEquals(6, widgetService.get().size());
	}
	
	@Test
	public void testCreateOrUpdateUpdate() {
		Widget existing = widgetService.get(1);
		
		Assert.assertEquals("Test 1", existing.getName());
		
		existing.setName("blah");
		
		widgetService.createOrUpdate(existing);
		
		Widget existing2 = widgetService.get(1);
		
		Assert.assertEquals("blah", existing2.getName());
	}
	
}
