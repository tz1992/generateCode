package com.tz.generate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tz.generate.tag.BaseAction;
import com.tz.generate.util.BeanNameUtil;

public class Generate {
  AnnotationConfigApplicationContext ctx;

  protected Generate() {
    this.ctx = new AnnotationConfigApplicationContext(
        new Class[] {com.tz.generate.config.GeneratorConfig.class});
  }

  public static String doGenerat(String jsonParam) {
    Generate generator = new Generate();
    doAction(generator, jsonParam);
    
    return "ok";
    
  }


  private static void doAction(Generate generator, String jsonParam) {
    JSONObject jsonobject = (JSONObject) JSON.parse(jsonParam);


    
    String version = jsonobject.getString("version");
    if (StringUtils.isEmpty(version)) {
      version = "ms";
    }

    String action = jsonobject.getString("action");


    BaseAction<?> baseaction =
        (BaseAction) generator.ctx.getBean(BeanNameUtil.genBeanName( version, action));

    baseaction.write(jsonobject);
  }
}
