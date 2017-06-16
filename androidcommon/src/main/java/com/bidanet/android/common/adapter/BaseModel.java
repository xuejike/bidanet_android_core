package com.bidanet.android.common.adapter;

import android.databinding.BaseObservable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by gange on 2017/6/16.
 */

public class BaseModel extends BaseObservable{

    public void setOnItemClick(AdapterView<?> parent, View view, int position, long id){
        Toast.makeText(view.getContext() , "点击了" + position  , Toast.LENGTH_SHORT).show();
    }

}
