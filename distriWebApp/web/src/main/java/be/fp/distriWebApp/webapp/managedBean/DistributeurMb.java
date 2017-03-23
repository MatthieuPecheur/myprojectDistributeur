package be.fp.distriWebApp.webapp.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.ManagedBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
 	
import be.fp.distiWebApp.webapp.enums.PrettyPatterns;
import be.fp.distriWebApp.core.bo.DistributeurBo;
import be.fp.distriWebApp.core.model.dto.DistributeurDto;
import be.fp.distriWebApp.webapp.action.BasePage;	

@Controller
@ManagedBean
@Scope(value = WebApplicationContext.SCOPE_SESSION)

public class DistributeurMb extends BasePage implements Serializable{
	private static final Logger logger = LoggerFactory.getLogger(DistributeurMb.class);
	
	@Autowired
	private DistributeurBo distributeurBo;
	
	@Autowired
	AdminiDistriMb adminDistriMb;
	
	private DistributeurDto currentDistributeur;
	
	private List<DistributeurDto> getAllDistributeur(){		
		return adminDistriMb.getDistributeurList();
	}
	
	public String edit(DistributeurDto distriToEdit){	
		setCurrentDistributeur(distriToEdit);
		return "distributeurForm";
	}	
	
	/*GETTERS AND SETTETS*/	
	
	public void setSelectedDistrubeur(DistributeurDto selectedDistri){
		currentDistributeur = selectedDistri;
	}

	public DistributeurBo getDistributeurBo() {
		return distributeurBo;
	}

	public void setDistributeurBo(DistributeurBo distributeurBo) {
		this.distributeurBo = distributeurBo;
	}


	public DistributeurDto getCurrentDistributeur() {
		return currentDistributeur;
	}

	public void setCurrentDistributeur(DistributeurDto currentDistributeur) {
		this.currentDistributeur = currentDistributeur;
	}
	
}
