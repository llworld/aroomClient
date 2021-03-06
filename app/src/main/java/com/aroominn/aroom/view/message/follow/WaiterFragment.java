package com.aroominn.aroom.view.message.follow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
 * 小二儿 关注我的
 */
public class WaiterFragment extends BaseFragment implements FriendsView ,BaseQuickAdapter.OnItemChildClickListener{


    @BindView(R.id.waiter_friends_list)
    RecyclerView mRecyclerView;
    private FollowListAdapter mAdapter;
    private ArrayList<FriendData> f;


    public static WaiterFragment newInstance() {
        return new WaiterFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fargment_waiter;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        FriendsPresenter mFriendsPresenter = new FriendsPresenterImpl(this);
        JSONObject param = new JSONObject();
        try {
            param.put("userId", SharedUtils.getInstance().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mFriendsPresenter.getWaiterFriends(this, param);
    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {


        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new FollowListAdapter(context,R.layout.list_item_follow, new ArrayList<FriendData>());
        mRecyclerView.setAdapter(mAdapter);


        View emptyView=getLayoutInflater().inflate(R.layout.layout_friends_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //添加空视图
        mAdapter.setEmptyView(emptyView);
        mAdapter.setOnItemChildClickListener(this);


        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "快去客栈看看吧", Snackbar.LENGTH_SHORT).show();
            }
        });

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
            Integer targetId=f.get(position).getId();
            String title=f.get(position).getNick();
                Intent intent = new Intent(getContext(), HomepageActivity.class);
                intent.putExtra("targetId",targetId+"" );
                intent.putExtra("title",title );
                startActivity(intent);
                break;
            case R.id.follow_item_rl:
                String targetId1=f.get(position).getId()+"";
                String title1=f.get(position).getNick();
//                聊天
                RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE, targetId1, title1);

//                Intent chatIntent = new Intent(getContext(), ChatActivity.class);
//                chatIntent.putExtra("targetId", targetId1+"");
//                chatIntent.putExtra("title", title1);
//                startActivity(chatIntent);
                break;
        }
    }
}
