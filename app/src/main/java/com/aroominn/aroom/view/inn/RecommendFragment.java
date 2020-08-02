package com.aroominn.aroom.view.inn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
import com.aroominn.aroom.presenter.TalePresenter;
import com.aroominn.aroom.presenter.impl.StoryPresenterImpl;
import com.aroominn.aroom.presenter.impl.TalePresenterImpl;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.ToastUtils;
import com.aroominn.aroom.utils.popupWindow.CommentPopup;
import com.aroominn.aroom.utils.popupWindow.ImagePopup;
import com.aroominn.aroom.utils.recyclelist.DividerItemDecoration;
import com.aroominn.aroom.view.views.StoryView;
import com.aroominn.aroom.view.views.TaleView;
import com.aroominn.aroom.view.views.UtilsView;
import com.aroominn.aroom.view.vintage.VintageActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

/**
 * 推荐
 */

public class RecommendFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemLongClickListener, BaseQuickAdapter.OnItemClickListener, StoryView, TaleView {


    public static final String TAG = RecommendFragment.class.getName();
    private StoryPresenter storyPresenter;
    private int maxPageNum;
    private TalePresenter talePresenter;
    private boolean hasNextPage;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @BindView(R.id.recommend_refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recomm_secondfloor)
    ImageView floor;

    @BindView(R.id.recomm_header)
    TwoLevelHeader header;

    @BindView(R.id.recomm_secondfloor_content)
    ImageView secondContent;

    @BindView(R.id.recommend_list)
    RecyclerView storyRv;


    private StoryListAdapter adapter;
    private ArrayList<Stories> stories = new ArrayList<>();
    private ArrayList<Stories> tempStories = new ArrayList<>();

    private int pageNum = 1;

    //true:刷新 false:加载
    private boolean refresh = true;

    @Override
    public int getContentViewId() {
        return R.layout.fragmetn_recommend;
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
                if (hasNextPage){
                    pageNum++;
                    getRequestData();
                }

                else {
                    refreshLayout.finishLoadMore();
                }
                refreshLayout.finishLoadMore(2000);
            }
        });

        /**
         * 二层楼功能
         */
        mRefreshLayout.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onStateChanged(@android.support.annotation.NonNull RefreshLayout refreshLayout, @android.support.annotation.NonNull RefreshState oldState, @android.support.annotation.NonNull RefreshState newState) {

            }

            @Override
            public void onRefresh(@android.support.annotation.NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onLoadMore(@android.support.annotation.NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
//隐藏标题
                //                toolbar.setAlpha(1 - Math.min(percent, 1));
                floor.setTranslationY(Math.min(offset - floor.getHeight() + 40, mRefreshLayout.getLayout().getHeight() - floor.getHeight()));
//                floor.setTranslationY(Math.min(offset - floor.getHeight() + toolbar.getHeight(), mRefreshLayout.getLayout().getHeight() - floor.getHeight()));

            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }
        });
      /*  header.setOnTwoLevelListener(new OnTwoLevelListener() {
            @Override
            public boolean onTwoLevel(@android.support.annotation.NonNull RefreshLayout refreshLayout) {
                secondContent.animate().alpha(1).setDuration(2000);
                ToastUtils.showBottomToast("跳转到新的故事", 0);
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        header.finishTwoLevel();
                        secondContent.animate().alpha(0).setDuration(1000);
                    }
                }, 5000);
                return true;//true 将会展开二楼状态 false 关闭刷新
            }
        });*/

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

//        startActivity(new Intent(MainActivity.this, VintageActivity.class));

        storyRv.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        storyRv.setLayoutManager(new LinearLayoutManager(context));
        storyRv.setAdapter(adapter);

        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);

    }

    private void getRequestData() {
        JSONObject param = new JSONObject();
        try {
            param.put("pageNum", pageNum);
            param.put("pageSize", 10);
            param.put("type", 1);    //推荐类型
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (storyPresenter == null)
            storyPresenter = new StoryPresenterImpl(this);
        storyPresenter.getStory(this, param);

    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

        talePresenter = new TalePresenterImpl(this);
    }



    /**
     * 子空间的点击事件
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        switch (view.getId()) {

            case R.id.story_item_head:

                String targetId = stories.get(position).getUserId() + "";
                if (SharedUtils.getInstance().getUserID()+""==targetId){
                    ToastUtils.showBottomToast("不能查看自己主页",1);

                    break;
                }

                ToastUtils.showBottomToast("查看主页" + position, 0);
                Intent intent = new Intent(context, HomepageActivity.class);
                intent.putExtra("targetId", targetId);
                intent.putExtra("title", stories.get(position).getNick());
                startActivity(intent);
                break;
            case R.id.story_item_like:
                ToastUtils.showBottomToast("点赞" + position, 0);
                postLikeRequest(stories.get(position), view);
                break;
            case R.id.story_item_comment:
                Intent commentIntent = new Intent(context, StoryActivity.class);
                //点赞状态
                commentIntent.putExtra("id", stories.get(position).getId());
                commentIntent.putExtra("storyInfo", stories.get(position));

                commentIntent.putExtra("content", stories.get(position).getContent());
                commentIntent.putExtra("nickname", stories.get(position).getNick());
//                commentIntent.putExtra("like", 1);
                startActivity(commentIntent);
                break;
            case R.id.story_item_repost:
                ToastUtils.showBottomToast("转发" + position, 0);
                break;
            case R.id.story_item_collect:
                collectRequest(stories.get(position), view);
                break;
        }
    }

    private void collectRequest(Stories s, View view) {

        ShineButton collect = (ShineButton) view;
        JSONObject param = new JSONObject();
        try {
            param.put("storyId", s.getId());
            param.put("ownerId", s.getUserId());
            param.put("userId", SharedUtils.getInstance().getUserID());
            param.put("enable", collect.isChecked() ? 1 : 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        talePresenter.collocationStory(this, param);
    }

    private void postLikeRequest(Stories s, View view) {
        ShineButton like = (ShineButton) view;
        JSONObject param = new JSONObject();
        try {
            param.put("storyId", s.getId());
            param.put("ownerId", s.getUserId());
            param.put("userId", SharedUtils.getInstance().getUserID());
            param.put("status", like.isChecked() ? 1 : 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        talePresenter.likeStory(this, param);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        ToastUtils.showBottomToast("onItemClick" + position, ToastUtils.DURATION);
        Intent intent = new Intent(context, StoryActivity.class);
        intent.putExtra("storyInfo", stories.get(position));
        intent.putExtra("id", stories.get(position).getId().toString());
        intent.putExtra("content", stories.get(position).getContent());
        intent.putExtra("nickname", stories.get(position).getNick());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showBottomToast("onItemLongClick" + position, ToastUtils.DURATION);
        return false;
    }

    //加载数据
    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setTale(Result result) {

    }


    @Override
    public void setStory(Story story) {
        if (story.getStatus_code() == 0) {
            hasNextPage = story.getData().isHasNextPage();
            maxPageNum = story.getData().getPageNum();
            tempStories = story.getData().getList();
            if (refresh) {
                stories.clear();
                stories = tempStories;
                //刷新数据
                adapter.setNewData(story.getData().getList());
            } else {
                //加载数据
                stories.addAll(tempStories);
                adapter.replaceData(stories);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setStoryDetails(PageHelper comment) {

    }

    @Override
    public void setResult(Result result) {
        if (result.getStatus_code() == 2001) {
//            点赞成功
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
