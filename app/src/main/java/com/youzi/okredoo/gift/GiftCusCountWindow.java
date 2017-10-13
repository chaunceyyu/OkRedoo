package com.youzi.okredoo.gift;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.youzi.okredoo.R;


public class GiftCusCountWindow extends BottomPushPopupWindow<Void> implements OnClickListener {

    private View view;
    ScrollView bottom;

    private LinearLayout llOne;
    private LinearLayout llTwo;
    private LinearLayout llThree;
    private LinearLayout llFour;
    private LinearLayout llFive;
    private LinearLayout llSix;
    private LinearLayout llSeven;
    private LinearLayout llEgt;
    private LinearLayout llNine;
    private LinearLayout llZero;
    private LinearLayout llDel;


    private GiftWindow giftWindow;

    private TextView tvNum;
    private TextView tvOk;

    public GiftCusCountWindow(Activity activity) {
        super(activity, activity, null);
    }




    @Override
    protected View generateCustomView(Void aVoid) {
        view = RelativeLayout.inflate(context, R.layout.pop_gift_cus_count, null);
        bottom = (ScrollView) view.findViewById(R.id.bottom);
        view.findViewById(R.id.pop_layout).setOnClickListener(this);

        tvNum = (TextView) view.findViewById(R.id.tvNum);

        llOne = (LinearLayout) view.findViewById(R.id.llOne);
        llTwo = (LinearLayout) view.findViewById(R.id.llTwo);
        llThree = (LinearLayout) view.findViewById(R.id.llThree);
        llFour = (LinearLayout) view.findViewById(R.id.llFour);
        llFive = (LinearLayout) view.findViewById(R.id.llFive);
        llSix = (LinearLayout) view.findViewById(R.id.llSix);
        llSeven = (LinearLayout) view.findViewById(R.id.llSeven);
        llEgt = (LinearLayout) view.findViewById(R.id.llEgt);
        llNine = (LinearLayout) view.findViewById(R.id.llNine);
        llZero = (LinearLayout) view.findViewById(R.id.llZero);
        llDel = (LinearLayout) view.findViewById(R.id.llDel);
        tvOk = (TextView) view.findViewById(R.id.tvOk);


        llOne.setOnClickListener(this);
        llTwo.setOnClickListener(this);
        llThree.setOnClickListener(this);
        llFour.setOnClickListener(this);
        llFive.setOnClickListener(this);
        llSix.setOnClickListener(this);
        llSeven.setOnClickListener(this);
        llEgt.setOnClickListener(this);
        llNine.setOnClickListener(this);
        llZero.setOnClickListener(this);
        llDel.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        return view;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_layout:
                dismiss();
                break;
            case R.id.llOne:
                upTvNum("1");
                break;
            case R.id.llTwo:
                upTvNum("2");
                break;
            case R.id.llThree:
                upTvNum("3");
                break;
            case R.id.llFour:
                upTvNum("4");
                break;
            case R.id.llFive:
                upTvNum("5");
                break;
            case R.id.llSix:
                upTvNum("6");
                break;
            case R.id.llSeven:
                upTvNum("7");
                break;
            case R.id.llEgt:
                upTvNum("8");
                break;
            case R.id.llNine:
                upTvNum("9");
                break;
            case R.id.llZero:
                upTvNum("0");
                break;
            case R.id.llDel:
                tvNumDel();
                break;
            case R.id.tvOk:
                giftWindow.setGiftCount(tvNum.getText().toString());
                dismiss();
                break;
            default:
                break;
        }
    }

    private void tvNumDel() {
        String tvCount = tvNum.getText().toString();
        if(tvCount.length()>0){
            tvCount = tvCount.substring(0,tvCount.length()-1);
            tvNum.setText(tvCount);
        }
    }

    private void upTvNum(String num) {
        String tvCount = tvNum.getText().toString();
        if(tvCount.length()<6){
            tvCount += num;
            tvNum.setText(tvCount);
        }else{
//            CommonHelper.showTip(activity,"最大五位数礼物");
        }
    }


    public void setGiftWindow(GiftWindow giftWindow) {
        this.giftWindow = giftWindow;
    }

}
