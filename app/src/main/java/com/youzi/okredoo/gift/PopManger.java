package com.youzi.okredoo.gift;

import android.app.Dialog;
import android.widget.PopupWindow;

import java.util.ArrayList;

/**
 * Created by Storm on 2017/9/28 0028.
 */
public class PopManger {

    private ArrayList<PopupWindow> popupWindows = new ArrayList<>();
    private ArrayList<Dialog> dialogs = new ArrayList<>();

    public void addPopupWindow(PopupWindow pop) {
        if (pop != null) {
            popupWindows.add(pop);
        }
    }

    public void removePopupWindow(PopupWindow pop) {
        if (pop != null) {
            popupWindows.remove(pop);
        }
    }

    public void addDialog(Dialog dialog) {
        if (dialog != null) {
            dialogs.add(dialog);
        }
    }

    public void removeDialog(Dialog dialog) {
        if (dialog != null) {
            dialogs.remove(dialog);
        }
    }

    public void dismissAll() {
        for (PopupWindow pop : popupWindows) {
            if (pop.isShowing()) {
                pop.dismiss();
            }
        }
        for (Dialog dialog : dialogs) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
