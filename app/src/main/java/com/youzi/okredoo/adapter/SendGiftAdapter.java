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
public class SendGiftAdapter extends AppBaseAdapter<UserList> {

    public SendGiftAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.send_gift_item;
    }

}
