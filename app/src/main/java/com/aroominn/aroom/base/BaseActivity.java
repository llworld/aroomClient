package com.aroominn.aroom.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;


import com.aroominn.aroom.R;
import com.aroominn.aroom.utils.DialogUtils;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.StatusBarUtils;
import com.aroominn.aroom.utils.customview.TitleBar;
import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Activity父类
 */
public abstract class BaseActivity extends BaseRxActivity implements BaseImpl {

    public Context context;
    public String TAG = this.getClass().getSimpleName();

    public Dialog mProgressDialog;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
        BaseApplication.instance.addActivity(this);
        context = this;
        mProgressDialog = DialogUtils.MyProgressDialog(this);
        initView(savedInstanceState);
        setListener();
        initData();
    }

    public abstract void initView(Bundle savedInstanceState);

    public abstract void setListener();

    public abstract void initData();

    public abstract int getContentViewId();

    @SuppressLint("InlinedApi")
    public void initTitleBar(TitleBar mTitleBar, int titleId) {
        // 透明状态栏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTitleBar.setImmersive(true);
        } else {
            mTitleBar.setImmersive(false);
        }
        mTitleBar.setBackgroundColor(getResources().getColor(R.color.white));
        mTitleBar.setLeftImageResource(R.drawable.activity_title_back);
        mTitleBar.setLeftClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
        mTitleBar.setLeftImagePadding(12);
        mTitleBar.setTitle(titleId);
        mTitleBar.setTitleSize(16);
        mTitleBar.setTitleColor(getResources().getColor(R.color.fontcolordeep));
        StatusBarUtils.StatusBarLightMode(this, StatusBarUtils.StatusBarLightMode(this));
    }


    @SuppressLint("InlinedApi")
    public void initTitleBar(TitleBar mTitleBar, String title) {
        // 透明状态栏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
        StatusBarUtils.StatusBarLightMode(this, StatusBarUtils.StatusBarLightMode(this));
    }

    /**
     * 是否可显示菊花
     * 加载数据时的加载圈圈
     *
     * @return boolean
     */
    public boolean isValidContext() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isDestroyed() || isFinishing()) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        BaseApplication.instance.activitys.remove(this);
    }

    //	@Override
    //	protected void onResume() {
    //		JPushInterface.onResume(this);
    //		super.onResume();
    //	}
    //
    //	@Override
    //	protected void onPause() {
    //		JPushInterface.onPause(this);
    //		super.onPause();
    //	}
}
