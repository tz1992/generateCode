package com.tz.generate.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.Generate;
import com.tz.generate.entity.ResponseData;
import com.tz.generate.entity.SqlEntity;
import com.tz.generate.util.SqliteConnection;

@RestController
public class ApiController {
  
  
  @Autowired
  public DruidDataSource dataSource;

  /**
   * 生成代码
   * 
   * @param json
   * @return
   */
  @PostMapping("/generateCode")
  public String generateCode(@RequestBody Map<String, Object> json) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("appName", json.get("appName"));
    jsonObject.put("action", "project");
    jsonObject.put("basepackage", json.get("basePackage"));
    jsonObject.put("writepath", json.get("writePath"));
    jsonObject.put("name", json.get("name"));
    jsonObject.put("entity", json.get("entity"));
    jsonObject.put("dao", json.get("dao"));
    jsonObject.put("version", json.get("version"));
    
    //获取对应的数据库信息
    int db=Integer.parseInt((String) json.get("db")) ;
    ResponseData responseData = this.getConnectInfos();
    List<Map<String, Object>> list = (List<Map<String, Object>>) responseData.getData();
    Map<String, Object> dbinfo = null;
    for (Map<String, Object> map : list) {
      if (db == (Integer) map.get("id")) {
        dbinfo = map;
        break;
      }
    }
    
    // 设置DataSource
    jsonObject.put("driverClassName", (String) dbinfo.get("driver_class_name"));
    jsonObject.put("url", (String) dbinfo.get("url"));
    jsonObject.put("username", (String) dbinfo.get("username"));
    jsonObject.put("password", (String) dbinfo.get("password"));
    
    
    // 设置models
    List<HashMap<String, String>> models = new ArrayList<HashMap<String, String>>();
    String tableName = (String) json.get("tableName");
    String writeName = (String) json.get("writeName");
    HashMap<String, String> tables = new HashMap<>();
    tables.put("table", tableName);
    tables.put("name", writeName);
    models.add(tables);
    jsonObject.put("model", models);


    try {
      Generate.doGenerat(jsonObject.toString());
      return "代码在" + json.get("writePath") + "成功生成";
    } catch (Exception e) {
      e.printStackTrace();
      return "代码生成失败";
    }


  }

  /**
   * 测试数据库连接
   * 
   * @param json
   * @return
   */
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

      String databaseUrl = url.toString();
      Class.forName(driverClassName);
      String username = json.get("username");
      String password = json.get("password");

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

  /**
   * 保存数据库的信息
   * 
   * @param json
   * @return
   * @throws Exception
   */
  @PostMapping("/makesure")
  public Map<String, String> makesure(@RequestBody Map<String, String> json) throws Exception {
    Connection connection = null;
    Map<String, String> map = new HashMap<>();
    ResponseData res = null;
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

      String databaseUrl = url.toString();
      Class.forName(driverClassName);
      String username = json.get("username");
      String password = json.get("password");
      System.out.println(username);
      System.out.println(password);

      connection = DriverManager.getConnection(databaseUrl, username, password);
      SqlEntity sqlEntity = new SqlEntity(databaseUrl, username, password, driverClassName,
          json.get("calledName"), dbName);
      res = SqliteConnection.insertSqlBean(sqlEntity);

      if (res.getCode() != 200) {
        map.put("code", "500");
        map.put("result", "连接失败");
      } else {
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

  /**
   * 获取数据库连接信息
   * 
   * @return
   */

  @GetMapping("/getConnectInfos")
  public ResponseData getConnectInfos() {
    return SqliteConnection.getSqlBeans();

  }

  /**
   * 删除对应的数据库
   * 
   * @param id
   * @return
   */

  @DeleteMapping("/deleteSqlBeans/{id}")
  public ResponseData deleteSqlBeans(@PathVariable("id") int id) {
    return SqliteConnection.deleteSqlBeans(id);

  }

  /**
   * 根据数据库id获取数据对应的基础表
   * @param id
   * @return
   */
  @GetMapping("/getTableInfoBydb/{id}")
  public ResponseData getTableInfoBydb(@PathVariable("id") int id) {
    ResponseData responseData = this.getConnectInfos();   
    List<Map<String, Object>> list = (List<Map<String, Object>>) responseData.getData();
    Map<String, Object> dbinfo = null;
    for (Map<String, Object> map : list) {
      if (id == (Integer) map.get("id")) {
        dbinfo = map;
        break;
      }
    }

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    try {
      Class.forName((String) dbinfo.get("driver_class_name"));
      connection = DriverManager.getConnection((String) dbinfo.get("url"),
          (String) dbinfo.get("username"), (String) dbinfo.get("password"));
      statement = connection.createStatement();
      String sql = null;
      String driverClassName = (String) dbinfo.get("driver_class_name");
      switch (driverClassName) {
        case "oracle.jdbc.driver.OracleDriver":

          sql = "SELECT table_name FROM user_tables";

          break;
        case "com.mysql.jdbc.Driver":

          sql = "select table_name from information_schema.tables where table_schema='"
              + dbinfo.get("db_name") + "' and table_type='base table';";


          break;
        case "com.microsoft.sqlserver.jdbc.SQLServerDriver":

          sql = "SELECT Name as table_name FROM SysObjects Where XType='U'";

          break;

        default:
          break;
      }
      rs = statement.executeQuery(sql);
      ResultSetMetaData md = rs.getMetaData(); // 获得结果集结构信息,元数据
      int columnCount = md.getColumnCount(); // 获得列数
      List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
      while (rs.next()) {
        Map<String, Object> rowData = new HashMap<String, Object>();
        for (int i = 1; i <= columnCount; i++) {
          rowData.put(md.getColumnName(i), rs.getObject(i));
        }
        lists.add(rowData);

      }

      responseData.setData(lists);
    } catch (Exception e) {
      e.printStackTrace();
      responseData.setCode(500);
      responseData.setMsg("获取表数据失败");
    }finally {
      try {
        connection.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }



    return responseData;
  }
}
