package microtech.hxswork.com.android_work.ui.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.bson.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.contract.PersonalMydeviceNextContract;
import microtech.hxswork.com.android_work.model.PersonalMydeviceNextModelImpl;
import microtech.hxswork.com.android_work.presenter.PersonalMydeviceNextPresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.ui.activity.LoginActivity;
import microtech.hxswork.com.citypicker.city_20170724.DayTimerPickerView;
import microtech.hxswork.com.citypicker.city_20170724.PersonPickerView;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonutils.KeyBordUtil;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;

import static microtech.hxswork.com.android_work.Util.TimeUtils.stampToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_model;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_sn;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_time;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userA_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userB_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userC_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.famile_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relation;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationC;

import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idC;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.bing_url;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.mDistrictBeanArrayList;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;
import static microtech.hxswork.com.android_work.ui.fragment.PersonalMydeviceFragment.init_ABC_name;
import static microtech.hxswork.com.android_work.ui.fragment.PersonalMydeviceFragment.start_bind_flage;
import static microtech.hxswork.com.commom.commonutils.FormatUtil.isMobileNO;

/**
 * Created by microtech on 2017/9/27.
 */

public class PersonalMydeviceNextFragment extends BaseFragment<PersonalMydeviceNextPresenterImpl,PersonalMydeviceNextModelImpl>implements PersonalMydeviceNextContract.View, View.OnClickListener {

   @Bind(R.id.personal_mydevice_back_linear)
   LinearLayout personal_mydevice_back_linear;

    @Bind(R.id.bind_abc)
    ImageView bind_abc;

    @Bind(R.id.bind_title)
    TextView bind_title ;//= view.findViewById(R.id.bind_title);//设备所属A，B，C

    @Bind(R.id.personal_mydevice_bindname)
    EditText personal_mydevice_bindname ;//= view.findViewById(R.id.personal_mydevice_bindname);//输入的姓名

    @Bind(R.id.personal_mydevice_bindphone)
    EditText personal_mydevice_bindphone ;//= view.findViewById(R.id.personal_mydevice_bindphone);//输入的手机

    @Bind(R.id.personal_mydevice_barthday)
    TextView personal_mydevice_barthday;//=  view.findViewById(R.id.personal_mydevice_barthday);//生日

    @Bind(R.id.personal_mydevice_guanxi)
    TextView personal_mydevice_guanxi;//=  view.findViewById(R.id.personal_mydevice_barthday);//关系

    @Bind(R.id.bind_tijiao)
    TextView bind_tijiao;//=  view.findViewById(R.id.bind_tijiao);//提交
    HomeActivity mActivity;
    String[] data;
    OkHttpClientManager ok;
    String bind_edit[]={null};
    Document document;
    String edit_url="https://doc.newmicrotech.cn/otsmobile/app/usersBoxEdit?";
    String new_relation;//判断关系
    public static ArrayList<String> mDistrictBeanArrayList1 ;//添加关系哦
    Handler handler ;
    int shen_flage=0;//标记为自己身份的不能修改自己的身份
    String year ,month,day;
    @Override
    protected int getLayoutResource() {
        return R.layout.personal_mydevice_bind_next;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mActivity = (HomeActivity) getActivity();
        mDistrictBeanArrayList1 = new ArrayList<>();
        handler = new Myhandler();
        data =new String[7];
        data = getArguments().getStringArray("data");
        if(data[0].equals("UserA"))
        {
            year =  stampToDate(birthdayA,"yyyy");
            month = stampToDate(birthdayA,"MM");
            day= stampToDate(birthdayA,"dd");
            personal_mydevice_barthday.setText(year+"-"+month+"-"+day);
            bind_abc.setImageResource(R.mipmap.icon_buttona);
            bind_title.setText("物理按键A对应的使用者");
            personal_mydevice_guanxi.setText(    init_ABC_name(relationA,relationA));
        }else if(data[0].equals("UserB"))
        {
            year =  stampToDate(birthdayB,"yyyy");
            month = stampToDate(birthdayB,"MM");
            day= stampToDate(birthdayB,"dd");
            bind_abc.setImageResource(R.mipmap.icon_buttonb);
            bind_title.setText("物理按键B对应的使用者");
            personal_mydevice_barthday.setText(year+"-"+month+"-"+day);
            personal_mydevice_guanxi.setText(init_ABC_name(relationB,relationB));
        }else if(data[0].equals("UserC"))
        {
            year =  stampToDate(birthdayC,"yyyy");
            month = stampToDate(birthdayC,"MM");
            day= stampToDate(birthdayC,"dd");
            bind_abc.setImageResource(R.mipmap.icon_buttonc);
            bind_title.setText("物理按键C对应的使用者");
            personal_mydevice_barthday.setText(year+"-"+month+"-"+day);
            personal_mydevice_guanxi.setText(    init_ABC_name(relationC,relationC));
          /*  if(relationC== relation)
            {
                shen_flage=1;
                personal_mydevice_guanxi.setText("自己");
            }*/
        }
        personal_mydevice_bindname.setText(data[5]);
        personal_mydevice_bindphone.setText(data[6]);
        personal_mydevice_back_linear.setOnClickListener(this);
        personal_mydevice_barthday.setOnClickListener(this);
        personal_mydevice_guanxi.setOnClickListener(this);
        bind_tijiao.setOnClickListener(this);
        new_relation = init_relation(personal_mydevice_guanxi.getText().toString().trim());
        init_shengfen();
    }

