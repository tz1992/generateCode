package com.tz.generate.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import com.tz.generate.util.BeanNameUtil;

public class ActionBeanNameGenerator implements BeanNameGenerator {

  @Override
  public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
    AnnotatedBeanDefinition annotatedDef = (AnnotatedBeanDefinition)definition;
    AnnotationMetadata amd = annotatedDef.getMetadata();
    Map<String, Object> map = amd.getAnnotationAttributes(com.tz.generate.annotation.Action.class.getName());
    Assert.notNull(map.get("tag"), "tag of Action annotation must be not null in " + annotatedDef.getBeanClassName() + " class.");
    Assert.notNull(map.get("version"), "version of Action annotation must be not null in " + annotatedDef.getBeanClassName() + " class.");
    Assert.notNull(map.get("action"), "action of Action annotation must be not null in " + annotatedDef.getBeanClassName() + " class.");
    return BeanNameUtil.getBeanName(map);
  }

}
