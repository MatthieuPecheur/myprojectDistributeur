package be.fp.distriWebApp.core.model.dao.impl;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import be.fp.distriWebApp.core.model.dao.IngredientDao;
import be.fp.distriWebApp.core.model.eo.Ingredient;

@Repository
public class IngredientDaoImpl extends GenericDaoHibernate<Ingredient, Integer> implements IngredientDao {
	public IngredientDaoImpl(){
		super(Ingredient.class);
	}
}
