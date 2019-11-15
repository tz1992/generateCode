package com.tz.generate.util;

import java.util.Map;

import com.tz.generate.annotation.Action;

public class BeanNameUtil {
  public static String genBeanName( String version, String action) {
    return  version + "-" + action;
  }



  public static String genBeanName(Action action) {
    return  action.version() + "-" + action.action();
  }



  public static String getBeanName(Map<String, Object> map) {
    return  map.get("version") + "-" + map.get("action");
  }
}
