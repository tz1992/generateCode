package com.tz.generate.util;

import java.io.File;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tz.generate.entity.ResponseData;
import com.tz.generate.entity.SqlEntity;



public class SqliteConnection {
  private static final String DB_URL = "jdbc:sqlite:./db/sqlite3.db";
  private static final Logger _LOG = LoggerFactory.getLogger(SqliteConnection.class);

  private static Connection getConnection() throws Exception {
    Class.forName("org.sqlite.JDBC");
    File file = new File(DB_URL.substring("jdbc:sqlite:".length())).getAbsoluteFile();
    _LOG.info("database FilePath :{}", file.getAbsolutePath());
    Connection conn = DriverManager.getConnection(DB_URL);
    return conn;
  }

  public static ResponseData insertSqlBean(SqlEntity sqlEntity) {
    Connection connection = null;
    Statement stat = null;
    ResultSet rs = null;
    try {
      connection = getConnection();

      stat = connection.createStatement();

      rs = stat
          .executeQuery("select * from dbs where called_name='" + sqlEntity.getCalledName() + "'");

      if (rs.next()) {
        return new ResponseData(500, "当前的数据库已存在", null);
      }
      String sql = String.format(
          "INSERT INTO dbs (url, password,username,driver_class_name,called_name,db_name) values('%s', '%s','%s', '%s', '%s', '%s')",
          sqlEntity.getUrl(), sqlEntity.getPassword(), sqlEntity.getUsername(),
          sqlEntity.getDriverClassName(), sqlEntity.getCalledName(),sqlEntity.getDbName());
      stat.executeUpdate(sql);
      return new ResponseData(200, "数据存储成功", null);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return new ResponseData(500, "数据存储失败", null);
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }



  }

  public static ResponseData getSqlBeans() {
    Connection connection = null;
    Statement stat = null;
    ResultSet rs = null;
    try {
      connection = getConnection();
      stat = connection.createStatement();
      rs = stat.executeQuery("select * from dbs");
      ResultSetMetaData md = rs.getMetaData(); // 获得结果集结构信息,元数据
      int columnCount = md.getColumnCount(); // 获得列数
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      while (rs.next()) {
        Map<String, Object> rowData = new HashMap<String, Object>();
        for (int i = 1; i <= columnCount; i++) {
          rowData.put(md.getColumnName(i), rs.getObject(i));
        }
        list.add(rowData);
      }
      
      return new ResponseData(200, "查询数据库数据成功", list);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return new ResponseData(500, "查询数据库数据失败", null);
    }



  }
  
  
  public static ResponseData deleteSqlBeans(int id) {
    Connection connection = null;
    Statement stat = null;
   
    try {
      connection = getConnection();
      stat = connection.createStatement();

      String  sql="delete from dbs where id="+id;
      stat.executeUpdate(sql);
     

      return new ResponseData(200, "删除数据库成功", null);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return new ResponseData(500, "删除数据库失败", null);
    }



  }
  
}
