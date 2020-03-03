package com.aroominn.aroom.view.message;

import android.os.Bundle;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.hjq.bar.TitleBar;

import butterknife.BindView;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.chat_title)
    TitleBar mTitleBar;

    @Override
    public void initView(Bundle savedInstanceState) {
        /*目标用户ID*/
        String targetId = getIntent().getData().getQueryParameter("targetId");//获取id
        /*目标用户 昵称*/
        String title = getIntent().getData().getQueryParameter("title");//获取消息title
//        mTitleBar.setTitle(R.string.title_message_chat);
        mTitleBar.setTitle(title);
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
}
