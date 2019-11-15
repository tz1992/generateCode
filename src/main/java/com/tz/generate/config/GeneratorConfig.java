package com.tz.generate.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@ComponentScan(basePackages={"com.tz"} ,useDefaultFilters = false, nameGenerator = ActionBeanNameGenerator.class, includeFilters = {@Filter({com.tz.generate.annotation.Action.class})})
public class GeneratorConfig {
 
  Map<String, Object> global = new HashMap<>();


  @Bean
  public Map<String, Object> global() {
    return this.global;
  }



  @Bean
  public DruidDataSource dataSource() {
    return new DruidDataSource();
  }
}
