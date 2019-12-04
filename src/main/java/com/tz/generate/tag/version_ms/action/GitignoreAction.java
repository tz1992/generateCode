package com.tz.generate.tag.version_ms.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.version_ms.model.Gitignore;
import com.tz.generate.tag.version_ms.model.Project;
import com.tz.generate.util.TemplateUtil;

import freemarker.template.Template;
import java.util.HashMap;
import java.util.Map;



@Action(action = "gitignore", version = "ms")
public class GitignoreAction extends BaseAction<Gitignore> {
  protected void before(JSONObject jsonobject) {}

  public void doWrite(JSONObject json) {
    Gitignore gitignore = null;
    if (json != null) {
      gitignore = (Gitignore) JSON.parseObject(json.toJSONString(), Gitignore.class);
    }

    Template template =
        getTemplate((gitignore == null) ? null : gitignore.getTemplate(), "gitignore");

    Project project = (Project) global().get("project");


    Map<String, Object> param = new HashMap<String, Object>();
    param.putAll(global());
    param.put("gitignore", gitignore);

    String filePath = project.getWritepath()+"/"+project.getAppName() + "/.gitignore";

    TemplateUtil.fprint(template, filePath, param);
  }

  protected void after(JSONObject jsonobject) {}
}
