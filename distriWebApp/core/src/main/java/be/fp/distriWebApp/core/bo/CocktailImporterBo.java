package be.fp.distriWebApp.core.bo;

import java.io.File;

import javax.jws.WebService;

@WebService
public interface CocktailImporterBo {
	public void importExcel(File excelfile);
}
