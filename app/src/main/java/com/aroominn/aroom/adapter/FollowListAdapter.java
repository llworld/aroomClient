package com.aroominn.aroom.adapter;

import android.support.annotation.Nullable;

import com.aroominn.aroom.R;
import com.aroominn.aroom.bean.FriendData;
import com.aroominn.aroom.bean.User;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class FollowListAdapter extends BaseQuickAdapter<FriendData, BaseViewHolder> {

    public FollowListAdapter(int layoutResId, @Nullable List<FriendData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendData item) {
        helper.setText(R.id.follow_item_nick, "小二");
        helper.addOnClickListener(R.id.follow_item_header);
        helper.addOnClickListener(R.id.follow_item_rl);

    }
}
