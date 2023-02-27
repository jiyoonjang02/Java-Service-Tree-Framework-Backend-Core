package com.egovframework.ple.treeframework.springhibernate.dao;

import com.egovframework.ple.treeframework.springhibernate.vo.JsTreeHibernateSearchDTO;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@SuppressWarnings("unchecked")
@Repository("jsTreeHibernateDao")
public class JsTreeHibernateDaoImpl<T extends JsTreeHibernateSearchDTO> extends
		JsTreeHibernateAbstractDao<T, Serializable> implements JsTreeHibernateDao<T, Serializable> {

	private Class<T> clazz;

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	@Override
	protected Class<T> getEntityClass() {
		return getClazz();
	}
}
