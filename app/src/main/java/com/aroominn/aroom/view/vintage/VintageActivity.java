package com.aroominn.aroom.view.vintage;

import android.Manifest;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.presenter.UtilsPresenter;
import com.aroominn.aroom.presenter.impl.UtilsPresenterImpl;
import com.aroominn.aroom.utils.KeyboardUtils;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.MyPermissionHelper;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.aroominn.aroom.utils.ToastUtils;
import com.aroominn.aroom.utils.popupWindow.MyPopupWindow;
import com.aroominn.aroom.view.views.UtilsView;
import com.aroominn.aroom.view.vintage.fragment.EmoticonFragment;
import com.aroominn.aroom.view.vintage.fragment.FriendsFragment;
import com.aroominn.aroom.view.vintage.fragment.ImagesFragment;
import com.aroominn.aroom.view.vintage.fragment.VoiceFragment;
import com.bumptech.glide.Glide;
import com.flyco.roundview.RoundTextView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.utils.StringUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import razerdp.basepopup.BasePopupWindow;
import sakura.bottomtabbar.BottomTabBar;

import static com.aroominn.aroom.view.vintage.fragment.ImagesFragment.REQUEST_CODE_CHOOSE;

/**
 * 酝酿新故事
 */
public class VintageActivity extends BaseActivity implements MyPermissionHelper.PermissionCallBack, UtilsView {

    private static final String IMG_ADD_TAG = "a";
    private static final int IMG_COUNT = 6;

    ////////////////////////////////////////////////////
    @BindView(R.id.vintage_title)
    TitleBar mTitleBar;

    @BindView(R.id.vintage_bottom_bar)
    BottomTabBar tabBar;

    @BindView(R.id.vintage_fl_content)
    FrameLayout tabFragment;

    @BindView(R.id.vintage_state)
    RoundTextView storyState;

    @BindView(R.id.vintage_input)
    EditText input_text;

    @BindView(R.id.vintage_grid_images)
    GridView gridView;

    //发布故事的可见状态
    private String story_state;
    private int story_type = 0;
    private int keyBoardHeight;

    //    权限
    private MyPermissionHelper permissionHelper;
    private GVAdapter adapter;
    private List<Uri> mSelected;
    private UtilsPresenter utilsPresenter;
    private String contentText = "";

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.setPaddingSmart(context, ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));

    }

    @Override
    public void setListener() {
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                getRequestParam();
            }
        });

        input_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.height = 0;
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                tabFragment.setLayoutParams(layoutParams);
                KeyboardUtils.getInstance().showSoftKeyboard(input_text);
            }
        });

        input_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    // 此处为得到焦点时的处理内容
//                    KeyboardUtils.getInstance().showSoftKeyboard(input_text);
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        input_text.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                contentText = editable.toString();
            }
        });

        input_text.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                VintageActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = VintageActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                if (keyBoardHeight == 0)
                    keyBoardHeight = screenHeight - r.bottom;
                L.e("Keyboard Size" + r.bottom, "Size: " + keyBoardHeight);
            }

        });

        tabBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b)
                    KeyboardUtils.getInstance().hideSoftKeyboard(input_text);
            }
        });

    }

    @Override
    public void initData() {

        utilsPresenter = new UtilsPresenterImpl(this);

        permissionHelper = new MyPermissionHelper(this);
        /**
         * 录音拍照所需权限
         */
        permissionHelper.requestPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        );
        tabBar.initFragmentorViewPager(getSupportFragmentManager())
                .addReplaceLayout(R.id.vintage_fl_content)
                .addTabItem("a", getResources().getDrawable(R.drawable.images_icon), getResources().getDrawable(R.drawable.images_icon), ImagesFragment.class)
                .addTabItem("s", getResources().getDrawable(R.drawable.aite_icon), getResources().getDrawable(R.drawable.aite_icon), FriendsFragment.class)
                .addTabItem("d", getResources().getDrawable(R.drawable.microphone_icon), getResources().getDrawable(R.drawable.microphone_icon), VoiceFragment.class)
                .addTabItem("f", getResources().getDrawable(R.drawable.smile_icon), getResources().getDrawable(R.drawable.smile_icon), EmoticonFragment.class)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, View V) {
                        KeyboardUtils.getInstance().hideSoftKeyboard(input_text);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, keyBoardHeight);
                        layoutParams.height = keyBoardHeight;
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        tabFragment.setLayoutParams(layoutParams);
                    }
                })
                .commit();
