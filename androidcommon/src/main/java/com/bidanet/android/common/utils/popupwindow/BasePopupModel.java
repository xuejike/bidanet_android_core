package com.bidanet.android.common.utils.popupwindow;

import android.databinding.BaseObservable;
import android.widget.PopupWindow;

import com.bidanet.android.common.adapter.BaseModel;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

/**
 * Created by gange on 2017/11/20.
 */

public class BasePopupModel extends BaseObservable {


    private PopupWindow popupWindow;

    public BasePopupModel() {
    }

    public BasePopupModel(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }



}
