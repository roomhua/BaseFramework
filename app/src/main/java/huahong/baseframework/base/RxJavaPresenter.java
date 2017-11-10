package huahong.baseframework.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by admin on 2017/11/10.
 * 基于Presenter控制订阅的生命周期
 */

public class RxJavaPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;

    private CompositeDisposable mCompositeDisposable;


    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    protected void addSubscribe(Disposable subscription) {

        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }


    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        unSubscribe();
    }
}
