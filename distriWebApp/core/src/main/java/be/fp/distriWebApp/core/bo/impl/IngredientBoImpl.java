package be.fp.distriWebApp.core.bo.impl;

import be.fp.distriWebApp.core.bo.IngredientBo;
import be.fp.distriWebApp.core.bo.abstractDozerMapperBo;
import be.fp.distriWebApp.core.model.dao.IngredientDao;
import be.fp.distriWebApp.core.model.dto.IngredientDto;
import be.fp.distriWebApp.core.model.eo.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@WebService
public class IngredientBoImpl extends abstractDozerMapperBo implements IngredientBo,Serializable {

	@Autowired IngredientDao ingredientDao;

	@Transactional(readOnly=true)
	@Override
	public List<IngredientDto> findAllIngredients(){
		List<IngredientDto> ingredientsDto = new ArrayList<IngredientDto>(0);
		List<Ingredient> ingredients = ingredientDao.getAll();
		if(ingredients != null){
			for(Ingredient currIngredient : ingredients){
				ingredientsDto.add(mapper.map(currIngredient,IngredientDto.class));
			}
		}

		return ingredientsDto;
	}
	@Transactional(readOnly=false)
	@Override
	public void saveDistributeur(IngredientDto ingredientDto){
		if(ingredientDto != null){
			ingredientDao.save(mapper.map(ingredientDto,Ingredient.class));
		}
	}

	@Transactional(readOnly=false)
	@Override
	public void deleteIngredient(IngredientDto ingredientDto){
		if(ingredientDto != null && ingredientDto.getId() > -1){
			ingredientDao.remove(ingredientDto.getId());
		}
	}

	@Transactional(readOnly=false)
	@Override
	public void addIngredient(IngredientDto ingredientDto){
		if(ingredientDto != null){
			ingredientDao.save(mapper.map(ingredientDto,Ingredient.class));
		}
	}

}
