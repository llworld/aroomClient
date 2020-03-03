package com.aroominn.aroom.adapter;

import android.support.annotation.Nullable;
import android.widget.Button;

import com.aroominn.aroom.R;
import com.aroominn.aroom.bean.Message;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MessageListAdapter  extends BaseQuickAdapter<Message,BaseViewHolder> {

    public MessageListAdapter(int layoutResId, @Nullable List<Message> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {

        helper.setText(R.id.message_item_nick,"ddddddddd");
        helper.addOnClickListener(R.id.message_item_delete);
        helper.addOnClickListener(R.id.message_item_header);
        helper.addOnClickListener(R.id.message_item_content);
        helper.addOnClickListener(R.id.message_item_stick);
//        Button button=helper.itemView.findViewById(R.id.message_item_delete);


    }
}
