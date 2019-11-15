package com.tz.generate.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateUtil {

  public static Template getTemplate(String refTemplate, String version,
      String templateName) {
    Configuration config = new Configuration();


    Template template = null;

    if (refTemplate == null || StringUtils.isEmpty(refTemplate)) {
      try {
        String dirPath = "/template/" + "/" + version.replaceAll("\\.", "_");
        config.setClassForTemplateLoading(TemplateUtil.class.getClass(), dirPath);
        template = config.getTemplate("/" + templateName + ".ftl");
      } catch (IOException e) {
        throw new RuntimeException(
            "can't get [" + templateName + "] freemarker template by " + refTemplate);
      }
    } else {

      try {
        File file = FileUtils.getFile(new String[] {refTemplate});
        File directory = file.getParentFile();
        config.setDirectoryForTemplateLoading(directory);
        template = config.getTemplate(file.getName());
      } catch (IOException e) {
        throw new RuntimeException(
            "can't get [" + templateName + "] freemarker template by " + refTemplate);
      }
    }

    return template;
  }

  public static boolean fprint(Template temp, String filePath, Map<String, Object> param) {
    OutputStreamWriter out = null;

    try {
      out = new FileWriter(new File(filePath));
      temp.process(param, out);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TemplateException e) {
      e.printStackTrace();
    } finally {
      try {
        if (out != null) out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return false;
  }
}
