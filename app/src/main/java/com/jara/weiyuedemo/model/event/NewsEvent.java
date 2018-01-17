package com.jara.weiyuedemo.model.event;


import com.jara.library.ui.BaseEvent;

public class NewsEvent extends BaseEvent {
    public NewsEvent(int code, Object data, String id) {
        super(code, data, id);
    }
}
