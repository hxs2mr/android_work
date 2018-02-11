package microtech.hxswork.com.android_work.ui.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.contract.SignItemNextContract;
import microtech.hxswork.com.android_work.model.SignItemNextModelImpl;
import microtech.hxswork.com.android_work.presenter.SignItemNextPresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.ui.activity.LoginActivity;
import microtech.hxswork.com.citypicker.city_20170724.DayTimerPickerView;
import microtech.hxswork.com.citypicker.city_20170724.PersonPickerView;
import microtech.hxswork.com.citypicker.city_20170724.TimerPickerView;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonutils.KeyBordUtil;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;

import static microtech.hxswork.com.android_work.Util.TimeUtils.date2TimeStamp1;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_array_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate1;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idC;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.mDistrictBeanArrayList;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.textA;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.textB;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.textC;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.sign_id;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;
import static microtech.hxswork.com.android_work.ui.fragment.SignDeailFragment.flgae_user;


/**
 * Created by microtech on 2017/9/25.
 */

public class SignItemNextFragment extends BaseFragment<SignItemNextPresenterImpl,SignItemNextModelImpl>implements SignItemNextContract.View, View.OnClickListener {

    @Bind(R.id.signnext_back_linear)
    LinearLayout signnext_back_linear;

    @Bind(R.id.sign_text_title)
    TextView sign_text_title;

    @Bind(R.id.sign_text1)
    TextView sign_text1;

    @Bind(R.id.sign_text2)
    TextView sign_text2;

    @Bind(R.id.sign_text3)
    TextView sign_text3;

    @Bind(R.id.sign_text4)
    TextView sign_text4;

    @Bind(R.id.sign_text5)
    TextView sign_text5;

    @Bind(R.id.signnext_tijiao)
    TextView signnext_tijiao;

     @Bind(R.id.deail_leibie)
     TextView deail_leibie;

    @Bind(R.id.our_linear_end)
            LinearLayout our_linear_end;

    HomeActivity mActivivty;
    OkHttpClientManager ok;
    final String[] SignDeailNext = {null};

    String home_url="https://doc.newmicrotech.cn/otsmobile/app/inputData";//体征数据录入
    Handler handler;
    String Next_uer_id;
    String sing_title[];
    String test_data="";
    ArrayList<String> mDistrictBeanArrayList1 ;//添加关系哦
    int flage_data=0;
    int flage_time=0;
    @Override
    protected int getLayoutResource() {
        return R.layout.signitemnextfragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mActivivty = (HomeActivity) getActivity();
        mDistrictBeanArrayList1 = new ArrayList<>();

        System.out.println("box_id*******"+box_id);
         sing_title =new String[3];
         sing_title = getArguments().getStringArray("data");
         sign_text_title.setText(sing_title[0]+"数据录入");
         Next_uer_id=  sing_title[1];
        if(flgae_user.equals("100"))
        {
            sign_text3.setText(mDistrictBeanArrayList.get(0));
        }else if(flgae_user.equals("010")){
            sign_text3.setText(mDistrictBeanArrayList.get(1));
        }else if (flgae_user.equals("001")){
            sign_text3.setText(mDistrictBeanArrayList.get(2));
        }
        handler = new MyHander();
        //sign_text4.setText(sing_title[0]);
        sign_text1.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        sign_text2.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
        if(sing_title[2].equals("1"))
        {
            deail_leibie.setText("收缩压");
            our_linear_end.setVisibility(View.VISIBLE);
            init_mDistrictBeanArrayList1(sing_title[2]);
            sign_text5.setText(mDistrictBeanArrayList1.get(40));
            sign_text4.setText(mDistrictBeanArrayList1.get(20));
        }else {
            deail_leibie.setText(sing_title[0]);
            our_linear_end.setVisibility(View.GONE);
            init_mDistrictBeanArrayList1(sing_title[2]);
            sign_text4.setText(mDistrictBeanArrayList1.get(10));
        }
        signnext_back_linear.setOnClickListener(this);
        sign_text1.setOnClickListener(this);
        sign_text2.setOnClickListener(this);
        sign_text3.setOnClickListener(this);
        sign_text4.setOnClickListener(this);
        signnext_tijiao.setOnClickListener(this);
        sign_text5.setOnClickListener(this);

       /* WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
        lp.alpha=1.0f;
        mActivivty.getWindow().setAttributes(lp);*/
    }

