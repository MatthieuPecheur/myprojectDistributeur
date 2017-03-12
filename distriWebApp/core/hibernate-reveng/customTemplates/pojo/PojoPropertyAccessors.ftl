<#-- // Property accessors -->
<#foreach property in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>
 <#if pojo.hasFieldJavaDoc(property)>
    /**
     * ${pojo.getFieldJavaDoc(property, 4)}
     */
</#if>
    ${pojo.getPropertyGetModifiers(property)}
    <#if pojo.getJavaTypeName(property, jdk5)="Clob">
		String
    <#else>
		${pojo.getJavaTypeName(property, jdk5)}
	</#if>
	${pojo.getGetterSignature(property)}() {
        return ${property.name};
    }

    ${pojo.getPropertySetModifiers(property)} void set${pojo.getPropertyName(property)}(
	<#if pojo.getJavaTypeName(property, jdk5)="Clob">
		String
    <#else>
		${pojo.getJavaTypeName(property, jdk5)}
	</#if>
	${property.name}) {
        this.${property.name} = ${property.name};
    }
</#if>
</#foreach>
