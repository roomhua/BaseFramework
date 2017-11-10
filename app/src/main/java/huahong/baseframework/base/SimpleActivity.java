package huahong.baseframework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import huahong.baseframework.util.AppManager;
import huahong.baseframework.util.LogUtil;

/**
 * Created by admin on 2017/11/10.
 * 无mvp的Activity基类
 */

public abstract class SimpleActivity extends AppCompatActivity implements View.OnClickListener{


    private Unbinder mUnbinder;
    protected Activity mContext;
    private long mClickTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        AppManager.getAppManager().addActivity(this);


        oncreateView();
        initEventAndData();
    }

    protected void oncreateView() {}

    /**逻辑处理*/
    protected abstract void initEventAndData();

    /**布局*/
    protected abstract int getLayoutId();


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        AppManager.getAppManager().removeActivity(this);
    }



    /***
     * 防重复点击
     * @param v
     */
    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - mClickTime > 800) {
            LogUtil.e("SimpleActivity-->onClick",true);
            onViewClick(v);
        }
        mClickTime =  currentTime;
    }


    /**点击事件*/
    protected  void onViewClick(View v) {}




    /**
     * 界面跳转
     * @param c       跳转的activity的class
     * @param flag    界面是否销毁
     * @param parcelable    Intent带的参数,可以为null
     * @param name     Intent带的参数的名字
     */
    protected void startActivity(Class c, boolean flag, Parcelable parcelable, String name) {
        Intent intent = new Intent(this,c);
        if (parcelable != null) {
            intent.putExtra(name,parcelable);
        }

        startActivity(intent);

        if (flag) {
            finish();
        }
    }
}
