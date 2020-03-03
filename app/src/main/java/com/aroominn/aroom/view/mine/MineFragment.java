package com.aroominn.aroom.view.mine;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.HistoryListAdapter;
import com.aroominn.aroom.adapter.MineFragmentAdapter;
import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Stories;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.presenter.HomePagePresenter;
import com.aroominn.aroom.presenter.impl.HomePagePresenterImpl;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.ScreenUtils;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.aroominn.aroom.utils.customview.JudgeNestedScrollView;
import com.aroominn.aroom.view.inn.StoryActivity;
import com.aroominn.aroom.view.mine.personal.PersonalActivity;
import com.aroominn.aroom.view.mine.setting.SettingActivity;
import com.aroominn.aroom.view.views.HomePageView;
import com.aroominn.aroom.view.vintage.VintageActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 */
public class MineFragment extends BaseFragment implements OnRefreshListener, OnRefreshLoadMoreListener ,HomePageView{

    @BindView(R.id.mine_toolbar)
    Toolbar toolbar;

    @BindView(R.id.mine_parallax)
    ImageView parallax;

    @BindView(R.id.mine_buttonBarLayout)
    ButtonBarLayout mBarLayout;

    @BindView(R.id.mine_scrollView)
    JudgeNestedScrollView mScrollView;

    @BindView(R.id.mine_refreshLayout)
    SmartRefreshLayout refreshLayout;

//    @BindView(R.id.mine_list)
//    RecyclerView recyclerView;

    private int mOffset = 0;
    private int mScrollY = 0;

    int toolBarPositionY = 0;
    private static ArrayList<Stories> stories=new ArrayList<>();

    private int pageNum=1;
    private int pageSize=10;
    private HomePagePresenter pagePresenter;
    private String headUrl;


    ///////////////////////
    public enum Item {
        NestedInner(R.string.tasty_text, SmartFragment.class),
        NestedOuter(R.string.noble_text, SmartFragment.class),;
        public int nameId;
        public Class<? extends Fragment> clazz;

        Item(@StringRes int nameId, Class<? extends Fragment> clazz) {
            this.nameId = nameId;
            this.clazz = clazz;
        }
    }

    @BindView(R.id.mine_tableLayout)
    TabLayout mTabLayout;

    @BindView(R.id.mine_viewPager)
    ViewPager mViewPager;

    private SmartPagerAdapter mAdapter;


    //////////////////////////////////////////////////////////////////
    @BindView(R.id.mine_header)
    CircleImageView headerImageView;



    private String[] titles;
    private String[] fragments;
    private int currentIndex = 0;


    public static final String TAG = MineFragment.class.getSimpleName();
    private MineFragmentAdapter adapter;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void setListener(View view, Bundle savedInstanceState) {


        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                L.e("控件中的刷新");
                mAdapter.fragments[mViewPager.getCurrentItem()].onRefresh(refreshLayout);

                refreshLayout.finishRefresh(3000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mAdapter.fragments[mViewPager.getCurrentItem()].onLoadMore(refreshLayout);
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
            private int color = ContextCompat.getColor(context, R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                int[] location = new int[2];
                mTabLayout.getLocationOnScreen(location);
                int yPosition = location[1];
                if (yPosition < toolBarPositionY) {
//                    mTabLayout.setVisibility(View.GONE);
                    mScrollView.setNeedScroll(false);
                } else {
//                    mTabLayout.setVisibility(View.VISIBLE);
                    mScrollView.setNeedScroll(true);
                }


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

        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolBarPositionY = toolbar.getHeight();
                ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
                params.height = ScreenUtils.getScreenHeight() - toolBarPositionY - mTabLayout.getHeight() + 1;
                mViewPager.setLayoutParams(params);
            }
        });


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mScrollView.setFocusable(true);
        mViewPager.setFocusable(false);
        mViewPager.setAdapter(mAdapter = new SmartPagerAdapter(Item.values()));
        mTabLayout.setupWithViewPager(mViewPager, true);

//        setSupportActionBar(toolbar);
        //状态栏透明和间距处理
        StatusBarUtil.immersive(context);
        StatusBarUtil.setPaddingSmart(context, toolbar);


