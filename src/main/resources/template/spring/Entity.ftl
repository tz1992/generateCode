
package ${project.basepackage}.entity;

<#if imports??>
 <#list imports as imp>
import ${imp};
 </#list>
</#if>

/**
 * ${model.name}.
 * @author ${project.userName}
 * @timestamp ${project.timeStamp}
 */
public class ${model.name} implements Serializable{

<#if columns??>
 <#list columns as p>
 	<#if p.columnComment!="">
 	// ${p.columnComment}
 	</#if>
	private ${p.columnClassName} ${p.propertyName};
 </#list>
 
 <#list columns as p>
 <#if (p.columnClassName!"")=="Boolean"><#assign methodName = "is"/><#else><#assign methodName = "get"/></#if>
	/**
	* ${methodName}${p.propertyName?cap_first}.
	* return ${p.columnClassName}.
	*/
	public ${p.columnClassName} ${methodName}${p.propertyName?cap_first}(){
		return ${p.propertyName}; 
	}
	
	/**
	* set${p.propertyName?cap_first}.
	* @param ${p.columnClassName}.
	*/
	public void set${p.propertyName?cap_first}(${p.columnClassName} ${p.propertyName}){
		this.${p.propertyName} = ${p.propertyName}; 
	}
	
 </#list>
</#if>
}