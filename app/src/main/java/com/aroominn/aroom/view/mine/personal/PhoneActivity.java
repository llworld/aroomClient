package com.aroominn.aroom.view.mine.personal;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.presenter.PersonalPresenter;
import com.aroominn.aroom.presenter.impl.PersonalPresenterImpl;
import com.aroominn.aroom.utils.PublicUtils;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.aroominn.aroom.utils.TimerUtils;
import com.aroominn.aroom.view.views.PersonalView;

import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改手机号
 */

public class PhoneActivity extends BaseActivity implements PersonalView {

    @BindView(R.id.phone_phone)
    EditText phone;

    @BindView(R.id.phone_vcode)
    EditText vCode;

    @BindView(R.id.phone_getVcode)
    TextView getCode;

    private String p;
    private PersonalPresenter personalPresenter;

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.setPaddingSmart(context, ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));

    }

    @Override
    public void setListener() {

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        vCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
    }

    @Override
    public void initData() {
        personalPresenter = new PersonalPresenterImpl(this);

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_phone;
    }

    @OnClick({R.id.phone_getVcode, R.id.phone_alter})
    public void OnViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_getVcode:
                p = phone.getText().toString();
                if (PublicUtils.isPhoneNumAvaliable(p)) {
                    requestSms(p);
                } else {
                    Snackbar.make(phone, "手机号格式错误", Snackbar.LENGTH_LONG);
                }

                break;
            case R.id.phone_alter:

                String v = vCode.getText().toString();
                if (v.length() == 6) {
                    requestPhone(v);
                }else {
                    Snackbar.make(vCode, "请输入完整的验证码", Snackbar.LENGTH_LONG);
                }
                break;
        }
    }

    private void requestPhone(String v) {
        JSONObject param=new JSONObject();
        try {
            param.put("phone",p);
            param.put("code",v);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        personalPresenter.getUser(this,param);
    }

    private void requestSms(String phone) {
        JSONObject param = new JSONObject();
        try {
            param.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        personalPresenter.getSms(this, param);

        //
        TimerUtils timerUtils = new TimerUtils(context, getCode);
        timerUtils.startTimer();
    }

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public void setPhoneSms(String sms) {

    }

}