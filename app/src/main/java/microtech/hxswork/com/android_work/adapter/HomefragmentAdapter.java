package microtech.hxswork.com.android_work.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.w3c.dom.Text;

import java.util.List;

import microtech.hxswork.com.android_work.InfoRes.HomeInfoRes;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.ui.fragment.HeathMoreFragment;
import microtech.hxswork.com.android_work.ui.fragment.SignDeailFragment;

import static android.content.Context.MODE_PRIVATE;
import static microtech.hxswork.com.android_work.Util.TimeUtils.stampToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_id;

/**
 * Created by microtech on 2017/8/22.
 */

public class HomefragmentAdapter extends BaseMultiItemQuickAdapter<HomeInfoRes,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    Context context;
    List<HomeInfoRes> data;
    HomeActivity mActivity;
    public HomefragmentAdapter(List<HomeInfoRes> data, Context context, HomeActivity mActivity) {
        super(data);
        this.context = context;
        this.data= data;
        this.mActivity =mActivity;
        addItemType(HomeInfoRes.SIGN_DATA, R.layout.home_signday_activity);//今日体质信息
        addItemType(HomeInfoRes.HEALTH_DATA, R.layout.home_heathday_activity);//今日健康状况
        addItemType(HomeInfoRes.OTHER_DATA,R.layout.home_other_activity);//其他信息
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeInfoRes item) {
        switch (helper.getItemViewType())
        {
            case HomeInfoRes.SIGN_DATA:

                LinearLayout xueya_linear = helper.convertView.findViewById(R.id.xueya_linear);
                LinearLayout xuelu_linear = helper.convertView.findViewById(R.id.xuelu_linear);
                LinearLayout xueyan_linear = helper.convertView.findViewById(R.id.xueyan_linear);
                LinearLayout xuetan_linear = helper.convertView.findViewById(R.id.xuetan_linear);
                LinearLayout tiwen_linear = helper.convertView.findViewById(R.id.tiwen_linear);//对应的signdeil_fragment

                if(LoadData().equals("0")) {
                    xueya_linear.startAnimation(AnimationUtils.loadAnimation(context, R.anim.x_anim));//加载动画
                    xuelu_linear.startAnimation(AnimationUtils.loadAnimation(context, R.anim.x_anim1));//加载动画
                    xueyan_linear.startAnimation(AnimationUtils.loadAnimation(context, R.anim.x_anim2));//加载动画
                    xuetan_linear.startAnimation(AnimationUtils.loadAnimation(context, R.anim.x_anim3));//加载动画
                    tiwen_linear.startAnimation(AnimationUtils.loadAnimation(context, R.anim.x_anim4));//加载动画
                    SaveData();
                }
                LinearLayout sign_linear1 =  helper.convertView.findViewById(R.id.sign_linear1);
                TextView text_tiwen_jieguo= helper.convertView.findViewById(R.id.sign_tiwen_jieguo);//设置结果字体样式
                TextView text_xueya_jieguo= helper.convertView.findViewById(R.id.sign_xueya_jieguo);
                TextView text_xueyan_jieguo= helper.convertView.findViewById(R.id.sign_xueyan_jieguo);
                TextView text_xuetan_jieguo= helper.convertView.findViewById(R.id.sign_xuetan_jieguo);
                TextView text_xinlu_jieguo= helper.convertView.findViewById(R.id.sign_xinlu_jieguo);


                LinearLayout home_signdata_linear1 = helper.convertView.findViewById(R.id.home_signdata_linear1);
                LinearLayout home_signdata_linear2 = helper.convertView.findViewById(R.id.home_signdata_linear2);
                LinearLayout home_signdata_linear3 = helper.convertView.findViewById(R.id.home_signdata_linear3);
                LinearLayout home_signdata_linear4 = helper.convertView.findViewById(R.id.home_signdata_linear4);
                LinearLayout home_signdata_linear5 = helper.convertView.findViewById(R.id.home_signdata_linear5);

                TextView home_sign_nodata1= helper.convertView.findViewById(R.id.home_sign_nodata1);
                TextView home_sign_nodata2= helper.convertView.findViewById(R.id.home_sign_nodata2);
                TextView home_sign_nodata3= helper.convertView.findViewById(R.id.home_sign_nodata3);
                TextView home_sign_nodata4= helper.convertView.findViewById(R.id.home_sign_nodata4);
                TextView home_sign_nodata5= helper.convertView.findViewById(R.id.home_sign_nodata5);

                FrameLayout sign_data_framelayout1 =  helper.convertView.findViewById(R.id.sign_data_framelayout1);
                FrameLayout sign_data_framelayout2 =  helper.convertView.findViewById(R.id.sign_data_framelayout2);
                FrameLayout sign_data_framelayout3 =  helper.convertView.findViewById(R.id.sign_data_framelayout3);
                FrameLayout sign_data_framelayout4 =  helper.convertView.findViewById(R.id.sign_data_framelayout4);
                FrameLayout sign_data_framelayout5 =  helper.convertView.findViewById(R.id.sign_data_framelayout5);


               /* Typeface typeface_tiwen = Typeface.createFromAsset(context.getAssets(),"fonts/pinfan.ttf");
                text_tiwen_jieguo.setTypeface(typeface_tiwen);
                text_xueya_jieguo.setTypeface(typeface_tiwen);
                text_xueyan_jieguo.setTypeface(typeface_tiwen);
                text_xuetan_jieguo.setTypeface(typeface_tiwen);
                text_xinlu_jieguo.setTypeface(typeface_tiwen);*/

                TextView sign_tiwen = helper.convertView.findViewById(R.id.sign_tiwen);
                TextView sign_xueya = helper.convertView.findViewById(R.id.sign_xueya);
                TextView sign_xueyan = helper.convertView.findViewById(R.id.sign_xueyan);
                TextView sign_xuetan = helper.convertView.findViewById(R.id.sign_xuetan);
                TextView sign_xinlu = helper.convertView.findViewById(R.id.sign_xinlu);


                if(item.getHome_bean1().getXueya_z().equals("3"))
                {
                    home_signdata_linear1.setVisibility(View.GONE);
                    home_sign_nodata1.setVisibility(View.VISIBLE);
                    text_xueya_jieguo.setText("");
                    helper.setText(R.id.sign_xueya_time, "");
                } else {
                    home_sign_nodata1.setVisibility(View.GONE);
                    home_signdata_linear1.setVisibility(View.VISIBLE);
                    helper.setText(R.id.sign_xueya_time, stampToDate(item.getHome_bean1().getXueya_time(),"MM-dd"));
                    if(item.getHome_bean1().getXueya_z().equals("-1"))//设置字体
                    {
                        text_xueya_jieguo.setTextColor(Color.parseColor("#FCD210"));
                        text_xueya_jieguo.setText("偏低");
                    }else if(item.getHome_bean1().getXueya_z().equals("0"))
                    {
                        text_xueya_jieguo.setTextColor(Color.parseColor("#38E6FF"));
                        text_xueya_jieguo.setText("正常");
                    }else if(item.getHome_bean1().getXueya_z().equals("1"))
                    {
                        text_xueya_jieguo.setTextColor(Color.parseColor("#FD5EA1"));
                        text_xueya_jieguo.setText("偏高");
                    }
                }

                if(item.getHome_bean1().getXinlu_z().equals("3"))
                {
                    home_sign_nodata2.setVisibility(View.VISIBLE);
                    home_signdata_linear2.setVisibility(View.GONE);
                    text_xinlu_jieguo.setText("");
                    helper.setText(R.id.sign_xinlu_time, "");
                }else {
                    home_sign_nodata2.setVisibility(View.GONE);
                    home_signdata_linear2.setVisibility(View.VISIBLE);
                    helper.setText(R.id.sign_xinlu_time,  stampToDate(item.getHome_bean1().getXinlu_time(),"MM-dd"));
                    if(item.getHome_bean1().getXinlu_z().equals("-1"))//设置字体
                    {
                        text_xinlu_jieguo.setText("偏低");
                        text_xinlu_jieguo.setTextColor(Color.parseColor("#FCD210"));
                    }else if(item.getHome_bean1().getXinlu_z().equals("0"))
                    {
                        text_xinlu_jieguo.setText("正常");
                        text_xinlu_jieguo.setTextColor(Color.parseColor("#38E6FF"));
                    }else if(item.getHome_bean1().getXinlu_z().equals("1"))
                    {
                        text_xinlu_jieguo.setText("偏高");
                        text_xinlu_jieguo.setTextColor(Color.parseColor("#FD5EA1"));
                    }
                }

                if(item.getHome_bean1().getXueyan_z().equals("3"))
                {
                    home_sign_nodata3.setVisibility(View.VISIBLE);
                    home_signdata_linear3.setVisibility(View.GONE);
                    text_xueyan_jieguo.setText("");
                    helper.setText(R.id.sign_xueyan_time, "");
                }else {
                    home_sign_nodata3.setVisibility(View.GONE);
                    home_signdata_linear3.setVisibility(View.VISIBLE);
                    text_xueyan_jieguo.setVisibility(View.VISIBLE);
                    helper.setText(R.id.sign_xueyan_time,  stampToDate(item.getHome_bean1().getXueyan_time(),"MM-dd"));

                    if(item.getHome_bean1().getXueyan_z().equals("-1"))//设置字体
                    {
                        text_xueyan_jieguo.setText("偏低");
                        text_xueyan_jieguo.setTextColor(Color.parseColor("#FCD210"));
                    }else if(item.getHome_bean1().getXueyan_z().equals("0"))
                    {
                        text_xueyan_jieguo.setText("正常");
                        text_xueyan_jieguo.setTextColor(Color.parseColor("#38E6FF"));
                    }else if(item.getHome_bean1().getXueyan_z().equals("1"))
                    {
                        text_xueyan_jieguo.setText("偏高");
                        text_xueyan_jieguo.setTextColor(Color.parseColor("#FD5EA1"));
                    }
                }

                 if(item.getHome_bean1().getXuetan_z().equals("3"))
                {
                    home_sign_nodata4.setVisibility(View.VISIBLE);
                    home_signdata_linear4.setVisibility(View.GONE);
                    text_xuetan_jieguo.setText("");
                    helper.setText(R.id.sign_xuetan_time, "");
                }else {
                     text_xuetan_jieguo.setVisibility(View.VISIBLE);
                     home_sign_nodata4.setVisibility(View.GONE);
                     home_signdata_linear4.setVisibility(View.VISIBLE);
                     helper.setText(R.id.sign_xuetan_time,  stampToDate(item.getHome_bean1().getXuetan_time(),"MM-dd"));
                     if(item.getHome_bean1().getXuetan_z().equals("-1"))//设置字体
                     {
                         text_xuetan_jieguo.setText("偏低");
                         text_xuetan_jieguo.setTextColor(Color.parseColor("#FCD210"));
                     }else if(item.getHome_bean1().getXuetan_z().equals("0"))
                     {
                         text_xuetan_jieguo.setText("正常");
                         text_xuetan_jieguo.setTextColor(Color.parseColor("#38E6FF"));
                     }else if(item.getHome_bean1().getXuetan_z().equals("1"))
                     {
                         text_xuetan_jieguo.setText("偏高");
                         text_xuetan_jieguo.setTextColor(Color.parseColor("#FD5EA1"));
                     }
                 }

                 if(item.getHome_bean1().getTiwen_z().equals("3"))
                {
                    home_signdata_linear5.setVisibility(View.GONE);
                    home_sign_nodata5.setVisibility(View.VISIBLE);
                    text_tiwen_jieguo.setText("");
                    helper.setText(R.id.sign_tiwen_time, "");
                }else {
                     home_sign_nodata5.setVisibility(View.GONE);
                     home_signdata_linear5.setVisibility(View.VISIBLE);
                     helper.setText(R.id.sign_tiwen_time,  stampToDate(item.getHome_bean1().getTiwen_time(),"MM-dd"));
                     if(item.getHome_bean1().getTiwen_z().equals("-1"))//设置字体
                     {
                         text_tiwen_jieguo.setText("偏低");
                         text_tiwen_jieguo.setTextColor(Color.parseColor("#FCD210"));
                     }else if(item.getHome_bean1().getTiwen_z().equals("0"))
                     { text_tiwen_jieguo.setText("正常");

                         text_tiwen_jieguo.setTextColor(Color.parseColor("#38E6FF"));
                     }else if(item.getHome_bean1().getTiwen_z().equals("1"))
                     {
                         text_tiwen_jieguo.setText("偏高");
                         text_tiwen_jieguo.setTextColor(Color.parseColor("#FD5EA1"));
                     }
                 }


                helper.setText(R.id.sign_tiwen, item.getHome_bean1().getTiwen())
                        .setText(R.id.sign_xueya, item.getHome_bean1().getXueya_1()+"/"+item.getHome_bean1().getXueya_2())
                        .setText(R.id.sign_xueyan, item.getHome_bean1().getXueyan())
                        .setText(R.id.sign_xuetan, item.getHome_bean1().getXuetan())
                        .setText(R.id.sign_xinlu, item.getHome_bean1().getXinlu());

                Typeface typeface_sign = Typeface.createFromAsset(context.getAssets(),"fonts/ag.otf");
                sign_tiwen.setTypeface(typeface_sign);
                sign_xueya.setTypeface(typeface_sign);
                sign_xueyan.setTypeface(typeface_sign);
                sign_xuetan.setTypeface(typeface_sign);
                sign_xinlu.setTypeface(typeface_sign);
                TextView danwei1 = helper.convertView.findViewById(R.id.danwei1);
                TextView danwei2 = helper.convertView.findViewById(R.id.danwei2);
                TextView danwei3 = helper.convertView.findViewById(R.id.danwei3);
                TextView danwei4 = helper.convertView.findViewById(R.id.danwei4);
                TextView danwei5 = helper.convertView.findViewById(R.id.danwei5);


                xueya_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String a[] = new String[2];
                         a[0]="血压";
                         a[1]="1";
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("data",a);
                        mActivity.pushFragment(new SignDeailFragment(),true,bundle);
                    }
                });
                xuelu_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String a[] = new String[2];
                         a[0]="心率";
                        a[1]="3";
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("data",a);
                        mActivity.pushFragment(new SignDeailFragment(),true,bundle);
                    }
                });

                xueyan_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String a[] = new String[2];
                         a[0]="血氧";
                        a[1] ="4";
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("data",a);
                        mActivity.pushFragment(new SignDeailFragment(),true,bundle);
                    }
                });

                xuetan_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String a[] = new String[2];
                        a[0]="血糖";
                        a[1]="0";
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("data",a);
                        mActivity.pushFragment(new SignDeailFragment(),true,bundle);
                    }
                });

                tiwen_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String a[] = new String[2];
                        a[0]="体温";
                        a[1]="2";
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("data",a);
                        mActivity.pushFragment(new SignDeailFragment(),true,bundle);
                    }
                });

          /*      Typeface typeface_danwei = Typeface.createFromAsset(context.getAssets(),"fonts/danwei.TTF");
                danwei1.setTypeface(typeface_danwei);
                danwei2.setTypeface(typeface_danwei);
                danwei3.setTypeface(typeface_danwei);
                danwei4.setTypeface(typeface_danwei);
                danwei5.setTypeface(typeface_danwei);*/

                break;
            case HomeInfoRes.HEALTH_DATA:
                TextView heath_data = helper.convertView.findViewById(R.id.heath_data);
                TextView heath_more =  helper.convertView.findViewById(R.id.heath_more);
                LinearLayout heath_data_linear= helper.convertView.findViewById(R.id.heath_data_linear);

              /*  if(LoadData().equals("0")) {
                    heath_data_linear.startAnimation(AnimationUtils.loadAnimation(context, R.anim.login_popwindow_enter));//加载动画
                    SaveData();
                }*/
              /*  Typeface typeface_heath_data = Typeface.createFromAsset(context.getAssets(),"fonts/pinfan.ttf");
                heath_data.setTypeface(typeface_heath_data);*/

                /*String str="这是设置TextView部分文字背景颜色和前景颜色的demo!";
                int bstart=str.indexOf("背景");
                int bend=bstart+"背景".length();
                int fstart=str.indexOf("前景");
                int fend=fstart+"前景".length();
                SpannableStringBuilder style=new SpannableStringBuilder(str);
                style.setSpan(new BackgroundColorSpan(Color.RED),bstart,bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new ForegroundColorSpan(Color.RED),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                TextView tvColor=(TextView) findViewById(R.id.tv_color);
                tvColor.setText(style);
*/


                int a[]=new int[2];//表示偏高
                int b[]=new int[2];//表示偏低

                String new_data;
                int aa=0;
                int bb=0;
                SpannableStringBuilder style=new SpannableStringBuilder(item.getHealthBeen());
                new_data = item.getHealthBeen().replace("&"," ").replace("$"," ");

                SpannableStringBuilder style1=new SpannableStringBuilder(new_data);
                for(int i=0;i<item.getHealthBeen().length();i++)
                {
                    if(item.getHealthBeen().charAt(i)=='$')
                    {
                        a[aa]=i;
                        aa++;
                        if(a[1]!=0)
                        {
                            style.setSpan(new ForegroundColorSpan(Color.parseColor("#FD5EA1")),a[0],a[1]+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            style1.setSpan(new ForegroundColorSpan(Color.parseColor("#FD5EA1")),a[0],a[1]+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            a = new int[2];
                            aa=0;
                        }
                    }
                    if(item.getHealthBeen().charAt(i)=='&')
                    {
                        b[bb]=i;
                        bb++;
                        if(b[1]!=0)
                        {
                            style.setSpan(new ForegroundColorSpan(Color.parseColor("#FCD210")),b[0],b[1]+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            style1.setSpan(new ForegroundColorSpan(Color.parseColor("#FCD210")),b[0],b[1]+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            b = new int[2];
                            bb=0;
                        }
                    }
                }
                Typeface typeface_sign1 = Typeface.createFromAsset(context.getAssets(),"fonts/ag.otf");
                helper.setText(R.id.heath_data,style1);
                heath_more.setTypeface(typeface_sign1);
                if(item.getHealthBeen().equals("0"))
                {
                    helper.setText(R.id.heath_data,"绑定设备才有签约医生为您定制化健康分析");
                }

                heath_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mActivity.pushFragment(new HeathMoreFragment(),true);
                    }
                });
                if(box_id==null)
                {
                    heath_more.setVisibility(View.INVISIBLE);
                }
                break;
            case HomeInfoRes.OTHER_DATA:
                break;

        }

    }
    public void SaveData() {
        //指定操作的文件名称
        SharedPreferences share = context.getSharedPreferences("anim", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("anim", "1");         //根据键值对添加数据
        edit.commit();  //保存数据信息
    }
    public String LoadData() {
        //指定操作的文件名称
        SharedPreferences share = context.getSharedPreferences("anim", MODE_PRIVATE);
        String device_no = share.getString("anim", "0");
        return device_no;
    }

}
