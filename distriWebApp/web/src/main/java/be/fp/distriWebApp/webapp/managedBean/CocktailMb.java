package be.fp.distriWebApp.webapp.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.ManagedBean;

import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import be.fp.distriWebApp.core.bo.CocktailBo;
import be.fp.distriWebApp.core.bo.IngredientBo;
import be.fp.distriWebApp.core.model.dto.CocktailDto;
import be.fp.distriWebApp.core.model.dto.IngredientDto;
import be.fp.distriWebApp.core.model.dto.IngredientcocktailDto;
import be.fp.distriWebApp.webapp.action.BasePage;

@Controller
@ManagedBean
@Scope(value = WebApplicationContext.SCOPE_SESSION)

public class CocktailMb extends BasePage implements Serializable{
	private static final Logger logger = LoggerFactory.getLogger(CocktailMb.class);
	
	@Autowired private CocktailBo cocktailBo;
	@Autowired private IngredientBo ingredientBo;
	
	AdminiDistriMb adminDistriMb;

	private IngredientDto currentIngredient;
	private CocktailDto currentCocktail;
	private DualListModel<IngredientDto> ingredientPickList;	

	/*COCKTAIL*/



	public List<CocktailDto> getAllCocktail(){
		List<CocktailDto> cocktailsListDto = cocktailBo.findAllCocktail();
		return cocktailsListDto;
	}

	public String editCocktail(CocktailDto cocktailToEdit){
		setCurrentCocktail(cocktailToEdit);
		refeshPickListInfredient();
		return "cocktailForm";
	}

	public String addCocktail(){
		currentCocktail = new CocktailDto();
		return "cocktailForm";
	}

	public String saveCocktail(){
		if(currentCocktail.getId() != null){
			saveIngredientCocktail();
			cocktailBo.saveCocktail(currentCocktail);
		}
		return "cocktailForm";
	}
	private void saveIngredientCocktail() {
		boolean bfound = false;
		// suppression
		 
		for(Iterator<IngredientcocktailDto> itIngCock  = currentCocktail.getIngredientcocktails().iterator(); itIngCock.hasNext();){		
			IngredientcocktailDto currIngCock = (IngredientcocktailDto) itIngCock.next();
		    for(IngredientDto curTarg : ingredientPickList.getTarget()){
			   if(curTarg.getId().equals(currIngCock.getIngredient().getId())){
				   bfound = true;
			   }			   
		    }	
		    if(bfound == false){
		    	currentCocktail.getIngredientcocktails().remove(currIngCock);
		    }
		    bfound = false;
		}		
		// ajout
		for(IngredientDto curTarg : ingredientPickList.getTarget()){
			
			for(Iterator<IngredientcocktailDto> itIngCockAdd  = currentCocktail.getIngredientcocktails().iterator(); itIngCockAdd.hasNext();){
				IngredientcocktailDto currIngCockToAdd = (IngredientcocktailDto) itIngCockAdd.next();
				if(curTarg.getId().equals(currIngCockToAdd.getIngredient().getId())){
					bfound = true;
				}
			}
			if(bfound = false){
				IngredientcocktailDto ingredientcocktailDto = new IngredientcocktailDto();
				ingredientcocktailDto.setIngredient(curTarg);
				currentCocktail.getIngredientcocktails().add(ingredientcocktailDto);				
			}
			bfound = false;
		}
	}

	public String deleteCocktail(){
		if(currentCocktail  != null){
			cocktailBo.deleteCocktail(currentCocktail);
		}
		currentCocktail = null;
		return "cocktails";
	}

	public String cancelCocktail(){
		currentCocktail = null;
		return "cocktails";
	}

	/*INGREDIENT*/


	public List<IngredientDto> getAllIngredient(){
		List<IngredientDto> ingredientsDto = ingredientBo.findAllIngredients();
		return ingredientsDto;
	}

	public List<IngredientDto> getAllIngredientSoft(){
		List<IngredientDto> ingredientsDto = ingredientBo.findAllIngredientsSoft();
		return ingredientsDto;
	}

	public List<IngredientDto> getAllIngredientAlcolise(){
		List<IngredientDto> ingredientsDto = ingredientBo.findAllIngredientsAlcolise();
		return ingredientsDto;
	}

	public String editIngredient(IngredientDto ingredientToEdit){
		setCurrentIngredient(ingredientToEdit);
		return "ingredientForm";
	}
	public String saveIngredient(){
		if(currentIngredient.getId() != null){
			ingredientBo.saveDistributeur(currentIngredient);
		}else{
			ingredientBo.addIngredient(currentIngredient);
		}
		currentIngredient = null;
		return "ingredients";
	}
	public String deleteIngredient(){
		if(currentIngredient  != null){
			ingredientBo.deleteIngredient(currentIngredient);
		}
		currentIngredient = null;
		return "ingredients";
	}
	public String cancelIngredient(){
		currentIngredient = null;
		return "ingredients";
	}

	public String addIngredient(boolean alcolise){
		currentIngredient = new IngredientDto();
		currentIngredient.setAlcoolise(alcolise);
		return "ingredientForm";
	}
	
	public void refeshPickListInfredient(){
		boolean bfound = false;
	    List<IngredientDto> source = new ArrayList<IngredientDto>();  
        List<IngredientDto> target = new ArrayList<IngredientDto>();  
        
        for(IngredientDto currIngredient : getAllIngredient()){
        	for(IngredientDto currIngredientCock : currentCocktail.getIngredientsDto()){
        		if(currIngredient.getId().equals(currIngredientCock.getId())){
        			bfound = true;
        		}
        	}
        	if(bfound == true){
        		target.add(currIngredient);
        	}else{
        		source.add(currIngredient);
        	}
        	bfound = false;
        }        
        ingredientPickList = new DualListModel<>(source, target);
	}
	/*GETTERS AND SETTETS*/

	public IngredientDto getCurrentIngredient() {
		return currentIngredient;
	}

	public void setCurrentIngredient(IngredientDto currentIngredient) {
		this.currentIngredient = currentIngredient;
	}

	public CocktailDto getCurrentCocktail() {
		return currentCocktail;
	}

	public void setCurrentCocktail(CocktailDto currentCocktail) {
		this.currentCocktail = currentCocktail;
	}

	public DualListModel<IngredientDto> getIngredientPickList() {
		return ingredientPickList;
	}

	public void setIngredientPickList(DualListModel<IngredientDto> ingredientPickList) {
		this.ingredientPickList = ingredientPickList;
	}
	
	
}
