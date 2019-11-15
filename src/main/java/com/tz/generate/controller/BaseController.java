package com.tz.generate.controller;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.tz.generate.Generate;



@RestController
public class BaseController {
  // 进入选择页面
  @GetMapping("/")
  public String get() {

    return "view";
  }

  @PostMapping("/")
  public void generateCode(HashMap<String, Object> json) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("appName", json.get("appName"));
    jsonObject.put("action", "project");
    jsonObject.put("basepackage", json.get("basepackage"));
    jsonObject.put("writepath", json.get("writepath"));
    jsonObject.put("db", json.get("db"));
    String tableName = (String) json.get("tableName");
    String writeName = (String) json.get("writeName");
    HashMap<String, String> tables = new HashMap<>();
    tables.put("table", tableName);
    tables.put("name", writeName);
    List<HashMap<String, String>> models = new ArrayList<HashMap<String, String>>();
    jsonObject.put("model", models);
    Generate.doGenerat(jsonObject.toString());
  }


  @PostMapping("/testSqlConnect")
  public String testSqlConnect(@RequestBody  Map<String, String> json) {
    System.out.println(json.toString());
    System.out.println(json.get("driverClassName"));
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
