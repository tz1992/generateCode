package com.tz.generate.tag.version_ms.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.Column;
import com.tz.generate.tag.Model;
import com.tz.generate.tag.version_ms.model.Entity;
import com.tz.generate.tag.version_ms.model.Project;
import com.tz.generate.util.TemplateUtil;
import com.tz.generate.util.Utils;

import freemarker.template.Template;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;









@Action(action = "entity",  version = "ms")
public class EntityAction
  extends BaseAction<Entity>
{
  protected void before(JSONObject jsonobject) {}
  
  public void doWrite(JSONObject json) {
    String strJson = (json == null) ? "" : json.toJSONString();
    Entity entity = (Entity)JSON.parseObject(strJson, Entity.class);
    
    Set<Model> models = Utils.getModels(global());
    Project project = (Project)global().get("project");
    models.forEach(model -> eachModel(project, entity, model));
  }

  


  
  protected void eachModel(Project project, Entity entity, Model model) {
    Template template = getTemplate((entity == null) ? null : entity.getTemplate(), "Entity");


    
    Set<String> imports = new HashSet<String>();    
    List<Column> filteredColumn = new ArrayList<Column>();
    for (Column column : model.getColumn()) {
      Column cloneColumn = new Column();
      BeanUtils.copyProperties(column, cloneColumn);
      
      String ColumnClassName = column.getColumnClassName();
      if (StringUtils.isEmpty(ColumnClassName))
        continue; 
      if (ColumnClassName.equals("java.sql.Timestamp")) {
        ColumnClassName = "java.util.Date";
      }
      if (!ColumnClassName.startsWith("java.lang.")) imports.add(ColumnClassName);
      
      cloneColumn.setPropertyName(Utils.getParamName(cloneColumn.getColumnName()));
      cloneColumn.setColumnClassName(Utils.getSimpleClassName(ColumnClassName));
      filteredColumn.add(cloneColumn);
    } 
    
    Map<String, Object> param = new HashMap<String, Object>();
    param.putAll(global());
    param.put("model", model);
    param.put("imports", imports);
//    param.put("columns", filteredColumn);
    
    String javaPath = project.getWritepath() +"/"+project.getAppName()+ "/src/main/java/" + project.getBasepackage().replaceAll("\\.", "/");
    
    String filePath = javaPath + "/entity/" + model.getName() + ".java";
    
    TemplateUtil.fprint(template, filePath, param);
  }
  
  protected void after(JSONObject jsonobject) {}
}
