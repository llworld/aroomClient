package com.aroominn.aroom.view.mine.setting;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.presenter.SettingPresenter;
import com.aroominn.aroom.presenter.impl.SettingPresenterImpl;
import com.aroominn.aroom.view.views.SettingView;
import com.deadline.statebutton.StateButton;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity implements SettingView {

    @BindView(R.id.feedback_text)
    TextView text;
    @BindView(R.id.feedback_submit)
    StateButton feedbackSubmit;

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_feed_back;
    }


    @OnClick(R.id.feedback_submit)
    public void onViewClicked() {
        String content = text.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Snackbar.make(text, "请输入意见反馈", Snackbar.LENGTH_SHORT).show();
            return;
        }
        SettingPresenter settingPresenter = new SettingPresenterImpl(this);
        JSONObject param = new JSONObject();
        try {
            param.put("text", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        settingPresenter.feedBack(this, param);
    }

    @Override
    public void onSuccess(Result result) {

    }

    @Override
    public void onError(BasicResponse response, String url) {

    }


}
