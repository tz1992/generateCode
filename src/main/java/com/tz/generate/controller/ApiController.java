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
import com.tz.generate.entity.ResponseData;
import com.tz.generate.entity.SqlEntity;
import com.tz.generate.util.SqliteConnection;

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
      return "代码在" + json.get("writepath") + "成功生成";
    } catch (Exception e) {
      e.printStackTrace();
      return "代码生成失败";
    }


  }


  @PostMapping("/testSqlConnect")
  public Map<String, String> testSqlConnect(@RequestBody Map<String, String> json) {
    Connection connection = null;
    Map<String, String> map = new HashMap<>();

    try {

      String driverClassName = json.get("driverClassName");
      String ip = json.get("host");
      String port = json.get("port");
      String dbName = json.get("dbName");
      StringBuilder url = new StringBuilder();
      switch (driverClassName) {
        case "oracle.jdbc.driver.OracleDriver":
          url.append("jdbc:oracle:thin:@").append(ip).append(":").append(port).append("/")
              .append(dbName);
          break;
        case "com.mysql.jdbc.Driver":
          url.append("jdbc:mysql://").append(ip).append(":").append(port).append("/")
              .append(dbName);

          break;
        case "com.microsoft.sqlserver.jdbc.SQLServerDriver":
          url.append("jdbc:sqlserver://").append(ip).append(":").append(port)
              .append(";DatabaseName=").append(dbName);

          break;

        default:
          break;
      }

      String databaseUrl=url.toString();
      Class.forName(driverClassName);
      String username = json.get("username");
      String password = json.get("password");
      System.out.println(username);
      System.out.println(password);

      connection = DriverManager.getConnection(databaseUrl, username, password);
      map.put("code", "200");
      map.put("result", "连接成功");

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      map.put("code", "500");
      map.put("result", "连接失败");
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      map.put("code", "500");
      map.put("result", "连接失败");
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


    return map;
  }
  
  
  @PostMapping("/makesure")
  public Map<String, String> makesure(@RequestBody Map<String, String> json) throws Exception {
    Connection connection = null;
    Map<String, String> map = new HashMap<>();
    ResponseData res=null;
    try {

      String driverClassName = json.get("driverClassName");
      String ip = json.get("host");
      String port = json.get("port");
      String dbName = json.get("dbName");
      StringBuilder url = new StringBuilder();
      switch (driverClassName) {
        case "oracle.jdbc.driver.OracleDriver":
          url.append("jdbc:oracle:thin:@").append(ip).append(":").append(port).append("/")
              .append(dbName);
          break;
        case "com.mysql.jdbc.Driver":
          url.append("jdbc:mysql://").append(ip).append(":").append(port).append("/")
              .append(dbName);

          break;
        case "com.microsoft.sqlserver.jdbc.SQLServerDriver":
          url.append("jdbc:sqlserver://").append(ip).append(":").append(port)
              .append(";DatabaseName=").append(dbName);

          break;

        default:
          break;
      }

      String databaseUrl=url.toString();
      Class.forName(driverClassName);
      String username = json.get("username");
      String password = json.get("password");
      System.out.println(username);
      System.out.println(password);

      connection = DriverManager.getConnection(databaseUrl, username, password);
      SqlEntity sqlEntity=new SqlEntity(databaseUrl,username,password,driverClassName,json.get("calledName"));
      res=SqliteConnection.insertSqlBean(sqlEntity);
      
      if(res.getCode()!=200){
        map.put("code", "500");
        map.put("result", "连接失败");
      }else{
        map.put("code", "200");
        map.put("result", "连接成功");
      }
     
     
      


    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      map.put("code", "500");
      map.put("result", "连接失败");
    
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


    return map;
  }
}
