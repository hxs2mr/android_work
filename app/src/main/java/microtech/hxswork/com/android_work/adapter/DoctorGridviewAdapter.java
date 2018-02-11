package microtech.hxswork.com.android_work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import microtech.hxswork.com.android_work.R;


/**
 * Created by microtech on 2017/8/30.
 */

public class DoctorGridviewAdapter extends BaseAdapter {

    int time[] ;
    Context context;
    public  DoctorGridviewAdapter(Context context,int time[])
    {
        this.context = context;
        this.time = time;
    }
    @Override
    public int getCount() {
        return time.length;
    }

    @Override
    public Object getItem(int i) {
        return time[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.doctor_gridview_item, null);
            viewHolder.imageView = view.findViewById(R.id.gridview_item_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (time[i] == 1) {//gridview_item
            //.imageView.setImageResource(R.mipmap.gridview_item);
            viewHolder.imageView.setVisibility(View.VISIBLE);
        } else
        {   //viewHolder.imageView.setImageResource(R.mipmap.gridview_item);
            viewHolder.imageView.setVisibility(View.INVISIBLE);
        }
        return view;
    }
    class ViewHolder{
        ImageView imageView;
    }
}
