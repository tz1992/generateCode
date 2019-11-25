package com.tz.generate.tag.version_1.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.version_1.model.Docker;
import com.tz.generate.tag.version_1.model.Project;
import com.tz.generate.util.TemplateUtil;

import freemarker.template.Template;
import java.util.HashMap;
import java.util.Map;



@Action(action = "docker",  version = "1.0")
public class DockerAction extends BaseAction<Docker> {
  protected void before(JSONObject jsonobject) {}

  public void doWrite(JSONObject json) {
    Docker docker = null;
    if (json != null) {
      docker = (Docker) JSON.parseObject(json.toJSONString(), Docker.class);
    }

    Template template = getTemplate((docker == null) ? null : docker.getTemplate(), "Dockerfile");

    Project project = (Project) global().get("project");


    Map<String, Object> param = new HashMap<String, Object>();
    param.putAll(global());
    param.put("docker", docker);

    String filePath = project.getWritepath() +"/"+project.getAppName()+ "/Dockerfile";

    TemplateUtil.fprint(template, filePath, param);
  }

  protected void after(JSONObject jsonobject) {}
}
