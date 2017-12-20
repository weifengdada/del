package com.bwei.weifeng20171219.presenter;

import com.bwei.weifeng20171219.DetailsActivity;
import com.bwei.weifeng20171219.JoinBean;
import com.bwei.weifeng20171219.model.JoinCallBack;
import com.bwei.weifeng20171219.model.JoinModel;
import com.bwei.weifeng20171219.view.JoinView;

/**
 * Created by acer on 2017/12/19.
 */

public class JoinCartPresenter {

    JoinView joinView;
    JoinModel joinModel;
    public JoinCartPresenter(JoinView joinView) {
        this.joinView=joinView;
        this.joinModel=new JoinModel();
    }

    public void joinCart(String pid){

           joinModel.joinCart(new JoinCallBack() {
               @Override
               public void success(JoinBean bean) {
                  if(joinView!=null){
                      joinView.success(bean);
                  }
               }
           },pid);

    }



}
