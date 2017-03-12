package be.fp.distriWebApp.core.model.dao.impl;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import be.fp.distriWebApp.core.model.dao.RefLovsDao;
import be.fp.distriWebApp.core.model.eo.RefLovs;

@Repository
public class RefLovsDaoImpl extends GenericDaoHibernate<RefLovs, String> implements RefLovsDao {
	public RefLovsDaoImpl(){
		super(RefLovs.class);
	}
}
