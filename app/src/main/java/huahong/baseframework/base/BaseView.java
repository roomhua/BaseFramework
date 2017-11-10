package huahong.baseframework.base;

/**
 * Created by admin on 2017/11/10.
 */

public interface BaseView {


    void showErrorMsg(String msg);

    //=======  State  =======
    void stateError();

    void stateEmpty();

    void stateLoading();

    void stateMain();
}
