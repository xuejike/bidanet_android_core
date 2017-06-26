package com.bidanet.android.common.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



import java.util.List;
import java.util.Map;

/**
 * Created by xuejike on 2017/5/25.
 */

public class DataBindAdapter<T> extends BaseAdapter {

    public static int BIND_ITEM_ID;
    public static int BIND_ITEM_INDEX_ID;
    private Context mContext;
    private List<T> data;
    private int layout;
    private int itemId=BIND_ITEM_ID ;
    private int item_index=BIND_ITEM_INDEX_ID ;
    private Map<Integer,Object> commonData;

    public void setData(List<T> data) {
        this.data = data;
    }

    public DataBindAdapter(Context mContext, List<T> data, int layout) {
        this.mContext = mContext;
        this.data = data;
        this.layout = layout;
    }

    public DataBindAdapter(Context mContext, List<T> data, int layout, int itemId) {
        this.mContext = mContext;
        this.data = data;
        this.layout = layout;
        this.itemId = itemId;
    }

    public DataBindAdapter(Context mContext, List<T> data, int layout, Map<Integer, Object> commonData) {
        this.mContext = mContext;
        this.data = data;
        this.layout = layout;
        this.commonData = commonData;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding binding = null;
        CoinsDetailViewHolder holder = null;
        if (convertView == null) {
            holder = new CoinsDetailViewHolder();
            //获取item布局的binding
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    this.layout, parent, false);
            //获取布局
            convertView = binding.getRoot();
            //缓存binding到holder
            holder.setItemCoinsDetailBinding(binding);
            //设置Tag
            convertView.setTag(holder);
        } else {
            holder = (CoinsDetailViewHolder) convertView.getTag();
            binding =  holder.getItemCoinsDetailBinding();
        }
        //通过binding设置当前的item对象，然后它就会自动给textview赋值
        binding.setVariable(itemId,data.get(position));
        binding.setVariable(item_index , position);
        //绑定公有数据
        if (this.commonData!=null){
            for (Map.Entry<Integer, Object> entry : commonData.entrySet()) {
                binding.setVariable(entry.getKey(),entry.getValue());
            }

        }
        return convertView;
    }

    //viewholder类里只有一个binding对象和它的get,set方法
    private class CoinsDetailViewHolder {
        private ViewDataBinding itemCoinsDetailBinding;

        public void setItemCoinsDetailBinding(ViewDataBinding itemCoinsDetailBinding) {
            this.itemCoinsDetailBinding = itemCoinsDetailBinding;
        }

        public ViewDataBinding getItemCoinsDetailBinding() {
            return itemCoinsDetailBinding;
        }
    }
}
