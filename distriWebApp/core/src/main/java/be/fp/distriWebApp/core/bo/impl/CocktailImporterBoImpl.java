package be.fp.distriWebApp.core.bo.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.jws.WebService;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import be.fp.distriWebApp.core.bo.CocktailImporterBo;
import be.fp.distriWebApp.core.bo.abstractDozerMapperBo;

@Service
@WebService
public class CocktailImporterBoImpl extends abstractDozerMapperBo implements CocktailImporterBo {
	
	private static final String FILE_NAME = "C:\\importCocktail.xlsx";
	
	public void importExcel(File excelfile){

	    try {
	    	FileInputStream excelFileIs = new FileInputStream(new File(FILE_NAME));
	    	Workbook workbook  = new XSSFWorkbook(excelFileIs);
	    	Sheet datatypeSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = datatypeSheet.iterator();
	        

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

		        while (cellIterator.hasNext()) {
	
	                Cell currentCell = cellIterator.next();
	                //getCellTypeEnum shown as deprecated for version 3.15
	                //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
	                if (currentCell.getCellTypeEnum() == CellType.STRING) {
	                    System.out.print(currentCell.getStringCellValue() + "--");
	                } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
	                    System.out.print(currentCell.getNumericCellValue() + "--");
	                }
	                System.out.println();
		        }
            }
	    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}	
}
