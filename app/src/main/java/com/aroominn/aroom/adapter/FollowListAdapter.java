package com.aroominn.aroom.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.aroominn.aroom.R;
import com.aroominn.aroom.bean.FriendData;
import com.aroominn.aroom.bean.User;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowListAdapter extends BaseQuickAdapter<FriendData, BaseViewHolder> {

    Context context;

    public FollowListAdapter(Context context, int layoutResId, @Nullable List<FriendData> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendData item) {
        if (item != null) {

            if (!TextUtils.isEmpty(item.getNick())) {

                helper.setText(R.id.follow_item_nick, item.getNick());
            }
            if (!TextUtils.isEmpty(item.getHead())) {
                CircleImageView imageView = helper.getView(R.id.follow_item_header);
                Glide.with(context)
                        .load(item.getHead())
                        .into(imageView);
            }
            helper.addOnClickListener(R.id.follow_item_header);
            helper.addOnClickListener(R.id.follow_item_rl);

        }
    }


}
