package be.fp.distriWebApp.core.model.dao.impl;

import be.fp.distriWebApp.core.model.dao.IngredientDao;
import be.fp.distriWebApp.core.model.eo.Ingredient;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngredientDaoImpl extends GenericDaoHibernate<Ingredient, Integer> implements IngredientDao {
	public IngredientDaoImpl(){
		super(Ingredient.class);
	}

	@Override
	public List<Ingredient> findAllDistinctAlcool(boolean withAlcool) {
		List<Ingredient> ingredientList = getSession().createCriteria(Ingredient.class).add(Restrictions.eq("alcoolise", withAlcool)).list();
		if(ingredientList.isEmpty()) return null;

		return ingredientList;
	}

}
