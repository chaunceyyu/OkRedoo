package com.youzi.okredoo.gift;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.youzi.okredoo.R;


/**
 * 从下方弹出的PopupWindow,仿iphone效果，增加半透明蒙层。
 * <p>
 * 实现原理：<br>
 * 在弹出自定义的PopupWindow时，增加一个半透明蒙层view到窗口，并置于PopupWindow下方。
 * </p>
 * <p>
 * 使用方法：<br>
 * 继承BottomPushPopupWindow，编写generateCustomView添加自定义的view，调用show方法显示。
 * </p>
 *
 * @author y
 */
public abstract class BottomPushPopupWindow<T> extends PopupWindow {

    protected Activity activity;
    protected Context context;
    private WindowManager wm;
    private View maskView;
    protected boolean hasMask = true;

    private PopManger popManger;

    @SuppressWarnings("deprecation")
    public BottomPushPopupWindow(Context context, Activity activity, T t) {
        super(context);
        this.context = context;
        this.activity = activity;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initType();
        setContentView(generateCustomView(t));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        setAnimationStyle(R.style.Animations_BottomPush);
    }

    public void setPopManger(PopManger popManger) {
        this.popManger = popManger;
    }

    public PopManger getPopManger() {
        return popManger;
    }

    private void addToManager() {
        if (popManger == null) {
            return;
        }
        popManger.addPopupWindow(this);
        super.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                popManger.removePopupWindow(BottomPushPopupWindow.this);
            }
        });
    }

    protected abstract View generateCustomView(T t);

    @TargetApi(23)
    private void initType() {
        // 解决华为手机在home建进入后台后，在进入应用，蒙层出现在popupWindow上层的bug。
        // android4.0及以上版本都有这个hide方法，根据jvm原理，可以直接调用，选择android6.0版本进行编译即可。
        setWindowLayoutType(WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL);
    }

    /**
     * 设置是否有蒙层
     *
     * @param hasMask
     */
    public BottomPushPopupWindow<T> showMask(boolean hasMask) {
        this.hasMask = hasMask;
        return this;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        try {
            if (hasMask) {
                addMaskView(parent.getWindowToken());
            }
            super.showAtLocation(parent, gravity, x, y);
            addToManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        try {
            if (hasMask) {
                addMaskView(anchor.getWindowToken());
            }
            super.showAsDropDown(anchor, xoff, yoff);
            addToManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            removeMaskView();
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示在界面的底部
     */
    public void show(Activity activity) {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void show(Activity activity, PopManger popManger) {
        setPopManger(popManger);
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void setOnDismissListener(final OnDismissListener onDismissListener) {
        if (popManger != null) {
            super.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    popManger.removePopupWindow(BottomPushPopupWindow.this);
                    if (onDismissListener != null) {
                        onDismissListener.onDismiss();
                    }
                }
            });
        } else {
            super.setOnDismissListener(onDismissListener);
        }
    }

    private void addMaskView(IBinder token) {
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.MATCH_PARENT;
        p.format = PixelFormat.TRANSLUCENT;
        p.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        p.token = token;
        p.windowAnimations = android.R.style.Animation_Toast;
        maskView = new View(context);
        maskView.setBackgroundColor(Color.parseColor("#70000000"));
        maskView.setFitsSystemWindows(false);
        // 华为手机在home建进入后台后，在进入应用，蒙层出现在popupWindow上层，导致界面卡死，
        // 这里新增加按bug返回。
        // initType方法已经解决该问题，但是还是留着这个按back返回功能，防止其他手机出现华为手机类似问题。
        maskView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    removeMaskView();
                    return true;
                }
                return false;
            }
        });
        wm.addView(maskView, p);
    }

    private void removeMaskView() {
        if (maskView != null) {
            wm.removeViewImmediate(maskView);
            maskView = null;
        }
    }


}