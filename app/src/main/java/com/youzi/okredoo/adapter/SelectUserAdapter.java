/**
 *
 */
package com.youzi.okredoo.adapter;

import android.content.Context;

import com.youzi.okredoo.R;
import com.youzi.okredoo.data.UserList;

/**
 * @author jack
 */
public class SelectUserAdapter extends AppBaseAdapter<UserList> {

    public SelectUserAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.select_user_item;
    }

}
