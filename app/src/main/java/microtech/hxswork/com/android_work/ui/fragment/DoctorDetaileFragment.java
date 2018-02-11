package microtech.hxswork.com.android_work.ui.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;

import org.bson.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.adapter.DoctorGridviewAdapter;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.Visit_Bean;
import microtech.hxswork.com.android_work.contract.DoctorContract;
import microtech.hxswork.com.android_work.model.DoctorModelImpl;
import microtech.hxswork.com.android_work.presenter.DoctorPressenterImpl;
import microtech.hxswork.com.android_work.ui.activity.ForgePassword;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.widget.MyScorllView;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;
import microtech.hxswork.com.commom.commonwidget.StatusBarCompat;

import static android.content.Context.MODE_PRIVATE;
import static com.netease.nim.uikit.NimUIKit.getContext;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.accid;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.doctor_status;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_head_image;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.token;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.service_flage;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;
import static microtech.hxswork.com.commom.commonutils.FormatUtil.isMobileNO;

/**
 * Created by microtech on 2017/8/30.
 */

public class DoctorDetaileFragment extends BaseFragment<DoctorPressenterImpl,DoctorModelImpl> implements DoctorContract.View, View.OnClickListener, MyScorllView.ScrollViewListener {

    @Bind(R.id.doctor_liao)
    TextView doctor_liao;

    @Bind(R.id.deail_top_name)
    TextView deail_top_name;

    @Bind(R.id.doctor_name)
     TextView tdoctorname;//医生名称
    @Bind(R.id.doctor_deail_headimage)
     ImageView idoctorhead;//医生头像

    @Bind(R.id.doctor_deail_sex)
    ImageView idoctorsex;//医生性别头像


    @Bind(R.id.doctor_deal_gonhao)
   TextView  tdoctorid;//医生工号

    @Bind(R.id.doctor_phone)
    TextView tdoctorphone;//医生手机号

    @Bind(R.id.doctor_yiyuan)
   TextView tdoctoryiyuan;//医生医院


    @Bind(R.id.doctor_deail_doctorleibie)
    TextView tdoctorleibue;//医生所属类别


    @Bind(R.id.doctor_chang)
     TextView tdoctorkemu;//医生擅长领域

    @Bind(R.id.doctor_time_gridview)
    GridView gridView;//出诊时间标记

    @Bind(R.id.doctor_deail_back)
    ImageView doctor_deail_back;

    @Bind(R.id.deail_linear2)
    LinearLayout deail_linear2;

    @Bind(R.id.deail_linear1)
    LinearLayout deail_linear1;


    @Bind(R.id.deail_top_linear)
    LinearLayout deail_top_linear;

    //申请签约医生模块

    @Bind(R.id.qian_name)
    EditText qian_name;

    @Bind(R.id.qian_phone)
    EditText qian_phone;

    @Bind(R.id.qian_city)
    EditText qian_city;

    @Bind(R.id.qian_1)
     ImageView qian_1;

    @Bind(R.id.qian_2)
    ImageView qian_2;

    @Bind(R.id.qian_3)
    ImageView qian_3;

    @Bind(R.id.qian_4)
    ImageView qian_4;

    @Bind(R.id.qian_ok)
    TextView  qian_ok;

    @Bind(R.id.qian_pop_linear)
    LinearLayout qian_pop_linear;

    @Bind(R.id.qianok_back)
  TextView forpassword_exit;

    @Bind(R.id.doctor_deail_tubiao_frame)
    FrameLayout frameLayout;

    @Bind(R.id.doctor_scorllview)
    MyScorllView scorllView;

    @Bind(R.id.new_head_linear)
            LinearLayout new_head_linear;

    @Bind(R.id.doctor_deail_headimage1)
            ImageView Imagehead2;
    @Bind(R.id.doctor_deail_sex1)
            ImageView sex2;
    @Bind(R.id.doctor_liebie2)
            TextView doctor_liebie2;
    @Bind(R.id.doctor_name2)
            TextView doctor_name2;

