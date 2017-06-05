package com.bidanet.android.common.utils.http.impl;

import android.content.Context;

import com.aries.ui.widget.progress.UIProgressView;
import com.bidanet.android.common.utils.Log;
import com.bidanet.android.common.utils.http.LoadingDialog;

/**
 * Created by xuejike on 2017/6/5.
 */

public class DefaultLoadingDialog implements LoadingDialog {

    public Context context;
    private final UIProgressView uiProgressView;

    public DefaultLoadingDialog(Context context,String msg) {
        this.context = context;
        uiProgressView = new UIProgressView(context);
        uiProgressView.setMessage(msg);
    }

    public DefaultLoadingDialog(Context context) {
        this(context,"加载中");
    }



    @Override
    public void show() {
        Log.d("显示加载对话框");
        uiProgressView.show();
    }

    @Override
    public void dismiss() {
        Log.d("关闭加载对话框");
        uiProgressView.dismiss();
    }
}
