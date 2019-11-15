package com.tz.generate.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tz.generate.tag.Model;

public class Utils {
  public static Set<Model> getModels(Map<String, Object> globalParameter) {
    Set<Model> models = (Set) globalParameter.get("model");
    if (models == null) {
      globalParameter.put("model", new HashSet());
      models = (Set) globalParameter.get("model");
    }
    return models;
  }

  public static void pushModel(Map<String, Object> globalParameter, Model model) {
    Set<Model> models = getModels(globalParameter);
    models.add(model);
  }

  public static String getParamName(String columnName) {
    if (columnName == null) return "";
    StringBuffer sbuf = new StringBuffer();
    if (columnName.indexOf("_") > 0) {
      Arrays.asList(columnName.toLowerCase().split("_")).forEach(word -> {
        if (sbuf.length() == 0) {
          sbuf.append(word);
        } else {
          sbuf.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }
      });
    } else {
      sbuf.append(columnName.toLowerCase());
    }
    return sbuf.toString();
  }

  public static String getSimpleClassName(String className) {
    if (className == null) return "";
    int last = className.lastIndexOf(".");
    if (last > 0) {
      return className.substring(last + 1);
    }
    return className;
  }


  public static String getUpfirst(String paramName) {
    if (paramName == null) return "";
    return paramName.substring(0, 0).toUpperCase() + paramName.substring(1);
  }
}

