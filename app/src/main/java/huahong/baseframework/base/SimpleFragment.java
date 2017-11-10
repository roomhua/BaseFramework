package huahong.baseframework.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import huahong.baseframework.util.LogUtil;

/**
 * Created by admin on 2017/11/10.
 * 无mvp的Fragment基类
 */

public abstract class SimpleFragment extends Fragment implements View.OnClickListener{


    protected Context mContext;
    private Unbinder mUnbinder;
    protected View mRootView;
    private long mClickTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (mContext == null) {
            mContext = container.getContext();
        }

        if (mRootView == null) {
            mRootView = getRootView(inflater,container);

        }else {
            ViewParent parent = mRootView.getParent();
            if (parent != null) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.removeView(mRootView);
            }
        }

        return mRootView;
    }



    protected abstract View getRootView(LayoutInflater inflater, ViewGroup container);


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnbinder = ButterKnife.bind(this, view);
        initEventAndData();
    }

    protected abstract void initEventAndData();


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    /***
     * 防重复点击
     * @param v
     */
    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - mClickTime > 800) {
            LogUtil.e("SimpleFragment-->onClick",true);
            onViewClick(v);
        }
        mClickTime =  currentTime;
    }


    protected  void onViewClick(View v) {}


    /**
     * 界面跳转
     * @param c       跳转的activity的class
     * @param parcelable    Intent带的参数,可以为null
     * @param name     Intent带的参数的名字
     */
    protected void startActivity(Class c, Parcelable parcelable, String name) {
        Intent intent = new Intent(mContext,c);
        if (parcelable != null) {
            intent.putExtra(name,parcelable);
        }
        mContext.startActivity(intent);
    }
}
