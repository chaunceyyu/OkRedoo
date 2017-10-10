/**
 *
 */
package com.youzi.okredoo.adapter;

import android.content.Context;

import com.youzi.okredoo.R;

import java.util.ArrayList;

/**
 * @author jack
 */
public class ListenerDataListAdapter extends AppBaseAdapter<ArrayList<String>> {

    public ListenerDataListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.listener_data_list_item;
    }

}
