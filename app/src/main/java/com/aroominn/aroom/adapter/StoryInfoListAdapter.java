package com.aroominn.aroom.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.Stories;
import com.aroominn.aroom.bean.Story;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 故事的评论信息
 */
public class StoryInfoListAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {

    public StoryInfoListAdapter(int layoutResId, @Nullable List<Comment> data) {
        super(layoutResId, data);
    }

    static int i = 1;

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {

        if (item != null) {


//        if (!TextUtils.isEmpty(item.getContent())) {
//            helper.setText(R.id.story_item_content, item.getContent());
//        }
//        String str = "默认颜色<font color='#FF0000'>红颜色</font>";
//        helper.setText(R.id.story_item_content, Html.fromHtml(str));
//        helper.setText(R.id.story_item_nick, "柴米油盐");
//        if (!TextUtils.isEmpty(item.getNickName())) {
//            helper.setText(R.id.story_item_nick, item.getContent());
//        }
//
//        ImageView logoview = helper.getView(R.id.story_item_head);
            ImageView test = helper.getView(R.id.story_info_item_head);
//        Glide.with(mContext)
//                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547389787791&di=291e063713a0240a2a686fea173b6ae6&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201503%2F12%2F20150312185948_veyXW.thumb.700_0.jpeg")
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(logoview);
//

            helper.setText(R.id.story_info_floor, i + "楼");
            i++;
            helper.setText(R.id.story_info_reply_text, item.getContent());

            if (item.getNick() != null) {
                helper.setText(R.id.story_info_nick, item.getNick());
            }

            if (item.getHead() != null) {
                Glide.with(mContext)
                        .load(item.getHead())
                        .into(test);
            }

        }
    }
}
