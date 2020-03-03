package com.aroominn.aroom.utils.retrofit;

import android.net.ParseException;
import android.widget.Toast;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.ToastUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.aroominn.aroom.utils.retrofit.DefaultObserver.ExceptionReason.CONNECT_ERROR;
import static com.aroominn.aroom.utils.retrofit.DefaultObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.aroominn.aroom.utils.retrofit.DefaultObserver.ExceptionReason.PARSE_ERROR;
import static com.aroominn.aroom.utils.retrofit.DefaultObserver.ExceptionReason.UNKNOWN_ERROR;

public abstract class DefaultObserver<T extends BasicResponse> implements Observer<T> {

    private BaseImpl mBaseImpl;
    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;

    public DefaultObserver(BaseImpl baseImpl) {
        mBaseImpl = baseImpl;
    }

    public DefaultObserver(BaseImpl baseImpl, boolean isShowLoading) {
        mBaseImpl = baseImpl;
        if (isShowLoading) {
            mBaseImpl.showProgress("");
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (isAddInStop) {    //  在onStop中取消订阅
            mBaseImpl.addRxStop(d);
        } else { //  在onDestroy中取消订阅
            mBaseImpl.addRxDestroy(d);
        }
    }

    @Override
    public void onNext(T response) {
        mBaseImpl.dismissProgress();
        if (response.getStatus_code() == 0) {
            onSuccess(response);
        } else {
            onFail(response);
        }
    }

    @Override
    public void onError(Throwable e) {
        mBaseImpl.dismissProgress();
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(PARSE_ERROR);
        } else {
            onException(UNKNOWN_ERROR);
        }
        L.e("rxjava","------rxjava----->" + e.getMessage());
    }

    @Override
    public void onComplete() {}

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为0
     *
     * @param response 服务器返回的数据
     */
    abstract public void onFail(T response);

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.showBottomToast(R.string.connect_error, Toast.LENGTH_SHORT);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.showBottomToast(R.string.connect_timeout, Toast.LENGTH_SHORT);
                break;

            case BAD_NETWORK:
                ToastUtils.showBottomToast(R.string.bad_network, Toast.LENGTH_SHORT);
                break;

            case PARSE_ERROR:
                ToastUtils.showBottomToast(R.string.parse_error, Toast.LENGTH_SHORT);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.showBottomToast(R.string.unknown_error, Toast.LENGTH_SHORT);
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}