    private String init_relation(String data) {//判断关系
        String flage = null;
        if(data.equals("父亲"))
        {
            flage = "1";
        }else if (data.equals("母亲"))
        {
            flage = "2";
        }else if (data.equals("祖父"))
        {
            flage = "3";
        }else if (data.equals("祖母"))
        {
            flage = "4";
        }else if (data.equals("外祖父"))
        {
            flage = "5";
        }else if (data.equals("外祖母"))
        {
            flage = "6";
        }else if (data.equals("儿子"))
        {
            flage = "7";
        }else if (data.equals("女儿"))
        {
            flage = "8";
        }else if (data.equals("丈夫"))
        {
            flage = "9";
        }else if (data.equals("妻子"))
        {
            flage = "10";
        }else if (data.equals("其他"))
        {
            flage = "11";
        }
        return flage;
    }
    private void init_shengfen() {
        mDistrictBeanArrayList1.add("父亲");
        mDistrictBeanArrayList1.add("母亲");
        mDistrictBeanArrayList1.add("祖父");
        mDistrictBeanArrayList1.add("祖母");
        mDistrictBeanArrayList1.add("外祖父");
        mDistrictBeanArrayList1.add("外祖母");
        mDistrictBeanArrayList1.add("儿子");
        mDistrictBeanArrayList1.add("女儿");
        mDistrictBeanArrayList1.add("丈夫");
        mDistrictBeanArrayList1.add("妻子");
        mDistrictBeanArrayList1.add("其他");

        System.out.println("*******data[4]**********"+data[4]);

        if(relationA!=-1&&relationB != -1&&relationC != -1)
        {
            if(relationB > relationA && relationA <relationC)
            {
                mDistrictBeanArrayList1.remove(relationA- 1);
                if(relationB<relationC)
                {
                    mDistrictBeanArrayList1.remove(relationB- 2);
                    mDistrictBeanArrayList1.remove(relationC-3);
                }else {
                    mDistrictBeanArrayList1.remove(relationC- 2);
                    mDistrictBeanArrayList1.remove(relationB-3);
                }
            }else  if(relationB < relationA && relationA <relationC){
                mDistrictBeanArrayList1.remove(relationB-1);
                mDistrictBeanArrayList1.remove(relationA-2);
                mDistrictBeanArrayList1.remove(relationC-3);

            }else  if(relationB < relationA && relationA >relationC){
                if(relationB<relationC)
                {
                    mDistrictBeanArrayList1.remove(relationB- 1);
                    mDistrictBeanArrayList1.remove(relationC-2);
                    mDistrictBeanArrayList1.remove(relationA- 3);
                }else {
                    mDistrictBeanArrayList1.remove(relationC- 1);
                    mDistrictBeanArrayList1.remove(relationB-2);
                    mDistrictBeanArrayList1.remove(relationA- 3);
                }
            }else  if(relationB > relationA && relationA >relationC){
                mDistrictBeanArrayList1.remove(relationC-1);
                mDistrictBeanArrayList1.remove(relationA-2);
                mDistrictBeanArrayList1.remove(relationB-3);
            }
        }else if(relationA!=-1&&relationB != -1&&relationC == -1)
        {
            if(relationA>relationB)
            {
                mDistrictBeanArrayList1.remove(relationB-1);
                mDistrictBeanArrayList1.remove(relationA-2);
            }else {
                mDistrictBeanArrayList1.remove(relationA-1);
                mDistrictBeanArrayList1.remove(relationB-2);
            }
        }else if(relationA!=-1&&relationB == -1&&relationC != -1){
            if(relationA>relationC)
            {
                mDistrictBeanArrayList1.remove(relationC-1);
                mDistrictBeanArrayList1.remove(relationA-2);
            }else {
                mDistrictBeanArrayList1.remove(relationA-1);
                mDistrictBeanArrayList1.remove(relationC-2);
            }
        }else if(relationA==-1&&relationB != -1&&relationC != -1) {
            if(relationB>relationC)
            {
                mDistrictBeanArrayList1.remove(relationC-1);
                mDistrictBeanArrayList1.remove(relationB-2);
            }else {
                mDistrictBeanArrayList1.remove(relationB-1);
                mDistrictBeanArrayList1.remove(relationC-2);
            }
        }else if(relationA!=-1&&relationB == -1&&relationC == -1) {
                mDistrictBeanArrayList1.remove(relationA-1);
        }else if(relationA==-1&&relationB != -1&&relationC == -1) {
            mDistrictBeanArrayList1.remove(relationB-1);
        }else if(relationA==-1&&relationB == -1&&relationC != -1) {
            mDistrictBeanArrayList1.remove(relationC-1);
        }
    }

