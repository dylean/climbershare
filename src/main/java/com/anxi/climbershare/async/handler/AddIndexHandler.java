package com.anxi.climbershare.async.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.anxi.climbershare.async.EventHandler;
import com.anxi.climbershare.async.EventModel;
import com.anxi.climbershare.async.EventType;
import com.anxi.climbershare.service.SearchService;

import java.util.Arrays;
import java.util.List;


/**
 * @Author:AnXi
 */
@Component
public class AddIndexHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(AddIndexHandler.class);

    @Autowired
    SearchService searchService;

    @Override
    public void doHandle(EventModel model){
        try {
            searchService.addQuestionIndex(model.getEntityId(),model.getExt("title"),model.getExt("content"));
        } catch (Exception e){
            logger.error("问题索引添加失败");
        }
    }

    @Override
    public List<EventType> getSupportEventTypes(){
        return Arrays.asList(EventType.ADDINDEX);
    }

}
