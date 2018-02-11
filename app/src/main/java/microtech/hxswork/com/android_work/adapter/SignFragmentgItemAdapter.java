package microtech.hxswork.com.android_work.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.bean.SignItemBean;

/**
 * Created by microtech on 2017/9/25.
 */

public class SignFragmentgItemAdapter extends RecyclerView.Adapter<SignFragmentgItemAdapter.ViewHolder> {
    Context context;
    List<SignItemBean> list;


    public SignFragmentgItemAdapter( Context context, List<SignItemBean> list)
    {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.signfragment_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(list.get(position).getType() == 0)//标识是测试的那天时间
        {
            holder.sing_time_linear.setVisibility(View.VISIBLE);
            holder.sign_time.setText(list.get(position).getSign_time());
            holder.sing_data_linear.setVisibility(View.GONE);
        }else {
            holder.sing_time_linear.setVisibility(View.GONE);
            holder.sing_data_linear.setVisibility(View.VISIBLE);
            holder.sign_number.setText(list.get(position).getNumber());
            holder.time.setText(list.get(position).getTime());
            if(list.get(position).getZhuantai().equals("1"))
            {
                holder.sign_number.setTextColor(Color.parseColor("#FD5EA1"));
                holder.sign_zhuantai.setTextColor(Color.parseColor("#FD5EA1"));
                holder.time.setTextColor(Color.parseColor("#FD5EA1"));
                holder.sign_zhuantai.setText("偏高");
            }else if(list.get(position).getZhuantai().equals("-1"))
            {
                holder.sign_number.setTextColor(Color.parseColor("#FCD210"));
                holder.sign_zhuantai.setTextColor(Color.parseColor("#FCD210"));
                holder.time.setTextColor(Color.parseColor("#FCD210"));
                holder.sign_zhuantai.setText("偏低");
            }else {
                holder.sign_number.setTextColor(Color.parseColor("#898EA6"));
                holder.sign_zhuantai.setTextColor(Color.parseColor("#898EA6"));
                holder.time.setTextColor(Color.parseColor("#898EA6"));
                holder.sign_zhuantai.setText("正常");
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView sign_number;
        public  TextView sign_zhuantai;
        public  TextView sign_time;
        public  TextView time;
        public LinearLayout sing_time_linear;
        public LinearLayout sing_data_linear;
        public ViewHolder(View view) {
            super(view);
            sign_number = (TextView) view.findViewById(R.id.sign_number);
            sign_zhuantai = (TextView) view.findViewById(R.id.sign_zhuantai);
            sign_time = (TextView) view.findViewById(R.id.sign_time);
            time = (TextView) view.findViewById(R.id.time);
            sing_time_linear = view.findViewById(R.id.sing_time_linear);
            sing_data_linear = view.findViewById(R.id.sing_data_linear);
        }

    }
}
