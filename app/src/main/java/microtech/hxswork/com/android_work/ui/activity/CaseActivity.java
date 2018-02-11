package microtech.hxswork.com.android_work.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.bson.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;
import java.util.Locale;

import butterknife.Bind;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.widget.MyScorllView;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;
import microtech.hxswork.com.zxing.ZxingActivity;

import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_array_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userA_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userB_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userC_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relation;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_name;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_sex;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idC;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_nameA;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_nameB;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_nameC;


/**
 * Created by microtech on 2017/9/4. 病例模块
 */

public class CaseActivity  extends AppCompatActivity implements View.OnClickListener {

    TextView case_name;//当前用户名称

    ImageView doctor_back;//返回

    LinearLayout xia_linear1;
    ImageView case_xia;//下拉选择角色

    TextView case_sex;//当前用户性别

    TextView case_sui;//当前用户岁数

    TextView case_phone;//当前用户电话号码


    View case_xia_1;//当前用户下拉一
    View case_xia_2;//当前用户下拉一
    View case_xia_3;//当前用户下拉一
    View case_xia_4;
    boolean isExpand1 = false;
    boolean isExpand2  = false;
    boolean isExpand3 = false;
    boolean isExpand4 = false;
    View layout1,layout2,layout3,layout4;

    TextView case_linear1_text;//当前用户下拉文本一

    TextView case_linear2_text;//当前用户下拉文本二

    TextView case_linear3_text;//当前用户下拉文本三


    TextView case_linear4_text;//当前用户下拉文本四

