package com.jara.library.util;


import com.jara.library.ui.BaseEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * className: EventBusUtil
 * author: lijun
 * date: 17/7/2 08:31
 */

public class EventBusUtil {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(BaseEvent event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(BaseEvent event) {
        EventBus.getDefault().postSticky(event);
    }
}
