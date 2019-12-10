package com.tz.generate.tag.version_spring.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.Model;
import com.tz.generate.tag.version_spring.model.Project;
import com.tz.generate.tag.version_spring.model.Resource;
import com.tz.generate.util.TemplateUtil;
import com.tz.generate.util.Utils;

import freemarker.template.Template;

@Action(action = "resource",  version = "spring")
public class ResourceAction extends BaseAction<Resource> {

  @Override
  protected void before(JSONObject paramJSONObject) {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void doWrite(JSONObject json) {
    String strJson = (json == null) ? "" : json.toJSONString();
    Resource resource = (Resource)JSON.parseObject(strJson, Resource.class);
    
    Set<Model> models = Utils.getModels(global());
    Project project = (Project)global().get("project");
    models.forEach(model -> 
        eachModel(project, resource, model));
    
  }

  @Override
  protected void after(JSONObject paramJSONObject) {
    // TODO Auto-generated method stub
    
  }
  
  
  protected void eachModel(Project project, Resource resource, Model model) {
    Template template = getTemplate((resource == null) ? null : resource.getTemplate(), "Resource");
    
    String javaPath = project.getWritepath() +"/"+project.getAppName()+ "/src/main/java/" + project.getBasepackage().replaceAll("\\.", "/");
    String filePath = javaPath + "/ui/" + model.getName() + "Controller.java";
    
    Map<String, Object> param = new HashMap<String, Object>();
    param.putAll(global());
    param.put("model", model);
    param.put("resource", resource);
    
    TemplateUtil.fprint(template, filePath, param);
  }
  
 


}
