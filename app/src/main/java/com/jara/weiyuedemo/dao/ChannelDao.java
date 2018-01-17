package com.jara.weiyuedemo.dao;

import android.database.sqlite.SQLiteDatabase;

import com.jara.weiyuedemo.MyApp;
import com.jara.weiyuedemo.model.ChannelVo;
import com.jara.weiyuedemo.model.ChannelVoDao;
import com.jara.weiyuedemo.model.DaoMaster;
import com.jara.weiyuedemo.model.DaoSession;

import java.util.List;

/**
 * Created by Administrator on 2018-1-16.
 */

public class ChannelDao {

    private static DaoSession daoSession;

    public static void initDatabase(String dbname) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApp.getInstance(), dbname, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    public static void insertChannel(ChannelVo channelVo) {
        getDaoInstant().getChannelVoDao().insertOrReplace(channelVo);
    }

    /**
     * 添加多条数据
     * @param channels
     */
    public static void insertsChannel(List<ChannelVo> channels){
        getDaoInstant().getChannelVoDao().insertInTx(channels);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteChannel(long id) {
        getDaoInstant().getChannelVoDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param channel
     */
    public static void updateChannel(ChannelVo channel) {
        getDaoInstant().getChannelVoDao().update(channel);
    }

    /**
     * 更新数据
     *
     * @param channels
     */
    public static void updatesChannel(List<ChannelVo> channels) {
        getDaoInstant().getChannelVoDao().updateInTx(channels);
    }


    /**
     * 查询全部
     * @return
     */
    public static List<ChannelVo> queryAll(){
        return getDaoInstant().getChannelVoDao().loadAll();
    }

    /**
     * 查询
     * @param type
     * @return
     */
    public static List<ChannelVo> queryChannel(int type) {
        return getDaoInstant().getChannelVoDao().queryBuilder().where(ChannelVoDao.Properties.Type.eq(type)).list();
    }
}
