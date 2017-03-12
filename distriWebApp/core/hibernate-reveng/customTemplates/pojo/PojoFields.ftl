<#-- // Fields -->

<#foreach field in pojo.getAllPropertiesIterator()><#if pojo.getMetaAttribAsBool(field, "gen-property", true)> <#if pojo.hasMetaAttribute(field, "field-description")>    /**
     ${pojo.getFieldJavaDoc(field, 0)}
     */
 </#if> <#include "Ejb3FieldGetAnnotation.ftl"/>
     ${pojo.getFieldModifiers(field)}
     <#if pojo.getJavaTypeName(field, jdk5)="Clob">
		String
	<#else>
		${pojo.getJavaTypeName(field, jdk5)}
	</#if>
	${field.name}<#if pojo.hasFieldInitializor(field, jdk5)> = ${pojo.getFieldInitialization(field, jdk5)}</#if>;
</#if>
</#foreach>
