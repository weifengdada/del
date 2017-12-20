package com.bwei.weifeng20171219.presenter;

import com.bwei.weifeng20171219.CartBean;
import com.bwei.weifeng20171219.JoinBean;
import com.bwei.weifeng20171219.MainActivity;
import com.bwei.weifeng20171219.model.CartModel;
import com.bwei.weifeng20171219.model.JoinCallBack;
import com.bwei.weifeng20171219.model.ModelCallBack;
import com.bwei.weifeng20171219.view.CartView;
import com.bwei.weifeng20171219.view.JoinView;

/**
 * Created by acer on 2017/12/19.
 */

public class CartPresenter {
    CartView cartView;
    static CartModel cartModel;
    static JoinView joinView;
    public CartPresenter(CartView cartView, JoinView joinView) {
        this.cartView=cartView;
        this.cartModel=new CartModel();
        this.joinView=joinView;
    }




    public void getData() {

       cartModel.getData(new ModelCallBack() {
           @Override
           public void success(CartBean bean) {
               if(cartView!=null){

                   cartView.success(bean);
               }
           }
       });



    }
    public static void delete(String pid) {

        cartModel.deletaData(new JoinCallBack() {

            @Override
            public void success(JoinBean bean) {
                if(joinView!=null){

                    joinView.success(bean);
                }
            }
        },pid);

    }
}