        /**
         * 加载布局
         */
       /* titles = getResources().getStringArray(R.array.food_wine);
        //加载fragments
        fragments = new String[]{TastyFragment.TAG, NobleFragment.TAG};
        adapter = new MineFragmentAdapter(this.getChildFragmentManager(), titles, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(titles.length);
        mViewPager.setCurrentItem(currentIndex);

        tabStrip.setViewPager(mViewPager);
        tabStrip.setSelectedTextColor(currentIndex);*/
        /**
         * 加载用户信息
         */
        /*未设置头像时加载默认头像*/
        User.UserData u=SharedUtils.getInstance().getUser();
        if (u!=null)
            headUrl = u.getHead();
        if (headUrl!=null&&headUrl!=""){

        Glide.with(context)
                .load(headUrl)
                .into(headerImageView);
        }else {
            Glide.with(context)
                    .load(R.mipmap.image_avatar_5)
                    .into(headerImageView);
        }
//        innName.setText(SharedUtils.getInstance().getUser().getNick());

        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
//                topLayout.setBackground(resource);
            }
        };

        Glide.with(this)
                .load("http://img1.imgtn.bdimg.com/it/u=1491533638,387887685&fm=26&gp=0.jpg")
                .into(simpleTarget);

        pagePresenter = new HomePagePresenterImpl(this);
        requestData();


    }

    public void requestData(){
        JSONObject param=new JSONObject();
        try {
            param.put("userId",SharedUtils.getInstance().getUserID());
            param.put("pageNum",pageNum);
            param.put("pageSize",pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pagePresenter.getHomeStory(this,param);
    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        L.e("接口中的刷新");
        mAdapter.fragments[mViewPager.getCurrentItem()].onRefresh(refreshLayout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mAdapter.fragments[mViewPager.getCurrentItem()].onRefresh(refreshLayout);

        mAdapter.fragments[mViewPager.getCurrentItem()].onLoadMore(refreshLayout);
    }
    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setHomeStory(Story s) {
        if (s!=null&& s.getStatus_code()==0){
            if (s.getData()!=null){
                this.stories.addAll(s.getData().getList());
                L.e("数据装填完毕");
                refreshLayout.autoRefresh();

            }
        }

    }

    @Override
    public void setHomeInfo(HomeInfo user) {

    }

    @OnClick({R.id.mine_setting, R.id.mine_header, R.id.mine_fmc_center_dynamic})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.mine_setting:
                startActivity(new Intent(context, SettingActivity.class));
                break;
            case R.id.mine_header:
                startActivity(new Intent(context, PersonalActivity.class));
                break;
            case R.id.mine_fmc_center_dynamic:
                startActivity(new Intent(context, VintageActivity.class));
                break;
        }



    }


    private class SmartPagerAdapter extends FragmentStatePagerAdapter {

        private final Item[] items;
        private final SmartFragment[] fragments;

        SmartPagerAdapter(Item... items) {
            super(getChildFragmentManager());
            this.items = items;
            this.fragments = new SmartFragment[items.length];
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(items[position].nameId);
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments[position] == null) {
                fragments[position] = new SmartFragment();
            }
            return fragments[position];
        }
    }

    public static class SmartFragment extends Fragment implements BaseQuickAdapter.OnItemChildClickListener {

        private RecyclerView mRecyclerView;
        private HistoryListAdapter mAdapter;


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return new RecyclerView(inflater.getContext());
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setFocusable(false);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            L.e("开始加载数据");
            mAdapter = new HistoryListAdapter(getContext(),R.layout.list_history_item, stories);
            mAdapter.setOnItemChildClickListener(this);
            mRecyclerView.setAdapter(mAdapter);
//            mRecyclerView.setNestedScrollingEnabled(false);
//            /*mAdapter = new BaseRecyclerAdapter<Void>(initData(), simple_list_item_2) {
//                @Override
//                protected void onBindViewHolder(SmartViewHolder holder, Void model, int position) {
//                    holder.text(android.R.id.text1, getString(R.string.item_example_number_title, position));
//                    holder.text(android.R.id.text2, getString(R.string.item_example_number_abstract, position));
//                    holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
//                }
//            }*/
        }


        public void onRefresh(final RefreshLayout refreshLayout) {
            refreshLayout.getLayout().postDelayed(new Runnable() {
                @Override
                public void run() {

                    mAdapter.setNewData(stories);
//                    mAdapter.refresh(initData());
                    refreshLayout.finishRefresh();
                    refreshLayout.resetNoMoreData();//setNoMoreData(false);
                }
            }, 2000);
        }

        public void onLoadMore(final RefreshLayout refreshLayout) {
            refreshLayout.getLayout().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.addData(stories);


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

            Intent intent = new Intent(getContext(), StoryActivity.class);
            intent.putExtra("storyInfo", stories.get(position));
            startActivity(intent);
        }
    }

}
