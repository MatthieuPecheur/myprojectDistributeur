	
<#foreach property in pojo.getAllPropertiesIterator()>
	public static final String ${property.getName()?upper_case} = "${property.getName()}";
	<#--${pojo.getJavaTypeName(property, jdk5)}-->
</#foreach>
