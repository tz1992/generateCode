package com.tz.generate.tag.version_1.model;

import com.tz.generate.tag.BaseTag;
import com.tz.generate.tag.Db;

public class Project extends BaseTag {
  private String head;
  private String name;
  private String title;
  private Db db;
  private String writepath;
  private String basepackage;
  private String userName;
  private String timeStamp;
  private String abbreviation;
  private boolean clear = true;
  private String model = "ms";


  private String version;


  public String getHead() {
    return this.head;
  }



  public void setHead(String head) {
    this.head = head;
  }



  public String getTitle() {
    return this.title;
  }



  public void setTitle(String title) {
    this.title = title;
  }



  public Db getDb() {
    return this.db;
  }



  public void setDb(Db db) {
    this.db = db;
  }



  public String getWritepath() {
    return this.writepath;
  }



  public void setWritepath(String writepath) {
    this.writepath = writepath;
  }



  public String getBasepackage() {
    return this.basepackage;
  }



  public void setBasepackage(String basepackage) {
    this.basepackage = basepackage;
  }



  public boolean isClear() {
    return this.clear;
  }



  public void setClear(boolean clear) {
    this.clear = clear;
  }



  public String getName() {
    return this.name;
  }



  public void setName(String name) {
    this.name = name;
  }



  public String getUserName() {
    return this.userName;
  }



  public void setUserName(String userName) {
    this.userName = userName;
  }



  public String getTimeStamp() {
    return this.timeStamp;
  }



  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }



  public String getAbbreviation() {
    return this.abbreviation;
  }



  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }



  public String getVersion() {
    return this.version;
  }



  public void setVersion(String version) {
    this.version = version;
  }



  public String getModel() {
    return this.model;
  }



  public void setModel(String model) {
    this.model = model;
  }
}

