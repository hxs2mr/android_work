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
import microtech.hxswork.com.android_work.bean.HeathMoreBean;

/**
 * Created by microtech on 2017/9/18.
 */

public class HeathMoreAdapter extends RecyclerView.Adapter<HeathMoreAdapter.ViewHolder> {

List<HeathMoreBean> list;

    Context context;
public HeathMoreAdapter(Context context,List<HeathMoreBean> list)
{
    this.context = context;
    this.list = list;
}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.heathmore_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.more_time.setText(list.get(position).getXueya().getTime());
        holder.more_title.setText(list.get(position).getXueya().getZhuan());
        holder.more_title_num.setText(list.get(position).getXueya().getZhuan_zhi());
        holder.heath_more_data.setText(list.get(position).getXueya().getZhuan_fen());
        int flage= list.get(position).getXueya().getFlage();

        if(flage == 0 )
        {
            holder.more_title.setTextColor(Color.parseColor("#FCD210"));
        }else if(flage == 1)
        {
            holder.more_title.setTextColor(Color.parseColor("#38E6FF"));
        }else {
            holder.more_title.setTextColor(Color.parseColor("#FD5EA1"));
        }
        if(position == 0 ) {
            holder.more_title_image.setImageResource(R.mipmap.more_xueya);
            holder.heath_title.setText("血压");
            holder.more_title_next.setText("收缩压/舒张压：");
            holder.more_changkao_num.setText("90<收缩压<140，60<舒张压<90mmHg");
        }else if(position == 1)
        {  holder.more_title_image.setImageResource(R.mipmap.more_xinlv);
            holder.heath_title.setText("心率");
            holder.more_title_next.setText("心率：");
            holder.more_changkao_num.setText("60<正常<100bpm，55<理想<70bpm");
        }else if(position == 2)
        {
            holder.more_title_image.setImageResource(R.mipmap.more_xueyan);
            holder.heath_title.setText("血氧");

            holder.more_title_next.setText("血氧饱和度：");
            holder.more_changkao_num.setText("95%<正常<99%，95%<理想<97%");

        }else if(position == 3)
        {
            holder.more_title_image.setImageResource(R.mipmap.more_xuetan);
            holder.heath_title.setText("血糖");
            holder.more_title_next.setText("血糖值：");
            holder.more_changkao_num.setText("3.9<正常<6.1mmol，4.1<理想<5.8mmol");

        }else if(position == 4)
        {
            holder.more_title_image.setImageResource(R.mipmap.more_xuetan);
            holder.heath_title.setText("体温");
            holder.more_title_next.setText("体温：");
            holder.more_changkao_num.setText("36.3℃<正常<37.2℃");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView more_title_image;
        TextView heath_title;
        TextView more_time;
        TextView more_title;
        TextView more_title_next;
        TextView more_title_num;
        TextView more_changkao_num;
        TextView heath_more_data;

        public ViewHolder(View itemView) {
            super(itemView);
            more_title_image = itemView.findViewById(R.id.more_title_image);
            heath_title =  itemView.findViewById(R.id.heath_title);
            more_time = (TextView) itemView.findViewById(R.id.more_time);
            more_title = (TextView) itemView.findViewById(R.id.more_title);
            more_title_next = (TextView) itemView.findViewById(R.id.more_title_next);
            more_title_num = (TextView) itemView.findViewById(R.id.more_title_num);
            more_changkao_num = (TextView) itemView.findViewById(R.id.more_changkao_num);
            heath_more_data = (TextView) itemView.findViewById(R.id.heath_more_data);

        }
    }
}
