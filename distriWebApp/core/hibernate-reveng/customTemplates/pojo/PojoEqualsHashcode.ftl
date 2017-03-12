	public boolean equals(Object obj) 
	{
		if(obj instanceof ${pojo.getDeclarationName()} == false)
		{
			return false;
		}
		if(this == obj)
		{
			return true;
		}
		${pojo.getDeclarationName()} rhs = (${pojo.getDeclarationName()}) obj;
		return new EqualsBuilder()
		<#if pojo.hasIdentifierProperty()>
			<#foreach property in pojo.getAllPropertiesIterator()>
				<#if property.equals(clazz.identifierProperty)>
				 .append("${property.getName()}", rhs.${property.getName()})
				</#if>
			</#foreach>
		<#else>
			<#foreach property in pojo.getAllPropertiesIterator()>
						.append("${property.getName()}", rhs.${property.getName()})
			</#foreach>
		</#if>
			.isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder(17, 37)
		<#if pojo.hasIdentifierProperty()>
			<#foreach property in pojo.getAllPropertiesIterator()>
				<#if property.equals(clazz.identifierProperty)>
				 .append(${property.getName()})
				</#if>
			</#foreach>
		<#else>
			<#foreach property in pojo.getAllPropertiesIterator()>
						.append(${property.getName()})
			</#foreach>
		</#if>
			.toHashCode();
	}