package be.fp.distiWebApp.webapp.enums;

public enum PrettyPatterns {
	home,
	login,
	homeAdmin,
	distributeurDetail;
	
	private PrettyPatterns(){
		
	}
	public String toPrettyPattern(){
		return "pretty:"+this.toString(); 
	}
}
