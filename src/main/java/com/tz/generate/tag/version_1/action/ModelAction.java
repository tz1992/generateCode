package com.tz.generate.tag.version_1.action;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.tag.Column;
import com.tz.generate.tag.Db;
import com.tz.generate.tag.Model;
import com.tz.generate.tag.version_1.model.Project;
import com.tz.generate.util.Utils;

@Action(action = "model", version = "1.0")
public class ModelAction extends BaseAction<Model> {
  @Autowired
  public DruidDataSource dataSource;

  protected void before(JSONObject jsonobject) {}

  protected void readByMysql(Model model) throws SQLException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    SqlRowSet rowSet =
        jdbcTemplate.queryForRowSet("select * from " + model.getTable() + " limit 0");
    SqlRowSetMetaData metaData = rowSet.getMetaData();
    DatabaseMetaData databaseMetaData = this.dataSource.getConnection().getMetaData();
    ResultSet columnSet = databaseMetaData.getColumns(null, "%", model.getTable(), "%");
    List<Column> columnList = new ArrayList<Column>();
    int columnCount = metaData.getColumnCount();
    for (int i = 1; i <= columnCount; i++) {
      Column column = new Column();
      column.setColumnName(metaData.getColumnName(i));
      column.setColumnType(String.valueOf(metaData.getColumnType(i)));
      column.setColumnTypeName(covertColomnType("mysql", metaData.getColumnTypeName(i)));
      column.setCatalogName(metaData.getCatalogName(i));
      column.setColumnClassName(metaData.getColumnClassName(i));
      column.setColumnLabel(metaData.getColumnLabel(i));
      column.setPrecision(String.valueOf(metaData.getPrecision(i)));
      column.setScale(String.valueOf(metaData.getScale(i)));
      column.setSchemaName(metaData.getSchemaName(i));
      column.setTableName(metaData.getTableName(i));

      columnSet.beforeFirst();
      while (columnSet.next()) {
        String columnName = columnSet.getString("COLUMN_NAME");
        String columnComment = columnSet.getString("REMARKS");

        if (column.getColumnName().equalsIgnoreCase(columnName)) {
          column.setColumnComment(columnComment);

          break;
        }
      }
      columnList.add(column);
    }


    model.setColumn(columnList);
  }


  protected void readByOracle(Model model) throws SQLException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from " + model.getTable());
    SqlRowSetMetaData metaData = rowSet.getMetaData();
    List<Column> columnList = new ArrayList<Column>();
    int columnCount = metaData.getColumnCount();
    for (int i = 1; i <= columnCount; i++) {
      Column column = new Column();
      column.setColumnName(metaData.getColumnName(i));
      column.setColumnType(String.valueOf(metaData.getColumnType(i)));
      column.setColumnTypeName(covertColomnType("oracle", metaData.getColumnTypeName(i)));
      column.setCatalogName(metaData.getCatalogName(i));
      column.setColumnClassName(metaData.getColumnClassName(i));
      column.setColumnLabel(metaData.getColumnLabel(i));
      column.setPrecision(String.valueOf(metaData.getPrecision(i)));
      column.setScale(String.valueOf(metaData.getScale(i)));
      column.setSchemaName(metaData.getSchemaName(i));
      column.setTableName(metaData.getTableName(i));
      column.setColumnComment("");
      columnList.add(column);
    }


    model.setColumn(columnList);
  }


  protected void readByMssql(Model model) throws SQLException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select top 1 * from " + model.getTable());
    SqlRowSetMetaData metaData = rowSet.getMetaData();



    List<Column> columnList = new ArrayList<Column>();
    int columnCount = metaData.getColumnCount();
    for (int i = 1; i <= columnCount; i++) {
      Column column = new Column();
      column.setColumnName(metaData.getColumnName(i));
      column.setColumnType(String.valueOf(metaData.getColumnType(i)));
      column.setColumnTypeName(metaData.getColumnTypeName(i));
      column.setCatalogName(metaData.getCatalogName(i));
      column.setColumnClassName(metaData.getColumnClassName(i));
      column.setColumnLabel(metaData.getColumnLabel(i));
      column.setPrecision(String.valueOf(metaData.getPrecision(i)));
      column.setScale(String.valueOf(metaData.getScale(i)));
      column.setSchemaName(metaData.getSchemaName(i));
      column.setTableName(metaData.getTableName(i));
      column.setColumnComment("");



      columnList.add(column);
    }


    model.setColumn(columnList);
  }

  protected void initModel(Model model) throws SQLException {
    if (model.getCode() == null) {
      model.setCode("");
    }

    if (StringUtils.isEmpty(model.getDesc())) {
      model.setDesc(model.getName() + " Resource");
    }
    

   Project project= (Project)global().get("project");
   
   String driver=project.getDriverClassName();
   
   
    if (driver == null) {
      return;
    }
    if (driver.indexOf("mysql") > 0) {
      readByMysql(model);

    } else if (driver.indexOf("sqlserver") > 0) {
      readByMssql(model);
    } else if (driver.indexOf("oracle") != -1) {
      readByOracle(model);
    }
  }


  public void doWrite(JSONObject json) {
    Model model = (Model) JSON.parseObject(json.toJSONString(), Model.class);
    try {
      initModel(model);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Utils.pushModel(global(), model);
  }



  protected void after(JSONObject jsonobject) {}



  public static String covertColomnType(String dbType, String source) {
    String res = "";
    if (dbType.equals("oracle")) {
      switch (source) {
        case "NUMBER":
          res = "NUMERIC";
          break;
        case "VARCHAR2":
          res = "VARCHAR";
          break;
        case "LONG VARCHAR":
          res = "LONGVARCHAR";
          break;
        case "NVARCHAR2":
          res = "NVARCHAR";
          break;
        default:
          res = source;
          break;
      }
    }
    if (dbType.equals("mysql")) {
      switch (source) {
        case "DATETIME":
          return "TIMESTAMP";

        case "TEXT":
          return "CLOB";

        case "INT":
          return "INTEGER";
      }

      res = source;
    }


    return res;
  }
}
