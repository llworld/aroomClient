package com.aroominn.aroom.view.mine.personal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import butterknife.BindView;

/**
 * 修改客栈名
 */

public class HostelActivity extends BaseActivity implements OnTitleBarListener, TextWatcher {

    @BindView(R.id.hostel_title)
    TitleBar mTitleBar;

    @BindView(R.id.hostel_nick)
    EditText editText;

    private String nick;


    @Override
    public void initView(Bundle savedInstanceState) {

        StatusBarUtil.setPaddingSmart(context, ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));
        try {
            nick = SharedUtils.getInstance().getUser().getNick();
        } catch (NullPointerException e) {
            nick = "";
        }
        editText.setText(nick);
    }

    @Override
    public void setListener() {
        editText.addTextChangedListener(this);
        mTitleBar.setOnTitleBarListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_hostel;
    }

    @Override
    public void onLeftClick(View v) {

    }

    @Override
    public void onTitleClick(View v) {

    }

    @Override
    public void onRightClick(View v) {
        Intent intent = new Intent();
        Bundle bundle =new Bundle();
        //传输的内容仍然是键值对的形式
        bundle.putString("nick",nick);//回发的消息,hello world from secondActivity!
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {
            mTitleBar.setRightColor(R.color.main_theme);
            nick = editable.toString();
        }
    }
}
