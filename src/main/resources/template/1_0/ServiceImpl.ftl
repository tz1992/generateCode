/*
* ${project.head}
*/
package ${project.basepackage}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiberhome.smartms.service.BaseServiceImpl;
import com.fiberhome.smartms.annotation.Cache;
import ${project.basepackage}.service.${model.name}Service;
import ${project.basepackage}.dao.${model.name}Dao;
import ${project.basepackage}.entity.${model.name};


/**
 * ${model.name}.
 * @author ${project.userName}
 * @timestamp ${project.timeStamp}
 */
@Service
@Cache(name = "Smart:${model.name?upper_case}")
public class ${model.name}ServiceImpl extends BaseServiceImpl<${model.name}> implements ${model.name}Service {

	@Autowired
	private ${model.name}Dao dao;

	protected ${model.name}Dao getDao() {
		return dao;
	}
	
	////

}
