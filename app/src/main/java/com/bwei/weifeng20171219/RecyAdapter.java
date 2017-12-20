package com.bwei.weifeng20171219;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.weifeng20171219.presenter.CartPresenter;
import com.bwei.weifeng20171219.presenter.JoinCartPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2017/12/19.
 */

public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyViewHolder> {
    Context context;
    List<CartBean.DataBean.ListBean> list;
    //存放商家的id和商家的名称的map集合
    private Map<String,String> map = new HashMap<>();
    public RecyAdapter(Context context) {
        this.context=context;
    }
    public void addData(CartBean bean) {
        //传进来的是bean对象
        if(list == null){
            list = new ArrayList<>();
        }
        //第一层遍历商家和商品
        for (CartBean.DataBean shop : bean.getData()){
            //把商品的id和商品的名称添加到map集合里 ,,为了之后方便调用
            map.put(shop.getSellerid(),shop.getSellerName());
            //第二层遍历里面的商品
            for (int i=0;i<shop.getList().size();i++){
                //添加到list集合里
                list.add(shop.getList().get(i));
            }
        }
        //调用方法 设置显示或隐藏 商铺名
        setFirst(list);
        notifyDataSetChanged();
    }

    private void setFirst(List<CartBean.DataBean.ListBean> list) {

        if(list.size()>0){
            //如果是第一条数据就设置isFirst为1
            list.get(0).setIsFirst(1);
            //从第二条开始遍历
            for (int i=1;i<list.size();i++){
                //如果和前一个商品是同一家商店的
                if (list.get(i).getSellerid() == list.get(i-1).getSellerid()){
                    //设置成2不显示商铺
                    list.get(i).setIsFirst(2);
                }else{//设置成1显示商铺
                    list.get(i).setIsFirst(1);
                    //如果当前条目选中,把当前的商铺也选中
                    if (list.get(i).isItem_check()==true){
                        list.get(i).setShop_check(list.get(i).isItem_check());
                    }
                }
            }
        }

    }

    @Override
    public RecyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recy_cart_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyAdapter.MyViewHolder holder, final int position) {
        /**
         * 设置商铺的 shop_checkbox和商铺的名字 显示或隐藏
         * */
        if(list.get(position).getIsFirst()==1){
            //显示商家
            holder.shop_checkbox.setVisibility(View.VISIBLE);
            holder.shop_name.setVisibility(View.VISIBLE);
            //设置shop_checkbox的选中状态
            holder.shop_checkbox.setChecked(list.get(position).isShop_check());
            holder.shop_name.setText(map.get(String.valueOf(list.get(position).getSellerid())));
        }else{//2
            //隐藏商家
            holder.shop_name.setVisibility(View.GONE);
            holder.shop_checkbox.setVisibility(View.GONE);
        }

        //拆分images字段
        final String[] split = list.get(position).getImages().split("\\|");
        //设置商品的图片
        holder.item_face.setImageURI(Uri.parse(split[0]));
        holder.item_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailsActivity.class);
                intent.putExtra("image",split[0]);
                intent.putExtra("pid",list.get(position).getPid()+"");
                intent.putExtra("goodPrice",list.get(position).getPrice()+"");
                intent.putExtra("shopName",map.get(String.valueOf(list.get(position).getSellerid())));
                intent.putExtra("name",list.get(position).getTitle());
                context.startActivity(intent);
            }
        });
        //控制商品的item_checkbox,,根据字段改变
        holder.item_checkbox.setChecked(list.get(position).isItem_check());
        holder.item_name.setText(list.get(position).getTitle());
        holder.item_price.setText(list.get(position).getPrice()+"");
        //调用customjiajian里面的方法设置 加减号中间的数字
        holder.customJiaJian.setEditText(list.get(position).getNum());

        //商铺的shop_checkbox点击事件 ,控制商品的item_checkbox
        holder.shop_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先改变数据源中的shop_check
                list.get(position).setShop_check(holder.shop_checkbox.isChecked());

                for (int i=0;i<list.size();i++){
                    //如果是同一家商铺的 都给成相同状态
                    if(list.get(position).getSellerid()==list.get(i).getSellerid()){
                        //当前条目的选中状态 设置成 当前商铺的选中状态
                        list.get(i).setItem_check(holder.shop_checkbox.isChecked());
                    }
                }
                //刷新适配器
                notifyDataSetChanged();
                //调用求和的方法
                sum(list);
            }
        });

        //商品的item_checkbox点击事件,控制商铺的shop_checkbox
        holder.item_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先改变数据源中的item_checkbox
                list.get(position).setItem_check(holder.item_checkbox.isChecked());

                //反向控制商铺的shop_checkbox
                for (int i=0;i<list.size();i++){
                    for (int j=0;j<list.size();j++){
                        //如果两个商品是同一家店铺的 并且 这两个商品的item_checkbox选中状态不一样
                        if(list.get(i).getSellerid()==list.get(j).getSellerid() && !list.get(j).isItem_check()){
                            //就把商铺的shop_checkbox改成false
                            list.get(i).setShop_check(false);
                            break;
                        }else{
                            //同一家商铺的商品 选中状态都一样,就把商铺shop_checkbox状态改成true
                            list.get(i).setShop_check(true);
                        }
                    }
                }

                //更新适配器
                notifyDataSetChanged();
                //调用求和的方法
                sum(list);
            }
        });

        //删除条目的点击事件
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent(context,DeleteActivity.class);
//                intent.putExtra("pid",list.get(position).getPid()+"");
//                context.startActivity(intent);
//http://120.27.23.105/product/deleteCart?source=android&uid=100&pid=1
                int pi = list.get(position).getPid();
                String pid = String.valueOf(pi);
                CartPresenter.delete(pid);
                //删除完当前的条目 重新判断商铺的显示隐藏
                 list.remove(position);//移除集合中的当前数据
                setFirst(list);
                //调用重新求和
                sum(list);
                notifyDataSetChanged();
            }
        });

        //加减号的监听,
        holder.customJiaJian.setCustomListener(new CustomJiaJian.CustomListener() {
            @Override
            public void jiajian(int count) {
                //改变数据源中的数量
                list.get(position).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }

            @Override
            //输入值 求总价
            public void shuRuZhi(int count) {
                list.get(position).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }
        });
    }

    /**
     * 计算总价的方法
     * */
    private void sum(List<CartBean.DataBean.ListBean> list){
        int totalNum = 0;//初始的总价为0
        float totalMoney = 0.0f;
        boolean allCheck = true;
        for (int i=0;i<list.size();i++){
            //把 已经选中的 条目 计算价格
            if (list.get(i).isItem_check()){
                totalNum += list.get(i).getNum();
                totalMoney += list.get(i).getNum() * list.get(i).getPrice();
            }else{
                //如果有个未选中,就标记为false
                allCheck = false;
            }
        }

        //接口回调出去 把总价 总数量 和allcheck 传给view层
        updateListener.setTotal(totalMoney+"",totalNum+"",allCheck);
    }

    //view层调用这个方法, 点击quanxuan按钮的操作
    public void quanXuan(boolean checked) {
        for (int i=0;i<list.size();i++){
            list.get(i).setShop_check(checked);
            list.get(i).setItem_check(checked);

        }
        notifyDataSetChanged();
        sum(list);
    }
    @Override
    public int getItemCount() {
        return list ==null ? 0 : list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox shop_checkbox;
        private final TextView shop_name;
        private final CheckBox item_checkbox;
        private final TextView item_name;
        private final TextView item_price;
        private final CustomJiaJian customJiaJian;
        private final ImageView item_delete;
        private final ImageView item_face;

        public MyViewHolder(View itemView) {
            super(itemView);
            shop_checkbox = (CheckBox) itemView.findViewById(R.id.shop_checkbox);
            shop_name = (TextView) itemView.findViewById(R.id.shop_name);
            item_checkbox = (CheckBox) itemView.findViewById(R.id.item_checkbox);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            item_price = (TextView) itemView.findViewById(R.id.item_price);
            customJiaJian = (CustomJiaJian) itemView.findViewById(R.id.custom_jiajian);
            item_delete = (ImageView) itemView.findViewById(R.id.item_delete);
            item_face = (ImageView) itemView.findViewById(R.id.item_face);
        }
    }
    UpdateListener updateListener;
    public void setUpdateListener(UpdateListener updateListener){
        this.updateListener = updateListener;
    }
    //接口
    public interface UpdateListener{
        public void setTotal(String total,String num,boolean allCheck);
    }
    
}
