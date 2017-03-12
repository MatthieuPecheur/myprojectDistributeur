package be.fp.distriWebApp.core.bo;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import be.fp.distriWebApp.core.model.dto.CocktailDto;
import be.fp.distriWebApp.core.model.dto.IngredientcocktailDto;

@WebService
public interface CocktailBo {
	@WebMethod public void createCocktail(@WebParam(name="coctailDto")CocktailDto coctailDto);
	@WebMethod public String getCocktailToString(@WebParam(name="coctailDto")CocktailDto coctailDto);
	@WebMethod public byte[] getCocktailToArrayByte(@WebParam(name="coctailDto")CocktailDto cocktailDto);
	@WebMethod public boolean remplirBouteille(@WebParam(name="idPompe")int idPompe);
	@WebMethod public int getNbreCocktailPosIngredient(@WebParam(name="coctailDto") CocktailDto cocktailDto);
	@WebMethod public List<IngredientcocktailDto> verifieCocktailSotck(@WebParam(name="coctailDto")CocktailDto cocktailDto);
	@WebMethod public void retireQuantiteIngredientcocktail(@WebParam(name="ingredCockDto")IngredientcocktailDto ingredCockDto);
}
