package com.tz.generate.tag;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import com.alibaba.fastjson.JSONObject;
import com.tz.generate.annotation.Action;
import com.tz.generate.util.BeanNameUtil;
import com.tz.generate.util.TemplateUtil;

import freemarker.template.Template;

public abstract class BaseAction<T extends BaseTag> extends Object {
  @Autowired
  private GenericApplicationContext ctx;

  protected GenericApplicationContext ctx() {
    return this.ctx;
  }



  protected Map<String, Object> global() {
    return (Map) ctx().getBean("global");
  }



  protected BaseAction getBean(String name) {
    Action ann = (Action) AnnotationUtils.findAnnotation(getClass(), Action.class);

    return (BaseAction) ctx().getBean(BeanNameUtil.genBeanName( ann.version(), name));
  }



  protected Template getTemplate(String refTemplate, String name) {
    Action ann = (Action) AnnotationUtils.findAnnotation(getClass(), Action.class);

    return TemplateUtil.getTemplate(refTemplate, ann.version(), name);
  }

  protected abstract void before(JSONObject paramJSONObject);

  public final void write(JSONObject jsonobject) {
    before(jsonobject);
    doWrite(jsonobject);
    after(jsonobject);
  }


  protected abstract void doWrite(JSONObject paramJSONObject);

  protected abstract void after(JSONObject paramJSONObject);

  protected void checkPath(String path, boolean isClear) {
    try {
      File writePath = new File(path);

      if (writePath.exists() && isClear) {
        FileUtils.deleteDirectory(writePath);
      } else {
        writePath.mkdirs();
      }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }
}

