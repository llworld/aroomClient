package com.aroominn.aroom.view.inn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.ImagesListAdapter;
import com.aroominn.aroom.adapter.StoryInfoListAdapter;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.PageHelper;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Stories;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.presenter.StoryPresenter;
import com.aroominn.aroom.presenter.TalePresenter;
import com.aroominn.aroom.presenter.impl.StoryPresenterImpl;
import com.aroominn.aroom.presenter.impl.TalePresenterImpl;
import com.aroominn.aroom.utils.KeyboardUtils;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.aroominn.aroom.utils.ToastUtils;
import com.aroominn.aroom.utils.popupWindow.CommentPopup;
import com.aroominn.aroom.utils.popupWindow.OnPopupItemClickListener;
import com.aroominn.aroom.utils.popupWindow.ReportBottomPopup;
import com.aroominn.aroom.utils.popupWindow.SlideFromBottomPopup;
import com.aroominn.aroom.view.views.StoryView;
import com.aroominn.aroom.view.views.TaleView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.utils.StringUtils;

/**
 * 故事详情
 */

public class StoryActivity extends BaseActivity implements StoryView, TaleView {

    @BindView(R.id.story_title)
    TitleBar mTitleBar;

    @BindView(R.id.story_refresh)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.story_rv)
    RecyclerView recommentRv;

    @BindView(R.id.story_comment_text)
    EditText commentText;


    private ArrayList<Stories> stories = new ArrayList<>();
    private StoryInfoListAdapter adapter;
    private StoryPresenter storyPresenter;
    private String storyId;
    private int pageNum = 1;
    private int pageSize = 10;
    private List<Comment> comments;
    private String storyText;
    private String nick;
    private Stories story;
    private String content;
    private TalePresenter talePresenter;
    private ShineButton like;
    private TextView repost;
    private boolean isLoadMore = false;
    private CommentPopup mCommentPopup;
    private boolean isLastPage = true;
    private ShineButton collect;

    @Override
    public void initView(Bundle savedInstanceState) {

        story = (Stories) getIntent().getExtras().getSerializable("storyInfo");
//        storyId = getIntent().getStringExtra("id");
//        storyText = getIntent().getStringExtra("content");
        nick = getIntent().getStringExtra("nickname");

        StatusBarUtil.setPaddingSmart(context, ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));

        final boolean isMe = story.getUserId().equals(SharedUtils.getInstance().getUserID());
        mCommentPopup = new CommentPopup(context, isMe);

        mTitleBar.setTitle(R.string.title_story_detail);
        mTitleBar.setRightIcon(R.drawable.icon_menu);
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                /*如果是自己的故事 可以进行删除*/
                /*不是自己的可以进行举报*/
                KeyboardUtils.getInstance().hideSoftKeyboard(v);
                SlideFromBottomPopup menu = new SlideFromBottomPopup(context, isMe);
                menu.showPopupWindow();
                menu.setOnMenuItemListener(new OnPopupItemClickListener() {
                    @Override
                    public void OnItemClickListener(View view) {
                        switch (view.getId()) {
                            case R.id.popup_menu_report:
                                ToastUtils.showBottomToast(context, "举报");
                                reportType();
                                break;

                            case R.id.popup_menu_delete:
                                requestDelete();
                                ToastUtils.showBottomToast(context, "删除");
                                break;
                        }
                    }
                });

            }
        });
    }

    private void reportType() {
        ReportBottomPopup reportBottomPopup = new ReportBottomPopup(this);
        reportBottomPopup.showPopupWindow();
        reportBottomPopup.setOnMenuItemListener(new OnPopupItemClickListener() {
            @Override
            public void OnItemClickListener(View view) {
                requestReport(view.getTag().toString());
            }
        });

    }

    private void requestReport(String tag) {
        JSONObject param = new JSONObject();
        try {
            param.put("storyId", story.getId());
            param.put("userId", story.getUserId());
            param.put("reason", tag);
            param.put("type", tag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        talePresenter.reportStory(this, param);
    }


    private void requestDelete() {
        JSONObject param = new JSONObject();
        try {
            param.put("id", story.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        talePresenter.deleteTale(this, param);
    }

    @Override
    public void setListener() {


        mCommentPopup.setOnCommentPopupClickListener(new CommentPopup.OnCommentPopupClickListener() {
            @Override
            public void onLikeClick(View v, TextView likeText) {
                ToastUtils.showBottomToast(context, "举报评论");
            }

            @Override
            public void onCommentClick(View v) {

                ToastUtils.showBottomToast(context, "删除评论");
            }
        });

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(true);

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (isLastPage) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    return;
                }
                pageNum++;
                isLoadMore = true;
                getRequestParam();
                refreshLayout.finishLoadMore(2000);
            }
        });
    }

    @Override
    public void initData() {

        storyPresenter = new StoryPresenterImpl(this);
        talePresenter = new TalePresenterImpl(this);


        getRequestParam();
        comments = new ArrayList<>();
        adapter = new StoryInfoListAdapter(R.layout.list_item_story_info, comments);
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                mCommentPopup.showPopupWindow(view);
                ToastUtils.showBottomToast(context, "长按评论");
                return false;
            }
        });


        /**
         * 添加头部
         */
        View headerView = getLayoutInflater().inflate(R.layout.list_item_story, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageView header = headerView.findViewById(R.id.story_item_head);
        like = headerView.findViewById(R.id.story_item_like);
        repost = headerView.findViewById(R.id.story_item_repost);
        collect = headerView.findViewById(R.id.story_item_collect);


        Glide.with(context)
                .load(R.drawable.ic_launcher_foreground)
                .into(header);
        TextView sd = headerView.findViewById(R.id.story_item_nick);
        TextView ssd = headerView.findViewById(R.id.story_item_content);
        GridView gridView = headerView.findViewById(R.id.story_item_grid);

        /**
         * 修改为工具方法
         */
        if (story.getImages() != null) {

            JSONArray myJsonArray = null;
            try {
                myJsonArray = new JSONArray(story.getImages());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            List<String> list = new ArrayList<>();
            for (int i = 0; i < myJsonArray.length(); i++) {
                try {
                    list.add(myJsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            gridView.setAdapter(new ImagesListAdapter(context, list));
        }
        ssd.setText(story.getContent());
        sd.setText(nick);
        CircleImageView circleImageView = headerView.findViewById(R.id.story_item_head);
        Glide.with(context)
                .load(story.getHead())
                .into(circleImageView);
        adapter.addHeaderView(headerView);

        if (story.getIsLike() > 0) {
            like.setChecked(true);
        }
        if (story.getIsCollection() > 0) {
            collect.setChecked(true);
        }

        recommentRv.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recommentRv.setLayoutManager(new LinearLayoutManager(context));
        recommentRv.setAdapter(adapter);

        setHeadListener();
    }

    private void setHeadListener() {

        like.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                L.e("点赞" + checked);
                likeTale(checked);
            }
        });

        collect.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                L.e("收藏" + checked);
                collectTale(checked);
            }
        });
        repost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*转发*/
                /*发送给*/
            }
        });
    }

    private void collectTale(boolean checked) {

        JSONObject param = new JSONObject();

        try {
            param.put("enable", checked ? 1 : 0);
            param.put("storyId", story.getId());
            param.put("ownerId", story.getUserId());
            param.put("userId", SharedUtils.getInstance().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        talePresenter.collocationStory(this, param);
    }

    private void getRequestParam() {
        JSONObject param = new JSONObject();
        try {
            param.put("storyId", story.getId());
            param.put("pageNum", pageNum);
            param.put("pageSize", pageSize);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        storyPresenter.getStoryDetails(StoryActivity.this, param);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_story;
    }


    @OnClick({R.id.story_send})
    public void OnViewClicked(View view) {

        switch (view.getId()) {

            case R.id.story_send:
                validateCommentInput();
                commentStory();
                break;


        }
    }

    private void likeTale(boolean check) {

        JSONObject param = new JSONObject();

        try {
            param.put("status", check ? 1 : 0);
            param.put("storyId", story.getId());
            param.put("ownerId", story.getUserId());
            param.put("userId", SharedUtils.getInstance().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        talePresenter.likeStory(this, param);
    }

    private void validateCommentInput() {
        content = commentText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showBottomToast(context, "请输入评论信息");
            return;
        }
    }

    private void commentStory() {
        JSONObject param = new JSONObject();
        try {
            param.put("storyId", story.getId());
            param.put("content", content);
            param.put("from_uid", SharedUtils.getInstance().getUserID());
            param.put("head", SharedUtils.getInstance().getUser().getHead());
            param.put("nick", SharedUtils.getInstance().getUser().getNick());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        talePresenter.commentStory(this, param);

    }


    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setTale(Result result) {
        if (result.getStatus_code() == 0) {
            ToastUtils.showBottomToast(this, result.getData().toString());

            if (result.getData().toString().equals("评论成功")) {
                /*删除文字 更新评论内容*/
                Comment newComment = new Comment();
                newComment.setContent(commentText.getText().toString());
                newComment.setHead(SharedUtils.getInstance().getUser().getHead());
                newComment.setTimes(new Date().toLocaleString());
                comments.add(newComment);
                commentText.setText("");
                KeyboardUtils.getInstance().hideSoftKeyboard(commentText);
                adapter.notifyDataSetChanged();

            }
            if (result.getData().toString().equals("删除成功")) {
                finish();
            }
        }
        switch (result.getStatus_code()) {
            case 2001:
                break;
            default:
                break;
        }
    }

    @Override
    public void setStory(Story story) {

    }

    @Override
    public void setStoryDetails(PageHelper comment) {
        if (comment.getStatus_code() == 0) {
            isLastPage = comment.getData().isLastPage();
            if (isLoadMore) {
                comments.addAll(comment.getData().getList());
                adapter.addData(comments);
            } else {
                isLastPage = false;
                comments = comment.getData().getList();
                adapter.setNewData(comments);

            }
            adapter.notifyDataSetChanged();
        }
        /**
         * The date should be a string value？？？
         */
    }

    @Override
    public void setResult(Result result) {

    }


    public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

        /*弹出菜单  对评论进行操作*/
        mCommentPopup.showPopupWindow(view);
        mCommentPopup.setOnCommentPopupClickListener(new CommentPopup.OnCommentPopupClickListener() {
            @Override
            public void onLikeClick(View v, TextView likeText) {
                ToastUtils.showBottomToast(context, "举报评论");
            }

            @Override
            public void onCommentClick(View v) {

                ToastUtils.showBottomToast(context, "删除评论");
            }
        });

        return false;
    }


    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        mCommentPopup.showPopupWindow(view);
        return false;
    }
}
