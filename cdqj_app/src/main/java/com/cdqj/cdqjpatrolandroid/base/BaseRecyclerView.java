package com.cdqj.cdqjpatrolandroid.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdqj.cdqjpatrolandroid.R;

/**
 * Created by lyf on 2018/5/2 10:00
 *
 * @author lyf
 * desc：设置空数据
 */
public class BaseRecyclerView extends RecyclerView {

    private View emptyView;

    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            //设置空view原理都一样，没有数据显示空view，有数据隐藏空view
            Adapter adapter = getAdapter();
            if (adapter.getItemCount() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                BaseRecyclerView.this.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                BaseRecyclerView.this.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            onChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            onChanged();
        }
    };

    public BaseRecyclerView(Context context) {
        super(context);
        emptyView = LayoutInflater.from(context).inflate(R.layout.cdqj_patrol_list_empty_view,null);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        emptyView = LayoutInflater.from(context).inflate(R.layout.cdqj_patrol_list_empty_view,null);
    }

    public void setEmptyView(View view) {
        this.emptyView = view;
        ((ViewGroup) this.getRootView()).addView(view);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        adapter.registerAdapterDataObserver(observer);
        //observer.onChanged();
    }
}
