package huahong.baseframework.base;

/**
 * Created by admin on 2017/11/10.
 */

public interface BasePresenter<T extends BaseView> {

    /**绑定*/
    void attachView(T view);

    /**解绑*/
    void detachView();
}
