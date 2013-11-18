package me.guymer.spring.config.persistence;

import javax.inject.Inject;

import me.guymer.spring.config.profile.Jpa;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;

/**
 * Registering a PersistenceExceptionTranslationPostProcessor bean does not convert exceptions when using AspectJ
 *
 * http://alexandros-karypidis.blogspot.com.au/2011/06/spring-exception-translation-with.html
 * https://jira.springsource.org/browse/ROO-182?focusedCommentId=47196#comment-47196
 */
@Jpa
@Aspect
@Configurable
public class JpaExceptionTranslator {
	
	@Inject
	private JpaVendorAdapter jpaVendorAdapter;
	
	@Pointcut("call(* javax.persistence.EntityManager.*(..))")
	public void entityManagerCall() {}
	
	@Pointcut("call(* javax.persistence.EntityManagerFactory.*(..))")
	public void entityManagerFactoryCall() {}
	
	@Pointcut("call(* javax.persistence.EntityTransaction.*(..))")
	public void entityTransactionCall() {}
	
	@Pointcut("call(* javax.persistence.Query.*(..))")
	public void queryCall() {}
	
	@AfterThrowing(pointcut = "entityManagerCall() || entityManagerFactoryCall() || entityTransactionCall() || queryCall()", throwing = "re")
	public void translate(RuntimeException re) {
		JpaDialect jpaDialect = jpaVendorAdapter.getJpaDialect();
		DataAccessException de = jpaDialect.translateExceptionIfPossible(re);
		
		if (de != null) {
			throw de;
		}
		
		throw re;
	}
	
}
