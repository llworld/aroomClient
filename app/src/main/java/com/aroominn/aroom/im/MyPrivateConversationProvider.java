package com.aroominn.aroom.im;


import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.aroominn.aroom.R;

import io.rong.common.RLog;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.R.bool;
import io.rong.imkit.R.color;
import io.rong.imkit.R.drawable;
import io.rong.imkit.R.id;
import io.rong.imkit.R.layout;
import io.rong.imkit.R.string;
import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ConversationProviderTag;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imkit.utils.RongDateUtils;
import io.rong.imkit.widget.provider.IContainerItemProvider.ConversationProvider;
import io.rong.imkit.widget.provider.PrivateConversationProvider;
import io.rong.imlib.model.UserInfo;
import io.rong.imlib.model.Conversation.ConversationNotificationStatus;
import io.rong.imlib.model.Message.SentStatus;
import io.rong.message.RecallNotificationMessage;

@ConversationProviderTag(
        conversationType = "private",
        portraitPosition = 1
)
public class MyPrivateConversationProvider implements ConversationProvider<UIConversation> {
    private static final String TAG = "MyPrivateConversationProvider";

    public MyPrivateConversationProvider() {
    }
    public View newView(Context context, ViewGroup viewGroup) {
        View result = LayoutInflater.from(context).inflate(layout.rc_item_base_conversation, (ViewGroup)null);
        ViewHolder holder = new ViewHolder();
        holder.title = (TextView)result.findViewById(id.rc_conversation_title);
        holder.time = (TextView)result.findViewById(id.rc_conversation_time);
        holder.content = (TextView)result.findViewById(id.rc_conversation_content);
        holder.notificationBlockImage = (ImageView)result.findViewById(id.rc_conversation_msg_block);
        holder.readStatus = (ImageView)result.findViewById(id.rc_conversation_status);
        result.setTag(holder);
        return result;
    }

