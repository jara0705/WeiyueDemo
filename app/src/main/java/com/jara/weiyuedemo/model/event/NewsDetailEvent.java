package com.jara.weiyuedemo.model.event;


import com.jara.library.ui.BaseEvent;

public class NewsDetailEvent extends BaseEvent {
    public NewsDetailEvent(int code, Object data, String id) {
        super(code, data, id);
    }
}
