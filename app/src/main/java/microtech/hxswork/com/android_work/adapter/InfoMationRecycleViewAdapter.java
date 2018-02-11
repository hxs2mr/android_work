package microtech.hxswork.com.android_work.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.bean.InforMationReycleViewBean;
import microtech.hxswork.com.android_work.widget.SlidingButtonView;

/**
 * Created by microtech on 2017/8/30.
 */

public class InfoMationRecycleViewAdapter extends RecyclerView.Adapter<InfoMationRecycleViewAdapter.ItemViewHolder> implements SlidingButtonView.IonSlidingButtonListener {

    private  List<InforMationReycleViewBean> list_data;
    private  OnItemClickListener mOnItemClickListener;
    private SlidingButtonView mMenu = null;
    Context context;
    public  InfoMationRecycleViewAdapter(Context context, List<InforMationReycleViewBean> list_data)
    {
        this.context = context;
        this.list_data = list_data;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.infomatiom_recycleview_item,parent,false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        holder.name.setText(list_data.get(position).getName());
        holder.message.setText(list_data.get(position).getName_message());
        if(list_data.get(position).getMessage_nums().equals("")) {
            holder.message_nums.setVisibility(View.GONE);
        }else {
            holder.message_nums.setVisibility(View.VISIBLE);
            holder.message_nums.setText(list_data.get(position).getMessage_nums());
        }
        holder.time.setText(list_data.get(position).getTime());
        if(list_data.get(position).getType().equals("0"))
        {
            //holder.name.setVisibility(View.GONE);
            holder.name.setText("系统消息");
            holder.name.setTextSize(17);
            holder.image.setImageResource(R.mipmap.service);
        }else {
            holder.image.setImageResource(R.mipmap.doctor_end);
        }
          /*      Glide.with(context)
                        .load(list_data.get(position).getHeadimage_url())
                        .placeholder(R.mipmap.deadpool)
                        .crossFade()
                        .into(holder.image);*/

        if (mOnItemClickListener != null)
            {
                /**
                 * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
                 * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
                 * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
                 */
                if(!holder.layout_content.hasOnClickListeners())
                {
                    holder.layout_content.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (menuIsOpen()) {
                                closeMenu();
                            } else {
                                int pos = holder.getLayoutPosition();
                                    mOnItemClickListener.onItemClick(view,pos);
                            }

                        }
                    });
                    holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int pos = holder.getLayoutPosition();
                            mOnItemClickListener.onDeleteBtnCilck(view,pos);
                        }
                    });
                    holder.layout_content.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            int pos = holder.getLayoutPosition();
                            mOnItemClickListener.onItemLongClick(view,pos);
                            return true;
                        }
                    });
            }

        }

    }


    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
                this.mOnItemClickListener = mOnItemClickListener;
             }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
        void onDeleteBtnCilck(View view, int position);
     }
    class  ItemViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView message;
        TextView time;
        TextView message_nums;
        ImageView image;
        TextView btn_Delete;
        LinearLayout layout_content;
        LinearLayout infomation_width_linear;
        ImageView infomation_right;
        LinearLayout infomation_xiaoxi_linear;


        public ItemViewHolder(View itemView) {
            super(itemView);
            infomation_width_linear = itemView.findViewById(R.id.infomation_width_linear);
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth(); //获取屏幕的宽度

            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) infomation_width_linear.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20

            linearParams.width = width;// 控件的宽强制设成30

            infomation_width_linear.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

            infomation_right = itemView.findViewById(R.id.infomation_right);
            infomation_xiaoxi_linear = itemView.findViewById(R.id.infomation_xiaoxi_linear);

            btn_Delete = itemView.findViewById(R.id.tv_delete);
            name= itemView.findViewById(R.id.infomation_recycleview_item_name);
            message= itemView.findViewById(R.id.infomation_recycleview_item_message);
            time= itemView.findViewById(R.id.infomation_recycleview_item_time);
            message_nums= itemView.findViewById(R.id.infomation_recycleview_item_nums);
            image= itemView.findViewById(R.id.infomation_recycleview_headimage);
            layout_content= itemView.findViewById(R.id.layout_content);
            ((SlidingButtonView) itemView).setSlidingButtonListener(InfoMationRecycleViewAdapter.this);
        }
    }
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                closeMenu();
            }
        }
    }
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        return false;
    }
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;
    }
    public void removeData(int position){
        list_data.remove(position);
        notifyItemRemoved(position);

    }

}
