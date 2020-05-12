package com.cdqj.cdqjpatrolandroid.adapter;

import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.PiesChildItem;
import com.cdqj.cdqjpatrolandroid.bean.PiesParentItem;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class PiesRvAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;
    private List<PiesChildItem> muitEntity = new ArrayList<>();
    private onSelectResultListener listener;
    private boolean isSingle = true;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PiesRvAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, com.cdqj.cdqjpatrolandroid.R.layout.cdqj_patrol_dialog_plus_item);
        addItemType(TYPE_LEVEL_1, com.cdqj.cdqjpatrolandroid.R.layout.cdqj_patrol_dialog_plus_item);
    }

    public void setListener(onSelectResultListener listener) {
        this.listener = listener;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final PiesParentItem parentItem = (PiesParentItem) item;
                holder.setText(R.id.dialog_item_name, parentItem.getText());
                TextView nameParent = holder.getView(R.id.dialog_item_name);
                holder.setVisible(R.id.dialog_item_true, false);
                nameParent.setTypeface(Typeface.DEFAULT_BOLD);
                /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (parentItem.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });*/
                break;
            case TYPE_LEVEL_1:
                final PiesChildItem childItem = (PiesChildItem) item;
                holder.setText(R.id.dialog_item_name, childItem.getText());
                TextView nameChild = holder.getView(R.id.dialog_item_name);
                nameChild.setTypeface(Typeface.DEFAULT);
                ImageView imageView = holder.getView(R.id.dialog_item_true);
                imageView.setVisibility(childItem.isSelect() ? View.VISIBLE : View.GONE);
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 36;
                holder.itemView.setLayoutParams(params);
                if (isSingle) {
                    holder.itemView.setOnClickListener(v -> {
                        List<MultiItemEntity> data = getData();
                        for (int k = 0; k < data.size(); k++) {
                            if (data.get(k).getItemType() == TYPE_LEVEL_1) {
                                PiesChildItem childItem1 = (PiesChildItem) data.get(k);
                                if (childItem1.getParentIndex() == childItem.getParentIndex() && childItem1.getIndex() == childItem.getIndex()) {
                                    childItem1.setSelect(true);
                                } else {
                                    childItem1.setSelect(false);
                                }
                            }
                        }
                        setNewData(data);
                        notifyDataSetChanged();
                        if (listener != null) {
                            listener.onSingleResult(childItem);
                        }
                    });
                } else {
                    holder.itemView.setOnClickListener(v -> {
                        childItem.setSelect(!childItem.isSelect());
                        notifyItemChanged(holder.getAdapterPosition());
                        if (childItem.isSelect()) {
                            muitEntity.add(childItem);
                        } else {
                            muitEntity.remove(childItem);
                        }
                        if (listener != null) {
                            listener.onMuitResult(muitEntity);
                        }
                    });
                }
                break;
        }
    }

    public interface onSelectResultListener {
        void onSingleResult(PiesChildItem entity);

        void onMuitResult(List<PiesChildItem> data);
    }
}
