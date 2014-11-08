package me.guymer.spring.config.persistence;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.sessions.Session;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

@Configurable
public class EclipseLinkSessionCustomizer implements SessionCustomizer {

	@Value("${config.schema}")
	private String schema;

	@Override
	public void customize(Session session) {
		// set the default schema to use
		session.getLogin().setTableQualifier(schema);
	}
}
