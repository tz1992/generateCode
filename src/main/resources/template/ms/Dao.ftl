
package ${project.basepackage}.dao;

import org.apache.ibatis.annotations.Mapper;

import com.fiberhome.smartms.BaseDao;
import ${project.basepackage}.entity.${model.name};


/**
 * ${model.name}Dao.
 * @author ${project.userName}
 * @timestamp ${project.timeStamp}
 */
@Mapper
public interface ${model.name}Dao extends BaseDao<${model.name}> {


}
