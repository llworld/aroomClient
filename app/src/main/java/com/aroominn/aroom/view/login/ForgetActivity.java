package com.aroominn.aroom.view.login;

import android.os.Bundle;
import android.view.ViewGroup;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.utils.StatusBarUtil;

/**
 * 忘记密码
 */
public class ForgetActivity extends BaseActivity {

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.setPaddingSmart(context, ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_forget;
    }
}
