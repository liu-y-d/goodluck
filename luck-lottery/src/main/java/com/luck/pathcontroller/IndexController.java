package com.luck.pathcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liuyd
 * @date 2022/4/11 20:51
 */
@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
