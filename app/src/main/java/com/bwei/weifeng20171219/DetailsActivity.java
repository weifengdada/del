package com.bwei.weifeng20171219;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.weifeng20171219.presenter.JoinCartPresenter;
import com.bwei.weifeng20171219.view.JoinView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity implements JoinView{

    @BindView(R.id.good_image)
    SimpleDraweeView goodImage;
    @BindView(R.id.good_name)
    TextView goodName;
    @BindView(R.id.good_price)
    TextView goodPrice;
    @BindView(R.id.shop_title)
    TextView shopTitle;
    @BindView(R.id.joinCart)
    Button joinCart;
    @BindView(R.id.buyNow)
    Button buyNow;
    private JoinCartPresenter joinCartPresenter;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String image = intent.getStringExtra("image");
        pid = intent.getStringExtra("pid");
        String price = intent.getStringExtra("goodPrice");
        String shopName = intent.getStringExtra("shopName");
        String name = intent.getStringExtra("name");

        goodImage.setImageURI(image);
        goodName.setText(name);
        goodPrice.setText("￥"+price);
        shopTitle.setText(shopName);
    }

    @OnClick({R.id.joinCart, R.id.buyNow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.joinCart:
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                joinCartPresenter = new JoinCartPresenter(this);
                joinCartPresenter.joinCart(pid);

                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.buyNow:
                Toast.makeText(this, "没钱买不起", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void success(JoinBean bean) {


    }
}
