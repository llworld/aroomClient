package com.aroominn.aroom.view.inn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.HistoryListAdapter;
import com.aroominn.aroom.adapter.StoryListAdapter;
import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.PageHelper;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Stories;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.presenter.StoryPresenter;
import com.aroominn.aroom.presenter.impl.StoryPresenterImpl;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.ToastUtils;
import com.aroominn.aroom.utils.popupWindow.ImagePopup;
import com.aroominn.aroom.view.views.StoryView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

/**
 * 关注
 */

public class FocusFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemLongClickListener, BaseQuickAdapter.OnItemClickListener, StoryView {

    @BindView(R.id.focus_refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.focus_list)
    RecyclerView recyclerView;

    private StoryPresenter storyPresenter;
    private int maxPageNum;

    private StoryListAdapter adapter;
    private ArrayList<Stories> stories = new ArrayList<>();
    private ArrayList<Stories> tempStories = new ArrayList<>();


    private int pageNum = 1;

    //true:刷新 false:加载
    private boolean refresh = true;

    public static final String TAG = FocusFragment.class.getName();
    private boolean hasNextPage;

    public static FocusFragment newInstance() {
        return new FocusFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_focus;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {


        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                refresh = true;
                pageNum = 1;
                getRequestData();
                refreshLayout.finishRefresh(2000);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@android.support.annotation.NonNull RefreshLayout refreshLayout) {
                refresh = false;
                if (hasNextPage) {
                    pageNum++;
                    getRequestData();
                } else {
                    refreshLayout.finishLoadMore();
//                    ToastUtils.showBottomToast("没有更多数据了", 0);
                }
                refreshLayout.finishLoadMore(2000);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        storyPresenter = new StoryPresenterImpl(this);
        getRequestData();


        adapter = new StoryListAdapter(context, R.layout.list_item_story, stories);
        final ImagePopup imagePopup = new ImagePopup(context);
        adapter.setImage(new HistoryListAdapter.ImageItemClickListener() {
            @Override
            public void onImageClick(String p) {
                imagePopup.initView(p);
            }
        });
        /**
         * 添加头部
         */
//        View headerView=getLayoutInflater().inflate(R.layout.support_simple_spinner_dropdown_item, null);
        TextView textView = new TextView(context);

        textView.setText("不问由来，不顾生死");
//        textView.setTextColor(getContext().getColor(R.color.black));
        textView.setGravity(Gravity.CENTER);
//        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

//
//        adapter.addHeaderView(textView);

        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);

    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

    }

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setStory(Story story) {

        if (story.getStatus_code() == 0) {
            maxPageNum = story.getData().getPageNum();
            tempStories = story.getData().getList();
            hasNextPage = story.getData().isHasNextPage();
            if (refresh) {
                stories.clear();
                //刷新数据
                stories = tempStories;
                adapter.setNewData(tempStories);
            } else {
                //加载数据
                stories.addAll(tempStories);
                adapter.addData(tempStories);
//                adapter.replaceData(story.getData().getList());
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setStoryDetails(PageHelper comment) {

    }

    @Override
    public void setResult(Result result) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        switch (view.getId()) {

            case R.id.story_item_head:
                String targetId = stories.get(position).getUserId() + "";
                if (SharedUtils.getInstance().getUserID() + "" == targetId) {
                    ToastUtils.showBottomToast("不能查看自己主页", 1);
                    break;
                }
                Intent intent = new Intent(context, HomepageActivity.class);
                intent.putExtra("targetId", targetId);
                intent.putExtra("title", stories.get(position).getNick());
                startActivity(intent);
                break;
            case R.id.story_item_like:
                ToastUtils.showBottomToast("点赞" + position, 0);
                break;
            case R.id.story_item_comment:
                ToastUtils.showBottomToast("评论" + position, 0);
                break;
            case R.id.story_item_repost:
                ToastUtils.showBottomToast("转发" + position, 0);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showBottomToast("onItemClick" + position, ToastUtils.DURATION);
        Intent intent = new Intent(context, StoryActivity.class);
        intent.putExtra("storyInfo", stories.get(position));
        intent.putExtra("id", stories.get(position).getId());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    private void getRequestData() {
        JSONObject param = new JSONObject();
        try {
            param.put("pageNum", pageNum);
            param.put("pageSize", 10);
            param.put("type", 0);    //推荐类型
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (storyPresenter == null)
            storyPresenter = new StoryPresenterImpl(this);
        storyPresenter.getStory(this, param);

    }
}
