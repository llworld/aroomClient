package com.aroominn.aroom.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.aroominn.aroom.MainActivity;
import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.im.IMListener;
import com.aroominn.aroom.presenter.LoginPresenter;
import com.aroominn.aroom.presenter.impl.LoginPresenterImpl;
import com.aroominn.aroom.utils.Const;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.ShareSaveUtil;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.ToastUtils;
import com.aroominn.aroom.utils.ValidatorUtil;
import com.aroominn.aroom.view.views.LoginView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

public class AccountFragment extends BaseFragment implements LoginView {

    @BindView(R.id.account_phone_et)
    EditText phoneET;

    @BindView(R.id.account_pass_et)
    EditText pwdET;

    String phoneTxt;
    String pwdTxt;
    public static final String TAG = AccountFragment.class.getName();
    private LoginPresenter loginPresenter;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_account;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

        phoneET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                phoneTxt = editable.toString();
            }
        });

        pwdET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pwdTxt = editable.toString();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        loginPresenter = new LoginPresenterImpl(this);

    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

    }

    @OnClick(R.id.account_login)
    public void onViewClicked(View view) {
        if (validate()) {


            JSONObject param = new JSONObject();
            try {
                param.put("phone", phoneET.getText().toString());
                param.put("password", pwdET.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loginPresenter.getAccountLogin(this, param);
        }
    }

    private boolean validate() {
        phoneTxt = phoneET.getText().toString();
        pwdTxt = pwdET.getText().toString();
        if (phoneTxt == null || pwdTxt == null)
            return false;
        if (!ValidatorUtil.isMobile(phoneTxt)) {
            ToastUtils.showBottomToast("请输入正确的手机号", 0);
            return false;
        }
        if (pwdTxt.length() < 6) {
            ToastUtils.showBottomToast("请输入6-18位字符组成的密码", 0);
            return false;
        }
        return true;
    }

 /*
 不在使用融云
    private void connect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                L.e("LoginActivity", "--onTokenIncorrect");
            }

            @Override
            public void onSuccess(String userid) {
                L.e("LoginActivity", "--onSuccess--" + userid);
//                Toast.makeText(MainActivity.this, "登录成功,用户：" + userid, Toast.LENGTH_SHORT).show();
                //服务器连接成功，跳转消息列表
//                startActivity(new Intent(context, MainActivity.class));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                L.e("LoginActivity", "--onError");
            }
        });
    }*/

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setVcode(String vcode) {

    }

    @Override
    public void setRegister(Boolean result) {

    }

    @Override
    public void setLogin(User user) {

        if (user.getStatus_code() == 0) {
            SharedUtils.getInstance().setLogin(true);
            SharedUtils.getInstance().setUser(user.getData());
            SharedUtils.getInstance().setUserID(user.getData().getId());
            SharedUtils.getInstance().setToken(user.getData().getToken());
            RongIM.connect(user.getData().getToken(), IMListener.getInstance().getConnectCallback());

            startActivity(new Intent(context, MainActivity.class));
            context.finish();
        }
    }
}
