package com.aroominn.aroom.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.aroominn.aroom.R;
import com.aroominn.aroom.utils.DialogUtils;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.aroominn.aroom.utils.customview.TitleBar;

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


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
        BaseApplication.instance.addActivity(this);
        context = this;
        mProgressDialog = DialogUtils.MyProgressDialog(this);
        initView(savedInstanceState);
        setListener();
        initData();

        /*状态栏文字颜色设置为黑  应该还达到了沉浸式效果*/
        View decor = this.getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        StatusBarUtil.immersive(this);
//        StatusBarUtil.StatusBarLightMode(this, StatusBarUtil.StatusBarLightMode(this));
//        StatusBarUtil.setMargin(context, mTitleBar);
//        StatusBarUtil.setPaddingSmart(context, ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));

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
        StatusBarUtil.StatusBarLightMode(this, StatusBarUtil.StatusBarLightMode(this));
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
        StatusBarUtil.StatusBarLightMode(this, StatusBarUtil.StatusBarLightMode(this));
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
