/**
 *
 */
package com.youzi.okredoo.adapter;

import android.content.Context;

import com.youzi.okredoo.R;
import com.youzi.okredoo.model.TongJi1;

import java.util.ArrayList;

/**
 * @author jack
 */
public class TongJiAdapter extends AppBaseAdapter<ArrayList<TongJi1>> {

    public TongJiAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.tong_ji_item;
    }

}
