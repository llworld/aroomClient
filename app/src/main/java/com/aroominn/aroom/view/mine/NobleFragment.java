package com.aroominn.aroom.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.HistoryListAdapter;
import com.aroominn.aroom.adapter.TastyListAdapter;
import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Stories;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.presenter.HomePagePresenter;
import com.aroominn.aroom.presenter.impl.HomePagePresenterImpl;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.popupWindow.ImagePopup;
import com.aroominn.aroom.view.inn.StoryActivity;
import com.aroominn.aroom.view.views.HomePageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 美酒
 * 收藏的故事
 */
public class NobleFragment extends BaseFragment implements HomePageView, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.noble_list)
    RecyclerView recyclerView;

    private int pageNum = 1;
    private int pageSize = 10;

    public static final String TAG = NobleFragment.class.getSimpleName();
    private HomePagePresenter homePagePresenter;
    private boolean isRefresh = true;
    private HistoryListAdapter adapter;
    private ArrayList<Stories> stories;
    private boolean isLastPage;

    public static NobleFragment newInstance() {
        return new NobleFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_noble;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        stories = new ArrayList<>();

        adapter = new HistoryListAdapter(context, R.layout.list_history_item, stories);
        adapter.setOnItemChildClickListener(this);
        final ImagePopup imagePopup = new ImagePopup(context);
        adapter.setImage(new HistoryListAdapter.ImageItemClickListener() {
            @Override
            public void onImageClick(String p) {
                imagePopup.initView(p);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setFocusable(false);
        homePagePresenter = new HomePagePresenterImpl(this);
        requestData();
    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

    }


    private void requestData() {
        JSONObject param = new JSONObject();
        try {
            param.put("userId", SharedUtils.getInstance().getUserID());
            param.put("targetId", SharedUtils.getInstance().getUserID());
            param.put("pageNum", pageNum);
            param.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        homePagePresenter.getCollectStory(this, param);
    }

    public void onRefresh(final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNum = 1;
                isRefresh = true;
                requestData();
//                mAdapter.setNewData(stories);
//                    mAdapter.refresh(initData());
                refreshLayout.finishRefresh();
//                refreshLayout.resetNoMoreData();//setNoMoreData(false);
            }
        }, 2000);
    }

    public void onLoadMore(final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLastPage) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    return;
                }
                isRefresh = false;
                pageNum += 1;
                requestData();
//                refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                refreshLayout.finishLoadMore();
            }
        }, 2000);
    }

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setHomeStory(Story s) {
        if (s.getStatus_code() == 0) {
            isLastPage = s.getData().isLastPage();
            if (isRefresh) {
                stories = s.getData().getList();
                adapter.setNewData(s.getData().getList());
            } else {
                stories.addAll(s.getData().getList());
                adapter.addData(stories);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setHomeInfo(HomeInfo user) {
        if (user.getStatus_code() == 0) {

        }
    }

    @Override
    public void setFollow(Result result) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.history_item_ll:
                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("storyInfo", stories.get(position));
                intent.putExtra("nickname", stories.get(position).getNick());
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setFocusable(false);
    }
}
