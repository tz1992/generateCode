package com.tz.generate.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tz.generate.entity.ResponseData;
import com.tz.generate.entity.SqlEntity;



public class SqliteConnection {
  private static final String DB_URL = "jdbc:sqlite:/sqlite3.db";
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

      rs = stat.executeQuery("select * from dbs where url='" + sqlEntity.getUrl() + "'");
      if (rs.next()) {
        return new ResponseData(500, "当前的数据库已存在", null);
      }
      String sql = String.format(
          "INSERT INTO dbs (url, password,username,driver_class_name) values('%s', '%s','%s', '%s')",
          sqlEntity.getUrl(), sqlEntity.getPassword(), sqlEntity.getUsername(),
          sqlEntity.getDriverClassName());
      stat.executeUpdate(sql);
      return new ResponseData(200, "数据存储成功", null);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return new ResponseData(500, "数据存储失败", null);
    }finally {
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
      String value=null;
     if(rs.next()){
        value= rs.getString("value");
       System.out.println(value);
     }
      
      return new ResponseData(200, "查询数据库数据成功", value);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return new ResponseData(500, "查询数据库数据失败", null);
    }



  }
}
