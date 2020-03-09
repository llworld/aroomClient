package com.aroominn.aroom.view.mine.setting;

import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.aroominn.aroom.utils.ToastUtils;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.aroominn.aroom.base.BaseApplication.getContext;


/**
 * 更多设置
 */
public class SettingActivity extends BaseActivity implements SwitchButton.OnCheckedChangeListener {

//    @BindView(R.id.setting_title)
//    TitleBar mTitleBar;

    @BindView(R.id.setting_tone)
    SwitchButton settingTone;
    @BindView(R.id.setting_vibration)
    SwitchButton settingVibration;
    @BindView(R.id.setting_like)
    SwitchButton settingLike;
    @BindView(R.id.setting_follow)
    SwitchButton settingFollow;
    @BindView(R.id.setting_message)
    SwitchButton settingMessage;
    @BindView(R.id.setting_browse_home)
    SwitchButton settingBrowseHome;


    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.setPaddingSmart(context, ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));
        settingTone.setChecked(SharedUtils.getInstance().getTone());
        settingVibration.setChecked(SharedUtils.getInstance().getVibration());
    }

    @Override
    public void setListener() {
        settingTone.setOnCheckedChangeListener(this);
        settingVibration.setOnCheckedChangeListener(this);
        settingLike.setOnCheckedChangeListener(this);
        settingFollow.setOnCheckedChangeListener(this);
        settingMessage.setOnCheckedChangeListener(this);
        settingBrowseHome.setOnCheckedChangeListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_setting;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        switch (view.getId()) {
            case R.id.setting_tone:
                SharedUtils.getInstance().setTone(isChecked);

                break;
            case R.id.setting_vibration:
                SharedUtils.getInstance().setVibration(isChecked);
                break;
            case R.id.setting_like:
                break;
            case R.id.setting_follow:
                break;
            case R.id.setting_message:
                break;
            case R.id.setting_browse_home:
                break;
        }
    }


    @OnClick({R.id.setting_about, R.id.setting_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.setting_feedback:
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
        }
    }


}
