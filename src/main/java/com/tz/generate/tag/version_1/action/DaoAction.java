package com.tz.generate.tag.version_1.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.Column;
import com.tz.generate.tag.Model;
import com.tz.generate.tag.version_1.model.Dao;
import com.tz.generate.tag.version_1.model.Project;
import com.tz.generate.util.TemplateUtil;
import com.tz.generate.util.Utils;

import freemarker.template.Template;

@Action(action = "dao", version = "1.0")
public class DaoAction extends BaseAction<Dao> {
  protected void before(JSONObject jsonobject) {}

  public void doWrite(JSONObject json) {
    String strJson = (json == null) ? "" : json.toJSONString();
    Dao dao = (Dao) JSON.parseObject(strJson, Dao.class);

    Set<Model> models = Utils.getModels(global());
    Project project = (Project) global().get("project");
    models.forEach(model -> {
      doDao(project, dao, model);
      doDaoXml(project, dao, model);
    });
  }

  protected void doDao(Project project, Dao dao, Model model) {
    Template template = getTemplate((dao == null) ? null : dao.getTemplate(), "Dao");

    String javaPath = project.getWritepath() +"/"+project.getAppName()+ "/src/main/java/"
        + project.getBasepackage().replaceAll("\\.", "/");
    String filePath = javaPath + "/dao/" + model.getName() + "Dao.java";

    Map<String, Object> param = new HashMap<String, Object>();
    param.putAll(global());
    param.put("model", model);
//    param.put("dao", dao);

    TemplateUtil.fprint(template, filePath, param);
  }

  protected void doDaoXml(Project project, Dao dao, Model model) {
    Template template = getTemplate((dao == null) ? null : dao.getXmlTemplate(), "DaoXml");

    String resourcesPath = project.getWritepath()+"/"+project.getAppName() + "/src/main/resources/"
        + project.getBasepackage().replaceAll("\\.", "/") + "/dao";
    String filePath = resourcesPath + "/" + model.getName() + "Dao.xml";

    for (Column column : model.getColumn()) {
      column.setPropertyName(Utils.getParamName(column.getColumnName()));
    }

    Map<String, Object> param = new HashMap<String, Object>();
    param.putAll(global());
    param.put("model", model);
//    param.put("dao", dao);

    TemplateUtil.fprint(template, filePath, param);
  }

  protected void after(JSONObject jsonobject) {}
}
