package com.aroominn.aroom.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aroominn.aroom.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HeadListAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater mInflater;//布局装载器对象



    public HeadListAdapter(Context context, List<String> list) {
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
        ViewHolder viewHolder;
        //如果view未被实例化过，缓存池中没有对应的缓存
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // 由于我们只需要将XML转化为View，并不涉及到具体的布局，所以第二个参数通常设置为null
            convertView = mInflater.inflate(R.layout.list_item_head, null);

            //对viewHolder的属性进行赋值
            viewHolder.imageView = convertView.findViewById(R.id.list_head_image);

//            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
//            viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content);

            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHolder);
        } else {//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }


        // 设置控件的数据
        Glide.with(convertView)
                .load(list.get(i))
                .into(viewHolder.imageView);
//        viewHolder.title.setText(bean.itemTitle);
//        viewHolder.content.setText(bean.itemContent);
        return convertView;
    }

    // ViewHolder用于缓存控件
    class ViewHolder {
        public ImageView imageView;
    }
}