    Document document;
    Document document_obj;
    Document document_gov;
    Document document_im;

    String qian[] = new String[4];
    int  time[] ;//= new int[21];//出诊时间

    int flage1 =0 ,flage2 =0 ,flage3 =0 ,flage4 =0  ;

    DoctorGridviewAdapter doctorGridviewAdapter;

     HomeActivity mActivity;

    PopupWindow pop;
    String qian_url="https://doc.newmicrotech.cn/otsmobile/app/signDoctor?";
    String dictirdeail_url="https://doc.newmicrotech.cn/otsmobile/app/doctorInfo?";
    //测试数据
    String home_url="https://doc.newmicrotech.cn/otsmobile/app/physical?";
    OkHttpClientManager ok;
    String qian_data[]={null};
    Handler handler;
    List<String> qian_list;
    String  doctor_headimage = "";
    List<Document> list;
    String doctor_id;
    @Override
    protected int getLayoutResource() {
        return R.layout.doctor_deaile_activity;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mActivity= (HomeActivity) getActivity();
        qian_list= new ArrayList<>();
        mActivity.mFlToolbar.setVisibility(View.VISIBLE);
        StatusBarCompat.setStatusBarColor((Activity) getContext(), ContextCompat.getColor(getContext(), R.color.main_xia));
        qian[0]="";
        qian[1]="";
        qian[2]="";
        qian[3]="";
        handler = new MyHander();
        doctor_deail_back.setOnClickListener(this);
        doctor_liao.setOnClickListener(this);
        qian_ok.setOnClickListener(this);
        qian_1.setOnClickListener(this);
        qian_2.setOnClickListener(this);
        qian_3.setOnClickListener(this);
        qian_4.setOnClickListener(this);
        frameLayout.setOnClickListener(this);
        forpassword_exit.setOnClickListener(this);
        scorllView.setScrollViewListener(this);
        new_head_linear.setOnClickListener(this);
        if(doctor_status==1)//申请签约医生的状态 1:正在申请
        {
            qian_pop_linear.setVisibility(View.VISIBLE);
            deail_linear1.setVisibility(View.GONE);
            deail_linear2.setVisibility(View.GONE);
            deail_top_name.setText("提交成功");
            //show_forgetpassword_pop();
            doctor_liao.setVisibility(View.INVISIBLE);
        }else if(doctor_status == 0) {  // 0:还没有申请
            deail_linear1.setVisibility(View.GONE);
            deail_linear2.setVisibility(View.VISIBLE);
            deail_top_name.setText("申请签约医生");
            doctor_liao.setVisibility(View.INVISIBLE);
        }else {//2：申请成功
                deail_linear1.setVisibility(View.VISIBLE);
                deail_linear2.setVisibility(View.GONE);
                deail_top_name.setText("医生详情");
                doctor_liao.setVisibility(View.VISIBLE);

            SharedPreferences share = mActivity.getSharedPreferences(user+"doctor_fragment", MODE_PRIVATE);//第一次获取医生详情
            if(share.getString("doctor_flage","0").equals("0")) {
                thread_doctor_deail();//获取医生详情
            }else
                {
                     doctor_id=share.getString("doctor_accid","0");
                      Glide.with(mActivity)
                        .load("http://ov62zyke0.bkt.clouddn.com/"+share.getString("doctor_headimage","0"))
                        .placeholder(R.mipmap.deadpool)
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource,
                                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                                if(idoctorhead!=null) {
                                    idoctorhead.setImageDrawable(resource);
                                }
                            }
                        });

