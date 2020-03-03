package com.aroominn.aroom.view.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.TastyListAdapter;
import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.bean.Stories;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 佳肴
 * 自己的故事历史
 */
public class TastyFragment extends BaseFragment {

    @BindView(R.id.tasty_list)
    RecyclerView recyclerView;

    public static final String TAG = TastyFragment.class.getSimpleName();
    private TastyListAdapter adapter;

    public static TastyFragment newInstance() {
        return new TastyFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_tasty;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ArrayList<Stories> stories = new ArrayList<>();
        Stories s = new Stories();
        stories.add(s);
        stories.add(s);
        stories.add(s);
        stories.add(s);
        stories.add(s);
        adapter = new TastyListAdapter(R.layout.list_history_item, stories);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

    }
}
