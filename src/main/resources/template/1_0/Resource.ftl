/*
* ${project.head}
*/
package ${project.basepackage}.ui;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;

import com.fiberhome.smartms.ui.BaseResource;
import com.fiberhome.smartms.annotation.Resource;
import ${project.basepackage}.entity.${model.name};
import ${project.basepackage}.service.${model.name}Service;

/**
 * ${model.name}.
 * @author ${project.userName}
 * @timestamp ${project.timeStamp}
 */
@Path("/${project.abbreviation}/${model.name?lower_case}")
@Api(tags="${project.abbreviation}/${model.name?lower_case}")
@Resource(code = ${model.code},  desc = "${model.desc}")
public class ${model.name}Resource extends BaseResource<${model.name}> {
	@Autowired
	private ${model.name}Service service;

	protected ${model.name}Service getService() {
		return service;
	}
	

}
