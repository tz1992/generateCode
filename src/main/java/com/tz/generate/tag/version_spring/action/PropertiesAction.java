package com.tz.generate.tag.version_spring.action;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.version_spring.model.Project;
import com.tz.generate.util.TemplateUtil;

import freemarker.template.Template;

@Action(action = "properties", version = "1.0")
public class PropertiesAction extends BaseAction<Project> {
  protected void before(JSONObject jsonobject) {}

  protected void doWrite(JSONObject jsonobject) {
    Project project = (Project) global().get("project");

    Template applicationProperties =
        getTemplate((project == null) ? null : project.getTemplate(), "applicationProperties");
    Template bootstrapProperties =
        getTemplate((project == null) ? null : project.getTemplate(), "bootstrapProperties");
    Template application =
        getTemplate((project == null) ? null : project.getTemplate(), "Application");

    Map<String, Object> param = new HashMap<String, Object>();
    param.putAll(global());

    String filePath = project.getWritepath() +"/"+project.getAppName()+ "/src/main/resources/application.properties";
    TemplateUtil.fprint(applicationProperties, filePath, param);

    filePath = project.getWritepath()+"/"+project.getAppName() + "/src/main/resources/bootstrap.properties";
    TemplateUtil.fprint(bootstrapProperties, filePath, param);

    String javaPath = project.getWritepath() +"/"+project.getAppName()+ "/src/main/java/"
        + project.getBasepackage().replaceAll("\\.", "/");
    filePath = javaPath + "/TestApplication.java";
    TemplateUtil.fprint(application, filePath, param);
  }

  protected void after(JSONObject jsonobject) {}
}

