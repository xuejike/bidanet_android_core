# bidanet_android_core

# 功能说明

# 网络

# databinding


封装好的自定义标签：

1、图片加载的：<br/>
封装了标签imageUrl，可以加载网络图片，填入地址直接加载图片
<br/>
   `
        <ImageView
            android:layout_width="match_parent" 
            android:layout_height="wrap_content"
            app:imageUrl="@{item.imageUrl}"
            app:goNext="@{`activity://testSecond/`}"/>
`<br/>
2、页面跳转：<br/>

封装了goNext标签：传入路由使用的字符串，点击控件跳转页面，如果需要携带简单的参数可以直接写在字符串中，如果带对象请另写方法<br/>
    `
        <ImageView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:imageUrl="@{item.imageUrl}"
            app:goNext="@{`activity://testSecond/`}"/>
     `<br/>
3、ListView 显示：<br/>
使用adapter_data标签传入数据List，使用adapter_view传入item页面，直接显示<br/>
  `
        <ListView
            app:adapter_data="@{listData}"
            app:adapter_view="@{@layout/item}"
            app:on_item_click="@{item}"
            android:layout_width="match_parent"
            android:layout_height="200dp">
        </ListView>
   `<br/>
 4、ListView的 item点击事件：<br/>
 使用on_item_click标签，传入当前页面绑定的Model<br/>
 `
         <ListView
            app:adapter_data="@{listData}"
            app:adapter_view="@{@layout/item}"
            app:on_item_click="@{item}"
            android:layout_width="match_parent"
            android:layout_height="200dp">
        </ListView>
 `<br/>
 5、页面的foreach 方法显示view<br/>
 标签 foreach_data传入数据list ，标签foreach_view传入显示页面 ， 标签item_id传入item页面使用的BR的id<br/>
 `
         <LinearLayout
            app:foreach_data="@{listData}"
            app:foreach_view="@{@layout/item}"
            app:item_id="@{itemId}"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal">
        </LinearLayout>
 `<br/>
 
# snappyDB

# android Router


[![](https://jitpack.io/v/xuejike/bidanet_android_core.svg)](https://jitpack.io/#xuejike/bidanet_android_core)


