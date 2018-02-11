package microtech.hxswork.com.android_work.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.List;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.bean.SIBean;

/**
 * Created by microtech on 2017/9/11.
 */

public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.ViewHolder>{

    List<SIBean> list;
    Context context;
    OnItemClickListener mOnItemClickListener;
    public ServiceItemAdapter( Context context,List<SIBean> list)
    {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.serviceitem_griditem,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.monkey.setText(list.get(position).getMokey());
        holder.title.setText(list.get(position).getTitle());
        holder.renshu.setText(list.get(position).getRenshu());
        Glide.with(context).load(list.get(position).getImage_url()).into(  holder.imageView);
        if (mOnItemClickListener != null)
        {
            /**
             * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
             * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
             * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
             */
                holder.r.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            int pos = holder.getLayoutPosition();
                            mOnItemClickListener.onItemClick(view,pos);
                    }
                });
                holder.r.setOnLongClickListener(new View.OnLongClickListener() {
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
        ImageView imageView;
        TextView title;
        TextView monkey;
        TextView renshu;
        LinearLayout r;

        public ViewHolder(View itemView) {
            super(itemView);
            r = itemView.findViewById(R.id.r);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            title = (TextView) itemView.findViewById(R.id.item_title);
            monkey = (TextView) itemView.findViewById(R.id.item_monkey);
            renshu = (TextView) itemView.findViewById(R.id.item_renshu);
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
