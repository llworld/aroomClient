package com.aroominn.aroom.base;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.aroominn.aroom.R;
import com.aroominn.aroom.utils.DialogUtils;
import com.aroominn.aroom.utils.StatusBarUtils;
import com.aroominn.aroom.utils.customview.TitleBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BaseFragment extends Fragment implements BaseImpl {

    protected Activity context;
    public View rootView;
    private Dialog mProgressDialog;
    Unbinder unbinder;

    private CompositeDisposable disposables2Stop;// 管理Stop取消订阅者者
    private CompositeDisposable disposables2Destroy;// 管理Destroy取消订阅者者


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (disposables2Destroy != null) {
            throw new IllegalStateException("onCreate called multiple times");
        }
        disposables2Destroy = new CompositeDisposable();
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getContentViewId(), null);
            unbinder = ButterKnife.bind(this, rootView);
//            rootView.setFitsSystemWindows(true);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialog = DialogUtils.MyProgressDialog(context);
        initTitle(view, savedInstanceState);
        setListener(view, savedInstanceState);
    }

    public abstract int getContentViewId();

    public abstract void setListener(View view, Bundle savedInstanceState);

    public abstract void initData(Bundle savedInstanceState);

    public abstract void initTitle(View view, Bundle savedInstanceState);

    public View getRootView() {
        return rootView;
    }


    @SuppressLint("InlinedApi")
    public void initTitleBar(TitleBar mTitleBar, int titleId) {
        // 透明状态栏
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTitleBar.setImmersive(true);
        } else {
            mTitleBar.setImmersive(false);
        }
        mTitleBar.setBackgroundColor(getResources().getColor(R.color.white));
        mTitleBar.setLeftImagePadding(12);
        mTitleBar.setTitle(titleId);
        mTitleBar.setTitleSize(16);
        mTitleBar.setTitleColor(getResources().getColor(R.color.fontcolordeep));
        StatusBarUtils.StatusBarLightMode(getActivity(),StatusBarUtils.StatusBarLightMode(getActivity()));
    }

    @SuppressLint("InlinedApi")
    public void initTitleBar(TitleBar mTitleBar, String title) {
        // 透明状态栏
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTitleBar.setImmersive(true);
        } else {
            mTitleBar.setImmersive(false);
        }
        mTitleBar.setBackgroundColor(getResources().getColor(R.color.white));
        mTitleBar.setLeftImagePadding(12);
        mTitleBar.setTitle(title);
        mTitleBar.setTitleSize(16);
        mTitleBar.setTitleColor(getResources().getColor(R.color.fontcolordeep));
        StatusBarUtils.StatusBarLightMode(getActivity(),StatusBarUtils.StatusBarLightMode(getActivity()));
    }

    /**
     * 是否可显示菊花
     *
     * @return boolean
     */
    public boolean isValidContext() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context.isDestroyed() || context.isFinishing()) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * 是否可显示
     *
     * @param dialog
     * @return
     */
    public boolean canShowDialog(Dialog dialog) {
        if (isValidContext() && dialog != null && !dialog.isShowing()) {
            return true;
        }
        return false;
    }

    /**
     * 是否可关闭
     *
     * @return
     */
    public boolean canDismissDialog() {
        if (isValidContext()) {
            return true;
        }
        return false;
    }


    @Override
    public void showProgress(String msg) {
        if (canShowDialog(mProgressDialog)) {
            mProgressDialog.show();
        }
    }

    @Override
    public void dismissProgress() {
        if (canDismissDialog()) {
            mProgressDialog.dismiss();
        }
    }

    public boolean addRxStop(Disposable disposable) {
        if (disposables2Stop == null) {
            throw new IllegalStateException(
                    "addUtilStop should be called between onStart and onStop");
        }
        disposables2Stop.add(disposable);
        return true;
    }

    public boolean addRxDestroy(Disposable disposable) {
        if (disposables2Destroy == null) {
            throw new IllegalStateException(
                    "addUtilDestroy should be called between onCreate and onDestroy");
        }
        disposables2Destroy.add(disposable);
        return true;
    }

    public void remove(Disposable disposable) {
        if (disposables2Stop == null && disposables2Destroy == null) {
            throw new IllegalStateException("remove should not be called after onDestroy");
        }
        if (disposables2Stop != null) {
            disposables2Stop.remove(disposable);
        }
        if (disposables2Destroy != null) {
            disposables2Destroy.remove(disposable);
        }
    }

    public void onStart() {
        super.onStart();
        if (disposables2Stop != null) {
            throw new IllegalStateException("onStart called multiple times");
        }
        disposables2Stop = new CompositeDisposable();
    }

    public void onStop() {
        super.onStop();
        if (disposables2Stop == null) {
            throw new IllegalStateException("onStop called multiple times or onStart not called");
        }
        disposables2Stop.dispose();
        disposables2Stop = null;
    }

    public void onDestroy() {
        super.onDestroy();
        if (disposables2Destroy == null) {
            throw new IllegalStateException(
                    "onDestroy called multiple times or onCreate not called");
        }
        disposables2Destroy.dispose();
        disposables2Destroy = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
