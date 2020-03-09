package com.aroominn.aroom.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aroominn.aroom.MainActivity;
import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.im.IMListener;
import com.aroominn.aroom.presenter.LoginPresenter;
import com.aroominn.aroom.presenter.impl.LoginPresenterImpl;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.PublicUtils;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.TimerUtils;
import com.aroominn.aroom.utils.ToastUtils;
import com.aroominn.aroom.utils.rongcloud.RongCloudUtils;
import com.aroominn.aroom.view.views.LoginView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
//import cn.jiguang.verifysdk.CtLoginActivity;
//import cn.jiguang.verifysdk.api.JVerificationAction;
//import cn.jiguang.verifysdk.api.JVerificationInterface;
//import cn.jiguang.verifysdk.api.LoginSettings;
//import cn.jiguang.verifysdk.api.VerifyListener;
//import cn.jpush.im.android.api.JMessageClient;
//import cn.jpush.im.api.BasicCallback;

public class PhoneFragment extends BaseFragment implements LoginView {

    @BindView(R.id.phone_phone_et)
    EditText phoneET;

    @BindView(R.id.phone_getVcode)
    TextView codeTV;

    @BindView(R.id.phone_vcode_et)
    EditText vCodeET;

    @BindView(R.id.phone_login)
    Button loginBtn;


    public static final String TAG = PhoneFragment.class.getName();
    private String phone;
    private String vCode;
    private LoginPresenter loginPresenter;
    private JSONObject param;


    public static PhoneFragment newInstance() {
        return new PhoneFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_phone;
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
//                phone = editable.toString().trim();

            }
        });

        vCodeET.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                vCode = editable.toString().trim();
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

    @OnClick({R.id.phone_login, R.id.phone_getVcode})
    public void onViewClick(View view) {

        switch (view.getId()) {

            case R.id.phone_getVcode:

                //对手机号进行判断
                phone = phoneET.getText().toString();

                if (phone.equals("")) {
                    ToastUtils.showBottomToast(context, "请输入手机号");
                }

                if (!PublicUtils.isPhoneNumAvaliable(phone)) {
                    //可获取验证码
                    ToastUtils.showBottomToast(context, "请输入正确的手机号");
                    break;
                }
                /**
                 * 请求后台 获取验证码
                 */

                param = new JSONObject();
                try {
                    param.put("phone", phone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loginPresenter.getVcode(this, param);


                break;
            case R.id.phone_login:

                //判断验证码输入情况
                vCode = vCodeET.getText().toString();
                if (TextUtils.isEmpty(vCode)) {
                    ToastUtils.showBottomToast(context, "请输入验证码");
                    break;
                }
                if (vCode.length() != 6) {
                    ToastUtils.showBottomToast(context, "请输入完整的验证码");
                    break;
                }
                param = new JSONObject();
                try {
                    param.put("phone", phone);
                    param.put("code", vCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loginPresenter.getLogin(this, param);


//                startActivity(new Intent(context, CtLoginActivity.class));



                /*JVerificationInterface.loginAuth(context, new VerifyListener() {
                    @Override
                    public void onResult(int code, String token, String operator) {
                        Log.e(TAG, "onResult: "+code+token+operator );


                    }
                });*/

                break;
        }
    }

/*    public void oneKeyLogin() {
        LoginSettings settings = new LoginSettings();
        settings.setAutoFinish(true);//设置登录完成后是否自动关闭授权页
        settings.setTimeout(15 * 1000);//设置超时时间，单位毫秒。 合法范围（0，30000],范围以外默认设置为10000


        JVerificationInterface.loginAuth(context, settings, new VerifyListener() {
            */

    /**
     * code 返回码，6000代表loginToken获取成功，6001代表loginToken获取失败，其他返回码详见描述
     * token 返回码的解释信息，若获取成功，内容信息代表loginToken。
     * operator 成功时为对应运营商，CM代表中国移动，CU代表中国联通，CT代表中国电信。失败时可能为null
     *//*
            @Override
            public void onResult(int code, String token, String operator) {
                *//*6000为成功*//*

                switch (code) {
                    case 6000:
                        L.e("认证成功");
//                                JMessageClient.login();
                        break;
                    case 2016:
                        L.e("当前网络环境不支持认证,请打开登录手机号的数据连接");
                        break;

                }
                if (code == 6000) {
                    Log.e(TAG, "code=" + code + ", token=" + token + " ,operator=" + operator);
                } else {
                    Log.e(TAG, "code=" + code + ", token=" + token + " ,operator=" + operator);
                }
            }
        });
    }*/
    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setVcode(String vcode) {

        //获取的验证码。。。
        //60s内不得再次获取验证码
        TimerUtils timerUtils = new TimerUtils(context, codeTV);
        timerUtils.startTimer();
        loginBtn.setEnabled(true);

    }

    @Override
    public void setRegister(Boolean result) {

    }

    @Override
    public void setLogin(User user) {

        if (user != null) {
            if (user.getStatus_code() == 0 && user.getData() != null) {

                SharedUtils.getInstance().setUser(user.getData());
                SharedUtils.getInstance().setToken(user.getData().getToken());
                SharedUtils.getInstance().setUserID(user.getData().getId());
                SharedUtils.getInstance().setLogin(true);

                RongIM.connect(user.getData().getToken(), IMListener.getInstance().getConnectCallback());

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                context.finish();
            }
            ToastUtils.showBottomToast(context, user.getMessage());
        }


        if (SharedUtils.getInstance().getFirst()) {
            //新用户
            SharedUtils.getInstance().setFirst(false);

        }

    }
}
