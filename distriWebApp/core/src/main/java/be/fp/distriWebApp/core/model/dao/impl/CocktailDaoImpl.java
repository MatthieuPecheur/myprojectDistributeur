package be.fp.distriWebApp.core.model.dao.impl;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import be.fp.distriWebApp.core.model.dao.CocktailDao;
import be.fp.distriWebApp.core.model.eo.Cocktail;

@Repository
public class CocktailDaoImpl extends GenericDaoHibernate<Cocktail, Integer> implements CocktailDao {
	public CocktailDaoImpl(){
		super(Cocktail.class);
	}
	
}
