<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${project.basepackage}.dao.${model.name}Dao">
	<!-- <cache /> -->

	<!-- ///////////////////////////基础接口定义///////////////////////////////// -->
	
	<select id="getById" resultType="${project.basepackage}.entity.${model.name}">
		SELECT
		T.*
		FROM ${model.table} T
		WHERE T.ID = ${r"#{id}"} 
			and T.TENANT_ID = ${r"#{_tenantId}"}
	</select>
	
	
	<select id="getByIds" resultType="${project.basepackage}.entity.${model.name}">
		SELECT
		T.*
		FROM ${model.table} T
		WHERE T.ID in
		<foreach item="id" collection="ids" open="(" separator="," close=")">
		  ${r"#{id}"}
		</foreach>
			and T.TENANT_ID = ${r"#{_tenantId}"}
	</select>

	<select id="getCount" resultType="int">
		SELECT
			count(1)
		FROM ${model.table} T
		WHERE T.TENANT_ID = ${r"#{_tenantId}"}
		<filter open="AND (" close=")" />
	</select>

	<select id="selectAll" resultType="${project.basepackage}.entity.${model.name}">
		SELECT
		T.*
		FROM ${model.table} T
		WHERE T.TENANT_ID = ${r"#{_tenantId}"}
		<filter open="AND (" close=")" />
	</select>

	<select id="select" pageable="true" resultType="${project.basepackage}.entity.${model.name}">
		SELECT T.* FROM ${model.table} T
		WHERE T.TENANT_ID = ${r"#{_tenantId}"}
		<filter open="AND (" close=")" />
	</select>
	
	<!-- 插入语句  -->
	<sql id="base_insert_sql">
		INSERT INTO ${model.table} (
		    <#list model.column as column><#lt />
            ${"\t\t\t"}${column.columnName}${",\n"}<#t>
		    </#list><#lt />
		  ) VALUES (
			<#list model.column as column><#lt>
			 ${"\t\t\t"}${r"#{"}${column.propertyName},jdbcType=${column.columnTypeName}${r"}"}${",\n"}<#t>
			</#list>
		  )
	</sql>

	<insert id="insert" parameterType="${project.basepackage}.entity.${model.name}" useGeneratedKeys="true" keyColumn="ID" keyProperty="id">
	<#if project.driverClassName?index_of("oracle")!=-1>
		<selectKey resultType="long" keyProperty="id" order="BEFORE" databaseId="oracle">
        	SELECT ${model.table?keep_before_last("_")}_S.nextval AS value FROM dual  
		</selectKey>
	<#elseif project.driverClassName?index_of("mysql")!=-1>
		<selectKey keyProperty="id" resultType="long">  
            select LAST_INSERT_ID()  
        </selectKey> 
	<#else>
		
	</#if>
		<include refid="base_insert_sql" />
	</insert>
	
	<update id="update" parameterType="${project.basepackage}.entity.${model.name}">
		UPDATE ${model.table}
		<set>  
	  	  <#list model.column as column><#lt>
	  	  <if test="${column.propertyName} != null">
			${column.columnName} = ${r"#{"}${column.propertyName},jdbcType=${column.columnTypeName}${r"}"},
		  ${(column_index%1==0)?string("\t\t  ","")}</if>${(column_index%1==0)?string("\n","")}<#t>
		  </#list>
		  REVISION = ${r"#{"}revisionNext,jdbcType=INTEGER${r"}"},
          LAST_UPDATE_USER_ID = ${r"#{"}currentUserId,jdbcType=NUMERIC${r"}"},
          LAST_UPDATE_DATE = ${r"#{"}_currentDate${r"}"},
		</set>
		WHERE ID = ${r"#{id,jdbcType=NUMERIC}"}
		and TENANT_ID = ${r"#{_tenantId}"}
	</update>

	<delete id="deleteById">
		DELETE FROM ${model.table}
		WHERE ID = ${r"#{id}"}
		and TENANT_ID = ${r"#{_tenantId}"}
	</delete>
	
	<delete id="deleteByIds">
		DELETE FROM ${model.table}
		WHERE ID in
	    <foreach item="id" collection="ids" open="(" separator="," close=")">
	     ${r"#{id}"}
	    </foreach>
	    and TENANT_ID = ${r"#{_tenantId}"}
	</delete>

	<!-- //////////////////////////////////////////////////////////// -->
 
</mapper>

