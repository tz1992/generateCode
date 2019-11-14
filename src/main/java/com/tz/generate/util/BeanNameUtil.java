package com.tz.generate.util;

import java.util.Map;

import com.tz.generate.annotation.Action;

public class BeanNameUtil {
  public static String genBeanName(String tag, String version, String action) {
    return tag + "-" + version + "-" + action;
  }



  public static String genBeanName(Action action) {
    return action.tag() + "-" + action.version() + "-" + action.action();
  }



  public static String getBeanName(Map<String, Object> map) {
    return map.get("tag") + "-" + map.get("version") + "-" + map.get("action");
  }
}
