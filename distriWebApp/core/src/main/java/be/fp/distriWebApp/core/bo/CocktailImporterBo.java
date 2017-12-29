package be.fp.distriWebApp.core.bo;

import javax.jws.WebMethod;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.File;

@WebService
public interface CocktailImporterBo {
	@WebMethod public void importExcel(@WebParam(name="excelfilePath")String excelfilePath);
}