    int max_line = 1;
    int max_line4= 5;
    PopupWindow change_name_pop;
    Handler handler;
    String  case_url="https://doc.newmicrotech.cn/otsmobile/app/healthRecords?";
    String case_data[]={null};
    OkHttpClientManager ok;
    PopupWindow bind_pop;
    Document document;
    Document document_obj;
    LinearLayout case_top_linear2;
    LinearLayout case_top_linear1;
    LinearLayout new_case_top_linear1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.case_activity);
        initview();
    }

    private void initview() {
        case_name = (TextView) findViewById(R.id.case_name);
        case_top_linear2 = (LinearLayout) findViewById(R.id.case_top_linear2);
        case_top_linear1 = (LinearLayout) findViewById(R.id.case_top_linear1);
        handler = new MyHander();
        case_name.setText(start_name);
        doctor_back = (ImageView) findViewById(R.id.doctor_back);
        case_xia = (ImageView) findViewById(R.id.case_xia);
        new_case_top_linear1 = (LinearLayout) findViewById(R.id.new_case_top_linear1);
        case_sex = (TextView) findViewById(R.id.case_sex);
        case_sui = (TextView) findViewById(R.id.case_sui);
        case_phone = (TextView) findViewById(R.id.case_phone);
        case_xia_1 = (ImageView) findViewById(R.id.case_xia_1);
        case_xia_2 = (ImageView) findViewById(R.id.case_xia_2);
        case_xia_3 = (ImageView) findViewById(R.id.case_xia_3);
        case_xia_4 = findViewById(R.id.case_xia_4);

        layout1 = findViewById(R.id.case_linear1);
        layout2 = findViewById(R.id.case_linear2);
        layout3= findViewById(R.id.case_linear3);
        layout4 = findViewById(R.id.case_linear4);

        case_linear1_text = (TextView) findViewById(R.id.case_linear1_text);
        case_linear2_text = (TextView) findViewById(R.id.case_linear2_text);
        case_linear3_text = (TextView) findViewById(R.id.case_linear3_text);


        case_linear4_text = (TextView) findViewById(R.id.case_linear4_text);

        xia_linear1 =(LinearLayout) findViewById(R.id.xia_linear1);
        xia_linear1.setOnClickListener(this);

        case_linear1_text.post(new Runnable() {
            @Override
            public void run() {
                case_xia_1.setVisibility(case_linear1_text.getLineCount() > max_line ?View.VISIBLE : View.GONE);
            }
        });

        case_linear2_text.post(new Runnable() {
            @Override
            public void run() {
                case_xia_2.setVisibility(case_linear2_text.getLineCount() > max_line ?View.VISIBLE : View.GONE);
            }
        });
        case_linear3_text.post(new Runnable() {
            @Override
            public void run() {
                case_xia_3.setVisibility(case_linear3_text.getLineCount() > max_line ?View.VISIBLE : View.GONE);
            }
        });
        case_linear4_text.post(new Runnable() {
            @Override
            public void run() {
                case_xia_4.setVisibility(case_linear4_text.getLineCount() > max_line4 ?View.VISIBLE : View.GONE);
            }
        });

        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        layout4.setOnClickListener(this);
        doctor_back.setOnClickListener(this);

        if(box_id != null) {
            thread(user_id);
        }else {
            new_case_top_linear1.setVisibility(View.GONE);
            case_top_linear2.setVisibility(View.VISIBLE);
            case_name.setText(start_name);
            case_sex.setText(start_sex);
            case_phone.setText(user);
            case_sui.setVisibility(View.INVISIBLE);

        }

    }

    public void thread(final String user_id ) {
        //获取健康文档
        LoadingDialog.showDialogForLoading(this, "正在加载..", true);
        ok = new OkHttpClientManager();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                        new OkHttpClientManager.Param("user_id", user_id),
                        new OkHttpClientManager.Param("box_id", box_id)
                };
                try {
                    case_data[0] = ok.postOneHeadAsString(case_url, device_no, params_home);//体征数据
                    document = Document.parse(case_data[0]);
                    if(document.get("obj")==null)
                    {
                        Message msg = new Message();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }else {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = case_data[0];
                        handler.sendMessage(msg);
                    }

                    System.out.println("健康档案数据:" + case_data[0]);
                    LoadingDialog.cancelDialogForLoading();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.xia_linear1:
                show_cookie_pop();
                break;
            case R.id.case_linear1:
                show_line(case_linear1_text,case_xia_1,isExpand1,1,1);
                break;
            case R.id.case_linear2:
                show_line(case_linear2_text,case_xia_2,isExpand2,2,1);
                break;
            case R.id.case_linear3:
                show_line(case_linear3_text,case_xia_3,isExpand3,3,1);
                break;
            case R.id.case_linear4:
                show_line(case_linear4_text,case_xia_4,isExpand4,4,5);
                break;
            case R.id.doctor_back:
                finish();
                break;

        }

    }

    private void show_line(final TextView text , View image, boolean isExpand,int i,int max){
        text.clearAnimation();
        final int deltaValue;//默认高度，即前边由maxLine确定的高度
        final int startValue = text.getHeight();//起始高度
        int durationMillis = 350;//动画持续时间
        if (isExpand) {
            if (i == 1) {
                    isExpand1 = false;
            } else if (i == 2) {
                isExpand2 = false;
            } else if (i == 3){
                isExpand3 = false;
            }else if (i == 4){
                isExpand4 = false;
            }
            /**
             * 折叠动画
             * 从实际高度缩回起始高度
             */
            deltaValue = text.getLineHeight() * text.getLineCount() - startValue;
            RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(durationMillis);
            animation.setFillAfter(true);
            image.startAnimation(animation);
        }else
        {
            /**
             * 展开动画
             * 从起始高度增长至实际高度
             */
            if (i == 1) {
                isExpand1 = true;
            } else if (i == 2) {
                isExpand2 = true;
            } else if (i == 3){
                isExpand3 = true;
            }else if (i == 4){
                isExpand4 = true;
            }
            deltaValue = text.getLineHeight() * max - startValue;
            RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(durationMillis);
            animation.setFillAfter(true);
            image.startAnimation(animation);
        }
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) { //根据ImageView旋转动画的百分比来显示textview高度，达到动画效果
                text.setHeight((int) (startValue + deltaValue * interpolatedTime));
            }
        };
        animation.setDuration(durationMillis);
        text.startAnimation(animation);

    }
    private void show_cookie_pop() {
        // mokHttpClientManager = new OkHttpClientManager();//网络接口
        final View view = LayoutInflater.from(this).inflate(R.layout.case_popuwindow, null);

        final TextView case_popu_user1 = view.findViewById(R.id.case_popu_user1);//用户一

        final TextView case_popu_user2 = view.findViewById(R.id.case_popu_user2);//用户二
        final TextView case_popu_user3 = view.findViewById(R.id.case_popu_user3);//用户二


        TextView back = view.findViewById(R.id.case_popu_back);//取消

        if(box_array_flage == 0)
        {
            case_popu_user3.setVisibility(View.GONE);
            case_popu_user2.setVisibility(View.GONE);
            case_popu_user1.setText(start_name);
        }else {
            if (box_userC_flage != 0) {
                case_popu_user3.setText(nameC+"("+init_ABC_name(relationC,relationC)+")");
            } else {
                case_popu_user3.setVisibility(View.GONE);
            }
            if (box_userB_flage != 0) {
                case_popu_user2.setText(nameB+"("+init_ABC_name(relationB,relationB)+")");
            } else {
                case_popu_user2.setVisibility(View.GONE);
            }
            if(box_userA_flage != 0){
                case_popu_user1.setText(nameA+"("+init_ABC_name(relationA,relationA)+")");
            }else {
                case_popu_user1.setVisibility(View.GONE);
            }
        }
        change_name_pop = new PopupWindow(view);
        change_name_pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        change_name_pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        change_name_pop.setAnimationStyle(R.style.LoginPopWindowAnim);//设置进入和出场动画
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha=0.5f;
        getWindow().setAttributes(lp);
        change_name_pop.setBackgroundDrawable(this.getResources().getDrawable(R.color.white));
        change_name_pop.setOutsideTouchable(true);
        change_name_pop.setFocusable(true);
        change_name_pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);


        change_name_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha=1f;
                getWindow().setAttributes(lp);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_name_pop.dismiss();
            }
        });
        case_popu_user1.setOnClickListener(new View.OnClickListener() {//清除缓存
            @Override
            public void onClick(View view) {
                case_name.setText(case_popu_user1.getText().toString().trim());
                change_name_pop.dismiss();
                thread(user_idA);
            }
        });
        case_popu_user2.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View view) {
                case_name.setText(case_popu_user2.getText().toString().trim());
                change_name_pop.dismiss();
                thread(user_idB);
            }
        });
        case_popu_user3.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View view) {
                case_name.setText(case_popu_user3.getText().toString().trim());
                change_name_pop.dismiss();
                thread(user_idC);
            }
        });
    }

    private void bind_file_pop() {
        final View view = LayoutInflater.from(this).inflate(R.layout.heathly_file, null);
        LinearLayout bind_linear_back = view.findViewById(R.id.bind_linear_back);
        bind_pop = new PopupWindow(view);
        bind_pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bind_pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bind_pop.setAnimationStyle(R.style.BintPopWindowAnim);//设置进入和出场动画
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.alpha=0.5f;
        getWindow().setAttributes(lp);
        bind_pop.setBackgroundDrawable(this.getResources().getDrawable(R.color.white));
        bind_pop.setOutsideTouchable(true);
        bind_pop.setFocusable(true);
        bind_pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        bind_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha=1f;
                getWindow().setAttributes(lp);
            }
        });
        bind_linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    class MyHander extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    new_case_top_linear1.setVisibility(View.GONE);
                    case_top_linear2.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    new_case_top_linear1.setVisibility(View.VISIBLE);
                    case_top_linear2.setVisibility(View.GONE);
                    String json = (String) msg.obj;
                    document = Document.parse(json);
                    document_obj = document.get("obj",Document.class);
                    //测试数据模块
                    if(document_obj.getString("illness")!=null) {
                        case_linear1_text.setText(document_obj.getString("illness"));//测试数据
                    }
                    if(document_obj.getString("always")!=null)
                    {
                        case_linear2_text.setText(document_obj.getString("always"));
                    }

                    if(document_obj.getString("familys")!=null)
                    {
                        case_linear3_text.setText(document_obj.getString("familys"));
                    }
                    if(document_obj.get("diagnose",Document.class)!=null)
                    {
                        if(document_obj.get("diagnose",Document.class).getString("record_des")!=null)
                        {
                            case_linear4_text.setText(document_obj.get("diagnose",Document.class).getString("record_des"));
                        }
                    }
                    if(document_obj.getString("name")!=null)
                    {
                        case_name.setText(document_obj.getString("name"));
                    }
                    if(document_obj.getInteger("sex")!=null)
                    {
                        if(document_obj.getInteger("sex")==1) {
                            case_sex.setText("男");
                        }else {
                            case_sex.setText("女");
                        }
                    }

                    if(document_obj.getString("phone")!=null)
                    {
                        case_phone.setText(document_obj.getString("phone"));
                    }

                    if(document_obj.getInteger("age")!=null)
                    {
                        case_sui.setText(document_obj.getInteger("age")+"");
                    }
                    case_linear1_text.setHeight(case_linear1_text.getLineHeight() * max_line);
                    case_linear2_text.setHeight(case_linear2_text.getLineHeight() * max_line);
                    case_linear3_text.setHeight(case_linear3_text.getLineHeight() * max_line);//设置初始高度
                    case_linear4_text.setHeight(case_linear3_text.getLineHeight() * max_line4);//设置初始高度

                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    public  static  String init_ABC_name(int i,int j) {
        String name = null;
        switch (i)//查看与当前用户的关系
        {
            case 1:
                if (j == relation)//判断是不是当前登录的用户
                {

                } else {
                name = "父亲";
                }
                break;
            case 2:
                  if (j == relation)//判断是不是当前登录的用户
                  {
                    name = "自己";
                } else {
                name = "母亲";
                 }
                break;
            case 3:
                 if (j == relation)//判断是不是当前登录的用户
                   {
                   name = "自己";
                } else {
                name = "祖父";
                 }
                break;
            case 4:
                  if (j == relation)//判断是不是当前登录的用户
                  {
                   name = "自己";
                } else {
                name = "祖母";
                 }
                break;
            case 5:
                 if (j == relation)//判断是不是当前登录的用户
                  {
                    name = "自己";
                } else {
                name = "外祖父";
                 }
                break;
            case 6:
                if (j == relation)//判断是不是当前登录的用户
                      {
                    name = "自己";
                } else {
                    name = "外祖父";
                 }
                break;
            case 7:
                 if (j == relation)//判断是不是当前登录的用户
                  {
                    name = "自己";
                } else {
                name = "儿子";
                }
                break;
            case 8:
                  if (j == relation)//判断是不是当前登录的用户
                 {
                    name = "自己";
                } else {
                name = "女儿";
                 }
                break;
            case 9:
                 if (j == relation)//判断是不是当前登录的用户
                {
                  name = "自己";
                } else {
                name = "丈夫";
                 }
                break;
            case 10:
                 if (j == relation)//判断是不是当前登录的用户
                  {
                   name = "自己";
                } else {
                name = "妻子";
                 }
                break;
            default:
                name = "其他";
                break;
        }
        return name;
    }


}
