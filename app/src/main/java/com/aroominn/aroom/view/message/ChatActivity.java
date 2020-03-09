package com.aroominn.aroom.view.message;

import android.os.Bundle;
import android.view.ViewGroup;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Friend;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.presenter.FriendsPresenter;
import com.aroominn.aroom.presenter.impl.FriendsPresenterImpl;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.aroominn.aroom.view.views.FriendsView;
import com.hjq.bar.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class ChatActivity extends BaseActivity implements FriendsView {

    @BindView(R.id.chat_title)
    TitleBar mTitleBar;
    private FriendsPresenter friendsPresenter;

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.setPaddingSmart(context, ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));

        /*目标用户ID*/
        String targetId = getIntent().getData().getQueryParameter("targetId");//获取id
        /*目标用户 昵称*/
        String title = getIntent().getData().getQueryParameter("title");//获取消息title
//        mTitleBar.setTitle(R.string.title_message_chat);
        mTitleBar.setTitle(title);

        friendsPresenter = new FriendsPresenterImpl(this);
        JSONObject param = new JSONObject();
        try {
            param.put("id", targetId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        friendsPresenter.getUserInfo(this, param);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_chat;
    }

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setFriends(Friend friends) {

    }

    @Override
    public void setFriendInfo(User user) {
        if (user.getStatus_code() == 0) {
            try {

                mTitleBar.setTitle(user.getData().getNick());
            } catch (NullPointerException e) {

            }

        }
    }
}
