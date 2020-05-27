package com.aroominn.aroom.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.bean.Stories;
import com.aroominn.aroom.utils.TimeUtils;
import com.aroominn.aroom.utils.customview.MyGridView;
import com.aroominn.aroom.utils.customview.SubConversationListFragment;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 自己的故事列表 适配器
 */
public class HistoryListAdapter extends BaseQuickAdapter<Stories, BaseViewHolder> {

    Context context;

    public HistoryListAdapter(Context context, int layoutResId, @Nullable List<Stories> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Stories item) {
        helper.addOnClickListener(R.id.history_item_ll);
        if (!TextUtils.isEmpty(item.getTimes())) {
            helper.setText(R.id.history_item_time, TimeUtils.getInstance().formatTime(item.getTimes()));
        }
        helper.setText(R.id.history_item_content, item.getContent());
        helper.addOnClickListener(R.id.history_item_ll);
        CircleImageView head = helper.getView(R.id.mine_list_item_head);
        Glide.with(context)
                .load(item.getHead())
                .thumbnail(0.2f)
                .into(head);
        if (item.getImages() != null && !item.getImages().equals("[]")) {
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

            MyGridView gridView = helper.getView(R.id.history_item_img);
            HisImagesListAdapter aaImagesListAdapter = new HisImagesListAdapter(context, list);
            gridView.setAdapter(aaImagesListAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    mClickListener.onImageClick(list.get(i));
                }
            });
            helper.setAdapter(R.id.history_item_img, aaImagesListAdapter);

        }
    }

    /*GridView中对图片点击 回调到activity fragment*/
    public void setImage(ImageItemClickListener i) {
        mClickListener = i;
    }

    ImageItemClickListener mClickListener;

    public interface ImageItemClickListener {
        void onImageClick(String p);
    }

}
