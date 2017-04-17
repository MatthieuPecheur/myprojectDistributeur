package be.fp.distriWebApp.core.technical;

import be.fp.distriWebApp.core.model.dto.DistributeurDto;
import be.fp.distriWebApp.core.model.eo.Cocktail;
import be.fp.distriWebApp.core.model.eo.Pompe;
import be.fp.distriWebApp.core.technical.protocol.ProtocolRequest;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;
import be.fp.distriWebApp.core.technical.protocol.response.CocktailEndResponse;
import be.fp.distriWebApp.core.technical.thread.DistributeurServerAtmega;
import org.omg.IOP.IORHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class DistributeurCommunicationServer {

	private static final Logger logger = LoggerFactory.getLogger(DistributeurCommunicationServer.class);
	private static boolean isStarted;
	private DistributeurDto distributeur;
	private static SerialCommunicationRxtx serialCommunication;
	private static LinkedList<ProtocolRequest> requestList;
	private static LinkedList<ProtocolResponse> responseList;

	private static Thread communicationThread;


	public DistributeurCommunicationServer(DistributeurDto distriDto)
	{
		isStarted = false;
		distributeur = distriDto;
		requestList = new LinkedList<ProtocolRequest>();
	}


	/*Manage element for distributeur*/

	public void startCommunication(){
		startSerialCommunication();
		if(serialCommunication != null && serialCommunication.isStarted()){
			startTreatmentQueue();
		}
	}

	public void stopCommunication(){
		stopTreatmentQueue();
		if(isStarted == false){
			stopSerialCommunication();
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

	public void startTreatmentQueue(){

		 communicationThread = new Thread(new ExecuteRequestQueue());
		 if(!communicationThread.isAlive()){
				communicationThread.start();
				isStarted = true;
		 }
	}

	public void stopTreatmentQueue(){
		if(communicationThread.isAlive()){
			isStarted = false;
		}
	}


	public boolean isActive(){
		return serialCommunication.isStarted() && isStarted;
	}


	public static ProtocolResponse bytesToProtocolResponse(byte[] bytes){

		/*analyse de la réponse pour la transformation en réponse*/

		return new CocktailEndResponse();
	}



	/** */
	public static class ExecuteRequestQueue implements Runnable
	{
		public void run ()
		{
			byte[] response = new byte[250];
			try{
				while (isStarted == true)
				{
					if(!requestList.isEmpty() && serialCommunication.isStarted()){
						ProtocolRequest request = requestList.getFirst();
						// une demande existe dans le pipe
						serialCommunication.writeOutputStreamForWrite(request.toBytes());
						serialCommunication.getLock().lock();
						try{
							serialCommunication.getCond1().await();
							// une réponse a été lue
							ProtocolResponse protResponse = bytesToProtocolResponse(serialCommunication.getBufferRead());
							// ajout de la réponse dans le pipe de réponse
							responseList.push(protResponse);

							serialCommunication.getCond2().signal();
						}finally {
							serialCommunication.getLock().unlock();
						}
					}
				}
			}catch (IOException e){
				e.printStackTrace();
				logger.error(e.getMessage());
				isStarted = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				isStarted = false;
			}
		}



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

	public LinkedList<ProtocolRequest> getRequestList() {
		return requestList;
	}

	public void setRequestList(LinkedList<ProtocolRequest> requestList) {
		this.requestList = requestList;
	}

	public LinkedList<ProtocolResponse> getResponseList() {
		return responseList;
	}

	public void setResponseList(LinkedList<ProtocolResponse> responseList) {
		DistributeurCommunicationServer.responseList = responseList;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setIsStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}


}
