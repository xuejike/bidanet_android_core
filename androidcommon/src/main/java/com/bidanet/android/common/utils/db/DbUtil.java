package com.bidanet.android.common.utils.db;

import android.content.Context;

import com.bidanet.android.common.utils.Log;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.io.Serializable;

/**
 * Created by xuejike on 2017/6/5.
 */

public class DbUtil {
    public static DbUtil INSTANCE;
    private DB db;

    private DbUtil(String name, Context context){
        try {
            db = DBFactory.open(context, name);

        } catch (SnappydbException e) {
//            e.printStackTrace();
            Log.e("数据库初始化失败",e);
        }
    }
    public static DbUtil init(Context context){
        return init(context,"db");
    }
    public static DbUtil init(Context context,String name){
        INSTANCE = new DbUtil(name, context);
        return INSTANCE;
    }


    public DB getDb() {
        return db;
    }

    public<T extends Serializable> void get(String key, DbCallBack<T> callback, Class<T> cls){
        try {
            T t = db.get(key, cls);
            if (t==null){
                callback.noVal();
            }else{
                callback.have(t);
            }
        } catch (SnappydbException e) {
//            e.printStackTrace();
            Log.e("数据库异常",e);
            callback.error(e);
        }

    }
}
