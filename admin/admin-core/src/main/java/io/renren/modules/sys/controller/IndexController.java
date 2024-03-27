package io.renren.modules.sys.controller;

import io.renren.common.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页提示
 *
 * @author Mark sunlightcs@gmail.com
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index.html";
    }
}
