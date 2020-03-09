package com.aroominn.aroom.view.mine.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.utils.StatusBarUtil;

public class AboutActivity extends BaseActivity {


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
        return R.layout.activity_about;
    }
}
