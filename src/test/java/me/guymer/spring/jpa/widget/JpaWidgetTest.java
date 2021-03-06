package me.guymer.spring.jpa.widget;

import java.time.LocalDate;

import javax.inject.Inject;

import me.guymer.spring.config.Config;
import me.guymer.spring.config.profile.Profiles;
import me.guymer.spring.widget.Widget;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles({Profiles.TEST, Profiles.JPA})
@Transactional
public class JpaWidgetTest {

	@Inject
	private JpaWidgetService widgetService;

	//@Test(expected = DataIntegrityViolationException.class)
	@Test(expected = JpaSystemException.class)
	public void testSqlError() {
		String nameToLong = "012345678901234567890123456789012345678901234567890";

		Widget widget = new Widget();
		widget.setName(nameToLong);
		widget.setCreateDate(LocalDate.now());
		widget.setActive(true);

		widgetService.create(widget);
	}

	@Test
	public void testCreateOrUpdateCreate() {
		Assert.assertEquals(5, widgetService.get().size());

		Widget widget = new Widget();
		widget.setName("name");
		widget.setCreateDate(LocalDate.now());
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
