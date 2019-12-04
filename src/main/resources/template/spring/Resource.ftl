
package ${project.basepackage}.ui;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ${project.basepackage}.service.${model.name}Service;

/**
 * ${model.name}.
 * @author ${project.userName}
 * @timestamp ${project.timeStamp}
 */
@RestController
public class ${model.name}Controller {
	@Autowired
	private ${model.name}Service service;

	

}