//////////////////////////////////////////////////////////////////////
//        list = new ArrayList<>();
        mSelected = new ArrayList<>();

        mSelected.add(Uri.parse(IMG_ADD_TAG));

        adapter = new GVAdapter();

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSelected.get(position).toString().equals(IMG_ADD_TAG)) {
                    Matisse
                            .from(VintageActivity.this)
                            .choose(MimeType.ofAll())//照片视频全部显示
                            .countable(true)//有序选择图片
                            .maxSelectable(9)//最大选择数量为9
                            .gridExpectedSize(300)//图片显示表格的大小getResources() 显示图片的大小 应该是px
//                    .getDimensionPixelSize(R.dimen.grid_expected_size)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//图像选择和预览活动所需的方向。
                            .thumbnailScale(1f)//缩放比例
                            .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                            .imageEngine(new com.aroominn.aroom.utils.GlideEngine())//加载方式
                            .forResult(REQUEST_CODE_CHOOSE);//请求码
                }
            }
        });

        refreshAdapter();


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_vintage;
    }

    @OnClick({R.id.vintage_state})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.vintage_state:
                MyPopupWindow myPopupWindow = new MyPopupWindow(context);
                myPopupWindow.showPopupWindow(storyState);


                myPopupWindow.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        storyState.setText(story_state);
                    }
                });
                myPopupWindow.setOnRadioPopupClickListener(new MyPopupWindow.OnRadioPopupClickListener() {
                    @Override
                    public void onItemClick(int what) {

                        switch (what) {
                            case R.id.story_state_all:
                                story_state = "所有人可见";
                                story_type = 0;
                                break;
                            case R.id.story_state_waiter:
                                story_state = "小二可见";
                                story_type = 1;
                                break;
                            case R.id.story_state_mate:
                                story_state = "知己可见";
                                story_type = 2;
                                break;
                            case R.id.story_state_me:
                                story_state = "自己可见";
                                story_type = 3;
                                break;
                            default:
                                story_state = "所有人可见";
                                story_type = 0;
                                break;
                        }
                    }
                });
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            /**
             * 最后的的  加号   会显示图片？？？？？
             */
            mSelected = Matisse.obtainResult(data);
            mSelected.add(Uri.parse(IMG_ADD_TAG));
            refreshAdapter();
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }


    /**
     * 动态申请权限
     *
     * @param permissions
     */
    @Override
    public void permissionRegisterSuccess(String... permissions) {
        L.e("permissions = " + Arrays.toString(permissions));
    }

    @Override
    public void permissionRegisterError(String... permissions) {
        permissionHelper.showGoSettingPermissionsDialog("定位");
    }


    //    权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.handleRequestPermissionsResult(requestCode, this, grantResults);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        permissionHelper.destroy();
    }

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setStory(Result story) {
        if (story.getStatus_code() == 0) {
            finish();
        }
    }

    /**
     * Gets the corresponding path to a file from the given content:// URI
     *
     * @param selectedVideoUri The content:// URI to find the file path from
     * @param contentResolver  The content resolver to use to perform the query.
     * @return the file path as a string
     */
    public static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                   ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
//	    也可用下面的方法拿到cursor
//	    Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }


    public void getRequestParam() {

        if (TextUtils.isEmpty(contentText) || mSelected.size() < 1) {
            ToastUtils.showBottomToast("请输入内容", 0);
            return;
        }

        ContentResolver contentResolver = getContentResolver();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", SharedUtils.getInstance().getUserID().toString())
                .addFormDataPart("content", contentText);//表单类型; add需要的文本，第一个参数与后台对应

        for (int i = 0; i < mSelected.size() - 1; i++) {
            File file1 = null;
            file1 = new File(getFilePathFromContentUri(mSelected.get(i), contentResolver));
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            builder.addFormDataPart("file", file1.getName(), imageBody);//imgfile 后台接收图片流的参数名
        }
        List<MultipartBody.Part> parts = builder.build().parts();

//        实现请求
        utilsPresenter.getStory(VintageActivity.this, contentText, parts);

        return;
    }


//    private List<Uri> list;

    private class GVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mSelected.size();
        }

        @Override
        public Object getItem(int position) {
            return mSelected.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplication()).inflate(R.layout.grid_item_images, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.grid_item_photo);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.grid_item_cb);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Uri s = mSelected.get(position);
            if (!s.toString().equals(IMG_ADD_TAG)) {
                holder.checkBox.setVisibility(View.VISIBLE);
//                holder.imageView.setImageBitmap(ImageTool.createImageThumbnail(s));
                if (s != null)
                    Glide.with(context)
                            .load(s)
                            .into(holder.imageView);
            } else {
                holder.checkBox.setVisibility(View.GONE);
                holder.imageView.setImageResource(R.drawable.bga_pp_ic_plus);
            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelected.remove(position);
                    refreshAdapter();
                    mSelected.size();
                }
            });
            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
        }
    }

    private void refreshAdapter() {
        if (mSelected == null) {
            mSelected = new ArrayList<>();
        }
        if (adapter == null) {
            adapter = new GVAdapter();
        }
        adapter.notifyDataSetChanged();
    }

}
