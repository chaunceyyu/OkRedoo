package com.youzi.okredoo.data;

import android.content.Context;

import com.youzi.okredoo.gendao.DaoMaster;
import com.youzi.okredoo.gendao.DaoSession;
import com.youzi.okredoo.gendao.GetRedPackDao;
import com.youzi.okredoo.gendao.RedPackInfoDao;
import com.youzi.okredoo.gendao.UserDao;
import com.youzi.okredoo.model.GetRedPack;
import com.youzi.okredoo.model.RedPackInfo;
import com.youzi.okredoo.model.User;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class DBManager {

    private static DBManager sDbManager;
    private UserDao mUserDao;
    private RedPackInfoDao mRedPackInfoDao;
    private GetRedPackDao mGetRedPackDao;
    private Context mContext;

    private DBManager(Context context) {
        mContext = context;
        DaoSession daoSession = DaoMaster.newDevSession(context, "youzi.db");
        mUserDao = daoSession.getUserDao();
        mRedPackInfoDao = daoSession.getRedPackInfoDao();
    }

    public static DBManager getInstance() {
        if (sDbManager == null) {
            throw new NullPointerException("DBManager is null");
        }
        return sDbManager;
    }

    public static void init(Context context) {
        if (sDbManager == null) {
            sDbManager = new DBManager(context);
        }
    }

    public UserDao getUserDao() {
        return mUserDao;
    }

    public void saveUser(User user) {
        mUserDao.insert(user);
    }

    public boolean isExistUser(String uid) {
        User u = getUserById(uid);
        if (u != null) {
            return true;
        }
        return false;
    }

    public void updateUser(User user) {
        mUserDao.update(user);
    }

    public void deleteUser(String uid) {
        mUserDao.deleteByKey(uid);
    }

    public void deleteAllUser() {
        mUserDao.deleteAll();
    }

    public User getUserById(String uid) {
        User user = mUserDao.queryBuilder().where(UserDao.Properties.Uid.eq(uid)).uniqueOrThrow();
        return user;
    }

    public void saveRedPack(RedPackInfo redPackInfo) {
        mRedPackInfoDao.insertOrReplace(redPackInfo);
    }

    /**
     * 用户获取的包
     */
    public void saveUserGetRedPack(GetRedPack getRedPack) {
        mGetRedPackDao.insertOrReplace(getRedPack);
    }

}
