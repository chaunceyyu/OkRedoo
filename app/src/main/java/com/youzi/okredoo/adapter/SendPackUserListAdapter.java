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
public class SendPackUserListAdapter extends AppBaseAdapter<UserList> {

    public SendPackUserListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.send_pack_user_list_item;
    }

}
