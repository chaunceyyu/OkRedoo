package com.youzi.okredoo.data;

import android.content.Context;

import com.youzi.okredoo.gendao.DaoMaster;
import com.youzi.okredoo.gendao.DaoSession;
import com.youzi.okredoo.gendao.UserDao;
import com.youzi.okredoo.model.User;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class DBManager {

    private static DBManager sDbManager;
    private UserDao mUserDao;
    private Context mContext;

    private DBManager(Context context) {
        mContext = context;
        DaoSession daoSession = DaoMaster.newDevSession(context, "youzi.db");
        mUserDao = daoSession.getUserDao();
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

    public void updateOnline(User user) {
        mUserDao.update(user);
    }
}
