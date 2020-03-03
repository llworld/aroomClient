package com.aroominn.aroom.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.utils.L;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlbumListAdapter extends BaseQuickAdapter<HashMap<String, List<String>>, BaseViewHolder> {

    private Context context;
    public AlbumListAdapter(Context context,int layoutResId, @Nullable List<HashMap<String, List<String>>> data) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HashMap<String, List<String>> item) {
        Set<String> set = item.keySet();
        helper.setText(R.id.list_item_album_name, set.iterator().next().toString());

        Collection<List<String>> t = item.values();
        helper.setText(R.id.list_item_number, "("+t.iterator().next().size()+")");
        Glide.with(context)
        .load(t.iterator().next().get(0).toString())
        .into((ImageView) helper.getView(R.id.list_item_cover));
        L.e(set.iterator().next().toString()+"-->"+t.iterator().next().size()+"----->"+t.iterator().next().toString());
    }


//    @Override
//    protected void convert(BaseViewHolder helper, List<HashMap<String, List<String>>> item) {
//        helper.setText(R.id.list_item_album_name, item.keySet().toArray()[0] + "");
//
//    }

    /*@Override
    protected void convert(BaseViewHolder helper, HashMap<String, List<String>> item) {
helper.setText(R.id.list_item_album_name,item.keySet().toArray()[0]+"");
    }*/
}
