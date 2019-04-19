package com.anxi.climbershare.controller;

import com.anxi.climbershare.async.EventModel;
import com.anxi.climbershare.async.EventProducer;
import com.anxi.climbershare.async.EventType;
import com.anxi.climbershare.pojo.Comment;
import com.anxi.climbershare.pojo.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.anxi.climbershare.service.CommentService;
import com.anxi.climbershare.service.LikeService;

/**
 * @Author:AnXi
 */
@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = "/user/like",method = {RequestMethod.POST})
    public String likeIt(RedirectAttributes redirectAttributes,
                         @RequestParam("commentId") int commentId){

        Comment comment = commentService.getCommentById(commentId);

        long likeCount = likeService.likeIt(hostHolder.getUser().getId(), 0,commentId);

        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
                .setEntityType(0).setEntityOwnerId(comment.getUserId())
                .setExt("questionId", String.valueOf(comment.getEntityId())));

        redirectAttributes.addAttribute("id",comment.getEntityId());

        return "redirect:/qst";
    }

    @RequestMapping(path = "/user/dislike",method = {RequestMethod.POST})
    public String dislikeIt(RedirectAttributes redirectAttributes,
                            @RequestParam("commentId") int commentId){
        Comment comment = commentService.getCommentById(commentId);

        long dislikeCount = likeService.dislikeIt(hostHolder.getUser().getId(),0,commentId);

        redirectAttributes.addAttribute("id",comment.getEntityId());

        return "redirect:/qst";

    }


}
