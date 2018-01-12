package com.jara.http.jsoup;

import org.jsoup.nodes.Document;

/**
 * Created by Administrator on 2018-1-11.
 */

public interface RxJsoupNetWorkListener<T> {
    void onNetWorkStart();

    void onNetWorkError(Throwable e);

    void onNetWorkComplete();

    void onNetWorkSuccess(T t);

    T getT(Document document);
}
