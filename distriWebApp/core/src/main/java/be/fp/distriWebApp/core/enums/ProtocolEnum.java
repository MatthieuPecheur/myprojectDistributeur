package be.fp.distriWebApp.core.enums;

public enum ProtocolEnum {
	ETAT_GENERAL_REQUEST("QDIS"),
	ETAT_GENERAL_RESPONSE("RDIS"),

	ETAT_TECHNIQUE_REQUEST("QTIS"),
	ETAT_TECHNIQUE_RESPONSE("RTIS"),

	AUTHEN_RFID_REQUEST("ARFI"),
	AUTHEN_RFID_RESPONSE_READ("RRFI"),
	AUTHEN_RFID_RESPONSE_ERROR("ERFI"),

	LED_AMBIANCE_REQUEST("LEDA"),
	LED_AMBIANCE_RESPONSE("OKDA"),

	LED_TABLE_REQUEST("MLED"),
	LED_TABLE_RESPONSE_OK("OLED"),
	LED_TABLE_RESPONSE_NOK("KLED"),

	COCKTAIL_REQUEST("COCK"),
	COCKTAIL_RESPONSE_ACK("CACK"),
	COCKTAIL_RESPONSE_START("SOCK"),
	COCKTAIL_RESPONSE_ERROR("NOCK"),
	COCKTAIL_RESPONSE_END("EOCK"),

	GOBELET_RESPONSE_NO("PGOB"),
	GOBELET_RESPONSE_TO_EARLY("TGOB"),


	INCONNUE_RESPONSE("UERR");

	private final String name;

	ProtocolEnum(String name) {
		this.name = name;
	}
	
	static public ProtocolEnum getEnumByName(String name){
		for(ProtocolEnum current : ProtocolEnum.values()){
			if(name.equals(current.getName())){
				return current;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}
	
	
}
