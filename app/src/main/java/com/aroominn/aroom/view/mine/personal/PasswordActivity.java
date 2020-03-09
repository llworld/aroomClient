package com.aroominn.aroom.view.mine.personal;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.presenter.PersonalPresenter;
import com.aroominn.aroom.presenter.impl.PersonalPresenterImpl;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.ToastUtils;
import com.aroominn.aroom.view.views.PersonalView;

import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 密码
 */
public class PasswordActivity extends BaseActivity implements PersonalView {

    @BindView(R.id.password_new)
    EditText newPassword;

    @BindView(R.id.password_again)
    EditText passwordAgain;
    private String newPwd;
    private String pwdAgain;


    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 6 || editable.length() > 16) {
                    Snackbar.make(newPassword, "请输入六至十六位的密码", Snackbar.LENGTH_SHORT).show();
                }
                newPwd = editable.toString();
            }
        });
        passwordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 6 || editable.length() > 16) {
                    Snackbar.make(newPassword, "请输入六至十六位的密码", Snackbar.LENGTH_SHORT).show();
                }
                pwdAgain = editable.toString();
                if (!newPwd.equals(pwdAgain)) {
                    Snackbar.make(newPassword, "两次密码输入不一致", Snackbar.LENGTH_SHORT);

                }
            }
        });
    }

    @Override
    public void initData() {


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_password;
    }

    @OnClick(R.id.password_alter_pwd)
    public void OnViewClicked(View view) {

        if (!newPwd.equals(pwdAgain)) {
            Snackbar.make(newPassword, "两次密码输入不一致", Snackbar.LENGTH_SHORT).show();
        }

        PersonalPresenter personalPresenter = new PersonalPresenterImpl(this);
        JSONObject param = new JSONObject();

        try {
            param.put("id", SharedUtils.getInstance().getUserID());
            param.put("password", newPwd);
        } catch (JSONException e) {

        }
        personalPresenter.getUser(this, param);
        finish();

    }

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setUser(User user) {
        if (user.getStatus_code() != 0) {
            ToastUtils.showBottomToast(this, "密码修改失败");
        }

    }

    @Override
    public void setPhoneSms(String sms) {

    }


}
