/*
* ${project.head}
*/
package ${project.basepackage}.service;

import org.springframework.transaction.annotation.Transactional;

import com.fiberhome.smartms.BaseService;
import ${project.basepackage}.entity.${model.name};

/**
 * ${model.name}Service.
 * @author ${project.userName}
 * @timestamp ${project.timeStamp}
 */
@Transactional()
public interface ${model.name}Service extends BaseService<${model.name}> {

	
}
