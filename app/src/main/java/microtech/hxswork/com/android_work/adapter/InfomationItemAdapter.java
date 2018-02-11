package microtech.hxswork.com.android_work.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.bean.InfomationItemBean;

/**
 * Created by microtech on 2017/9/14.
 */

public class InfomationItemAdapter extends RecyclerView.Adapter<InfomationItemAdapter.ViewHolder> {

   Context context;
    List<InfomationItemBean> list;
    public InfomationItemAdapter( Context context, List<InfomationItemBean> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.infomationitem_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitel());
        holder.time.setText(list.get(position).getTime());
        holder.content.setText(list.get(position).getContent());
        if(list.get(position).getTitel().equals("系统消息"))
        {
            holder.some.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public  TextView content;
        public  TextView time;
        public  TextView some;
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.infomation_title);
            content = (TextView) view.findViewById(R.id.infomation_content);
            time = (TextView) view.findViewById(R.id.infomationitem_time);
            some = (TextView) view.findViewById(R.id.infomation_some);
        }

    }
    public void add( InfomationItemBean bean , int position) {
        list.add(position, bean);
        notifyItemInserted(position);
    }

}
