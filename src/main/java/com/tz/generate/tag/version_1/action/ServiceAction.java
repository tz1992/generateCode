package com.tz.generate.tag.version_1.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.Model;
import com.tz.generate.tag.version_1.model.Project;
import com.tz.generate.tag.version_1.model.Service;
import com.tz.generate.util.TemplateUtil;
import com.tz.generate.util.Utils;

import freemarker.template.Template;

@Action(action = "service",  version = "1.0")
public class ServiceAction extends BaseAction<Service> {
  protected void before(JSONObject jsonobject) {}

  public void doWrite(JSONObject json) {
    String strJson = (json == null) ? "" : json.toJSONString();
    Service service = (Service) JSON.parseObject(strJson, Service.class);

    Set<Model> models = Utils.getModels(global());
    Project project = (Project) global().get("project");
    models.forEach(model -> {
      doService(project, service, model);
      doServiceImpl(project, service, model);
    });
  }


  protected void doService(Project project, Service service, Model model) {
    Template template = getTemplate((service == null) ? null : service.getTemplate(), "Service");

    String javaPath = project.getWritepath()+"/"+project.getAppName() + "/src/main/java/"
        + project.getBasepackage().replaceAll("\\.", "/");
    String filePath = javaPath + "/service/" + model.getName() + "Service.java";

    Map<String, Object> param = new HashMap<String, Object>();
    param.putAll(global());
    param.put("model", model);
    param.put("service", service);

    TemplateUtil.fprint(template, filePath, param);
  }


  protected void doServiceImpl(Project project, Service service, Model model) {
    Template template =
        getTemplate((service == null) ? null : service.getImplTemplate(), "ServiceImpl");

    String javaPath = project.getWritepath()+"/"+project.getAppName() + "/src/main/java/"
        + project.getBasepackage().replaceAll("\\.", "/");
    String filePath = javaPath + "/service/impl/" + model.getName() + "ServiceImpl.java";

    Map<String, Object> param = new HashMap<String, Object>();
    param.putAll(global());
    param.put("model", model);
    param.put("service", service);

    TemplateUtil.fprint(template, filePath, param);
  }

  protected void after(JSONObject jsonobject) {}
}
