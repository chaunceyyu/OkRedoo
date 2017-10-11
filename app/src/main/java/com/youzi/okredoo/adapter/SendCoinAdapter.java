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
public class SendCoinAdapter extends AppBaseAdapter<UserList> {

    public SendCoinAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.send_coin_item;
    }

}
