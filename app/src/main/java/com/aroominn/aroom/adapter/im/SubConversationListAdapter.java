package com.aroominn.aroom.adapter.im;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.utils.L;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.rong.imkit.RongContext;
import io.rong.imkit.R.color;
import io.rong.imkit.R.drawable;
import io.rong.imkit.R.id;
import io.rong.imkit.R.layout;
import io.rong.imkit.R.string;
import io.rong.imkit.model.ConversationProviderTag;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.model.UIConversation.UnreadRemindType;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imkit.widget.ProviderContainerView;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Conversation.ConversationType;

public class SubConversationListAdapter extends ConversationListAdapter {
    LayoutInflater mInflater;
    Context mContext;

    public long getItemId(int position) {
        UIConversation conversation = (UIConversation) this.getItem(position);
        return conversation == null ? 0L : (long) conversation.hashCode();
    }

    public SubConversationListAdapter(Context context) {
        super(context);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    protected View newView(Context context, int position, ViewGroup group) {
        View result = this.mInflater.inflate(layout.rc_item_conversation, group, false);
        ViewHolder holder = new ViewHolder();
        holder.layout = this.findViewById(result, id.rc_item_conversation);
        holder.leftImageLayout = this.findViewById(result, id.rc_item1);
        holder.rightImageLayout = this.findViewById(result, id.rc_item2);
        holder.leftUnReadView = this.findViewById(result, id.rc_unread_view_left);
        holder.rightUnReadView = this.findViewById(result, id.rc_unread_view_right);
        holder.leftImageView = (AsyncImageView) this.findViewById(result, id.rc_left);
        holder.rightImageView = (AsyncImageView) this.findViewById(result, id.rc_right);
        holder.contentView = (ProviderContainerView) this.findViewById(result, id.rc_content);
        holder.unReadMsgCount = (TextView) this.findViewById(result, id.rc_unread_message);
        holder.unReadMsgCountRight = (TextView) this.findViewById(result, id.rc_unread_message_right);
        holder.unReadMsgCountIcon = (ImageView) this.findViewById(result, id.rc_unread_message_icon);
        holder.unReadMsgCountRightIcon = (ImageView) this.findViewById(result, id.rc_unread_message_icon_right);
        result.setTag(holder);
        return result;
    }

    protected void bindView(View v, int position, UIConversation data) {

        SubConversationListAdapter.ViewHolder holder = (SubConversationListAdapter.ViewHolder) v.getTag();
        IContainerItemProvider provider = RongContext.getInstance().getConversationTemplate(data.getConversationType().getName());

        View view = holder.contentView.inflate(provider);
        provider.bindView(view, position, data);
        /*是否制定*/
        if (data.isTop()) {
            holder.layout.setBackgroundColor(this.mContext.getResources().getColor(color.rc_conversation_top_bg));
        } else {
            holder.layout.setBackgroundColor(this.mContext.getResources().getColor(color.rc_text_color_primary_inverse));
        }

        /*头像*/
        ConversationProviderTag tag = RongContext.getInstance().getConversationProviderTag(data.getConversationType().getName());
//        int defaultId = false;
        int defaultId;
        if (data.getConversationType() == ConversationType.GROUP) {
            defaultId = drawable.rc_default_group_portrait;
        } else if (data.getConversationType() == ConversationType.DISCUSSION) {
            defaultId = drawable.rc_default_discussion_portrait;
        } else {
            defaultId = drawable.rc_default_portrait;
        }


        if (tag.portraitPosition() == 1) {
            holder.leftImageLayout.setVisibility(View.VISIBLE);
            if (data.getIconUrl() != null) {
        L.e(data.getIconUrl().toString()+"  not is null");
                holder.leftImageView.setAvatar(data.getIconUrl().toString(), defaultId);
            } else {
        L.e(data.getIconUrl()+"--》is null");
                holder.leftImageView.setAvatar("https://b-ssl.duitang.com/uploads/item/201701/07/20170107102642_SCAQe.jpeg", defaultId);
//                holder.leftImageView.setAvatar((String) null, defaultId);
            }

            /*未读消息*/
            if (data.getUnReadMessageCount() > 0) {
                holder.unReadMsgCountIcon.setVisibility(View.VISIBLE);
                this.setUnReadViewLayoutParams(holder.leftUnReadView, data.getUnReadType());
                if (data.getUnReadType().equals(UnreadRemindType.REMIND_WITH_COUNTING)) {
                    holder.unReadMsgCount.setVisibility(View.VISIBLE);
                    if (data.getUnReadMessageCount() > 99) {
                        holder.unReadMsgCount.setText(this.mContext.getResources().getString(string.rc_message_unread_count));
                    } else {
                        holder.unReadMsgCount.setText(Integer.toString(data.getUnReadMessageCount()));
                    }

                    holder.unReadMsgCountIcon.setImageResource(drawable.rc_unread_count_bg);
                } else {
                    holder.unReadMsgCount.setVisibility(View.GONE);
                    holder.unReadMsgCountIcon.setImageResource(drawable.rc_unread_remind_without_count);
                }
            } else {
                holder.unReadMsgCountIcon.setVisibility(View.GONE);
                holder.unReadMsgCount.setVisibility(View.GONE);
            }

            holder.rightImageLayout.setVisibility(View.GONE);
        } else if (tag.portraitPosition() == 2) {
            holder.rightImageLayout.setVisibility(View.VISIBLE);
            if (data.getIconUrl() != null) {
                holder.rightImageView.setAvatar(data.getIconUrl().toString(), defaultId);
            } else {
                holder.rightImageView.setAvatar((String) null, defaultId);
            }

            if (data.getUnReadMessageCount() > 0) {
                holder.unReadMsgCountRight.setVisibility(View.VISIBLE);
                holder.unReadMsgCountIcon.setVisibility(View.VISIBLE);
                this.setUnReadViewLayoutParams(holder.rightUnReadView, data.getUnReadType());
                if (data.getUnReadType().equals(UnreadRemindType.REMIND_WITH_COUNTING)) {
                    if (data.getUnReadMessageCount() > 99) {
                        holder.unReadMsgCountRight.setText(this.mContext.getResources().getString(string.rc_message_unread_count));
                    } else {
                        holder.unReadMsgCountRight.setText(Integer.toString(data.getUnReadMessageCount()));
                    }

                    holder.unReadMsgCountIcon.setImageResource(drawable.rc_unread_count_bg);
                } else {
                    holder.unReadMsgCountIcon.setImageResource(drawable.rc_unread_remind_without_count);
                }
            }

            holder.leftImageLayout.setVisibility(View.GONE);
        } else {
            if (tag.portraitPosition() != 3) {
                throw new IllegalArgumentException("the portrait position is wrong!");
            }

            holder.rightImageLayout.setVisibility(View.GONE);
            holder.leftImageLayout.setVisibility(View.GONE);
        }

    }

    class ViewHolder {
        View layout;
        View leftImageLayout;
        View rightImageLayout;
        View leftUnReadView;
        View rightUnReadView;
        AsyncImageView leftImageView;
        AsyncImageView rightImageView;
        ProviderContainerView contentView;
        TextView unReadMsgCount;
        TextView unReadMsgCountRight;
        ImageView unReadMsgCountRightIcon;
        ImageView unReadMsgCountIcon;

        ViewHolder() {
        }
    }
}
