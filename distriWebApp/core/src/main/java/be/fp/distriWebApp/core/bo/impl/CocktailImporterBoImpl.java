package be.fp.distriWebApp.core.bo.impl;

import be.fp.distriWebApp.core.bo.CocktailImporterBo;
import be.fp.distriWebApp.core.bo.IngredientBo;
import be.fp.distriWebApp.core.bo.abstractDozerMapperBo;
import be.fp.distriWebApp.core.model.dto.IngredientDto;
import be.fp.distriWebApp.core.model.eo.Ingredient;
import org.apache.poi.UnsupportedFileFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

@Service
@WebService
public class CocktailImporterBoImpl extends abstractDozerMapperBo implements CocktailImporterBo {

	@Autowired private IngredientBo ingredientBo;

	@Override
	public void importExcel(String excelfilePath){

	    try {
	    	FileInputStream excelFileIs = new FileInputStream(new File(excelfilePath));
			XSSFWorkbook workbook  = new XSSFWorkbook(excelFileIs);

			/*First page : Ingredients*/
			XSSFSheet datatypeSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = datatypeSheet.iterator();
			int i = 1;
            while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				if(i != 1){
					IngredientDto newIngredientDto = new IngredientDto();
					Iterator<Cell> cellIterator = currentRow.iterator();
					int k =1;
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();

						switch (k){
							case 1:
								// Numéro unique de l'ingredient
								break;
							case 2:
								if(currentCell.getCellTypeEnum() == CellType.STRING){
									newIngredientDto.setIntitule(currentCell.getStringCellValue());
								}else{
									throw (new Exception("Mauvais format de cellule dans la feuille 1 / ligne " + i + " / colonne " + k));
								}
								break;
							case 3:
								// Marque de l'ingrédient
								if(currentCell.getCellTypeEnum() == CellType.STRING){
									newIngredientDto.setMarque(currentCell.getStringCellValue());
								}else{
									throw (new Exception("Mauvais format de cellule dans la feuille 1 / ligne " + i + " / colonne " + k));
								}
								break;
							case 4:
								// Quantité pleine
								if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
									newIngredientDto.setQuantitePleine(new BigDecimal(currentCell.getNumericCellValue()));
								}else{
									throw (new Exception("Mauvais format de cellule dans la feuille 1 / ligne " + i + " / colonne " + k));
								}
								break;
							case 5:
								// alcolisé O/N
								if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
									if((Double)currentCell.getNumericCellValue() != null) {
										newIngredientDto.setAlcoolise(currentCell.getNumericCellValue() == 1 ? true : false);
									}else{
										newIngredientDto.setAlcoolise(false);
									}
								}else{
									throw (new Exception("Mauvais format de cellule dans la feuille 1 / ligne " + i + " / colonne " + k));
								}
								break;
							case 6:
								// degré alcool
								if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
									if((Double)currentCell.getNumericCellValue() != null){
										newIngredientDto.setDegreAlcool((int)currentCell.getNumericCellValue());
									}
								}else{
									throw (new Exception("Mauvais format de cellule dans la feuille 1 / ligne " + i + " / colonne " + k));
								}
								break;
						}

						k++;
					}
					ingredientBo.addIngredient(newIngredientDto);
				}
				i++;
            }

			/*Second page : Cocktails*/
			XSSFSheet datatypeSheet2 = workbook.getSheetAt(1);
			Iterator<Row> iterator2 = datatypeSheet2.iterator();

	    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
	    	e.printStackTrace();
        } catch (UnsupportedFileFormatException e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}

		
	}	
}
