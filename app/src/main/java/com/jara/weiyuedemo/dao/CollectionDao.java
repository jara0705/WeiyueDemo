package com.jara.weiyuedemo.dao;

import android.database.sqlite.SQLiteDatabase;

import com.jara.weiyuedemo.MyApp;
import com.jara.weiyuedemo.model.CollectionVo;
import com.jara.weiyuedemo.model.CollectionVoDao;
import com.jara.weiyuedemo.model.DaoMaster;
import com.jara.weiyuedemo.model.DaoSession;

import java.util.List;

/**
 * Created by Administrator on 2018-1-16.
 */

public class CollectionDao {
    private static DaoSession daoSession;

    /**
     * 配置数据库
     */
    public static void initDatabase(String dbname) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApp.getInstance(), dbname, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    public static void insert(CollectionVo channel) {
        getDaoInstant().getCollectionVoDao().insertOrReplace(channel);
    }

    public static void deleteChannel(long id) {
        getDaoInstant().getCollectionVoDao().deleteByKey(id);
    }

    public static CollectionVo queryImgUrl(String imgurl){
        return getDaoInstant().getCollectionVoDao().queryBuilder().where(CollectionVoDao.Properties.ImgUrl.eq(imgurl)).unique();
    }

    public static List<CollectionVo> queryAll(){
        return getDaoInstant().getCollectionVoDao().loadAll();
    }

    public static List<CollectionVo> query(int type) {
        return getDaoInstant().getCollectionVoDao().queryBuilder().where(CollectionVoDao.Properties.Type.eq(type)).list();
    }
}
