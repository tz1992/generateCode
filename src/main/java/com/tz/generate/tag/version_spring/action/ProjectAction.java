package com.tz.generate.tag.version_spring.action;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.version_spring.model.Project;
import org.springframework.beans.factory.annotation.Autowired;



@Action(action = "project", version = "spring")
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
        writepath +"/"+project.getAppName()+ "/src/main/java/" + project.getBasepackage().replaceAll("\\.", "/");
    String resourcesPath = writepath +"/"+project.getAppName()+ "/src/main/resources/"
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

    goProperties(json);


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



  protected void goProperties(JSONObject json) {
    if (json.getBooleanValue("noproperties")) {
      return;
    }
    JSONObject properties = (JSONObject) json.get("properties");

    BaseAction action = getBean("properties");
    if (action != null) action.write(properties);

  }

 
}
