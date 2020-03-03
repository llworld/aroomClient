package com.aroominn.aroom.view.mine.setting;

import android.os.Bundle;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.suke.widget.SwitchButton;

import butterknife.BindView;

/**
 * 更多设置
 */
public class SettingActivity extends BaseActivity implements SwitchButton.OnCheckedChangeListener{

//    @BindView(R.id.setting_title)
//    TitleBar mTitleBar;

    @Override
    public void initView(Bundle savedInstanceState) {

//        initTitleBar(mTitleBar,R.string.title_setting);

//        StatusBarUtil.setColor(SettingActivity.this,1);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_setting;
    }


    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {

    }
}
