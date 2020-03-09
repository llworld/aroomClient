package com.aroominn.aroom.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.bean.Stories;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.TimeUtils;
import com.aroominn.aroom.utils.TimerUtils;
import com.aroominn.aroom.utils.customview.MyGridView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoryListAdapter extends BaseQuickAdapter<Stories, BaseViewHolder> {

    private Context context;
    private ImagesListAdapter imagesListAdapter;

    public StoryListAdapter(Context context, int layoutResId, @Nullable List<Stories> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Stories item) {

//        绑定子空间的点击事件
        helper.addOnClickListener(R.id.story_item_head);
        helper.addOnClickListener(R.id.story_item_like);
        helper.addOnClickListener(R.id.story_item_comment);
        helper.addOnClickListener(R.id.story_item_repost);
        helper.addOnClickListener(R.id.story_item_collect);


        helper.setText(R.id.story_item_time, TimeUtils.getInstance().formatTime(item.getTimes()));
        if (!TextUtils.isEmpty(item.getContent())) {
            helper.setText(R.id.story_item_content, item.getContent());
        }
        String str = "默认颜色<font color='#FF0000'>红颜色</font>";
//        helper.setText(R.id.story_item_content, Html.fromHtml(str));
        if (!TextUtils.isEmpty(item.getNick())) {
            helper.setText(R.id.story_item_nick, item.getNick());
        }
        int l = item.getIsLike();
        L.e(l + "这是点赞的");
        /*点赞*/
        ShineButton like = helper.getView(R.id.story_item_like);
        if (item.getIsLike() > 0) {
            like.setChecked(true);
        }

        ShineButton collect = helper.getView(R.id.story_item_collect);
        if (item.getIsCollection() > 0) {
            collect.setChecked(true);
        }

        ImageView logoview = helper.getView(R.id.story_item_head);
        Glide.with(context)
                .load(item.getHead())
                .thumbnail(0.5f)
                .into(logoview);
//            helper.setImage(R.id.story_item_head,"");
//    }
        if (item.getImages() != null) {
            JSONArray myJsonArray = null;
            try {
                myJsonArray = new JSONArray(item.getImages());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < myJsonArray.length(); i++) {
                try {
                    list.add(myJsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//            JSONArray array = new JSONArray();
//            List<T> list = JSONObject.parseArray(array.toJSONString(), T.class);
            imagesListAdapter = new ImagesListAdapter(this.context, list);

            MyGridView gridView = helper.getView(R.id.story_item_grid);

            gridView.setAdapter(imagesListAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    mClickListener.onImageClick(list.get(i));
                }
            });
//            helper.setAdapter(R.id.story_item_grid, imagesListAdapter);
        }
    }

    public void setImage(HistoryListAdapter.ImageItemClickListener i) {
        mClickListener = i;
    }

    HistoryListAdapter.ImageItemClickListener mClickListener;

    public interface ImageItemClickListener {
        void onImageClick(String p);
    }


}