    private void handleMentionedContent(final MyPrivateConversationProvider.ViewHolder holder, final View view, final UIConversation data) {
//        int empirical_value = true;
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        final String preStr = view.getContext().getString(string.rc_message_content_mentioned);
        if (holder.content.getWidth() > 60) {
            CharSequence cutStr = TextUtils.ellipsize(preStr + " " + data.getConversationContent(), holder.content.getPaint(), (float)(holder.content.getWidth() - 60), TruncateAt.END);
            SpannableString string = new SpannableString(cutStr);
            int colorSpanLength = preStr.length();
            if (colorSpanLength > cutStr.length()) {
                colorSpanLength = cutStr.length();
            }

            string.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(color.rc_mentioned_color)), 0, colorSpanLength, 33);
            builder.append(string);
            AndroidEmoji.ensure(builder);
            holder.content.setText(builder, BufferType.SPANNABLE);
        } else {
            holder.content.post(new Runnable() {
                public void run() {
                    SpannableString str;
                    if (holder.content.getWidth() > 60) {
                        CharSequence cutStr = TextUtils.ellipsize(preStr + " " + data.getConversationContent(), holder.content.getPaint(), (float)(holder.content.getWidth() - 40), TruncateAt.END);
                        str = new SpannableString(cutStr);
                        int colorSpanLength = preStr.length();
                        if (colorSpanLength > cutStr.length()) {
                            colorSpanLength = cutStr.length();
                        }

                        str.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(color.rc_mentioned_color)), 0, colorSpanLength, 33);
                        builder.append(str);
                    } else {
                        String oriStr = preStr + " " + data.getConversationContent();
                        str = new SpannableString(oriStr);
                        str.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(color.rc_mentioned_color)), 0, preStr.length(), 33);
                        builder.append(str);
                    }

                    AndroidEmoji.ensure(builder);
                    holder.content.setText(builder, BufferType.SPANNABLE);
                }
            });
        }

    }

    private void handleDraftContent(final ViewHolder holder, final View view, final UIConversation data) {
//        int empirical_value = true;
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        final String preStr = view.getContext().getString(string.rc_message_content_draft);
        if (holder.content.getWidth() > 60) {
            CharSequence cutStr = TextUtils.ellipsize(preStr + " " + data.getDraft(), holder.content.getPaint(), (float)(holder.content.getWidth() - 60), TruncateAt.END);
            SpannableString string = new SpannableString(cutStr);
            int colorSpanLength = preStr.length();
            if (colorSpanLength > cutStr.length()) {
                colorSpanLength = cutStr.length();
            }

            string.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(color.rc_draft_color)), 0, colorSpanLength, 33);
            builder.append(string);
            AndroidEmoji.ensure(builder);
            holder.content.setText(builder, BufferType.SPANNABLE);
        } else {
            holder.content.post(new Runnable() {
                public void run() {
                    SpannableString str;
                    if (holder.content.getWidth() > 60) {
                        CharSequence cutStr = TextUtils.ellipsize(preStr + " " + data.getDraft(), holder.content.getPaint(), (float)(holder.content.getWidth() - 60), TruncateAt.END);
                        str = new SpannableString(cutStr);
                        int colorSpanLength = preStr.length();
                        if (colorSpanLength > cutStr.length()) {
                            colorSpanLength = cutStr.length();
                        }

                        str.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(color.rc_draft_color)), 0, colorSpanLength, 33);
                        builder.append(str);
                    } else {
                        String oriStr = preStr + " " + data.getDraft();
                        str = new SpannableString(oriStr);
                        str.setSpan(new ForegroundColorSpan(view.getContext().getResources().getColor(color.rc_draft_color)), 0, preStr.length(), 33);
                        builder.append(str);
                    }

                    AndroidEmoji.ensure(builder);
                    holder.content.setText(builder, BufferType.SPANNABLE);
                }
            });
        }

    }

    private void handleCommonContent(final MyPrivateConversationProvider.ViewHolder holder, UIConversation data) {
//        int empirical_value = true;
        if (holder.content.getWidth() > 60 && data.getConversationContent() != null) {
            CharSequence cutStr = TextUtils.ellipsize(data.getConversationContent(), holder.content.getPaint(), (float)(holder.content.getWidth() - 60), TruncateAt.END);
            holder.content.setText(cutStr, BufferType.SPANNABLE);
        } else {
            final CharSequence cutStr = data.getConversationContent();
            holder.content.post(new Runnable() {
                public void run() {
                    if (holder.content.getWidth() > 60 && cutStr != null) {
                        CharSequence str = TextUtils.ellipsize(cutStr, holder.content.getPaint(), (float)(holder.content.getWidth() - 60), TruncateAt.END);
                        holder.content.setText(str, BufferType.SPANNABLE);
                    } else {
                        holder.content.setText(cutStr);
                    }

                }
            });
        }

    }

    public void bindView(View view, int position, UIConversation data) {
        ViewHolder holder = (ViewHolder)view.getTag();
        ProviderTag tag = null;
        if (data == null) {
            holder.title.setText((CharSequence)null);
            holder.time.setText((CharSequence)null);
            holder.content.setText((CharSequence)null);
        } else {
            holder.title.setText(data.getUIConversationTitle());
            String time = RongDateUtils.getConversationListFormatDate(data.getUIConversationTime(), view.getContext());
            holder.time.setText(time);
            if (TextUtils.isEmpty(data.getDraft()) && !data.getMentionedFlag()) {
                boolean readRec = false;

                try {
                    readRec = view.getResources().getBoolean(bool.rc_read_receipt);
                } catch (NotFoundException var10) {
                    RLog.e("PrivateConversationProvider", "rc_read_receipt not configure in rc_config.xml");
                    var10.printStackTrace();
                }

                if (readRec) {
                    if (data.getSentStatus() == SentStatus.READ && data.getConversationSenderId().equals(RongIM.getInstance().getCurrentUserId()) && !(data.getMessageContent() instanceof RecallNotificationMessage)) {
                        holder.readStatus.setVisibility(View.VISIBLE);
                    } else {
                        holder.readStatus.setVisibility(View.GONE);
                    }
                }

                this.handleCommonContent(holder, data);
            } else {
                if (data.getMentionedFlag()) {
                    this.handleMentionedContent(holder, view, data);
                } else {
                    this.handleDraftContent(holder, view, data);
                }

                holder.readStatus.setVisibility(View.GONE);
            }

            if (RongContext.getInstance() != null && data.getMessageContent() != null) {
                tag = RongContext.getInstance().getMessageProviderTag(data.getMessageContent().getClass());
            }

            if (data.getSentStatus() != null && (data.getSentStatus() == SentStatus.FAILED || data.getSentStatus() == SentStatus.SENDING) && tag != null && tag.showWarning() && data.getConversationSenderId() != null && data.getConversationSenderId().equals(RongIM.getInstance().getCurrentUserId())) {
                Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), drawable.rc_conversation_list_msg_send_failure);
                int width = bitmap.getWidth();
                Drawable drawable = null;
                if (data.getSentStatus() == SentStatus.FAILED && TextUtils.isEmpty(data.getDraft())) {
                    drawable = view.getContext().getResources().getDrawable(R.drawable.rc_conversation_list_msg_send_failure);
                } else if (data.getSentStatus() == SentStatus.SENDING && TextUtils.isEmpty(data.getDraft())) {
                    drawable = view.getContext().getResources().getDrawable(R.drawable.rc_conversation_list_msg_sending);
                }

                if (drawable != null) {
                    drawable.setBounds(0, 0, width, width);
                    holder.content.setCompoundDrawablePadding(10);
                    holder.content.setCompoundDrawables(drawable, (Drawable)null, (Drawable)null, (Drawable)null);
                }
            } else {
                holder.content.setCompoundDrawables((Drawable)null, (Drawable)null, (Drawable)null, (Drawable)null);
            }

            ConversationNotificationStatus status = data.getNotificationStatus();
            if (status != null && status.equals(ConversationNotificationStatus.DO_NOT_DISTURB)) {
                holder.notificationBlockImage.setVisibility(View.VISIBLE);
            } else {
                holder.notificationBlockImage.setVisibility(View.GONE);
            }
        }

    }

    public Spannable getSummary(UIConversation data) {
        return null;
    }

    public String getTitle(String userId) {
        UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(userId);
        return userInfo == null ? userId : userInfo.getName();
    }

    public Uri getPortraitUri(String userId) {
        UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(userId);
        return userInfo == null ? null : userInfo.getPortraitUri();
    }

    protected class ViewHolder {
        public TextView title;
        public TextView time;
        public TextView content;
        public ImageView notificationBlockImage;
        public ImageView readStatus;

        protected ViewHolder() {
        }
    }
}
