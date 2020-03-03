package com.aroominn.aroom.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * @author yml
 * Created on 2016/9/19 0019.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    View convertView;
    Context context;

    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        this.convertView = itemView;
        this.context = context;
    }

    public void setText(int id, String text) {
        TextView textView = (TextView) convertView.findViewById(id);
        textView.setText(text);
    }

    public void setTextColor(int id, int color) {
        TextView textView = (TextView) convertView.findViewById(id);
        textView.setTextColor(context.getResources().getColor(color));
    }

    public void setImage(int id, String url) {
//        SimpleDraweeView imageView = (SimpleDraweeView) convertView.findViewById(id);
//        FrescoUtils.displayImage(context,url,imageView);
    }

    public void setViewVisible(int id, int isVisible) {
        View view = convertView.findViewById(id);
        view.setVisibility(isVisible);
    }
}
