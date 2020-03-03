package com.aroominn.aroom.view.mine.personal;


import android.content.Intent;
import android.os.Bundle;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.presenter.PersonalPresenter;
import com.aroominn.aroom.presenter.impl.PersonalPresenterImpl;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.customview.TitleBar;
import com.aroominn.aroom.view.views.PersonalView;
import com.bumptech.glide.Glide;

import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心
 */
public class PersonalActivity extends BaseActivity implements PersonalView {

    @BindView(R.id.personal_title)
    TitleBar mTitleBar;

    @BindView(R.id.per_image)
    CircleImageView perImg;

    @BindView(R.id.per_name)
    TextView perName;

    @BindView(R.id.per_sex)
    TextView perSex;

    @BindView(R.id.per_phone)
    TextView perPhone;

    @BindView(R.id.per_category)
    TextView perCategory;
    private PersonalPresenter personalPresenter;


    @Override
    public void initView(Bundle savedInstanceState) {
        initTitleBar(mTitleBar, R.string.title_personal);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

        loadUserInfo();
        personalPresenter = new PersonalPresenterImpl(this);
    }

    private void loadUserInfo() {
        /*加载用户信息*/
        User.UserData u = SharedUtils.getInstance().getUser();
        if (u != null) {
            /*用户信息为空则显示请修改*/
            perName.setText(u.getNick() != null ? u.getNick() : "一间");
            Glide.with(this)
                    .load(u.getHead() != null ? u.getHead() : R.mipmap.image_avatar_5)
                    .into(perImg);
            perSex.setText(u.getSex() == 0 ? "男" : "女");
            perPhone.setText(u.getPhone());
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_personal;
    }

    @OnClick({R.id.personal_head_layout, R.id.personal_nick_layout, R.id.personal_sex_layout,
            R.id.personal_phone_layout, R.id.personal_tag_layout, R.id.personal_password_layout, R.id.personal_email_layout})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.personal_head_layout:
                Intent intent = new Intent(this, HeadActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.personal_nick_layout:
                Intent nick = new Intent(this, HostelActivity.class);
                startActivityForResult(nick, 102);
                break;
            case R.id.personal_sex_layout:
                /*弹出选择器*/
                break;
            case R.id.personal_phone_layout:

                startActivity(new Intent(this, PhoneActivity.class));
                break;
            case R.id.personal_tag_layout:
                startActivity(new Intent(this, CharacterActivity.class));
                break;
            case R.id.personal_password_layout:
                startActivity(new Intent(this, PasswordActivity.class));
                break;
            case R.id.personal_email_layout:
                break;
        }
    }

    public void requestData(String url, String name, int sex, String category) {
        JSONObject param = new JSONObject();
        try {
            int id = SharedUtils.getInstance().getUserID();
            param.put("Id", SharedUtils.getInstance().getUserID());
            param.put("head", url);
            param.put("nick", name);
            if (sex == 0 || sex == 1)
                param.put("sex", sex);
            param.put("category", category);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        personalPresenter.getUser(this, param);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String url = "";
                    if (bundle != null) {
                        url = bundle.getString("url");
                        requestData(url, null, -1, null);
                    }
                }
                break;
            case 102:
                break;
        }
    }

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setUser(User user) {
        if (user.getStatus_code() == 0) {
            if (user.getData() != null) {
                SharedUtils.getInstance().setUser(user.getData());
                loadUserInfo();
            }
        }
    }

    @Override
    public void setPhoneSms(String sms) {

    }
}
