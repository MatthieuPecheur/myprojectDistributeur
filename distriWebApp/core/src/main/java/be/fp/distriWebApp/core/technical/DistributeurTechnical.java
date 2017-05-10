package be.fp.distriWebApp.core.technical;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.fp.distriWebApp.core.model.dto.DistributeurDto;
import be.fp.distriWebApp.core.model.eo.Cocktail;
import be.fp.distriWebApp.core.model.eo.Pompe;
import be.fp.distriWebApp.core.technical.thread.DistributeurServerAtmega;



public class DistributeurTechnical {
	
	private static final Logger logger = LoggerFactory.getLogger(DistributeurTechnical.class);
	private Set<Pompe> pompes = new HashSet<Pompe>();
	private boolean etatMarche;

	private DistributeurDto distributeur;
	
	private Set<Cocktail> cocktailDipso = new HashSet<Cocktail>();
	
	private ServerSocket sServerAtmega ;
	
	private DistributeurServerAtmega distriServerAtmega;
	private DistributeurCommunicationServer distriCommunication;

	public DistributeurTechnical(DistributeurDto distriDto)
	{
		etatMarche = false;
		distributeur = distriDto;
		distriServerAtmega = new DistributeurServerAtmega(distriDto);	
	}
	public Set<Pompe> getPompes() {
		return pompes;
	}
	public void setPompes(Set<Pompe> pompes) {
		this.pompes = pompes;
	}
	public boolean isEtatMarche() {
		return etatMarche;
	}
	public void setEtatMarche(boolean etatMarche) {
		this.etatMarche = etatMarche;
	}

	public DistributeurServerAtmega getDistiClientAtmega() {
		return distriServerAtmega;
	}
	public void setDistiClientAtmega(DistributeurServerAtmega distiClientAtmega) {
		this.distriServerAtmega = distiClientAtmega;
	}
	

	/*Manage element for distributeur*/

	public void startDistributeur(){
		startCommunication();
	}

	public void stopDistributeur(){
		stopCommunication();
	}

	public void startServerAtmega()
	{
		try {
			sServerAtmega = new ServerSocket(distributeur.getPortAtmega());
			distriServerAtmega.setServerSocket(sServerAtmega);
			distriServerAtmega.startServer();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void startCommunication()
	{
		try {
			if(distriCommunication == null){
				distriCommunication = new DistributeurCommunicationServer(distributeur);
			}
			if(!distriCommunication.isStarted()){
				distriCommunication.startCommunication();
				etatMarche = true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void stopCommunication(){
		if(distriCommunication != null && distriCommunication.isStarted()){
			distriCommunication.stopCommunication();
			etatMarche = false;
		}

	}
	public void stopServerAtmega()
	{
		distriServerAtmega.stopServer();		
	}
	
	public void startAllServerTablette()
	{

		startServerAtmega();
		
	}
	public void stopAllServerTablette()
	{
		stopServerAtmega();
	}



	/* Getters and setters*/


	public DistributeurDto getDistributeur() {
		return distributeur;
	}

	public void setDistributeur(DistributeurDto distributeur) {
		this.distributeur = distributeur;
	}
	public DistributeurCommunicationServer getDistriCommunication() {
		return distriCommunication;
	}

	public void setDistriCommunication(DistributeurCommunicationServer distriCommunication) {
		this.distriCommunication = distriCommunication;
	}
}
