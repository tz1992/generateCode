package com.tz.generate.controller;





import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ViewController {
  // 进入选择页面
  @GetMapping("/")
  public String get() {

    return "view";
  }

  



}
