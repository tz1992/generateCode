package com.tz.generate.entity;

import java.io.Serializable;

public class SqlEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String url;
  private String username;
  private String password;
  private String driverClassName;
  private String calledName;

  public String getCalledName() {
    return calledName;
  }

  public void setCalledName(String calledName) {
    this.calledName = calledName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public SqlEntity() {
    super();
  }



  public SqlEntity(String url, String username, String password, String driverClassName,String calledName) {
    super();
    this.url = url;
    this.username = username;
    this.password = password;
    this.driverClassName = driverClassName;
    this.calledName=calledName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDriverClassName() {
    return driverClassName;
  }

  public void setDriverClassName(String driverClassName) {
    this.driverClassName = driverClassName;
  }

}
