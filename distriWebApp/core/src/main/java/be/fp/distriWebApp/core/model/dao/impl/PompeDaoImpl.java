package be.fp.distriWebApp.core.model.dao.impl;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import be.fp.distriWebApp.core.model.dao.PompeDao;
import be.fp.distriWebApp.core.model.eo.Pompe;

@Repository
public class PompeDaoImpl extends GenericDaoHibernate<Pompe, Integer> implements PompeDao {
	public PompeDaoImpl(){
		super(Pompe.class);
	}
}
