package com.anxi.climbershare.async;

import java.util.List;

/**
 * @Author:AnXi
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
    
}
