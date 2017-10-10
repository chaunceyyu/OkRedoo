package com.youzi.okredoo.data;

import com.youzi.okredoo.model.User;

import java.util.ArrayList;

/**
 * Created by zhangjiajie on 2017/10/8.
 */

public class UserList extends ArrayList<User> {


    public User getUser(String uid) {

        for (int i = 0; i < size(); i++) {
            if (get(i).getUid().equals(uid)) {
                return get(i);
            }
        }
        return null;
    }

}
