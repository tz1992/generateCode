package com.tz.generate.tag.version_spring.model;

import java.util.List;

import com.tz.generate.tag.BaseTag;

public class Entity extends BaseTag {
  private List<String> ignoreColumn;

  public List<String> getIgnoreColumn() {
    return this.ignoreColumn;
  }



  public void setIgnoreColumn(List<String> ignoreColumn) {
    this.ignoreColumn = ignoreColumn;
  }
}

