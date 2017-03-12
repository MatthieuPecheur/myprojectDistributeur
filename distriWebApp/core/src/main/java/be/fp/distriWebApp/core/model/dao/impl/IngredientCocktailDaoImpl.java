package be.fp.distriWebApp.core.model.dao.impl;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import be.fp.distriWebApp.core.model.dao.IngredientCocktailDao;
import be.fp.distriWebApp.core.model.eo.Ingredientcocktail;

@Repository
public class IngredientCocktailDaoImpl extends GenericDaoHibernate<Ingredientcocktail, Integer> implements IngredientCocktailDao {
	public IngredientCocktailDaoImpl(){
		super(Ingredientcocktail.class);
	}
}
