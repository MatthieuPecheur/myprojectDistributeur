<#if ejb3>
<#if pojo.hasIdentifierProperty()>
<#if field.equals(clazz.identifierProperty)>
 ${pojo.generateAnnIdGenerator()}
<#if pojo.generateAnnIdGenerator() == '' >
@${pojo.importType("javax.persistence.SequenceGenerator")}(name = "sequence", sequenceName = "seq_${clazz.table.name}", allocationSize = 1, initialValue = 1)
@${pojo.importType("javax.persistence.GeneratedValue")}(strategy = ${pojo.importType("javax.persistence.GenerationType")}.SEQUENCE, generator = "sequence")
</#if>
<#-- if this is the id field (getter)-->
<#-- explicitly set the column name for this field-->
</#if>
</#if>

<#if c2h.isOneToOne(field)>
${pojo.generateOneToOneAnnotation(field, cfg)}
<#elseif c2h.isManyToOne(field)>
${pojo.generateManyToOneAnnotation(field)}
<#--TODO support optional and targetEntity-->
${pojo.generateJoinColumnsAnnotation(field, cfg)}
<#elseif c2h.isCollection(field)>
${pojo.generateCollectionAnnotation(field, cfg)}
<#else>
${pojo.generateBasicAnnotation(field)}
${pojo.generateAnnColumnAnnotation(field)}
</#if>
<#if pojo.getJavaTypeName(field, jdk5)="Clob">
@${pojo.importType("org.hibernate.annotations.Type")}(type="text")
@${pojo.importType("javax.persistence.Lob")}
</#if>
</#if>