package com.anxi.climbershare.async.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.anxi.climbershare.async.EventHandler;
import com.anxi.climbershare.async.EventModel;
import com.anxi.climbershare.async.EventType;
import com.anxi.climbershare.pojo.Message;
import com.anxi.climbershare.pojo.User;
import com.anxi.climbershare.service.MessageService;
import com.anxi.climbershare.service.UserService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author:AnXi
 */
@Component
public class AdoptHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(0);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        message.setConversationId("system");
        User user = userService.getUser(model.getActorId());
        String questionId = model.getExt("questionId");
        message.setContent("用户" + user.getName() + "采纳了你的评论，http：//localhost:8080/qst?id="+ questionId);
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {

        return Arrays.asList(EventType.ADOPT);
    }


}
