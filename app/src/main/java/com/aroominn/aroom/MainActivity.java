package com.aroominn.aroom;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.utils.Const;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.StatusBarUtils;
import com.aroominn.aroom.im.IMListener;
import com.aroominn.aroom.view.inn.InnFragment;
import com.aroominn.aroom.view.message.MessageFragment;
import com.aroominn.aroom.view.mine.MineFragment;
import com.aroominn.aroom.view.radio.RadioFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 查看smartrefresh demo 优化此布局
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_container)
    FrameLayout mainContainer;

    @BindView(R.id.main_message_tab)
    ImageView mainHomeTab;

    @BindView(R.id.main_message_text)
    TextView mainHomeText;

    @BindView(R.id.frame_fm)
    FrameLayout frameRadio;

    //消息
    @BindView(R.id.frame_message)
    FrameLayout frameHome;

    @BindView(R.id.main_inn_tab)
    ImageView mainOrderTab;

    @BindView(R.id.main_inn_text)
    TextView mainOrderText;

    @BindView(R.id.frame_inn)
    FrameLayout frameOrder;

    @BindView(R.id.main_mine_tab)
    ImageView mainMineTab;

    @BindView(R.id.main_mine_text)
    TextView mainMineText;

    @BindView(R.id.frame_mine)
    FrameLayout frameMine;

    private Map<Integer, String> fragments;
    private FragmentManager mFragManager;
    private FrameLayout[] mTabs;
    private boolean firstLogin = false;

    public RadioFragment radioFragment;
    public MessageFragment messageFragment;
    public InnFragment innFragment;
    public MineFragment mineFragment;

    private int index;
    // 当前fragment的index
    private int currentTabIndex;

    @Override
    public void initView(Bundle savedInstanceState) {
//        StatusBarUtil.setTransparent(this);
        boolean te = SharedUtils.getInstance().getLogin();

        mFragManager = getSupportFragmentManager();
        fragments = new HashMap<>();

        fragments.put(0, RadioFragment.TAG);
        fragments.put(1, MessageFragment.TAG);
        fragments.put(2, InnFragment.TAG);
        fragments.put(3, MineFragment.TAG);

        mTabs = new FrameLayout[4];
        mTabs[0] = frameRadio;
        mTabs[1] = frameHome;
        mTabs[2] = frameOrder;
        mTabs[3] = frameMine;

        StatusBarUtils.StatusBarLightMode(this, StatusBarUtils.StatusBarLightMode(this));

    }

    @Override
    public void setListener() {

        L.e("融云IM连接状态" +
                RongIM.getInstance().getCurrentConnectionStatus().getMessage() + RongIM.getInstance().getCurrentConnectionStatus().getValue());
        if (RongIM.getInstance().getCurrentConnectionStatus() ==
                RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED) {
            RongIM.connect(SharedUtils.getInstance().getToken(), IMListener.getInstance().getConnectCallback());

        }
    }


    @Override
    public void initData() {

        //关闭、滑动退出activity
        setSwipeBackEnable(false);

        SharedUtils.getInstance().setFirst(false);
//        exitDialog = DialogUtils.MyChoseDialog(MainActivity.this, onClickListener, R.string.are_you_sure_exit);
        resetButton();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //如果没有授权，则请求授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, Const.MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }


    /**
     * 重置按钮
     */
    public void resetButton() {
        index = currentTabIndex = 0;
        changeFragment(currentTabIndex, index);
        // 把第一个tab设为选中状态
        mTabs[currentTabIndex].setSelected(true);
    }

    /**
     * 刷新页面
     *
     * @param currentIndex 当前页
     */
    public void refreshUI(int currentIndex) {
        index = currentIndex;
        changeFragment(currentTabIndex, index);
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // && index != currentTabIndex
        index = SharedUtils.getInstance().getInt(Const.SHARE_CURRENT_INDEX);
        if (index != -1) {
            SharedUtils.getInstance().setInt(Const.SHARE_CURRENT_INDEX, -1);
            changeFragment(currentTabIndex, index);
            mTabs[currentTabIndex].setSelected(false);
            // 把当前tab设为选中状态
            mTabs[index].setSelected(true);
            currentTabIndex = index;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        firstLogin = getIntent().getBooleanExtra("first", false);
        if (firstLogin) {
            refreshUI(0);
        }
    }

    @OnClick({R.id.frame_fm, R.id.frame_message, R.id.frame_inn, R.id.frame_mine, R.id.frame_vintage})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.frame_fm:
                index = 0;
                refreshUI(index);
                break;
            case R.id.frame_message:
                index = 1;
                refreshUI(index);
                break;
            case R.id.frame_inn:
                index = 2;
                refreshUI(index);
                break;
            case R.id.frame_mine:
                index = 3;
                refreshUI(index);
                break;

            case R.id.frame_vintage:
//                startActivity(new Intent(MainActivity.this, VintageActivity.class));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断请求码
        if (requestCode == Const.MY_PERMISSIONS_REQUEST_LOCATION) {
            //grantResults授权结果
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    startLocation();
                } else {
                    //授权失败
                    L.e("MainActivity 授权失败");
//                    ToastUtils.showBottomToast(this, "授权失败");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void changeFragment(int from, int to) {
        FragmentTransaction transaction = mFragManager.beginTransaction();
        Fragment tofragment = mFragManager.findFragmentByTag(fragments.get(to));

        if (tofragment == null) {
            tofragment = getFragmentFromFactory(fragments.get(to));
        }
        Fragment fromfragment = mFragManager.findFragmentByTag(fragments.get(from));
        if (fromfragment != null) {
            transaction.hide(fromfragment);
        }

        if (!tofragment.isAdded()) {
            transaction.add(R.id.main_container, tofragment, fragments.get(to));
        }
        transaction.show(tofragment).commitAllowingStateLoss();
    }

    private Fragment getFragmentFromFactory(String tag) {

        if (RadioFragment.TAG.equals(tag)) {
            if (radioFragment == null) {
                radioFragment = RadioFragment.newInstance();
            }
            return radioFragment;
        }

        if (MessageFragment.TAG.equals(tag)) {
            if (messageFragment == null) {
                messageFragment = MessageFragment.newInstance();
            }
            return messageFragment;
        }

        if (InnFragment.TAG.equals(tag)) {
            if (innFragment == null) {
                innFragment = InnFragment.newInstance();
            }
            return innFragment;
        }

        if (MineFragment.TAG.equals(tag)) {
            if (mineFragment == null) {
                mineFragment = MineFragment.newInstance();
            }
            return mineFragment;
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        SharedUtils.getInstance().setFirst(false);

    }
}
