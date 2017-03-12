package be.fp.distriWebApp.core.enums;

public enum RefLovsEnum {
	ETAT_DISTRI_ENABLE("");
	
	private final String name;
	
	RefLovsEnum(String name) {
		this.name = name;
	}
	
	static public RefLovsEnum getEnumByName(String name){
		for(RefLovsEnum current : RefLovsEnum.values()){
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
