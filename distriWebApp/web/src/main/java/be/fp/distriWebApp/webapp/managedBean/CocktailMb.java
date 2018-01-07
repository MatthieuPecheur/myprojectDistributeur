package be.fp.distriWebApp.webapp.managedBean;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.appfuse.Constants;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import be.fp.distriWebApp.core.bo.CocktailBo;
import be.fp.distriWebApp.core.bo.CocktailImporterBo;
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
	@Autowired private CocktailImporterBo cocktailImporterBo;

	
	AdminiDistriMb adminDistriMb;

	private IngredientDto currentIngredient;
	private CocktailDto currentCocktail;
	private DualListModel<IngredientDto> ingredientPickList;	
	private String colorInline;
	private String colorPopup;

	private UploadedFile file;
	private String name;
	
	/*COCKTAIL*/

   @PostConstruct
    public void init() {
	   ingredientPickList = new DualListModel<>();
   }
	         

	public List<CocktailDto> getAllCocktail(){
		List<CocktailDto> cocktailsListDto = cocktailBo.findAllCocktail();
		return cocktailsListDto;
	}

	public String editCocktail(CocktailDto cocktailToEdit){
		setCurrentCocktail(cocktailToEdit);
		refeshPickListIngredient();

		return "cocktailForm";
	}

	public String addCocktail(){
		currentCocktail = new CocktailDto();
		refeshPickListIngredient();
		return "cocktailForm";
	}

	public String saveCocktail(){
		if(currentCocktail.getId() != null){		
			cocktailBo.saveCocktail(currentCocktail);		
		}
		return "cocktailForm";
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
			ingredientBo.saveIngredient(currentIngredient);
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
	
	public void refeshPickListIngredient(){
		boolean bfound = false;
	
	    List<IngredientDto> source = new ArrayList<IngredientDto>();  
        List<IngredientDto> target = new ArrayList<IngredientDto>();  
		
		ingredientPickList.getSource().clear();
		ingredientPickList.getTarget().clear();
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
	
	public void ingredientSourceToTarget(){
		if(currentCocktail != null){
			
			/*Set<IngredientDto> locaTargets = new HashSet<IngredientDto>(ingredientPickList.getTarget());
			Map<IngredientDto,IngredientcocktailDto> currentCocktailIng = new HashMap<IngredientDto,IngredientcocktailDto>();*/
			
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
			    	itIngCock.remove();
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
				if(bfound == false){
					IngredientcocktailDto ingredientcocktailDto = new IngredientcocktailDto();
					ingredientcocktailDto.setIngredient(curTarg);					
					currentCocktail.getIngredientcocktails().add(ingredientcocktailDto);				
				}
				bfound = false;
			}
		}
	}

	public String uploadExcel() throws IOException {
		HttpServletRequest request = getRequest();

		// write the file to the filesystem
		// the directory to upload to
		String uploadDir = getFacesContext().getExternalContext().getRealPath("/resources");
		// The following seems to happen when running jetty:run
		if (uploadDir == null) {
			uploadDir = new File("src/main/webapp/resources").getAbsolutePath();
		}
		uploadDir += "/" + request.getRemoteUser() + "/";

		// Create the directory if it doesn't exist
		File dirPath = new File(uploadDir);

		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		//retrieve the file data
		InputStream stream = file.getInputstream();
		String filename = file.getFileName();

		// APF-946: Canoo Web Tests R_1702 sets full path as name instead of only file name
		if (filename.contains("/")) {
			filename = filename.substring(filename.lastIndexOf("/") + 1);
		}

		// APF-758: Fix for Internet Explorer's shortcomings
		if (filename.contains("\\")) {
			int slash = filename.lastIndexOf("\\");
			if (slash != -1) {
				filename = filename.substring(slash + 1);
			}
			// Windows doesn't like /'s either
			int slash2 = filename.lastIndexOf("/");
			if (slash2 != -1) {
				filename = filename.substring(slash2 + 1);
			}
			// In case the name is C:foo.txt
			int slash3 = filename.lastIndexOf(":");
			if (slash3 != -1) {
				filename = filename.substring(slash3 + 1);
			}
		}

		//write the file to the file specified
		OutputStream bos = new FileOutputStream(uploadDir + filename);
		int bytesRead;
		byte[] buffer = new byte[8192];

		while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}

		bos.close();

		//close the stream
		stream.close();

		// place the data into the request for retrieval on next page

		cocktailImporterBo.importExcel(dirPath.getAbsolutePath() + Constants.FILE_SEP + filename);

		/*request.setAttribute("friendlyName", name);
		request.setAttribute("fileName", filename);
		request.setAttribute("contentType", file.getContentType());
		request.setAttribute("size", file.getSize() + " bytes");
		request.setAttribute("location", dirPath.getAbsolutePath() + Constants.FILE_SEP + filename);

		String link = request.getContextPath() + "/resources" + "/" + request.getRemoteUser() + "/";
		request.setAttribute("link", link + filename); */

		return "success";
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
		if(ingredientPickList == null || (ingredientPickList.getSource().size() == 0 && ingredientPickList.getTarget().size() == 0) ){
			refeshPickListIngredient();
		}
		return ingredientPickList;
	}

	public void setIngredientPickList(DualListModel<IngredientDto> ingredientPickList) {
		this.ingredientPickList = ingredientPickList;
	}


	public String getColorPopup() {
		return colorPopup;
	}


	public void setColorPopup(String colorPopup) {
		this.colorPopup = colorPopup;
	}


	public String getColorInline() {
		return colorInline;
	}


	public void setColorInline(String colorInline) {
		this.colorInline = colorInline;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
