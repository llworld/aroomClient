package com.aroominn.aroom.view.message.follow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.FollowListAdapter;
import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Friend;
import com.aroominn.aroom.bean.FriendData;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.presenter.FriendsPresenter;
import com.aroominn.aroom.presenter.impl.FriendsPresenterImpl;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.view.inn.HomepageActivity;
import com.aroominn.aroom.view.message.ChatActivity;
import com.aroominn.aroom.view.views.FriendsView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * 掌柜的  我关注的
 */
public class BossFragment extends BaseFragment implements FriendsView, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.boss_friends_list)
    RecyclerView recyclerView;

    private FriendsPresenter friendsPresenter;
    private FollowListAdapter mAdapter;
    private ArrayList<FriendData> f;

    public static BossFragment newInstance() {
        return new BossFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fargment_boss;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        friendsPresenter = new FriendsPresenterImpl(this);
        JSONObject param = new JSONObject();
        try {
            param.put("userId", SharedUtils.getInstance().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        friendsPresenter.getBossFriends(this, param);
    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new FollowListAdapter(context,R.layout.list_item_follow, new ArrayList<FriendData>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        View emptyView=getLayoutInflater().inflate(R.layout.layout_friends_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //添加空视图
        mAdapter.setEmptyView(emptyView);
//        mAdapter.setEmptyView(R.layout.layout_friends_empty);

    }

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setFriends(Friend friends) {

        if (friends.getStatus_code() == 0) {
            f = friends.getData();
            mAdapter.setNewData(f);
        }
    }

    @Override
    public void setFriendInfo(User user) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.follow_item_header:
//                查看主页
                Intent intent = new Intent(getContext(), HomepageActivity.class);
                intent.putExtra("targetId", f.get(position).getId());
                intent.putExtra("title", f.get(position).getNick());
                startActivity(intent);
                break;
            case R.id.follow_item_rl:
//                聊天
                String targetId=f.get(position).getId()+"";
                String title=f.get(position).getNick();
                RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE, targetId, title);
//                Intent chatIntent = new Intent(getContext(), ChatActivity.class);
//                chatIntent.putExtra("targetId", f.get(position).getId());
//                chatIntent.putExtra("title", f.get(position).getNick());
//                startActivity(chatIntent);
                break;
        }
    }
}
