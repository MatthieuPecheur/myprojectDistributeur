package be.fp.distriWebApp.core.model.dao;

import org.appfuse.dao.GenericDao;

import be.fp.distriWebApp.core.model.eo.Ingredient;

import java.util.List;

public interface IngredientDao extends GenericDao<Ingredient, Integer> {
    public List<Ingredient> findAllDistinctAlcool(boolean withAlcool);
}
