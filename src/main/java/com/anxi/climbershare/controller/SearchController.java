package com.anxi.climbershare.controller;

import com.anxi.climbershare.pojo.Question;
import com.anxi.climbershare.pojo.User;
import com.anxi.climbershare.pojo.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.anxi.climbershare.service.QuestionService;
import com.anxi.climbershare.service.SearchService;
import com.anxi.climbershare.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:AnXi
 */
@Controller
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    SearchService searchService;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/search"},method = {RequestMethod.GET})
    public String searchQuestion(Model model,
                                 @RequestParam("text") String keyword,
                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                 @RequestParam(value = "count", defaultValue = "10") int count){
        try {
            List<Question> questionList = searchService.searchQuestion(keyword,offset,count,"<strong>","</strong>");

            List<ViewObject> vos = new ArrayList<>();
            for (Question question:questionList){
                Question question1 = questionService.findQuestionById(question.getId());
                question.setCreatedDate(question1.getCreatedDate());
                User user = userService.getUser(question1.getUserId());

                ViewObject vo = new ViewObject();
                vo.setQuestion(question);
                vo.setUser(user);

                vos.add(vo);
            }
            model.addAttribute("vos",vos);

        }catch (Exception e){
            logger.error("搜索失败"+ e.getMessage());
        }

        return "search";

    }
}
