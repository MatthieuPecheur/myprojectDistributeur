package be.fp.distriWebApp.core.bo;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import be.fp.distriWebApp.core.enums.RefLovsEnum;
import be.fp.distriWebApp.core.model.dto.CocktailDto;
import be.fp.distriWebApp.core.model.dto.DistributeurDto;

@WebService
public interface DistributeurBo {
	@WebMethod public List<CocktailDto> getListCocktailDispo(@WebParam(name="DistributeurDto")DistributeurDto distributeurDto);
	@WebMethod public List<CocktailDto> getListCocktailToDo(@WebParam(name="DistributeurDto")DistributeurDto distributeurDto);
	@WebMethod public List<DistributeurDto> getAllDistributeur();
	@WebMethod public List<DistributeurDto> getAllDistributeurByEtat(@WebParam(name="etatEnum")RefLovsEnum etatEnum);
	@WebMethod public void deleteDistributeur(@WebParam(name="DistributeurDto")DistributeurDto distributeurDto);
	@WebMethod public void saveDistributeur(@WebParam(name="DistributeurDto")DistributeurDto distributeurDto);
	@WebMethod public void addDistributeur(@WebParam(name="DistributeurDto")DistributeurDto distributeurDto);

	/*technical*/

}
