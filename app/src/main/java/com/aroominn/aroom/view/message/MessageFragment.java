package com.aroominn.aroom.view.message;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.MessageListAdapter;
import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.bean.Message;
import com.aroominn.aroom.utils.ToastUtils;
import com.aroominn.aroom.utils.customview.SubConversationListFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MessageFragment extends BaseFragment {


    @BindView(R.id.message_rv)
    RecyclerView messageRv;


    public static final String TAG = MessageFragment.class.getSimpleName();

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_message;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        SubConversationListFragment mConversationListFragment = new SubConversationListFragment();
        Uri uri = Uri.parse("rong://" + context.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        mConversationListFragment.setUri(uri);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.subconversationlist, mConversationListFragment);
//        transaction.replace("自己的布局", "融云的布局(可自定义)");
        transaction.commit();
//        mConversationListFragment.getConversationList();

//        RongIM.getInstance().startConversationList(context);
//        RongIM.setUserInfoProvider();


    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

    }

    @OnClick({R.id.message_follow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message_follow:
                startActivity(new Intent(context, FollowActivity.class));
                break;
        }
    }
/*
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        switch (view.getId()) {
            case R.id.message_item_header:

                ToastUtils.showBottomToast("查看主页", 0);
                break;
            case R.id.message_item_delete:
                ToastUtils.showBottomToast("删除对话", 0);
                break;
            case R.id.message_item_stick:
                adapter.setData(0, adapter.getData().get(position));
                ToastUtils.showBottomToast("置顶", 0);
                break;
            case R.id.message_item_content:
                startActivity(new Intent(context, ChatActivity.class));
                break;
        }
    }*/


}
