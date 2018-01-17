package com.jara.weiyuedemo.presenter;


import com.jara.weiyuedemo.model.request.NewsDetailRequest;
import com.jara.weiyuedemo.model.request.NewsRequest;

public interface INewsPresenter {
    void getNewsList(NewsRequest news);
    void getNewsDetail(NewsDetailRequest detailRequest);
}
