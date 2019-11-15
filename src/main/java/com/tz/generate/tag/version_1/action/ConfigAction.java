package com.tz.generate.tag.version_1.action;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.Db;
import com.tz.generate.tag.version_1.model.Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;



@Action(action = "config", version = "1.0")
public class ConfigAction extends BaseAction<Project> {
  private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Autowired
  public DruidDataSource dataSource;

  protected void before(JSONObject jsonobject) {
    Project project = (Project) JSON.parseObject(jsonobject.toJSONString(), Project.class);

    String currentUser = System.getProperty("user.name");
    if (StringUtils.isEmpty(project.getUserName())) {
      project.setUserName(currentUser);
    }
    if (StringUtils.isEmpty(project.getAbbreviation())) {
      String[] splitName = project.getName().split("-");
      try {
        project.setAbbreviation(splitName[splitName.length - 1]);
      } catch (IndexOutOfBoundsException iobe) {
        throw new RuntimeException("name must be 3 paragraph like {}-{}-{} ");
      }
    }
    project.setTimeStamp(df.format(new Date()));

    global().put("project", project);


    checkPath(project.getWritepath(), project.isClear());

    Db db = project.getDb();

    this.dataSource.setDriverClassName(db.getDriverClassName());
    this.dataSource.setUrl(db.getUrl());
    this.dataSource.setUsername(db.getUsername());
    this.dataSource.setPassword(db.getPassword());
    global().put("db", db);
  }



  public void doWrite(JSONObject json) {}



  public void after(JSONObject json) {
    if (!goModel(json)) {
      return;
    }

    goEntity(json);

    goDao(json);

    goService(json);

    goResource(json);
  }



  protected JSONArray getModelArray(JSONObject json) {
    return (JSONArray) json.get("model");
  }


  protected boolean goModel(JSONObject json) {
    if (json.getBooleanValue("nomodel") || json.getBooleanValue("nodb")) {
      return false;
    }

    JSONArray modelArray = getModelArray(json);
    if (modelArray == null) {
      return false;
    }

    BaseAction action = getBean("model");
    if (action == null) {
      return false;
    }

    modelArray.stream().forEach(entity -> action.write((JSONObject) entity));

    return true;
  }

  protected void goEntity(JSONObject json) {
    if (json.getBooleanValue("noentity")) {
      return;
    }
    JSONObject entity = (JSONObject) json.get("entity");

    BaseAction action = getBean("entity");
    if (action != null) action.write(entity);

  }

  protected void goDao(JSONObject json) {
    if (json.getBooleanValue("nodao")) {
      return;
    }

    JSONObject dao = (JSONObject) json.get("dao");

    BaseAction action = getBean("dao");
    if (action != null) action.write(dao);

  }

  protected void goService(JSONObject json) {
    if (json.getBooleanValue("noservice")) {
      return;
    }

    JSONObject service = (JSONObject) json.get("service");

    BaseAction action = getBean("service");
    if (action != null) action.write(service);

  }

  protected void goResource(JSONObject json) {
    if (json.getBooleanValue("noresource")) {
      return;
    }

    JSONObject resource = (JSONObject) json.get("resource");

    BaseAction action = getBean("resource");
    if (action != null) action.write(resource);
  }
}
