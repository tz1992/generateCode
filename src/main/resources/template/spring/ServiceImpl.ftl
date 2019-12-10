
package ${project.basepackage}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${project.basepackage}.dao.${model.name}Dao;
import ${project.basepackage}.service.${model.name}Service;


/**
 * ${model.name}.
 * @author ${project.userName}
 * @timestamp ${project.timeStamp}
 */
@Service
public class ${model.name}ServiceImpl implements ${model.name}Service {

	@Autowired
	private ${model.name}Dao dao;


}