                    Glide.with(mActivity)
                            .load("http://ov62zyke0.bkt.clouddn.com/"+share.getString("doctor_headimage","0"))
                            .placeholder(R.mipmap.deadpool)
                            .into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource,
                                                            GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    if(Imagehead2!=null) {
                                        Imagehead2.setImageDrawable(resource);
                                    }
                                }
                            });
                    doctor_name2.setText(share.getString("doctor_name","0"));
                    doctor_liebie2.setText(share.getString("doctor_leibie","0"));
                tdoctorname.setText(share.getString("doctor_name","0"));
                tdoctorid.setText(share.getString("doctor_id","0"));
                tdoctorphone.setText(share.getString("doctor_phone","0"));
                tdoctorleibue.setText(share.getString("doctor_leibie","0"));
                    tdoctoryiyuan.setText(share.getString("hospital","0"));
                if(share.getString("doctor_sex","0").equals("男")) {
                    idoctorsex.setImageResource(R.mipmap.doctor_men);
                }else {
                    idoctorsex.setImageResource(R.mipmap.doctor_women);
                }
                int length =  Integer.parseInt(share.getString("time_length","0"));

                    System.out.println("****length****"+length);
                time = new int[length];
                for(int i =0 ; i <  length;i++)
                {
                    time[i] = Integer.parseInt(share.getString("t"+i,"0"));
                }
                    doctorGridviewAdapter = new DoctorGridviewAdapter(getContext(),time);
                    gridView.setAdapter(doctorGridviewAdapter);
            }
            }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.qianok_back:
                mActivity.pushFragment(new PersonalHeathlyFragment(),true);
                break;
            case R.id.doctor_deail_back:
                break;
            case  R.id.doctor_liao:
                if (doctor_status==2) {
                    final SharedPreferences share = mActivity.getSharedPreferences(user+"doctor_im", MODE_PRIVATE);//第一次获取医生详情
                    NimUIKit.doLogin(new LoginInfo(accid, token), new RequestCallback<LoginInfo>() {
                        @Override
                        public void onSuccess(LoginInfo loginInfo) {
                            NimUIKit.startP2PSession(getContext(), share.getString("accid","0"),tdoctorname.getText().toString(),tdoctoryiyuan.getText().toString());//对方的accid 如：医生的accid
                            //finish(),;
                        }
                        @Override
                        public void onFailed(int i) {

                        }
                        @Override
                        public void onException(Throwable throwable) {
                        }
                    });
                }else {
                    SAToast.makeText(getContext(),"请先绑定专属医生").show();
                }
                break;
           case  R.id.new_head_linear:
               if (doctor_status==2) {
                   NimUIKit.doLogin(new LoginInfo(accid, token), new RequestCallback<LoginInfo>() {
                       @Override
                       public void onSuccess(LoginInfo loginInfo) {
                           NimUIKit.startP2PSession(getContext(), doctor_id,tdoctorname.getText().toString(),tdoctoryiyuan.getText().toString());//对方的accid 如：医生的accid
                           //finish(),;
                       }
                       @Override
                       public void onFailed(int i) {

                       }
                       @Override
                       public void onException(Throwable throwable) {
                       }
                   });
               }else {
                   SAToast.makeText(getContext(),"请先绑定专属医生").show();
               }
            break;
            case R.id.doctor_deail_tubiao_frame:
                if (doctor_status==2) {
                    NimUIKit.doLogin(new LoginInfo(accid, token), new RequestCallback<LoginInfo>() {
                        @Override
                        public void onSuccess(LoginInfo loginInfo) {
                            NimUIKit.startP2PSession(getContext(), doctor_id,tdoctorname.getText().toString(),tdoctoryiyuan.getText().toString());//对方的accid 如：医生的accid
                            //finish();
                        }
                        @Override
                        public void onFailed(int i) {

                        }
                        @Override
                        public void onException(Throwable throwable) {
                        }
                    });
                }else {
                    SAToast.makeText(getContext(),"请先绑定专属医生").show();
                }
                break;
            case R.id.qian_ok://签约医生的网络请求
                init_qian_list();
                init_statr_thread();
                break;
            case R.id.qian_1:
                if (flage1 ==0  ) {
                    qian_1.setImageResource(R.mipmap.qian_on);
                    qian[0] = "老年人";
                    flage1=  1;
                }else {
                    qian_1.setImageResource(R.mipmap.qian_off);
                    qian[0] = "";
                    flage1=  0;
                }
                break;
            case R.id.qian_2:
                if(flage2 == 0) {
                    qian_2.setImageResource(R.mipmap.qian_on);
                    qian[1] = "冠心病";
                    flage2 = 1;
                }else {
                    qian_2.setImageResource(R.mipmap.qian_off);
                    qian[1] = "";
                    flage2 = 0;
                }
                break;
            case R.id.qian_3:
                if(flage3 == 0) {
                    qian_3.setImageResource(R.mipmap.qian_on);
                    qian[2] = "心脏病";
                    flage3 = 1;
                }else {
                    qian_3.setImageResource(R.mipmap.qian_off);
                    qian[2] = "";
                    flage3 = 0;
                }
                break;
            case R.id.qian_4:
                if(flage4 == 0) {
                    qian_4.setImageResource(R.mipmap.qian_on);
                    qian[3] = "糖尿病";
                    flage4 = 1;
                }else {
                    qian_4.setImageResource(R.mipmap.qian_off);
                    qian[3] = "";
                    flage4 = 0;
                }
                break;
        }
    }

    private void init_statr_thread() {
        if(!qian_name.getText().toString().trim().equals("") && !qian_phone.getText().toString().trim().equals(""))
        {
            if(qian_name.getText().toString().trim().length() <= 6)
            {
                if(qian_phone.getText().toString().trim().length()==11)
                {
                    if(isMobileNO(qian_phone.getText().toString().trim()))
                    {
                        if(qian_list.size()>0) {
                            thread(user_id);
                        }else {
                            SAToast.makeText(getContext(),"请选择患病类型").show();
                        }
                    }else {
                        SAToast.makeText(getContext(),"手机号格式不对").show();
                    }
                }else {
                    SAToast.makeText(getContext(),"手机号格式不对").show();
                }

            }else {
                SAToast.makeText(getContext(),"用户名不能大于六位").show();
            }

        }else {
            SAToast.makeText(getContext(),"用户名和手机不能为空").show();
        }
    }

    private void init_qian_list() {
        for(int i =0  ; i < qian_data.length ; i ++)
        {
            if(!qian[i].equals(""))
            {
                qian_list.add(qian_data[i]);
            }
        }
    }


    public void SaveData() {
        //指定操作的文件名称
        SharedPreferences share = getContext().getSharedPreferences("qian", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("qian", "1");         //根据键值对添加数据
        edit.commit();  //保存数据信息
    }
    public String LoadData() {
        //指定操作的文件名称
        SharedPreferences share = getContext().getSharedPreferences("qian", MODE_PRIVATE);
        String a= share.getString("qian", "0");
        return a;
    }

    private void show_forgetpassword_pop()
    {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_qianok, null);
        TextView forpassword_exit = view.findViewById(R.id.qianok_back);
        pop = new PopupWindow(view);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setAnimationStyle(R.style.BintPopWindowAnim);//设置进入和出场动画
        pop.setBackgroundDrawable(this.getResources().getDrawable(R.color.white));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        forpassword_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.pushFragment(new HomeFragment(),true);
                pop.dismiss();
               /* deail_linear1.setVisibility(View.VISIBLE);
                deail_linear1.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.x_anim));//加载动画
                deail_linear2.setVisibility(View.GONE);
                deail_top_name.setText("医生详情");
                doctor_liao.setVisibility(View.VISIBLE);*/
            }
        });

    }


    public void thread(final String user_id) {
//数据加载
        LoadingDialog.showDialogForLoading(mActivity, "申请中..", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ok = new OkHttpClientManager();
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("time_str",""+getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),
                        new OkHttpClientManager.Param("name", qian_name.getText().toString().trim()),
                        new OkHttpClientManager.Param("phone", qian_phone.getText().toString().trim()),
                        new OkHttpClientManager.Param("city_id", ""),
                        new OkHttpClientManager.Param("user_id",user_id),//时间
                        new OkHttpClientManager.Param("address", qian_city.getText().toString().trim()),
                        new OkHttpClientManager.Param("marke", qian_list.toString())
                };


                try {
                    qian_data[0] = ok.postOneHeadAsString(qian_url,device_no, params_home);//体征数据
                    document = Document.parse(qian_data[0]);
                    System.out.println("*********申请签约返回的数据********"+qian_data[0]);
                    if (document.getInteger("code") == 200) {
                        Message message = new Message();
                        message.what = 0;
                        message.obj = qian_data[0];
                        handler.sendMessage(message);
                    }else {
                        LoadingDialog.cancelDialogForLoading();
                    }

                    //handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void thread_doctor_deail() {
        LoadingDialog.showDialogForLoading(mActivity, "获取数据..", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ok = new OkHttpClientManager();
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("time_str",""+getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),
                        new OkHttpClientManager.Param("user_id", user_id)
                };
                try {
                    System.out.println("*********doctorId********"+user_id);
                    System.out.println("*********device_no********"+device_no);
                    qian_data[0] = ok.postOneHeadAsString(dictirdeail_url,device_no,params_home);//医生详情
                    System.out.println("*********医生详情返回的数据********"+qian_data[0]);
                    document = Document.parse(qian_data[0]);
                    if (document.getInteger("code") == 200) {
                        Message message = new Message();
                        message.what = 2;
                        message.obj = qian_data[0];
                        handler.sendMessage(message);
                    }else if(document.getInteger("code") == 301)
                    {
                        Message message = new Message();
                        message.what = 301;
                        handler.sendMessage(message);
                    }else {
                        LoadingDialog.cancelDialogForLoading();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onScrollChanged(MyScorllView scrollView, int x, int y, int oldx, int oldy) {//监听scrollview的滑动
        if(doctor_status == 2) {
            if (y > 0) {
                if (y > 300) {
                    new_head_linear.setVisibility(View.VISIBLE);
                }else {
                    new_head_linear.setVisibility(View.INVISIBLE);
                    deail_top_name.setVisibility(View.INVISIBLE);
                }
            } else {
                new_head_linear.setVisibility(View.INVISIBLE);
                deail_top_name.setVisibility(View.VISIBLE);
            }
        }
    }

    class MyHander extends Handler{
        //跟新数据handler
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 0://申请签约医生
                    //申请完成之后使用  SaveData();
                    doctor_status=1;
                    qian_pop_linear.setVisibility(View.VISIBLE);
                    deail_linear1.setVisibility(View.GONE);
                    deail_linear2.setVisibility(View.GONE);
                    deail_top_name.setText("提交成功");
                    //show_forgetpassword_pop();
                    doctor_liao.setVisibility(View.INVISIBLE);
                    LoadingDialog.cancelDialogForLoading();
                    break;
                case 1://申请签约医生完成
                    break;
                case 2://获取医生的详情信息
                    String doctor_data = (String) msg.obj;
                    document = Document.parse(doctor_data);
                    document_obj = document.get("obj",Document.class);
                    document_gov = document_obj.get("gov",Document.class);
                    document_im = document_obj.get("im_account",Document.class);
                    if(document_obj.getString("accid") != null) {
                        tdoctorid.setText(document_obj.getString("accid"));
                    }
                    if(document_obj.getString("phone")!=null) {
                        tdoctorphone.setText(document_obj.getString("phone"));
                    }
                    if(document_gov.getString("title")!=null) {
                        tdoctorleibue.setText(document_gov.getString("title"));
                        doctor_liebie2.setText(document_gov.getString("title"));
                    }
                    if(document_obj.getInteger("sex") == 1)
                    {
                        idoctorsex.setImageResource(R.mipmap.doctor_men);
                    }else {
                        idoctorsex.setImageResource(R.mipmap.doctor_women);
                    }
                    if(document_obj.getString("avatar")!= null)
                    {
                        doctor_headimage = document_obj.getString("avatar");
                    }
                    if(document_obj.getString("name")!= null)
                    {
                        doctor_name2.setText(document_obj.getString("name"));
                        tdoctorname.setText(document_obj.getString("name"));
                    }
                    if(document_obj.getString("hospital")!= null)
                    {
                        tdoctoryiyuan.setText(document_obj.getString("hospital"));
                    }
                    Glide.with(mActivity)
                            .load("http://ov62zyke0.bkt.clouddn.com/"+doctor_headimage)
                            .placeholder(R.mipmap.deadpool)
                            .into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource,
                                                            GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    if(idoctorhead!=null) {
                                        idoctorhead.setImageDrawable(resource);
                                    }
                                }
                            });

                    list = new ArrayList<>();
                    list = document_obj.get("visit_cycle",List.class);
                    int  k =0 ;
                    time= new int[]{list.get(0).getInteger("forenoon"), list.get(0).getInteger("at_noon"), list.get(0).getInteger("evening"),
                            list.get(1).getInteger("forenoon"), list.get(1).getInteger("at_noon"), list.get(1).getInteger("evening"),
                            list.get(2).getInteger("forenoon"), list.get(2).getInteger("at_noon"), list.get(2).getInteger("evening"),
                            list.get(3).getInteger("forenoon"), list.get(3).getInteger("at_noon"), list.get(3).getInteger("evening"),
                            list.get(4).getInteger("forenoon"), list.get(4).getInteger("at_noon"), list.get(4).getInteger("evening"),
                            list.get(5).getInteger("forenoon"), list.get(5).getInteger("at_noon"), list.get(5).getInteger("evening"),
                            list.get(6).getInteger("forenoon"), list.get(6).getInteger("at_noon"), list.get(6).getInteger("evening"),
                    };//21个空格
                    doctorGridviewAdapter = new DoctorGridviewAdapter(getContext(),time);
                    gridView.setAdapter(doctorGridviewAdapter);
                    System.out.println("********1*******"+list.get(0).getString("day"));
                    LoadingDialog.cancelDialogForLoading();
                    Save_doctor_Date();
                    Save_im();
                    break;
                case 301:
                    doctor_status = 0;
                    deail_linear1.setVisibility(View.GONE);
                    deail_linear2.setVisibility(View.VISIBLE);
                    deail_top_name.setText("申请签约医生");
                    doctor_liao.setVisibility(View.INVISIBLE);
                    SAToast.makeText(getContext(),"申请失败").show();
                    LoadingDialog.cancelDialogForLoading();
                    break;

            }
        }
    }

    private void Save_im()
    {
        //指定操作的文件名称
        SharedPreferences share = mActivity.getSharedPreferences(user+"doctor_im", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("accid",document_im.getString("accid"));
        edit.putString("token",document_im.getString("token"));
        edit.putString("name",tdoctorname.getText().toString());
        edit.putString("hospital",tdoctoryiyuan.getText().toString());
        edit.commit();  //保存数据信息
    }
    private void Save_doctor_Date() {
            //指定操作的文件名称
            SharedPreferences share = mActivity.getSharedPreferences(user+"doctor_fragment", MODE_PRIVATE);
            SharedPreferences.Editor edit = share.edit(); //编辑文件
                String sex ="";
            if(document_obj.getInteger("sex") == 1)
            {sex = "男";
            }else {
            sex = "女";
             }
            edit.putString("doctor_flage","1");
            edit.putString("doctor_name", tdoctorname.getText().toString());         //根据键值对添加数据
            edit.putString("doctor_sex",sex);
            edit.putString("doctor_headimage",doctor_headimage);
            edit.putString("doctor_phone",tdoctorphone.getText().toString());
             edit.putString("hospital",tdoctoryiyuan.getText().toString());
            edit.putString("doctor_leibie", tdoctorleibue.getText().toString());         //根据键值对添加数据
            edit.putString("doctor_id",tdoctorid.getText().toString());
            edit.putString("time_length",time.length+"");
            edit.putString("doctor_accid",document_obj.getString("_id"));
            for(int i = 0; i < time.length; i++)
            {
                edit.putString("t"+i,time[i]+"");
            }
            edit.commit();  //保存数据信息

    }


}
