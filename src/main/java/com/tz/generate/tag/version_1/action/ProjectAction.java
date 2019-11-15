package com.tz.generate.tag.version_1.action;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.version_1.model.Project;
import org.springframework.beans.factory.annotation.Autowired;



@Action(action = "project", version = "1.0")
public class ProjectAction extends ConfigAction {
  @Autowired
  public DruidDataSource dataSource;

  protected void before(JSONObject jsonobject) {
    super.before(jsonobject);
  }



  public void doWrite(JSONObject json) {
    Project project = (Project) JSON.parseObject(json.toJSONString(), Project.class);

    String writepath = project.getWritepath();
    String javaPath =
        writepath + "/src/main/java/" + project.getBasepackage().replaceAll("\\.", "/");
    String resourcesPath = writepath + "/src/main/resources/"
        + project.getBasepackage().replaceAll("\\.", "/") + "/dao";

//    String testJavaPath = writepath + "/src/test/java/";
//    String testResourcesPath = writepath + "/src/test/resources/";

    checkPath(javaPath, false);
    checkPath(javaPath + "/dao", false);
    checkPath(javaPath + "/entity", false);
    checkPath(javaPath + "/service/impl", false);
    checkPath(javaPath + "/ui", false);

    checkPath(resourcesPath, false);
//    checkPath(testJavaPath, false);
//    checkPath(testResourcesPath, false);
  }



  public void after(JSONObject json) {
    goMaven(json);

    goDocker(json);

    goGitignore(json);

    goProperties(json);

    goTest(json);

    if (!goModel(json)) {
      return;
    }

    goEntity(json);

    goDao(json);

    goService(json);

    goResource(json);
  }


  protected void goMaven(JSONObject json) {
    if (json.getBooleanValue("nomaven")) {
      return;
    }
    JSONObject maven = (JSONObject) json.get("maven");

    BaseAction action = getBean("maven");
    if (action != null) action.write(maven);
  }

  protected void goDocker(JSONObject json) {
    if (json.getBooleanValue("nodocker")) {
      return;
    }
    JSONObject docker = (JSONObject) json.get("docker");

    BaseAction action = getBean("docker");
    if (action != null) action.write(docker);
  }

  protected void goGitignore(JSONObject json) {
    if (json.getBooleanValue("nogitignore")) {
      return;
    }
    JSONObject gitignore = (JSONObject) json.get("gitignore");

    BaseAction action = getBean("gitignore");
    if (action != null) action.write(gitignore);
  }

  protected void goProperties(JSONObject json) {
    if (json.getBooleanValue("noproperties")) {
      return;
    }
    JSONObject properties = (JSONObject) json.get("properties");

    BaseAction action = getBean("properties");
    if (action != null) action.write(properties);

  }

  protected void goTest(JSONObject json) {
    if (json.getBooleanValue("notest")) {
      return;
    }
    JSONObject test = (JSONObject) json.get("test");

    BaseAction action = getBean("test");
    if (action != null) action.write(test);
  }
}
