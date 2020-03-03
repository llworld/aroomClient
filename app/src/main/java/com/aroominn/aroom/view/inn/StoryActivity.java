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
import com.aroominn.aroom.presenter.impl.StoryPresenterImpl;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.ToastUtils;
import com.aroominn.aroom.utils.customview.TitleBar;
import com.aroominn.aroom.view.views.StoryView;
import com.bumptech.glide.Glide;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.utils.StringUtils;

/**
 * 故事详情
 */

public class StoryActivity extends BaseActivity implements StoryView {

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

    @Override
    public void initView(Bundle savedInstanceState) {

        initTitleBar(mTitleBar, R.string.title_story_detail);
    }

    @Override
    public void setListener() {

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(true);

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum++;
            }
        });
    }

    @Override
    public void initData() {

        storyPresenter = new StoryPresenterImpl(this);
        story = (Stories) getIntent().getExtras().getSerializable("storyInfo");
//        storyId = getIntent().getStringExtra("id");
//        storyText = getIntent().getStringExtra("content");
        nick = getIntent().getStringExtra("nickname");

        getRequestParam();
        comments = new ArrayList<>();
        adapter = new StoryInfoListAdapter(R.layout.list_item_story_info, comments);


        /**
         * 添加头部
         */
        View headerView = getLayoutInflater().inflate(R.layout.list_item_story, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageView header = headerView.findViewById(R.id.story_item_head);
        Glide.with(context)
                .load(R.drawable.ic_launcher_foreground)
                .into(header);
        TextView sd = headerView.findViewById(R.id.story_item_nick);
        TextView ssd = headerView.findViewById(R.id.story_item_content);
        GridView gridView = headerView.findViewById(R.id.story_item_grid);
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

        ShineButton like = headerView.findViewById(R.id.story_item_like);
        if (story.getIsLike() > 0) {
            like.setChecked(true);
        }

        recommentRv.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recommentRv.setLayoutManager(new LinearLayoutManager(context));
        recommentRv.setAdapter(adapter);

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

//        storyPresenter.getStory();
    }


    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setStory(Story story) {

    }

    @Override
    public void setStoryDetails(PageHelper comment) {
        if (comment.getStatus_code() == 0) {
            comments.addAll(comment.getData().getList());
            adapter.addData(comments);
            adapter.notifyDataSetChanged();
        }
        /**
         * The date should be a string value？？？
         */
    }

    @Override
    public void setResult(Result result) {

    }
}
