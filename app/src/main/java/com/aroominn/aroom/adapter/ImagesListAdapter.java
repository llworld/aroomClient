package com.aroominn.aroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aroominn.aroom.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImagesListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;//布局装载器对象
    private List<String> list = new ArrayList<>();

    public ImagesListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.list_item_images, null);
            holder = new ViewHolder();
//            holder.textView = (TextView) convertView.findViewById(R.id.textview_item);
            holder.imageView = convertView.findViewById(R.id.list_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置控件的数据
        Glide.with(convertView)
                .load(list.get(i))
                .thumbnail(0.1f)
                .into(holder.imageView);

//        GridItem item = mGridData.get(position);
//        holder.textView.setText(item.getTitle());
//        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        return convertView;
    }

    // ViewHolder用于缓存控件
    class ViewHolder {
        public ImageView imageView;
    }
}
