package com.aroominn.aroom.view.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.FollowListAdapter;
import com.aroominn.aroom.bean.FriendData;
import com.aroominn.aroom.view.inn.HomepageActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;


public class FollowFragment extends Fragment implements BaseQuickAdapter.OnItemChildClickListener {

    private RecyclerView mRecyclerView;
    private FollowListAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(inflater.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mAdapter = new FollowListAdapter(R.layout.list_item_follow, initData());
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<FriendData> initData() {
        ArrayList<FriendData> friends = new ArrayList<>();
        FriendData data = new FriendData();
        friends.add(data);
        friends.add(data);
        friends.add(data);
        friends.add(data);
        friends.add(data);

        return friends;
    }

    public void onRefresh(final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setNewData(initData());
                refreshLayout.finishRefresh();
                refreshLayout.resetNoMoreData();//setNoMoreData(false);
            }
        }, 2000);
    }

    public void onLoadMore(final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
//                mAdapter.loadMore(initData());
                mAdapter.addData(initData());
                if (mAdapter.getItemCount() > 60) {
                    Toast.makeText(getContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        }, 2000);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        switch (view.getId()) {
            case R.id.follow_item_header:
//                查看主页
                Intent homeIntent = new Intent(getContext(), HomepageActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.follow_item_rl:
//                聊天
                Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                startActivity(chatIntent);
                break;
        }
    }
}
