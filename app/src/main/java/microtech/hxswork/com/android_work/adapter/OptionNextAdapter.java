package microtech.hxswork.com.android_work.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.bean.OptionNextBean;

/**
 * Created by microtech on 2017/9/27.
 */

public class OptionNextAdapter extends RecyclerView.Adapter<OptionNextAdapter.ViewHolder> {
    Context context;
    List<OptionNextBean> list;
    OnItemClickListener mOnItemClickListener;
    public  OptionNextAdapter(Context context,List<OptionNextBean> list)
    {
        this.context = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.optionnext_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.str1.setText(list.get(position).getStr1());
        holder.str1_time.setText(list.get(position).getStr1_time());
        holder.str2_time.setText(list.get(position).getStr2_time());
        if (list.get(position).getStr2().equals(""))
        {
            holder.str2.setText("暂无回复请耐心等待");
            holder.str2.setTextColor(Color.parseColor("#D1DDE6"));
        }else {
            holder.str2.setText(list.get(position).getStr2());
        }
        if (mOnItemClickListener != null)
        {
            /**
             * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
             * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
             * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
             */
            holder.optionnext_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(view,pos);
                }
            });
            holder.optionnext_linear.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(view,pos);
                    return true;
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout  optionnext_linear;
        TextView str1;
        TextView str1_time;
        TextView str2;
        TextView str2_time;

        public ViewHolder(View itemView) {
            super(itemView);
            optionnext_linear = itemView.findViewById(R.id.optionnext_linear);
            str1 =  itemView.findViewById(R.id.str1);
            str1_time = (TextView) itemView.findViewById(R.id.str1_time);
            str2 = (TextView) itemView.findViewById(R.id.str2);
            str2_time = (TextView) itemView.findViewById(R.id.str2_time);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
        void onDeleteBtnCilck(View view, int position);
    }

}
