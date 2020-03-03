package com.aroominn.aroom.adapter;

import android.support.annotation.Nullable;

import com.aroominn.aroom.R;
import com.aroominn.aroom.bean.Stories;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TastyListAdapter extends BaseQuickAdapter<Stories,BaseViewHolder>{

    public TastyListAdapter(int layoutResId, @Nullable List<Stories> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Stories item) {
        helper.setText(R.id.history_item_time,"6月17日");
        helper.setText(R.id.history_item_content,"小店有茶有酒有时间，就缺一位老板娘");
    }
}
