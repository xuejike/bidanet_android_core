package com.bidanet.android.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by gange on 2017/6/13.
 */

public class ImageGlide {

    private static int errorImg;
    private static int placeholderImg;

    private static ImageGlide INSTACE;

    public static void init(int errorImg , int placeholderImg){
        INSTACE = new ImageGlide();
        INSTACE.errorImg = errorImg;
        INSTACE.placeholderImg = placeholderImg;
    }

    /**
     * 直接显示图片
     * @param context
     * @param imageView
     * @param url
     */
    public static void showImage(Context context , ImageView imageView , String url){
        Glide.with(context)
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void showImageWithErrAndPlaceholder(Context context , ImageView imageView , String url){
        Glide.with(context)
                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errorImg)
                .placeholder(placeholderImg)
                .into(imageView);
    }

    public static void downloadImg(Context context , String url){

    }


}