    @Override
    public void onClick(View view) {
switch (view.getId())
{
    case R.id.personal_mydevice_back_linear:
        mActivity.onBackPressed();
        break;
    case R.id.personal_mydevice_barthday:
        new KeyBordUtil().hideSoftKeyboard(getView());

        DayTimerPickerView cityPicker = new DayTimerPickerView.Builder(mActivity,getContext()).textSize(20)
                .titleTextColor("#000000")
                .backgroundPop(0xffffff)
                .province(year)
                .city(month)
                .district(day)
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
                //返回结果
                personal_mydevice_barthday.setText(province+ "-" + city+ "-" + district);
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1f;
                mActivity.getWindow().setAttributes(lp);
            }

            @Override
            public void onCancel() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        break;
    case R.id.personal_mydevice_guanxi:
        new KeyBordUtil().hideSoftKeyboard(getView());
        PersonPickerView cityPicker2 = new PersonPickerView.Builder(mActivity,getContext()).textSize(20)
                .titleTextColor("#000000")
                .backgroundPop(0xffffff)
                .province("")
                .city(personal_mydevice_guanxi.getText().toString())
                .district("26")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .list(mDistrictBeanArrayList1)//添加用户选择
                .districtCyclic(false)
                .visibleItemsCount(5)
                .itemPadding(15)
                .build();
        cityPicker2.show();
        cityPicker2.setOnCityItemClickListener(new PersonPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(String province, String city, String district) {
                //返回结果
                personal_mydevice_guanxi.setText(district);
                new_relation = init_relation(personal_mydevice_guanxi.getText().toString().trim());
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1f;
                mActivity.getWindow().setAttributes(lp);

            }
            @Override
            public void onCancel() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        break;
    case R.id.bind_tijiao://提交按钮网络请求
        if(!personal_mydevice_bindname.getText().toString().equals("")&&!personal_mydevice_bindphone.getText().toString().equals(""))
        {
        if(isMobileNO(personal_mydevice_bindphone.getText().toString().trim()))
        {
            thread_binddevice_edit();
        }else {
            SAToast.makeText(getContext(),"手机号格式不对!").show();
        }
        }else {
            SAToast.makeText(getContext(),"姓名和手机号不能为空!").show();
        }
        break;

}
    }
    private void thread_binddevice_edit() {
        //绑定盒子线程
        LoadingDialog.showDialogForLoading(mActivity, "正在加载..", true);
        ok=  new OkHttpClientManager();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClientManager.Param[] params_home;
                if(data[1].equals("0")) {  //
                    params_home  = new OkHttpClientManager.Param[]{
                            //主界面
                            new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                            new OkHttpClientManager.Param("family_id",famile_id),
                            new OkHttpClientManager.Param("region_id", ""),
                            new OkHttpClientManager.Param("user_id", user_id),
                            new OkHttpClientManager.Param("name", personal_mydevice_bindname.getText().toString().trim()),
                            new OkHttpClientManager.Param("phone", personal_mydevice_bindphone.getText().toString().trim()),
                            new OkHttpClientManager.Param("birth_data", personal_mydevice_barthday.getText().toString().trim()),
                            new OkHttpClientManager.Param("relation", new_relation),
                            new OkHttpClientManager.Param("box_id", box_id),
                            new OkHttpClientManager.Param("sn", data[2]),
                            new OkHttpClientManager.Param("keys", data[0]),
                            new OkHttpClientManager.Param("type", "0")
                    };
                }else {
                    params_home  = new OkHttpClientManager.Param[]{
                            //主界面
                            new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                            new OkHttpClientManager.Param("family_id",famile_id),
                            new OkHttpClientManager.Param("region_id", ""),
                            new OkHttpClientManager.Param("user_id", data[3]),
                            new OkHttpClientManager.Param("name", personal_mydevice_bindname.getText().toString().trim()),
                            new OkHttpClientManager.Param("phone", personal_mydevice_bindphone.getText().toString().trim()),
                            new OkHttpClientManager.Param("birth_data", personal_mydevice_barthday.getText().toString().trim()),
                            new OkHttpClientManager.Param("relation", new_relation),
                            new OkHttpClientManager.Param("box_id", box_id),
                            new OkHttpClientManager.Param("sn",data[2]),
                            new OkHttpClientManager.Param("keys", data[0]),
                            new OkHttpClientManager.Param("type", "1")
                    };
                }
                try {
                    bind_edit[0] = ok.postOneHeadAsString(edit_url, device_no, params_home);//体征数据
                    System.out.println("编辑按键返回的数据:" + bind_edit[0]);

                    document = Document.parse(bind_edit[0]);//解析主界面绑定的盒子数据

                        if(document.getInteger("code")==200) {
                            if (data[0].equals("UserA")) {
                                box_userA_flage = 1;
                                nameA = personal_mydevice_bindname.getText().toString().trim();
                                phoneA =  personal_mydevice_bindphone.getText().toString().trim();
                                user_idA = document.getString("obj");
                                relationA  = Integer.parseInt(new_relation);
                                relation = Integer.parseInt(new_relation);
                            } else if (data[0].equals("UserB"))
                            {
                                box_userB_flage= 1;
                                nameB = personal_mydevice_bindname.getText().toString().trim();
                                relationB  = Integer.parseInt(new_relation);
                                phoneB=  personal_mydevice_bindphone.getText().toString().trim();
                                user_idB = document.getString("obj");
                            }else {
                                box_userC_flage= 1;
                                nameC = personal_mydevice_bindname.getText().toString().trim();
                                relationC = Integer.parseInt(new_relation);
                                phoneC =  personal_mydevice_bindphone.getText().toString().trim();
                                user_idC = document.getString("obj");
                            }
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }else {
                            toast("编辑失败 请重试!");
                            LoadingDialog.cancelDialogForLoading();
                        }
                    LoadingDialog.cancelDialogForLoading();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    class Myhandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    LoadingDialog.cancelDialogForLoading();
                    mActivity.onBackPressed();
                    break;
                case 2:
                    break;
            }
        }
    }
    private void toast(final String msg) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
