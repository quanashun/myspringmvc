package top.ashun.myspringmvc.controller;

import top.ashun.myspringmvc.annotation.Controller;
import top.ashun.myspringmvc.annotation.RequestMapping;
import top.ashun.myspringmvc.core.DispatcherServlet;

/**
 * @author 18483
 */
@Controller
public class TestController {

    @RequestMapping("/getName")
    public String getName() {
        return DispatcherServlet.threadLocalUserDTO.get().getUserName();
    }
}
