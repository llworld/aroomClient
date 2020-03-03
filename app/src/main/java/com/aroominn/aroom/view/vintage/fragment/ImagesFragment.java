package com.aroominn.aroom.view.vintage.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.AlbumListAdapter;
import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.utils.L;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class ImagesFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.images_album_list)
    RecyclerView listView;

    @BindView(R.id.images_image_grid)
    GridView gridView;

    private HashMap<String, List<String>> mAlbumMap = new HashMap<String, List<String>>();
    private ArrayList<HashMap<String, List<String>>> arrayList = new ArrayList<>();

    //照片请求吗
    public static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //关闭进度条
                    //                    mProgressDialog.dismiss();

                    L.e("--->" + mAlbumMap);
                    List<HashMap<String, List<String>>> hashMaps = new ArrayList<>();

                    listAdapter = new AlbumListAdapter(context, R.layout.list_item_album, subGroupOfAlbum(mAlbumMap));
                    listAdapter.setOnItemChildClickListener(ImagesFragment.this);
                    //                    listAdapter = new AlbumListAdapter(context, list = subGroupOfImage(mAlbumMap), mGroupGridView);

                    listView.setAdapter(listAdapter);
                    listView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                    listView.setLayoutManager(new LinearLayoutManager(getContext()));
                    //                    mGroupGridView.setAdapter(adapter);
                    break;
            }
        }

    };

    //hashmap转换为ArrayList，一个键值对转换为一个元素
    private ArrayList<HashMap<String, List<String>>> subGroupOfAlbum(HashMap<String, List<String>> map) {
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            entry.getKey();
            entry.getValue();
            HashMap<String, List<String>> listHashMap = new HashMap<>();
            listHashMap.put(entry.getKey(), entry.getValue());
            arrayList.add(listHashMap);
        }
        return arrayList;
            /*
            Map<String, String> map = new HashMap<String, String>();
            for (Entry<String, String> entry : map.entrySet()) {
            entry.getKey();
            entry.getValue();
    }

            *
            * */
    }


    private AlbumListAdapter listAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_images;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

    }

    @OnClick({R.id.images_album})
    public void OnViewClicked(View view) {
        switch (view.getId()) {
            case R.id.images_album:
//                getAlbums();
                Matisse
                        .from(context)
                        .choose(MimeType.ofAll())//照片视频全部显示
                        .countable(true)//有序选择图片
                        .maxSelectable(9)//最大选择数量为9
                        .gridExpectedSize(120)//图片显示表格的大小getResources()
//                    .getDimensionPixelSize(R.dimen.grid_expected_size)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//图像选择和预览活动所需的方向。
                        .thumbnailScale(0.85f)//缩放比例
                        .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                        .imageEngine(new GlideEngine())//加载方式
                        .forResult(REQUEST_CODE_CHOOSE);//请求码
                break;
        }
    }

    //    读取本地相册
    private void getAlbums() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                //                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED+" DESC");
                //                {Camera=[/storage/emulated/0/DCIM/Camera/IMG_20190222_081342.jpg, /storage/emulated/0/DCIM/Camera/IMG_20190222_081056.jpg, /storage/emulated/0/DCIM/Camera/IMG_20190222_080924.jpg]}

                if (mCursor == null) {
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();


                    //根据父路径名将图片放入到mAlbumMap中
                    if (!mAlbumMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        mAlbumMap.put(parentName, chileList);
                    } else {
                        mAlbumMap.get(parentName).add(path);
                    }
                }
                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(1);
                mCursor.close();
            }
        }).start();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    List<Uri> mSelected;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }
}
