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

@Controller
@ManagedBean
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@URLMappings(mappings={
		@URLMapping(id = "distributeurDetail" , pattern = "/distributeurDetail",viewId = "/distributeur/distributeurForm.xhtml")
})
public class DistributeurMb implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3950268603385378751L;

	private static final Logger logger = LoggerFactory.getLogger(DistributeurMb.class);
	
	@Autowired
	private DistributeurBo distributeurBo;
	
	@Autowired
	AdminiDistriMb adminDistriMb;
	
	private DistributeurDto currentDistributeur;
	
	private List<DistributeurDto> getAllDistributeur(){		
		return adminDistriMb.getDistributeurList();
	}
	
	private String edit(DistributeurDto distriToEdit){	
		setCurrentDistributeur(distriToEdit);
		return PrettyPatterns.distributeurDetail.toPrettyPattern();
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
