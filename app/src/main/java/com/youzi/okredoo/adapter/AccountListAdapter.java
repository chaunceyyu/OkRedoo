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
public class AccountListAdapter extends AppBaseAdapter<UserList> {

    public AccountListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.account_list_item;
    }

}
