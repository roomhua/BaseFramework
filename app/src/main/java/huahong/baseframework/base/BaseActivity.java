package huahong.baseframework.base;

import javax.inject.Inject;

/**
 * Created by admin on 2017/11/10.
 * MVP     Activity基类
 */

public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView{

    @Inject
    protected T mPresenter;


    @Override
    protected void oncreateView() {
        super.oncreateView();

        initInject();

        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }



    /**dagger2注入Presenter对象*/
    protected abstract void initInject();


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

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
