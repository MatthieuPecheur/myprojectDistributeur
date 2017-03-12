package be.fp.distriWebApp.core.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import be.fp.distriWebApp.core.model.dao.DistributeurDao;
import be.fp.distriWebApp.core.model.eo.Distributeur;

@Repository
public class DistributeurDaoImpl extends GenericDaoHibernate <Distributeur, Integer> implements DistributeurDao {
	
	public DistributeurDaoImpl(){
		super(Distributeur.class);
	}
	
	
	@Override
	public List<Distributeur> getAllDistributeurByEtat(String domCodeEtat) {
		List<Distributeur> distriList = getSession().createCriteria(Distributeur.class).add(Restrictions.eq("etat_lovs.domcode", domCodeEtat)).list();
		if(distriList.isEmpty()) return null;
		
		return distriList;
	}
	
}
