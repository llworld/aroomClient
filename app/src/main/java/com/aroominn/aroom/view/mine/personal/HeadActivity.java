package com.aroominn.aroom.view.mine.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.HeadListAdapter;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.view.views.PersonalView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 头像
 */
public class HeadActivity extends BaseActivity implements AdapterView.OnItemClickListener,PersonalView{

    @BindView(R.id.head_grid)
    GridView gridView;

    private HeadListAdapter adapter;
    private List<String> list;

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {


        list = new ArrayList<>();
        String s1 = "http://192.168.1.6/resource/images/DefaultHead/xiaoliu.jpg";
        String s2 = "http://192.168.1.6/resource/images/DefaultHead/xiaoguo.jpg";
        String s3 = "http://192.168.1.6/resource/images/DefaultHead/zhanggui.jpg";
        String s4 = "http://192.168.1.6/resource/images/DefaultHead/xiucai.jpg";
        String s5 = "http://192.168.1.6/resource/images/DefaultHead/dazui.jpg";
        String s6 = "http://192.168.1.6/resource/images/DefaultHead/laobai.jpg";
        list.add(s1);

        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        list.add(s6);
        adapter = new HeadListAdapter(this, list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_head;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        L.e("这是点击了"+i+"-->"+list.get(i));

        Intent intent =getIntent();
        //这里使用bundle绷带来传输数据
        Bundle bundle =new Bundle();
        //传输的内容仍然是键值对的形式
        bundle.putString("url",list.get(i));//回发的消息,hello world from secondActivity!
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();


    }

    @Override
    public void showError(BasicResponse error, String url) {

    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public void setPhoneSms(String sms) {

    }
}
