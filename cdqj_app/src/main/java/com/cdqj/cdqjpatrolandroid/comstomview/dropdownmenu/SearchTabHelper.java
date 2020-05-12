package com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;

import java.util.List;

/**
 * Created by lyf on 2018/10/24 14:33
 *
 * @author lyf
 * desc：查询类辅助
 */
public class SearchTabHelper {
    /**
     * 查询筛选标题
     */
    private List<SearchTabBean> mList;
    /**
     * 筛选Adapter
     */
    private SearchTabAdapter mAdapter;

    private RecyclerView mRecyclerView;

    public SearchTabHelper(List<SearchTabBean> list, RecyclerView mRecyclerView) {
        mList = list;
        this.mRecyclerView = mRecyclerView;
    }

    @SuppressLint("InflateParams")
    public void initView(Context context, View showView, View parent, OnSearchTabClick click) {
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(5)));
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,mList.size()));

        mAdapter = new SearchTabAdapter(R.layout.cdqj_patrol_search_tab_item, mList, context);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0;i < mList.size();i ++) {
                if (i == position) {
                    mList.get(i).setSelect(!mList.get(i).isSelect());
                } else {
                    mList.get(i).setSelect(false);
                }
            }
            new SpinnerBuild().build(context, mList.get(position).getBeans(), position, showView, parent,
                    new SpinnerBuild.IOnItemSelectListener() {
                        @Override
                        public void onItemClick(int parentPosition, int pos, List<SelectSpinnerBean> spinnerBeanList) {
                            for (int i = 0;i < mList.get(parentPosition).getBeans().size();i ++) {
                                mList.get(parentPosition).getBeans().get(pos).setSelect(i == pos);
                            }
                            click.searchOnItem(parentPosition, mList.get(parentPosition).getBeans().get(pos).getTitle(),
                                    mList.get(parentPosition).getBeans().get(pos).getValue(), true);
                        }

                        @Override
                        public void onSubmitClick(int parentPosition, List<SelectSpinnerBean> spinnerBeanList) {
                            StringBuilder stringBuilder = new StringBuilder();
                            StringBuilder values = new StringBuilder();
                            for (SelectSpinnerBean selectSpinnerBean : spinnerBeanList) {
                                stringBuilder.append(selectSpinnerBean.getTitle());
                                stringBuilder.append("|");
                                values.append(selectSpinnerBean.getValue());
                                values.append(",");
                                for (int i = 0;i < mList.get(parentPosition).getBeans().size();i ++) {
                                    SelectSpinnerBean bean = mList.get(parentPosition).getBeans().get(i);
                                    if (selectSpinnerBean.getTitle().equals(bean.getTitle())) {
                                        mList.get(parentPosition).getBeans().get(i).setSelect(true);
                                    }
                                }
                            }
                            click.searchOnItem(parentPosition, stringBuilder.toString(), values.toString(), false);
                        }

                        @Override
                        public void onReView() {
                            for (int i = 0;i < mList.size();i ++) {
                                mList.get(i).setSelect(false);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setListView(mList.get(position).isListView())
                    .setSingle(mList.get(position).isSingle())
                    .showPop();
            adapter.notifyDataSetChanged();
        });
    }

    /**
     * 查询筛选
     */
    public interface OnSearchTabClick {
        /**
         * 查询筛选监听
         * @param point point 点击位置
         * @param names names 名称（多选以“|”隔开）
         * @param values values 值（多选以“,”隔开）
         * @param isSinge 是否单选
         */
        void searchOnItem(int point, String names, String values, boolean isSinge);
    }
}
