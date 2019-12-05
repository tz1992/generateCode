package com.tz.generate.tag.version_spring.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.version_spring.model.Maven;
import com.tz.generate.tag.version_spring.model.Project;
import com.tz.generate.util.TemplateUtil;

import freemarker.template.Template;
import java.util.HashMap;
import java.util.Map;



@Action(action = "maven", version = "spring")
public class MavenAction extends BaseAction<Maven> {
  protected void before(JSONObject jsonobject) {}

  public void doWrite(JSONObject json) {
    Maven maven = null;
    if (json != null) {
      maven = (Maven) JSON.parseObject(json.toJSONString(), Maven.class);
    }

    Template template = getTemplate((maven == null) ? null : maven.getTemplate(), "pom");

    Project project = (Project) global().get("project");


    Map<String, Object> param = new HashMap<String, Object>();
    param.putAll(global());
    param.put("maven", maven);

    String filePath = project.getWritepath() +"/"+project.getAppName()+ "/pom.xml";

    TemplateUtil.fprint(template, filePath, param);
  }

  protected void after(JSONObject jsonobject) {}
}

