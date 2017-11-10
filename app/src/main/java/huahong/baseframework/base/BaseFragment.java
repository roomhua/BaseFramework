package huahong.baseframework.base;

import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

/**
 * Created by admin on 2017/11/10.
 * MVP     Fragment基类
 */

public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements BaseView{


    @Inject
    protected T mPresenter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }

    protected abstract void initInject();


    @Override
    public void showErrorMsg(String msg) {

    }



    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

}
