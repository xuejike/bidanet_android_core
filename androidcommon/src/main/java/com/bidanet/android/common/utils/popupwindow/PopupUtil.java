package com.bidanet.android.common.utils.popupwindow;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.PaintDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


/**
 * Created by gange on 2017/11/20.
 */

public class PopupUtil {


    /**
     *
     * @param view
     * @param layout
     * @param t
     * @param dismiss  点击外围是否消失
     * @param gravity
     * @param popupId  BR 的ID
     * @param <T>
     * @return
     */
    public static <T extends BasePopupModel> PopupWindow showPopupWindow(final View view, int layout, T t, boolean dismiss, int gravity, int popupId) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(view.getContext()), layout, null, false);

        View contentView = binding.getRoot();
        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        t.setPopupWindow(popupWindow);
        binding.setVariable(popupId, t);

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        if (dismiss) {
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new PaintDrawable(0x00000000));
        }
        popupWindow.showAtLocation(view, gravity, 0, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) view.getContext()).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) view.getContext()).getWindow().setAttributes(lp);
            }
        });

        WindowManager.LayoutParams params = ((Activity) view.getContext()).getWindow().getAttributes();
        params.alpha = 0.5f;
        ((Activity) view.getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        ((Activity) view.getContext()).getWindow().setAttributes(params);

        return popupWindow;
    }


    /**
     *
     * @param view
     * @param layout
     * @param t
     * @param dismiss  点击外围是否消失
     * @param x
     * @param y
     * @param popupId  BR的ID
     * @param <T>
     * @return
     */
    public static <T extends BasePopupModel> PopupWindow showPopupWindowLocation(final View view, int layout, T t, boolean dismiss, int x, int y, int popupId) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(view.getContext()), layout, null, false);

        View contentView = binding.getRoot();
        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        t.setPopupWindow(popupWindow);
        binding.setVariable(popupId, t);

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        if (dismiss) {
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new PaintDrawable(0x00000000));
        }
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) view.getContext()).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) view.getContext()).getWindow().setAttributes(lp);
            }
        });

        WindowManager.LayoutParams params = ((Activity) view.getContext()).getWindow().getAttributes();
        params.alpha = 0.5f;
        ((Activity) view.getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        ((Activity) view.getContext()).getWindow().setAttributes(params);

        return popupWindow;
    }

}
