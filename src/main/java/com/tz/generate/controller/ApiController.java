package com.tz.generate.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.tz.generate.Generate;

@RestController
public class ApiController {
  @PostMapping("/")
  public String generateCode(@RequestBody Map<String, Object> json) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("appName", json.get("appName"));
    jsonObject.put("action", "project");
    jsonObject.put("basepackage", json.get("basepackage"));
    jsonObject.put("writepath", json.get("writepath"));
    jsonObject.put("name", json.get("name"));
    jsonObject.put("db", json.get("db"));
    jsonObject.put("entity", json.get("entity"));
    jsonObject.put("dao", json.get("dao"));
    String tableName = (String) json.get("tableName");
    String writeName = (String) json.get("writeName");
    HashMap<String, String> tables = new HashMap<>();
    tables.put("table", tableName);
    tables.put("name", writeName);
    List<HashMap<String, String>> models = new ArrayList<HashMap<String, String>>();
    models.add(tables);
    jsonObject.put("model", models);
    
    try {
      Generate.doGenerat(jsonObject.toString());
      return "代码在"+json.get("writepath")+"成功生成";
    } catch (Exception e) {
      e.printStackTrace();
      return "代码生成失败";
    }
    
   
  }


  @PostMapping("/testSqlConnect")
  public String testSqlConnect(@RequestBody  Map<String, String> json) {
    Connection connection = null;
    try {
      Class.forName(json.get("driverClassName"));
      String url=json.get("url");
      String username=json.get("username");
      String password=json.get("password");
      connection=DriverManager.getConnection(url, username, password);
      return "连接成功";
      
      
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return "连接失败";
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return "连接失败";
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }

      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }
}
