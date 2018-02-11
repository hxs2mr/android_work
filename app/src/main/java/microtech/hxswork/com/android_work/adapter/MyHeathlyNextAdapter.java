package microtech.hxswork.com.android_work.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.bean.NextBean1;

/**
 * Created by microtech on 2017/9/6.
 */

public class MyHeathlyNextAdapter extends RecyclerView.Adapter<MyHeathlyNextAdapter.ViewHolder> {

    Context context;
    List<NextBean1> list;
    OnItemClickListener mOnItemClickListener;
    public  MyHeathlyNextAdapter(Context context,List<NextBean1> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.heathly_next_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.next_time.setText(list.get(position).getTime());
        holder.next_content.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getImage_url()).into(holder.next_image);
        if (mOnItemClickListener != null)
        {
            /**
             * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
             * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
             * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
             */
            holder.heathly_next_item_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(view,pos);
                }
            });
            holder.heathly_next_item_linear.setOnLongClickListener(new View.OnLongClickListener() {
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
        ImageView next_image;
        TextView next_content;
        TextView next_time;
        LinearLayout heathly_next_item_linear;

        public ViewHolder(View itemView) {
            super(itemView);
            next_image = itemView.findViewById(R.id.next_image);
            next_content =  itemView.findViewById(R.id.next_content);
            next_time = (TextView) itemView.findViewById(R.id.next_time);
            heathly_next_item_linear = itemView.findViewById(R.id.heathly_next_item_linear);

        }
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
        void onDeleteBtnCilck(View view, int position);
    }

}
