package com.bwei.weifeng20171219.model;

import com.bwei.weifeng20171219.CartBean;
import com.bwei.weifeng20171219.JoinBean;
import com.bwei.weifeng20171219.okhttp.AbstractUiCallBack;
import com.bwei.weifeng20171219.okhttp.OkhttpUtils;

/**
 * Created by acer on 2017/12/19.
 */

public class CartModel {

    public void getData(final ModelCallBack modelCallBack){
        //访问接口
        String path = "http://120.27.23.105/product/getCarts?source=android&uid=100";
        OkhttpUtils.getInstance().asy(null, path, new AbstractUiCallBack<CartBean>() {
            @Override
            public void success(CartBean cartBean) {
                modelCallBack.success(cartBean);
            }

            @Override
            public void failure(Exception e) {

            }

        });
    }


    public void deletaData(final JoinCallBack joinCallBack, String pid) {

        String path="http://120.27.23.105/product/deleteCart?source=android&uid=100&pid="+pid;
        OkhttpUtils.getInstance().asy(null, path, new AbstractUiCallBack<JoinBean>() {
            @Override
            public void success(JoinBean bean) {
                joinCallBack.success(bean);
            }

            @Override
            public void failure(Exception e) {

            }
        });

    }
}
