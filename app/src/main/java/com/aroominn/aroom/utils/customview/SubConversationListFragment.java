package com.aroominn.aroom.utils.customview;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import java.util.List;
import android.view.View;

import com.aroominn.aroom.adapter.im.SubConversationListAdapter;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.fragment.IHistoryDataResultCallback;
import io.rong.imkit.widget.adapter.ConversationListAdapter;

import io.rong.imlib.model.Conversation;

/*rc_fr_conversationlist.xml 	会话列表布局
        rc_item_conversation.xml 	会话列表单个 item 对应的布局*/
//查看 ConversationListFragment 类，选择您需要重写的方法。
public class SubConversationListFragment extends ConversationListFragment {

    private SubConversationListAdapter mAdapter;

    public void setAdapter(SubConversationListAdapter adapter) {
        mAdapter = adapter;
    }
    //重写此方法，设置自己的adapter
    @Override
    public ConversationListAdapter onResolveAdapter(Context context) {
        if (mAdapter == null) {
            mAdapter = new SubConversationListAdapter(context);
        }
        return mAdapter;
    }
    //重写此方法，来填充自定义数据到会话列表界面。

    @Override
    public void getConversationList(Conversation.ConversationType[] conversationTypes, IHistoryDataResultCallback<List<Conversation>> callback, boolean isLoadMore) {
        super.getConversationList(conversationTypes, callback, isLoadMore);
    }

    //重写此方法，来填充头部布局。无会话时不显示，需要在布局中添加填充的头部布局。
    @Override
    protected List<View> onAddHeaderView() {
        List<View> headerViews = super.onAddHeaderView();
        TextView textView = new TextView(this.getActivity());
        textView.setGravity(Gravity.CENTER);
        textView.setText("这是添加的头部布局");
        headerViews.add(textView);
        return headerViews;
    }
}