    private void init_mDistrictBeanArrayList1(String s) {

        if(s.equals("0"))//血糖
        {
            for (int i=0 ; i <= 20;i++)
            {
                mDistrictBeanArrayList1.add(i+".0mmol/L");
            }
        }else if(s.equals("1"))//血压
        {
            for (int i=30 ; i <= 230;i++)
            {
                i = i+3;
                mDistrictBeanArrayList1.add(i+".mmHg");
            }
        }
        else if(s.equals("2"))//体温
        {
            for (int i=30 ; i <= 42;i++)
            {
                mDistrictBeanArrayList1.add(i+".0℃");
            }
        }
        else if(s.equals("3"))//心率
        {
            for (int i=40 ; i <= 150;i++)
            {
                mDistrictBeanArrayList1.add(i+"次/分钟");
            }
        }
        else if(s.equals("4"))//血氧
        {
            for (int i=70 ; i <= 100;i++)
            {
                mDistrictBeanArrayList1.add(i+"%");
            }
        }


    }

    @Override
    public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.sign_text1:
                    DayTimerPickerView cityPicker = new DayTimerPickerView.Builder(mActivivty,getContext()).textSize(20)
                            .titleTextColor("#000000")
                            .backgroundPop(0xffffff)
                            .province(new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date()))
                            .city(new SimpleDateFormat("MM", Locale.getDefault()).format(new Date()))
                            .district(new SimpleDateFormat("dd", Locale.getDefault()).format(new Date()))
                            .textColor(Color.parseColor("#000000"))
                            .provinceCyclic(true)
                            .cityCyclic(false)
                            .districtCyclic(false)
                            .visibleItemsCount(5)
                            .itemPadding(15)
                            .build();
                    cityPicker.show();
                    cityPicker.setOnCityItemClickListener(new DayTimerPickerView.OnCityItemClickListener() {
                        @Override
                        public void onSelected(String province, String city, String district) {

                            if(Integer.parseInt(province)>Integer.parseInt(new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date())))
                            {
                                province = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
                            }
                            if(Integer.parseInt("1"+city)>Integer.parseInt("1"+new SimpleDateFormat("MM", Locale.getDefault()).format(new Date())))
                            {
                                city = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
                            }

                            if(Integer.parseInt("1"+district)>Integer.parseInt("1"+new SimpleDateFormat("dd", Locale.getDefault()).format(new Date())))
                            {
                                district = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                            }
                          /*  int a =Integer.parseInt(city);
                            if(a>0&&a<10)
                            {
                                city="0"+city;
                            }
                            int b =Integer.parseInt(district);
                            if(b>0&&b<10)
                            {
                                district="0"+district;
                            }*/
                            //返回结果
                            sign_text1.setText(province+ "-" + city+ "-" + district);
                            WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
                            flage_data= 1;
                            lp.alpha=1f;
                            mActivivty.getWindow().setAttributes(lp);

                        }
                        @Override
                        public void onCancel() {
                            WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
                            lp.alpha=1f;
                            mActivivty.getWindow().setAttributes(lp);
                        }
                    });

                    break;
                case R.id.sign_text2:
                    TimerPickerView cityPicker1 = new TimerPickerView.Builder(mActivivty,getContext()).textSize(20)
                            .titleTextColor("#000000")
                            .backgroundPop(0xffffff)
                            .province("12")
                            .city("9")
                            .district("2")
                            .textColor(Color.parseColor("#000000"))
                            .provinceCyclic(true)
                            .cityCyclic(false)
                            .districtCyclic(false)
                            .visibleItemsCount(5)
                            .itemPadding(15)
                            .build();
                    cityPicker1.show();
                    cityPicker1.setOnCityItemClickListener(new TimerPickerView.OnCityItemClickListener() {
                        @Override
                        public void onSelected(String province, String city, String district) {
                            int a =Integer.parseInt(province);
                                    if(a>0&&a<10)
                                    {
                                        province="0"+province;
                                    }
                            int b =Integer.parseInt(district);
                            if(b>0&&b<10)
                            {
                                district="0"+district;
                            }
                            //返回结果
                            sign_text2.setText(province+":"+district);
                            WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
                            flage_time = 1;
                            lp.alpha=1f;
                            mActivivty.getWindow().setAttributes(lp);

                        }

                        @Override
                        public void onCancel() {
                            WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
                            lp.alpha=1f;
                            mActivivty.getWindow().setAttributes(lp);
                        }
                    });
                    break;
                case R.id.sign_text3:
                    new KeyBordUtil().hideSoftKeyboard(getView());
                    PersonPickerView cityPicker2 = new PersonPickerView.Builder(mActivivty,getContext()).textSize(20)
                            .titleTextColor("#000000")
                            .backgroundPop(0xffffff)
                            .province("")
                            .city("自己")
                            .district("26")
                            .textColor(Color.parseColor("#000000"))
                            .provinceCyclic(true)
                            .cityCyclic(false)
                            .list(mDistrictBeanArrayList)//添加用户选择
                            .districtCyclic(false)
                            .visibleItemsCount(5)
                            .itemPadding(15)
                            .build();
                    cityPicker2.show();
                    cityPicker2.setOnCityItemClickListener(new PersonPickerView.OnCityItemClickListener() {
                        @Override
                        public void onSelected(String province, String city, String district) {
                            //返回结果
                            if(district.equals(textA))
                            {
                                Next_uer_id = user_idA;
                            }else  if(district.equals(textB))
                            {
                                Next_uer_id = user_idB;
                            }else {
                                Next_uer_id = user_idC;
                            }
                            sign_text3.setText(district);
                            WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
                            lp.alpha=1f;
                            mActivivty.getWindow().setAttributes(lp);

                        }

                        @Override
                        public void onCancel() {
                            WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
                            lp.alpha=1f;
                            mActivivty.getWindow().setAttributes(lp);
                        }
                    });
                    break;
                case R.id.sign_text4:
                    new KeyBordUtil().hideSoftKeyboard(getView());
                    PersonPickerView cityPicker3 = new PersonPickerView.Builder(mActivivty,getContext()).textSize(20)
                            .titleTextColor("#000000")
                            .backgroundPop(0xffffff)
                            .province("")
                            .city("男")
                            .district("26")
                            .textColor(Color.parseColor("#000000"))
                            .provinceCyclic(true)
                            .cityCyclic(false)
                            .list(mDistrictBeanArrayList1)//添加用户选择
                            .districtCyclic(false)
                            .visibleItemsCount(5)
                            .itemPadding(15)
                            .build();
                    cityPicker3.show();
                    cityPicker3.setOnCityItemClickListener(new PersonPickerView.OnCityItemClickListener() {
                        @Override
                        public void onSelected(String province, String city, String district) {
                            //返回结果
                            sign_text4.setText(district);
                            WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
                            lp.alpha=1f;
                            mActivivty.getWindow().setAttributes(lp);

                        }
                        @Override
                        public void onCancel() {
                            WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
                            lp.alpha=1f;
                            mActivivty.getWindow().setAttributes(lp);
                        }
                    });

                    break;
                case R.id.signnext_back_linear:
                        mActivivty.onBackPressed();
                    break;
                case R.id.signnext_tijiao:
                    if(box_id !=null)
                    {
                        thread(Next_uer_id, "");
                    }else
                    {
                        SAToast.makeText(getContext(),"未绑定盒子!").show();
                    }
                    break;
                case R.id.sign_text5:
                    new KeyBordUtil().hideSoftKeyboard(getView());
                    PersonPickerView cityPicker4 = new PersonPickerView.Builder(mActivivty,getContext()).textSize(20)
                            .titleTextColor("#000000")
                            .backgroundPop(0xffffff)
                            .province("")
                            .city("男")
                            .district("26")
                            .textColor(Color.parseColor("#000000"))
                            .provinceCyclic(true)
                            .cityCyclic(false)
                            .list(mDistrictBeanArrayList1)//添加用户选择
                            .districtCyclic(false)
                            .visibleItemsCount(5)
                            .itemPadding(15)
                            .build();
                    cityPicker4.show();
                    cityPicker4.setOnCityItemClickListener(new PersonPickerView.OnCityItemClickListener() {
                        @Override
                        public void onSelected(String province, String city, String district) {
                            //返回结果
                            sign_text5.setText(district);
                            WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
                            lp.alpha=1f;
                            mActivivty.getWindow().setAttributes(lp);

                        }
                        @Override
                        public void onCancel() {
                            WindowManager.LayoutParams lp = mActivivty.getWindow().getAttributes();
                            lp.alpha=1f;
                            mActivivty.getWindow().setAttributes(lp);
                        }
                    });
                    break;

            }
    }


    public void thread(final String user_id , final String phone) {
    if(sing_title[2].equals("1")) {
    test_data =  sign_text5.getText().toString().replace("mmHg","")+"|"+sign_text4.getText().toString().replace("mmHg","");
    }else {
    if(sing_title[2].equals("0")) {
        test_data = sign_text4.getText().toString().replace("mmol/L","");
    }else if(sing_title[2].equals("2")) {
        test_data = sign_text4.getText().toString().replace("℃","");
    }else if(sing_title[2].equals("3")) {
        test_data = sign_text4.getText().toString().replace("次/分钟","");
    }else if(sing_title[2].equals("4")) {
        test_data = sign_text4.getText().toString().replace("%","");
    }
}
        System.out.println("*************user_id******" + user_id);
        System.out.println("*************sign_id******" + sign_id);
        System.out.println("*************box_id******" + box_id);
        System.out.println("*************test_data******" + test_data);
        System.out.println("*************time ******" + sign_text1.getText().toString().trim()+" "+sign_text2.getText().toString().trim());
//数据加载
//数据加载
        LoadingDialog.showDialogForLoading(mActivivty, "修改数据中..", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ok = new OkHttpClientManager();

                    final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                            //主界面
                            new OkHttpClientManager.Param("user_id", user_id),
                            new OkHttpClientManager.Param("time_str",date2TimeStamp1(sign_text1.getText().toString().trim()+" "+sign_text2.getText().toString().trim()+":00","yyyy-MM-dd HH:mm:ss")),//时间戳
                            new OkHttpClientManager.Param("box_id", box_id),
                            new OkHttpClientManager.Param("type", sing_title[2]),
                            new OkHttpClientManager.Param("value", test_data)
                    };
                    SignDeailNext[0] = ok.postOneHeadAsString(home_url,device_no, params_home);//体征数据

                    System.out.println("*******type*******"+sing_title[2]);
                    System.out.println("*******device_no*******"+device_no);
                    System.out.println("*******getStringToDate234234*******"+getStringToDate1(sign_text1.getText().toString().trim()+" "+sign_text2.getText().toString().trim())+"");

                    System.out.println("*******SignDeailNext*******"+SignDeailNext[0]);
                    //修改成功逻辑判断
                    if(sign_id.equals(user_id))//如果修改的是当前用户id的话 返回当前用户的折线图
                    {
                        toast("录入成功!");
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                    LoadingDialog.cancelDialogForLoading();
                    toast("录入成功");
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = SignDeailNext[0];
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class MyHander extends Handler{
        //跟新数据handler
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://代表首次获取
                    break;
                case 1://
                    LoadingDialog.cancelDialogForLoading();
                    mActivivty.onBackPressed();
                    break;

            }
        }
    }

    private void toast(final String msg) {
        mActivivty.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String date2TimeStamp(String date_str,String format){
               try {
                 SimpleDateFormat sdf = new SimpleDateFormat(format);
                  return String.valueOf(sdf.parse(date_str).getTime()/1000);
               } catch (Exception e) {
               e.printStackTrace();
               }
             return "";
            }
}
