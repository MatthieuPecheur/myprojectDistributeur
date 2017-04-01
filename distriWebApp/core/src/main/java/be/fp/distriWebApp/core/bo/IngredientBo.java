package be.fp.distriWebApp.core.bo;

import be.fp.distriWebApp.core.model.dto.CocktailDto;
import be.fp.distriWebApp.core.model.dto.IngredientDto;
import be.fp.distriWebApp.core.model.dto.IngredientcocktailDto;
import be.fp.distriWebApp.core.model.eo.Ingredient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IngredientBo {
	@WebMethod public List<IngredientDto> findAllIngredients();
	@WebMethod public void saveDistributeur(@WebParam(name="IngredientDto")IngredientDto ingredientDto);
	@WebMethod public void deleteIngredient(@WebParam(name="IngredientDto")IngredientDto ingredientDto);
	@WebMethod public void addIngredient(@WebParam(name="IngredientDto")IngredientDto ingredientDto);

}
