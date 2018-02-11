package microtech.hxswork.com.android_work.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by microtech on 2017/11/8.
 */

public abstract class BaseRecyclerViewAdapter<T> extends BaseQuickAdapter<T , BaseViewHolder> {
    public BaseRecyclerViewAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }
}
