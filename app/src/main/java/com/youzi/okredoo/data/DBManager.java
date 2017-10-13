package com.youzi.okredoo.data;

import android.content.Context;

import com.youzi.okredoo.gendao.DaoMaster;
import com.youzi.okredoo.gendao.DaoSession;
import com.youzi.okredoo.gendao.GetRedPackDao;
import com.youzi.okredoo.gendao.RedPackInfoDao;
import com.youzi.okredoo.gendao.UserDao;
import com.youzi.okredoo.model.GetRedPack;
import com.youzi.okredoo.model.RedPackInfo;
import com.youzi.okredoo.model.TongJi1;
import com.youzi.okredoo.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
        mGetRedPackDao = daoSession.getGetRedPackDao();
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
        User user = mUserDao.queryBuilder().where(UserDao.Properties.Uid.eq(uid)).unique();
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

    public ArrayList<TongJi1> GetTongJi1List() {
        List<GetRedPack> getRedPacks = mGetRedPackDao.loadAll();
        HashMap<String, TongJi1> tongJi1HashMap = new HashMap<>();
        for (int i = 0; i < getRedPacks.size(); i++) {
            GetRedPack getRedPack = getRedPacks.get(i);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(new Date(getRedPack.getTime()));

            TongJi1 tongJi1 = tongJi1HashMap.get(date);
            if (tongJi1 == null) {
                tongJi1 = new TongJi1();
                tongJi1.setDate(date);
                tongJi1HashMap.put(date, tongJi1);
            }
            tongJi1.addCount(getRedPack.getCount());
        }

        ArrayList<TongJi1> tongJi1s = new ArrayList<>();
        tongJi1s.addAll(tongJi1HashMap.values());

        Collections.sort(tongJi1s, new Comparator<TongJi1>() {
            @Override
            public int compare(TongJi1 t1, TongJi1 t2) {
                return t2.getDate().compareTo(t1.getDate());
            }
        });

        return tongJi1s;
    }

}
