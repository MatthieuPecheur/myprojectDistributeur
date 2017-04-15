package be.fp.distriWebApp.webapp.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import be.fp.distriWebApp.core.bo.DistributeurBo;
import be.fp.distriWebApp.core.model.dto.DistributeurDto;
import be.fp.distriWebApp.core.technical.DistributeurTechnical;	


@Controller
@ManagedBean
@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
public class AdminiDistriMb implements Serializable{

	private static final Logger logger = LoggerFactory.getLogger(AdminiDistriMb.class);
	
	@Autowired
	private DistributeurBo distributeurBo;	
	private List<DistributeurDto> distributeurList;
	private List<DistributeurTechnical> distributeurTechnList;
	
	public AdminiDistriMb(){
		//init();
	}
	@PostConstruct
	public void init(){
		buildDistributeurList();
	}
	
	public void buildDistributeurList(){
		distributeurTechnList = new ArrayList<DistributeurTechnical>();
		clearDistributeurList();
		setDistributeurList(distributeurBo.getAllDistributeur());
		if(distributeurList != null ){
			for(DistributeurDto currDistriDto :  distributeurList){
				DistributeurTechnical newDisTechnical = new DistributeurTechnical(currDistriDto);
				distributeurTechnList.add(newDisTechnical);
				//newDisTechnical.startReadSerial();
				
			}	
		}
	}
	
	public boolean isStartedDistri(DistributeurDto currentDistri){
		DistributeurTechnical currTechn = getTechnicalByDistributeurDto(currentDistri);
		if(currTechn != null){
			return currTechn.isEtatMarche();
		}
		return false;
	}
	
	public void startDistributeur(DistributeurDto currentDistri){
		if(!isStartedDistri(currentDistri)){
			DistributeurTechnical currTechn = getTechnicalByDistributeurDto(currentDistri);
			currTechn.startDistributeur();
		}
	}
	
	public void stopDistributeur(DistributeurDto currentDistri){
		if(isStartedDistri(currentDistri)){
			DistributeurTechnical currTechn = getTechnicalByDistributeurDto(currentDistri);
			currTechn.stopDistributeur();
		}
	}
	
	public void clearDistributeurList(){
		if(distributeurList != null && distributeurTechnList != null){
			distributeurList.clear();
			distributeurTechnList.clear();
		}
	}
	
	private List<DistributeurDto> getAllDistributeur(){		
		return distributeurBo.getAllDistributeur();
	}

	
	public void add(){
		
	}

	public DistributeurTechnical getTechnicalByDistributeurDto(DistributeurDto distriDto){
		for(DistributeurTechnical curTechnical : distributeurTechnList){
			if(curTechnical.getDistributeur().getId().equals(distriDto.getId())){
				return curTechnical;
			}
		}
		return null;
	}

	/*GETTERS AND SETTETS*/
		
	public DistributeurBo getDistributeurBo() {
		return distributeurBo;
	}

	public void setDistributeurBo(DistributeurBo distributeurBo) {
		this.distributeurBo = distributeurBo;
	}

	public List<DistributeurDto> getDistributeurList() {
		if(distributeurList == null){init();}
		return distributeurList;
	}

	public void setDistributeurList(List<DistributeurDto> distributeurList) {
		this.distributeurList = distributeurList;
	}

	public List<DistributeurTechnical> getDistributeurTechnList() {
		return distributeurTechnList;
	}

	public void setDistributeurTechnList(List<DistributeurTechnical> distributeurTechnList) {
		this.distributeurTechnList = distributeurTechnList;
	}



}
