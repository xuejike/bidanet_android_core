package com.bidanet.android.model;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bidanet.android.common.utils.ImageGlide;

import cn.campusapp.router.Router;

/**
 * Created by gange on 2017/6/13.
 */

public class BaseModel extends BaseObservable {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        ImageGlide.showImageWithErrAndPlaceholder(view.getContext(), view, url);
    }

    @BindingAdapter({"goNext"})
    public static void goNext(View view, final String url) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.open(url);
            }
        });
    }


}
