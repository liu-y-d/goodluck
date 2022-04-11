package com.luck.pathcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liuyd
 * @date 2022/4/11 20:51
 */
@Controller
@RequestMapping("/web")
public class IndexController {
    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
