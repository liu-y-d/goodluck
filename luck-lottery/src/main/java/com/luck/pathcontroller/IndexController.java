package com.luck.pathcontroller;

import com.luck.api.R;
import com.luck.entity.ActivityEntity;
import com.luck.feign.RenrenFeignClient;
import com.luck.utils.AuthUtil;
import com.luck.utils.PageUtils;
import com.luck.vo.LuckUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyd
 * @date 2022/4/11 20:51
 */
@Controller
@RequestMapping("/web")
@AllArgsConstructor
public class IndexController {
    private final RenrenFeignClient renrenFeignClient;

    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/activity")
    public String go(Model model){
        R<List<ActivityEntity>> listR = renrenFeignClient.activityList();
        model.addAttribute("activityList",listR.getData());
        return "activity";
    }
}
