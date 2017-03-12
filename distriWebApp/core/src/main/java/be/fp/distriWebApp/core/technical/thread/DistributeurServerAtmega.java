package be.fp.distriWebApp.core.technical.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import be.fp.distriWebApp.core.enums.RefLovsEnum;
import be.fp.distriWebApp.core.model.dto.DistributeurDto;
import be.fp.distriWebApp.core.model.eo.Cocktail;

public class DistributeurServerAtmega implements Runnable {
	 
	
	  private Thread threadForServer; // contiendra le thread du client
	  private ServerSocket serversocket; // recevra le socket liant au client
	  private PrintWriter fluxOut; // pour gestion du flux de sortie
	  private BufferedReader fluxIn; // pour gestion du flux d'entr�e
	  private Socket laSocket; 
	  private DistributeurDto leDistri; // pour utilisation des méthodes de la classe principale
	  private boolean Etat = false;

	  
	

	  //** Constructeur : crée les �l�ments n�cessaires au dialogue avec le client **
	  public DistributeurServerAtmega(DistributeurDto distriParam) // le param s est donn�e dans BlablaServ par ss.accept()
	  {
		   leDistri = distriParam; 

	  }
	  public DistributeurServerAtmega(ServerSocket servSocket,DistributeurDto monDistri)
	  {
		  leDistri = monDistri;
		  serversocket = servSocket;
	  }	
		
	  public void startServer()
	  {
			threadForServer = new Thread(this);
			threadForServer.start();
			Etat = true;
			System.out.println("Le serveur d'Authentification avec la tablette a démarrer correctement");
			
		}
      public void stopServer()
	  {
	 	try {
	 		laSocket.close();
	 		serversocket.close();
	 		Etat = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	  }	
		
	public Socket getLaSocket() {
		return laSocket;
	}
	public void setLaSocket(Socket laSocket) {
		this.laSocket = laSocket;
	}
	
	public void run()
	  {
	/*	  String messageServer;
		  byte[] monCocktail;
		  String cocktailString;
		  Cocktail cokTODO = new Cocktail();
		  
		  System.out.println("Démarrage du thread du serveur de cocktail Atmega");
			while(RefLovsEnum.ETAT_DISTRI_ENABLE.getName().equals(leDistri.getEtat_lovs().getDomCode())  && Etat == true)
			{
				try {
					System.out.println("Attente d'une connexion");
					laSocket = serversocket.accept();
					System.out.println("Un DistrNA a été détecté");
					fluxOut = new PrintWriter(laSocket.getOutputStream());
					fluxIn = new BufferedReader(new InputStreamReader(laSocket.getInputStream()));
			//		fluxOut.print("NAAAA");
			//		fluxOut.flush();
					
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while( RefLovsEnum.ETAT_DISTRI_ENABLE.getName().equals(leDistri.getEtat_lovs().getDomCode()) && laSocket.isConnected() && Etat == true)
				{
									
					if(!leDistri.getDistricoktailtodos().isEmpty())
					{	
						System.out.println("SERVER_DISTRI ==> j'ai trouver un cocktail a faire dan le FIFO");
			
						try {
							
							leDistri.setCocktailEnCours(true);
						
							cokTODO = leDistri.getCoktailsTODO().getLast();
							
							cocktailString = leDistri.getCocktailToString(cokTODO);
												
						    monCocktail = leDistri.getCocktailToArrayByte(cokTODO);
					
						    
						    laSocket.getOutputStream().write(monCocktail);
					
						    fluxOut.flush();
						    leDistri.getCoktailsTODO().getLast().setEtat(1);
						    
							if((messageServer = fluxIn.readLine()) != null)
							{
								if(messageServer.trim().equals("CACK"))
								{
									System.out.println("DISTRI ==> " + "J'ai reçu un cocktail compris");	
									// attente de la fin du coktail
									while(leDistri.isCocktailEnCours() == true)
									{
										if((messageServer = fluxIn.readLine()) != null)
										{
											System.out.println("DISTRI ==> j'ai un cock mais jte repond :  " +  messageServer);	
											if(messageServer.equals("SCOC"))
											{
												System.out.println("DISTRI ==> je commencerais ton cocktail que �a ne m'�tonnerais pas.");	
												//TODO : pr�venir tablette
												
											}
											if(messageServer.equals("CACK"))
											{
												leDistri.getCoktailsTODO().getLast().setEtat(1);
												System.out.println("DISTRI ==> " + "J'ai re�u un cocktail compris");
											}
											if(messageServer.equals("ECOC"))
											{
												//System.out.println(messageServer);
												leDistri.getCoktailsTODO().getLast().setEtat(2);
												//System.out.println(leDistri.getCoktailsTODO().getLast().getNomCocktail() + "  // Etat cocktail : " + leDistri.getCoktailsTODO().getLast().getEtat());
												while(leDistri.isCocktailEnCours() == true)
												{
													Thread.sleep(100);
												}
												System.out.println("DISTRI ==> " + "Le cocktail est finit en fait ");									
												
											}
											else if(messageServer.equals("NECOC"))
											{
												leDistri.getCoktailsTODO().getLast().setEtat(3);
												System.out.println("DISTRI ==> " + "Hey, j'ai compris ton cockna mais je sais pas le faire");
											}
											else if(messageServer.equals("PGOB"))
											{
												// pas de gobelet ==> envoyer un message � la tablette et recommencer le cocktail
												System.out.println("DISTRI ==> " + " PAS DE GOBELET ON PREVIEN LA TAB");
												leDistri.getCoktailsTODO().getLast().setEtat(4);
												while(leDistri.getCoktailsTODO().getLast().getEtat() != 1)
												{
													Thread.sleep(100);
												}
												System.out.println("DISTRI ==> " + " LA TAB EST AU COURRANT POUR LE GOB MANQUANT");											
												
											}
											else if (messageServer.equals("TGOB"))
											{
												// gobelet retir� trop tot.. TANPIS
												leDistri.getCoktailsTODO().getLast().setEtat(5);
												System.out.println("DISTRI ==> " + "Un gobelet repris trop tot c'est comme ..." + messageServer);
												while(leDistri.isCocktailEnCours() == true)
												{
													Thread.sleep(100);
												}
												
											}
											else
											{
												leDistri.getCoktailsTODO().getLast().setEtat(3);
												System.out.println("DISTRI ==> HAYYYYYYYYYYYYYYYYYY     : " + messageServer);
												while(leDistri.isCocktailEnCours() == true)
												{
													Thread.sleep(100);
												}
											}
										}
									}
								}
								else if(messageServer.equals("NACK"))
								{
									System.out.println("DISTRI ==> " + "Hey, j'ai rien compris a ton truc");
								}
								else 
								{
									System.out.println("DISTRIB ====> MAIS qu'est-ce qui dit  :  " + messageServer) ;
								}

							}	
						
				
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}
			} */
	  }
	public ServerSocket getServerSocket() {
		return serversocket;
	}
	public void setServerSocket(ServerSocket socket) {
		this.serversocket = socket;
	}
	public boolean isEtat() {
		return Etat;
	}
	public void setEtat(boolean etat) {
		Etat = etat;
	}
}
