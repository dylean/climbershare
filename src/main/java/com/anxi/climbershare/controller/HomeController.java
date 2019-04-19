package com.anxi.climbershare.controller;

import com.anxi.climbershare.pojo.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.keyu.askme.pojo.*;
import com.anxi.climbershare.service.CommentService;
import com.anxi.climbershare.service.LastedViewService;
import com.anxi.climbershare.service.QuestionService;
import com.anxi.climbershare.service.UserService;

/**
 * @Author:AnXi
 */
@Controller
public class HomeController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    LastedViewService lastedViewService;

    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET,RequestMethod.POST})
    public String index(Model model,ModelAndView modelAndView,
                        @RequestParam(value = "pop",defaultValue = "0") int pop){

        model.addAttribute("cvos",lastedViewService.getLastedComment(0,5));

        model.addAttribute("vos",lastedViewService.getLastedQuestion(0,10));


        return "home";
    }

    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(){

        return "login";
    }


}
