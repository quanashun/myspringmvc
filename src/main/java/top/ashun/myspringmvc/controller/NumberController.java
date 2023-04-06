package top.ashun.myspringmvc.controller;

import top.ashun.myspringmvc.annotation.Controller;
import top.ashun.myspringmvc.annotation.RequestMapping;
import top.ashun.myspringmvc.enums.RequestMethod;

/**
 * @author 18483
 */
@Controller
public class NumberController{

    @RequestMapping(value = "/getYear",method = RequestMethod.POST)
    public Integer getName() {
        return 2023;
    }
}
