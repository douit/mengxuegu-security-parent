package com.mengxuegu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping({"index", "/", ""})
    public String index() {
        return "index";//resources/tempates/index
    }
}
