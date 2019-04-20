package com.anxi.climbershare.controller;

import com.anxi.climbershare.async.EventModel;
import com.anxi.climbershare.async.EventProducer;
import com.anxi.climbershare.async.EventType;
import com.anxi.climbershare.pojo.Comment;
import com.anxi.climbershare.pojo.HostHolder;
import com.anxi.climbershare.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.anxi.climbershare.service.CommentService;
import com.anxi.climbershare.service.LastedViewService;
import com.anxi.climbershare.service.QuestionService;
import com.anxi.climbershare.service.UserService;


import java.util.Date;
import java.util.List;

/**
 * @Author:AnXi
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    LastedViewService lastedViewService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;


    /**
     * 问题页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(path = "/qst",method = {RequestMethod.GET})
    public String goToQuestion(Model model,
                               @RequestParam("id") int id){
        Question question = questionService.findQuestionById(id);
        List<Comment> commentList = commentService.getCommentsByEntity(id,0);
        model.addAttribute("qst",question);
        model.addAttribute("cmts",lastedViewService.getCommentsById(id));
        model.addAttribute("cvos",lastedViewService.getLastedComment(0,5));
        int adoptCommentId = question.getStatus();
        if(adoptCommentId > 0){
            Comment comment = commentService.getCommentById(adoptCommentId);
            model.addAttribute("acmt",comment);
        }
        return  "question";
    }

    @RequestMapping(path = "/user/submitqst" ,method = {RequestMethod.POST})
    public String submitQuestion(@RequestParam("title") String title,
                                 @RequestParam("content") String content
                                 ){

        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setCreatedDate(new Date());
        question.setUserId(hostHolder.getUser().getId());

        if (questionService.postQuestion(question) > 0){
            eventProducer.fireEvent(new EventModel(EventType.ADDINDEX)
                .setActorId(question.getUserId()).setEntityId(question.getId())
                .setExt("title",question.getTitle()).setExt("content",question.getContent()));
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/user/adopt",method = {RequestMethod.POST})
    public String adoptComment(RedirectAttributes redirectAttributes,
                               @RequestParam("commentId") int commentId,
                               @RequestParam("questionId") int qustionId,
                               @RequestParam("questionUserId") int questionUserId){
        redirectAttributes.addAttribute("id",qustionId);

        if (hostHolder.getUser().getId() == questionUserId){
            questionService.updateStatus(qustionId,commentId);

            return "redirect:/qst";
        }
        return "redirect:/qst";
    }



}
