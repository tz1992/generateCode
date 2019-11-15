package com.tz.generate.tag;

import java.util.List;

import com.alibaba.druid.util.StringUtils;

public class Model extends BaseTag {
  private String table;
  private String name;
  private String code;
  private String desc;
  private List<Column> column;

  public int hashCode() {
    int prime = 31;
    int result = 1;
    return 31 * result + ((this.name == null) ? 0 : this.name.hashCode());
  }



  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Model other = (Model) obj;
    if (this.name == null) {
      if (other.name != null) return false;
    } else if (!this.name.equals(other.name)) {
      return false;
    }
    return true;
  }


  public String getTable() {
    return this.table;
  }



  public void setTable(String table) {
    this.table = table;
  }



  public String getName() {
    return this.name;
  }



  public void setName(String name) {
    this.name = name;
  }


  public String getCode() {
    if (StringUtils.isEmpty(this.code)) {
      return "";
    }
    return this.code;
  }


  public void setCode(String code) {
    this.code = code;
  }



  public String getDesc() {
    return this.desc;
  }



  public void setDesc(String desc) {
    this.desc = desc;
  }



  public List<Column> getColumn() {
    return this.column;
  }



  public void setColumn(List<Column> column) {
    this.column = column;
  }
}
