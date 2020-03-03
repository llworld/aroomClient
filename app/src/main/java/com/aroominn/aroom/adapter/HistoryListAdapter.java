package com.aroominn.aroom.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Adapter;

import com.aroominn.aroom.R;
import com.aroominn.aroom.bean.Stories;
import com.aroominn.aroom.utils.TimeUtils;
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

        if (!TextUtils.isEmpty(item.getTimes())) {
            helper.setText(R.id.history_item_time, TimeUtils.getInstance().formatTime(item.getTimes()));
        }
        helper.setText(R.id.history_item_content, item.getContent());
        helper.addOnClickListener(R.id.history_item_ll);

        if (item.getImages() != null) {
            JSONArray myJsonArray = null;
            try {
                myJsonArray = new JSONArray(item.getImages());
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

            aaImagesListAdapter aaImagesListAdapter = new aaImagesListAdapter(context, R.layout.list_item_images, list);
//            helper.setAdapter(R.id.history_item_img, (Adapter) aaImagesListAdapter);

        }

    }

}
