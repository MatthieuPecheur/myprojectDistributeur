package be.fp.distriWebApp.core.technical;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.fp.distriWebApp.core.model.dto.DistributeurDto;
import be.fp.distriWebApp.core.model.eo.Cocktail;
import be.fp.distriWebApp.core.model.eo.Ingredient;
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
	private SerialCommunicationRxtx serialCommunication;

	 		

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
		startSerialCommunication();
	}

	public void stopDistributeur(){
		stopSerialCommunication();
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
	public void startSerialCommunication()
	{
		try {
			if(serialCommunication == null){
				serialCommunication = new SerialCommunicationRxtx("COM3");
			}
			if(!serialCommunication.isStarted()){
				serialCommunication.connect();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void stopSerialCommunication(){
		if(serialCommunication != null && serialCommunication.isStarted()){
			serialCommunication.unConnect();
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

	public SerialCommunicationRxtx getSerialCommunication() {
		return serialCommunication;
	}

	public void setSerialCommunication(SerialCommunicationRxtx serialCommunication) {
		this.serialCommunication = serialCommunication;
	}
}
