package be.fp.distriWebApp.core.technical;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.model.dto.DistributeurDto;
import be.fp.distriWebApp.core.model.eo.Cocktail;
import be.fp.distriWebApp.core.model.eo.Pompe;
import be.fp.distriWebApp.core.technical.protocol.ProtocolRequest;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;
import be.fp.distriWebApp.core.technical.protocol.response.*;
import be.fp.distriWebApp.core.technical.thread.DistributeurServerAtmega;
import com.ctc.wstx.util.StringUtil;
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
		responseList = new LinkedList<ProtocolResponse>();
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

	public static ProtocolResponse stringToProtocolResponse(String lineReaded){

		UnkownResponse unResponse = new UnkownResponse();
		if(lineReaded != null && lineReaded.length() > 3){

			/*analyse de la réponse pour la transformation en réponse*/
			String code = lineReaded.substring(0,3);

			if(ProtocolEnum.AUTHEN_RFID_RESPONSE_ERROR.getName().equals(code)){
				AuthentificationRfidErrorResponse authenError = new AuthentificationRfidErrorResponse();
				return authenError;
			}else if(ProtocolEnum.AUTHEN_RFID_RESPONSE_READ.getName().equals(code)) {
				AuthentificationRfidReadResponse authenRead = new AuthentificationRfidReadResponse();
				return authenRead;
			}else if(ProtocolEnum.COCKTAIL_RESPONSE_ACK.getName().equals(code)){
				CocktailAckResponse cockAckRes = new CocktailAckResponse();
				return cockAckRes;
			}else if(ProtocolEnum.COCKTAIL_RESPONSE_END.getName().equals(code)){
				CocktailEndResponse cockEnd = new CocktailEndResponse();
				return cockEnd;
			}else if(ProtocolEnum.COCKTAIL_RESPONSE_ERROR.getName().equals(code)){
				CocktailErrorResponse cockErr = new CocktailErrorResponse();
				return cockErr;
			}else if(ProtocolEnum.COCKTAIL_RESPONSE_START.getName().equals(code)){
				CocktailStartResponse cockStart = new CocktailStartResponse();
				return cockStart;
			}else if(ProtocolEnum.ETAT_GENERAL_RESPONSE.getName().equals(code)){
				EtatGeneralResponse etatGen = new EtatGeneralResponse();
				return etatGen;
			}else if(ProtocolEnum.ETAT_TECHNIQUE_RESPONSE.getName().equals(code)){
				EtatTechniqueResponse etatTech = new EtatTechniqueResponse();
				return etatTech;
			}else if(ProtocolEnum.GOBELET_RESPONSE_NO.getName().equals(code)){
				GobeletEmptyResponse gobeletEmp = new GobeletEmptyResponse();
				return gobeletEmp;
			}else if(ProtocolEnum.GOBELET_RESPONSE_TO_EARLY.getName().equals(code)){
				GobeletToEarlyResponse gobeletToEarly = new GobeletToEarlyResponse();
				return gobeletToEarly;
			}else if(ProtocolEnum.LED_AMBIANCE_RESPONSE.getName().equals(code)){
				LedAmbianceResponse ledAmb = new LedAmbianceResponse();
				return ledAmb;
			}else if(ProtocolEnum.LED_TABLE_RESPONSE_OK.getName().equals(code)){
				LedMapOkResponse ledMapOk = new LedMapOkResponse();
				return ledMapOk;
			}else if(ProtocolEnum.LED_TABLE_RESPONSE_NOK.getName().equals(code)){
				LedMapNokResponse ledMapNok = new LedMapNokResponse();
				return ledMapNok;
			}
		}
		return unResponse;
	}

	/** */
	public static class ExecuteRequestQueue implements Runnable
	{
		public void run ()
		{
			try{
				while (isStarted == true)
				{
					if(!requestList.isEmpty() && serialCommunication.isStarted()){
						ProtocolRequest request = requestList.getFirst();
						// une demande existe dans le pipe
						serialCommunication.getLock().lock();
						serialCommunication.writeOutputStreamForWrite(request.toBytes());						
						try{							
							serialCommunication.getCond1().await();

							// une réponse a été lue
							//ProtocolResponse protResponse = bytesToProtocolResponse(serialCommunication.getBufferRead());
							ProtocolResponse protResponse = stringToProtocolResponse(serialCommunication.getLineRead());

							// ajout de la réponse dans le pipe de réponse
							if(protResponse != null){
								responseList.push(protResponse);
							}
							requestList.removeFirst();

							//serialCommunication.getCond2().signal();
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
