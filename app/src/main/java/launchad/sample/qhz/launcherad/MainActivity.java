package launchad.sample.qhz.launcherad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

import launchad.sample.qhz.launcherad.Contant.Constant;
import launchad.sample.qhz.launcherad.entity.AdverBean;

public class MainActivity extends AppCompatActivity {

    private boolean mDataInitStatus = false;//资源初始化状态
    private boolean mAdShowStatus = false;//广告加载状态
    private ImageView ad_iv;
    private TextView ad_time_tv;
    private int time;
    private Handler mDataHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDataInitStatus = true;
            enterToMain();
        }
    };
    private Handler mAdverHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AdverBean bean = (AdverBean)msg.obj;
            if(bean == null){
                enterToMain();
            }else{
                showAd(bean);
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (time <=0) {
                mAdShowStatus = true;
                if (mHandler != null && mHandler.hasMessages(0)) {
                    mHandler.removeMessages(0);
                }
                enterToMain();
            } else {
                time = time - 1;
                ad_time_tv.setText("跳过" + time);
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }

    };

    private void showAd(AdverBean bean) {
        ad_time_tv.setVisibility(View.VISIBLE);
        switch (bean.getAdvertType()) {
            case Constant.IMAGE_TYPE://Image类型
                final WeakReference<ImageView> imageViewWeakReference1 = new WeakReference<>(ad_iv);
                ImageView target1 = imageViewWeakReference1.get();
                if (target1 != null) {
                    Glide.with(this)
                            .load(bean.getAdvertUrl())
                            .into(target1);
                    time = 3;
                    ad_time_tv.setText("跳过" + time);
                    mHandler.sendEmptyMessageDelayed(0, 1000);
                }
                break;
            case Constant.GIF_TYPE://GIF类型
                final WeakReference<ImageView> imageViewWeakReference2 = new WeakReference<>(ad_iv);
                ImageView target2 = imageViewWeakReference2.get();
                if (target2!= null) {
                    Glide.with(this)
                            .load(bean.getAdvertUrl())
                            .into(target2);
                    time = 3;
                    ad_time_tv.setText("跳过" + time);
                    mHandler.sendEmptyMessageDelayed(0, 1000);
                }
                break;
            default:
                mAdShowStatus = true;
                enterToMain();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        getAd();
    }

    private void initView() {
        ad_iv = (ImageView)findViewById(R.id.ad_iv);
        ad_time_tv = (TextView)findViewById(R.id.ad_time_tv);
        ad_time_tv.setVisibility(View.GONE);
    }

    private void initData(){
        /**
         * 初始化应用数据,异步访问后端接口
         */
        mDataHandler.sendEmptyMessageDelayed(0, 2000); //模拟访问网络延时
    }
    private void enterToMain(){
        if(mDataInitStatus && mAdShowStatus){
            Intent intent = new Intent(MainActivity.this,MainPage.class);
            startActivity(intent);
            finish();
        }
    }

    public void getAd() {
        /**
         * 模拟异步获取广告数据
         */
        AdverBean bean = new AdverBean();
        bean.setAdvertType(Constant.GIF_TYPE);
        //网络图片地址,可替换
        bean.setAdvertUrl("file:///android_asset/sun.gif");
        Message message = new Message();
        message.obj = bean;
        mAdverHandler.sendMessageDelayed(message,2000);
    }
}
