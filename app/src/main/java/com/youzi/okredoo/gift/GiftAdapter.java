package com.youzi.okredoo.gift;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzi.okredoo.App;
import com.youzi.okredoo.R;
import com.youzi.okredoo.model.Gift;
import com.youzi.okredoo.util.WidgetUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 礼物Adapter
 * Created by houjingwei on 15/11/4.
 */
public class GiftAdapter extends BaseAdapter {
    Activity activity;
    Context context;
    GiftWindow gw;
    List<Gift> voList = new ArrayList<Gift>();

    int choisePosition = -1;

    private Map<Integer, View> viewMap = new HashMap<Integer, View>();

    private ScaleAnimation animation;//放大动画

    private Drawable coinDrawable;

    public GiftAdapter(Activity activity, GiftWindow giftWindow) {
        this.activity = activity;
        this.context = activity;
        this.gw = giftWindow;
        animation = new ScaleAnimation(1f, 1.25f, 1f, 1.25f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return (float) Math.sin(input * Math.PI);
            }
        });
        animation.setDuration(1500);
        animation.setRepeatCount(Integer.MAX_VALUE);
        coinDrawable = activity.getResources().getDrawable(R.drawable.live_coin);
        coinDrawable.setBounds(0, 0, WidgetUtil.dip2px(activity, 10), WidgetUtil.dip2px(activity, 10));
    }

    @Override
    public int getCount() {
        return voList.size();
    }

    @Override
    public Object getItem(int position) {
        return voList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = this.viewMap.get(position);
        if (null == rowView) {
            final Gift gift = (Gift) getItem(position);
            rowView = LayoutInflater.from(context).inflate(R.layout.gift_list_item, null);
            final ImageView icon = (ImageView) rowView.findViewById(R.id.ivIcon);
            TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
            TextView tvExp = (TextView) rowView.findViewById(R.id.tvExp);
            ImageView ivChoise = (ImageView) rowView.findViewById(R.id.ivChoise);
            RelativeLayout giftRoot = (RelativeLayout) rowView.findViewById(R.id.giftItem);
            final View giftbg = rowView.findViewById(R.id.giftbg);
            giftRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if (Integer.parseInt(gift.getPrice()) <= 18) {
                    if (TextUtils.isEmpty(gift.getReciveAnim())) { //没有礼物动画的可以选择数量
                        //可以选择数量
                        gw.showChoiseNum();
                    } else {
                        //不可以选择数量
                        gw.hideChoiseNum();
                    }
                    clearSelected();
                    choisePosition = position;
                    giftbg.setBackgroundResource(R.drawable.bg_gift_selected);
                    icon.startAnimation(animation);
                    gw.setChoiseGift(gift);

                }
            });


            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) giftRoot.getLayoutParams();
            params.width = params.height = App.screenWidth / 4;
            giftRoot.setLayoutParams(params);

            tvTitle.setText(gift.getPrice());
            tvTitle.setCompoundDrawables(coinDrawable, null, null, null);
            tvExp.setText("+" + gift.getExp() + "土豆泥");


            RelativeLayout.LayoutParams iconParams = (RelativeLayout.LayoutParams) icon.getLayoutParams();
            iconParams.width = (App.screenWidth / 8);
            iconParams.height = (App.screenWidth / 8);
            icon.setLayoutParams(iconParams);

            Glide.with(activity).load( gift.getIcon()).into(icon);
//            ImageUtils.displayImagePure(icon, gift.getIcon());
            viewMap.put(position, rowView);
        }

        return rowView;
    }

    public void clearSelected() {
        if (choisePosition >= 0) {
            View oldView = viewMap.get(choisePosition);
            oldView.findViewById(R.id.giftbg).setBackgroundColor(0);
            oldView.findViewById(R.id.ivIcon).setAnimation(null);
        }
        choisePosition = -1;
    }


    public void clear() {
        viewMap.clear();
        voList.clear();
        notifyDataSetChanged();
    }

    public void addList(List<Gift> ls) {
        if (ls != null) {
            voList.addAll(ls);

        }
        notifyDataSetChanged();
    }


}
