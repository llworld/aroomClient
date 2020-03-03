package com.aroominn.aroom.view.inn;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.HistoryListAdapter;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Stories;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.presenter.HomePagePresenter;
import com.aroominn.aroom.presenter.impl.HomePagePresenterImpl;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.aroominn.aroom.view.views.HomePageView;
import com.aroominn.aroom.view.views.LoginView;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * 查看的客栈主页
 */
public class HomepageActivity extends BaseActivity implements HomePageView {

    @BindView(R.id.homepage_toolbar)
    Toolbar toolbar;

    @BindView(R.id.homepage_parallax)
    ImageView parallax;

    @BindView(R.id.homepage_buttonBarLayout)
    ButtonBarLayout mBarLayout;

    @BindView(R.id.homepage_scrollView)
    NestedScrollView mScrollView;

    @BindView(R.id.homepage_refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.homepage_list)
    RecyclerView recyclerView;


    @BindView(R.id.homepage_avatar)
    CircleImageView avatarView;

    @BindView(R.id.homepage_nickname)
    TextView nickName;

    private int mOffset = 0;
    private int mScrollY = 0;

    HistoryListAdapter adapter;
    private String targetId;
    private String title;
    private HomePagePresenter presenter;
    private int pageNum = 1;

    @Override
    public void initView(Bundle savedInstanceState) {

        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setListener() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                requestHisStory();
                refreshLayout.finishRefresh(2000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum += 1;
                requestHisStory();
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mOffset = offset / 2;
                parallax.setTranslationY(mOffset - mScrollY);
                toolbar.setAlpha(1 - Math.min(percent, 1));
            }
//            @Override
//            public void onHeaderPulling(@NonNull RefreshHeader header, float percent, int offset, int bottomHeight, int maxDragHeight) {
//                mOffset = offset / 2;
//                parallax.setTranslationY(mOffset - mScrollY);
//                toolbar.setAlpha(1 - Math.min(percent, 1));
//            }
//            @Override
//            public void onHeaderReleasing(@NonNull RefreshHeader header, float percent, int offset, int bottomHeight, int maxDragHeight) {
//                mOffset = offset / 2;
//                parallax.setTranslationY(mOffset - mScrollY);
//                toolbar.setAlpha(1 - Math.min(percent, 1));
//            }
        });

        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {

            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (lastScrollY < h) {
                    i1 = Math.min(h, i1);

                    mScrollY = i1 > h ? h : i1;
                    mBarLayout.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    parallax.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = i2;
            }
        });
        mBarLayout.setAlpha(0);
        toolbar.setBackgroundColor(0);

    }

    @Override
    public void initData() {


        targetId = getIntent().getStringExtra("targetId");
        title = getIntent().getStringExtra("title");
        /*请求用户数据*/
        presenter = new HomePagePresenterImpl(this);
        JSONObject param = new JSONObject();
        try {
            param.put("id", targetId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        presenter.getHomeUser(this, param);
        requestHisStory();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void requestHisStory() {
        /*加载用户历史故事*/
        JSONObject object = new JSONObject();
        try {
            object.put("userId", targetId);
            object.put("pageNum", pageNum);
            object.put("pageSize", 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        presenter.getHomeStory(this, object);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_homepage;
    }

    @OnClick({R.id.homepage_leaveword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_leaveword:
                /*跳转到聊天界面*/
                RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE, targetId, title);
                break;
        }

    }

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setHomeStory(Story stories) {
        if (stories.getStatus_code() == 0) {
            if (adapter == null || pageNum == 1) {
                adapter = new HistoryListAdapter(this,R.layout.list_history_item, stories.getData().getList());
                recyclerView.setAdapter(adapter);
                recyclerView.setNestedScrollingEnabled(false);
            } else {
                adapter.addData(stories.getData().getList());
            }
        }

    }

    @Override
    public void setHomeInfo(HomeInfo user) {
        if (user.getStatus_code() == 0) {

            Glide.with(this)
                    .load(user.getData().getHeadUrl())
                    .into(avatarView);
            nickName.setText(user.getData().getName());

        }


    }


}
