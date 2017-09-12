package com.bidanet.android.common.adapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.WrapperListAdapter;

import com.bidanet.android.common.BR;
import com.bidanet.android.common.exception.BaseException;
import com.bidanet.android.common.utils.ImageGlide;
import com.bidanet.android.common.utils.Log;

import java.util.List;

import cn.campusapp.router.Router;

/**
 * Created by gange on 2017/6/16.
 */

public class XmlLabelAdapter {


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

    @BindingAdapter({"adapter_data", "adapter_view"})
    public static void setAdapter(AdapterView adapterView , List data , int view){
        Adapter adapter = adapterView.getAdapter();
        if (adapter==null){
            adapterView.setAdapter(new DataBindAdapter(adapterView.getContext() , data , view));
        }else{
            if (adapter instanceof WrapperListAdapter){
                adapter=((WrapperListAdapter) adapter).getWrappedAdapter();
            }

            if (adapter instanceof BaseAdapter){
                ((BaseAdapter) adapter).notifyDataSetChanged();
            }else{
//                throw new BaseException("适配器并不是 BaseAdapter");
                Log.d("无法自动刷新");
            }
        }

    }

    @BindingAdapter({"on_item_click"})
    public static <T extends BaseModel> void setOnItemClick(AdapterView adapterView , final T t){
        adapterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                t.setOnItemClick(parent , view , position , id);
            }
        });
    }

    @BindingAdapter({"foreach_data" ,"foreach_view" , "item_id"})
    public static void foreach(ViewGroup viewGroup , List data , int view , int itemId){
        for (int i = 0; i < data.size(); i++) {
            ViewDataBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), view, viewGroup, false);
            inflate.setVariable(itemId , data.get(i));
            viewGroup.addView(inflate.getRoot());
        }
    }



}
