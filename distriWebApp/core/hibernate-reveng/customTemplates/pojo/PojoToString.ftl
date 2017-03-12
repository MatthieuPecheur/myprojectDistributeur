	/**
   * toString
   * @return String
   */
  public String toString()
  {
  	return new ToStringBuilder(this)
  		.append("\n")
<#if pojo.hasIdentifierProperty()>
	<#foreach property in pojo.getAllPropertiesIterator()>
		<#if property.equals(clazz.identifierProperty)>
		 .append("${property.getName()}", ${property.getName()})
		</#if>
	</#foreach>
<#else>
	<#foreach property in pojo.getAllPropertiesIterator()>
				.append("${property.getName()}", ${property.getName()})
	</#foreach>
</#if>
			.append("\n")
			.toString();
	}
