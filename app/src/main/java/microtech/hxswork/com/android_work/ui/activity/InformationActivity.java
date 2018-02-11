package microtech.hxswork.com.android_work.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;

import org.bson.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.adapter.InfoMationRecycleViewAdapter;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.InforMationReycleViewBean;
import microtech.hxswork.com.android_work.bean.NIMBean;
import microtech.hxswork.com.android_work.bean.XGBean;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;
import microtech.hxswork.com.commom.commonwidget.StatusBarCompat;
import xander.elasticity.ElasticityHelper;
import xander.elasticity.ORIENTATION;

import static com.netease.nim.uikit.NimUIKit.getContext;
import static microtech.hxswork.com.android_work.ui.activity.HomeActivity.incomingMessageObserver1;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.accid;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.flage_NIM;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.list_NIM;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.list_XG;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.token;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.right_image_flage;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.right_image_flage_NIMM;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;

/**
 * Created by microtech on 2017/8/22.消息模块
 */

public class InformationActivity extends AppCompatActivity{

//<InformationPresenterImpl,InformationModelImpl> implements InformationContract.View{

    //@Bind(R.id.infomation_back)
    ImageView infomation_back;
   // @Bind(R.id.infomation_recycleview)

    LinearLayout infomation_back_linear;
    RecyclerView infomation_recycleview;
    InfoMationRecycleViewAdapter adapter;
    RecyclerView.LayoutManager manager;
    public List<InforMationReycleViewBean> list_data; //前期模拟消息 后期重服务器获取
    String title="";//服务模块的标题
    String id="";
    String content="";//服务模块的内容
    SharedPreferences share;
    SharedPreferences share_NIM;
    Handler handler;
    OkHttpClientManager ok;
    String qian_data[]={null};
    Observer<List<IMMessage>> incomingMessageObserver;
    String dictirdeail_url="https://doc.newmicrotech.cn/otsmobile/app/doctorInfo?";
    Document document;
    Document document_obj;
    Document document_im;
    int flage = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomation_fragment);
        initView();
        incomingMessageObserver =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                        System.out.println("********收到新消息内容*******"+messages.get(messages.size()-1).getContent());
                        NIMBean nimBean = new NIMBean(messages.get(messages.size()-1).getContent(),new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                        if(flage == 0) {
                            flage = 1;
                            list_NIM.add(nimBean);
                            System.out.println("********收到新消息内容大小*******" + list_NIM.size());
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = messages;
                            handler.handleMessage(msg);
                        }
                        right_image_flage_NIMM = 1;
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);

    }


    protected void initView() {
        handler = new MyHander();
        share = getSharedPreferences(user+"doctor_im", MODE_PRIVATE);//第一次获取医生详情
        share_NIM = getSharedPreferences(user+"nim", MODE_PRIVATE);
        infomation_back_linear = (LinearLayout) findViewById(R.id.infomation_back_linear);
        infomation_recycleview  = (RecyclerView) findViewById(R.id.infomation_recycleview);
        list_data = new ArrayList<InforMationReycleViewBean>();
        infomation_recycleview.setLayoutManager(new LinearLayoutManager(this));
       // swipeLayout.setOnRefreshListener(this);
        ElasticityHelper.setUpOverScroll(infomation_recycleview, ORIENTATION.VERTICAL);//弹性拉伸
        infomation_back_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_image_flage = 0;
                NIMClient.getService(MsgServiceObserve.class)
                        .observeReceiveMessage(incomingMessageObserver, false);

                NIMClient.getService(MsgServiceObserve.class)
                        .observeReceiveMessage(incomingMessageObserver1, true);
              finish();
            }
        });

        XGPushManager.setNotifactionCallback(new XGPushNotifactionCallback() {

            @Override
            public void handleNotify(XGNotifaction xGNotifaction) {
                System.out.println("test处理信鸽通1111111*********************知"+xGNotifaction);
                // 获取标签、内容、自定义内容
                right_image_flage= 1;
                String XG_title = xGNotifaction.getTitle();
                String XG_content = xGNotifaction.getContent();
                String XG_customContent = xGNotifaction.getCustomContent();
                XGBean XG = new XGBean(XG_title,XG_content,XG_customContent);
                list_XG.add(XG);
                System.out.println("list_XG_size+++++++++++："+list_XG.size());
                Message msg = new Message();
                msg.what=2;//代表跟新图标的状态
                handler.sendMessage(msg);
                System.out.println("test处理信鸽通知11111*************："+XG_title+XG_content+XG_customContent);
                // 其它的处理
                // 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
                xGNotifaction.doNotify();
                //initCustomPushNotificationBuilder(getBaseContext());
            }
        });
        if (share.getString("accid","0").equals("0"))
        {
            thread_doctor_deail();
        }
        //测试数据开始走一波
        System.out.println("******list_XG.size********"+list_XG.size());
        InforMationReycleViewBean bean;
        InforMationReycleViewBean bean1;
        if(list_XG.size() != 0) {
        bean = new InforMationReycleViewBean("0", "何医生", "", list_XG.get(list_XG.size() - 1).getContent(), new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()), list_XG.size() + "");//姓名，头像链接，消息内容，时间，条数
        }else {
            bean = new InforMationReycleViewBean("0", "何医生", "", "", new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()), "");
        }
        if(!share_NIM.getString("NIM_numbers","0").equals("0"))
        {
            if(list_NIM.size()>0) {
                bean1 = new InforMationReycleViewBean("1", share.getString("name", "0"), "http://p0.so.qhimgs1.com/bdr/_240_/t01e9783b7417515cc0.jpg", list_NIM.get(list_NIM.size() - 1).getContent(), list_NIM.get(list_NIM.size() - 1).getTime(), share_NIM.getString("NIM_numbers", "0"));//姓名，头
            }else {
                bean1 = new InforMationReycleViewBean("1",share.getString("name","0"), "http://p0.so.qhimgs1.com/bdr/_240_/t01e9783b7417515cc0.jpg", "", "", "");//姓名，头
            }
        }else {
                bean1 = new InforMationReycleViewBean("1",share.getString("name","0"), "http://p0.so.qhimgs1.com/bdr/_240_/t01e9783b7417515cc0.jpg", "", "", "");//姓名，头
        }

        list_data.add(bean);
        list_data.add(bean1);

        adapter = new InfoMationRecycleViewAdapter(getContext(),list_data);
      //  adapter.setEnableLoadMore(true);//允许刷新操作\
         infomation_recycleview.setAdapter(adapter);
         adapter.setOnItemClickListener(new InfoMationRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (list_data.get(position).getType().equals("1"))
                {
                    if (!share.getString("accid","0").equals("0")) {
                  /*  String[] liao = new String[5];
                    liao[0] = list_data.get(position).getName();//传递用户名
                    liao[1] = list_data.get(position).getName();//传递聊天对象的用户ID
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("data",liao);
                    mActivity.startActivity(MessageListActivity.class,bundle);*/
                        NimUIKit.doLogin(new LoginInfo(accid, token), new RequestCallback<LoginInfo>() {

                            @Override
                            public void onSuccess(LoginInfo loginInfo) {
                                list_NIM= new ArrayList<NIMBean>();
                                NimUIKit.startP2PSession(InformationActivity.this, share.getString("accid","0"),share.getString("name","0"),share.getString("hospital","0"));//对方的accid  如：医生的accid
                                right_image_flage_NIMM = 0;
                                save_NIM_numvbers(0);
                                flage_NIM = 0 ;
                                list_data.clear();
                                if(list_XG.size() != 0) {
                                    InforMationReycleViewBean   bean = new InforMationReycleViewBean("0", "何医生", "", list_XG.get(list_XG.size() - 1).getContent(), new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()), list_XG.size() + "");//姓名，头像链接，消息内容，时间，条数
                                    list_data.add(bean);
                                }else {
                                    InforMationReycleViewBean   bean = new InforMationReycleViewBean("0", "何医生", "", "", new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()), "");
                                    list_data.add(bean);
                                }
                                    InforMationReycleViewBean   bean1 = new InforMationReycleViewBean("1",share.getString("name","0"), "http://p0.so.qhimgs1.com/bdr/_240_/t01e9783b7417515cc0.jpg", "", "", "");//姓名，头
                                    list_data.add(bean1);
                                adapter.notifyDataSetChanged();
                                //finish();
                            }

                            @Override
                            public void onFailed(int i) {

                            }

                            @Override
                            public void onException(Throwable throwable) {

                            }
                        });
                    }else
                    {
                        SAToast.makeText(getContext(),"请先绑定专属医生").show();
                    }
                }else {
                 startActivity(new Intent(InformationActivity.this,InfomationItemActivity.class));
                                 right_image_flage = 0;
                                list_data.clear();
                                 InforMationReycleViewBean bean  =new InforMationReycleViewBean("0","何医生","","","","");//姓名，头像链接，消息内容，时间，条数
                                     list_data.add(bean);
                                if(list_NIM.size()!=0)
                                {
                                InforMationReycleViewBean   bean1 =new InforMationReycleViewBean("1",share.getString("name","0"),"http://p0.so.qhimgs1.com/bdr/_240_/t01e9783b7417515cc0.jpg",list_NIM.get(list_NIM.size()-1).getContent(),list_NIM.get(list_NIM.size()-1).getTime(),list_NIM.size()+"");//姓名，头
                                list_data.add(bean1);
                                 }else {
                                 InforMationReycleViewBean   bean1 = new InforMationReycleViewBean("1",share.getString("name","0"), "http://p0.so.qhimgs1.com/bdr/_240_/t01e9783b7417515cc0.jpg", "", "", "");//姓名，头
                                 list_data.add(bean1);
                                }
                                adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"长按了"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteBtnCilck(View view, int position) {
                adapter.removeData(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver1, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);
        if(clickedResult != null){
           title = clickedResult.getTitle();
            System.out.println("*******标题*****"+title);
            id = clickedResult.getMsgId() + "";
            System.out.println("*******id*****"+id);
            content = clickedResult.getContent();
            System.out.println("*******内容*****"+content);

        }
    }
    class  MyHander extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case  1://表示云信的消息
                    flage = 0;
                    flage_NIM++;
                    save_NIM_numvbers(flage_NIM);
                    for(int i= 0 ; i < list_data.size() ; i ++)
                    {
                        if(list_data.get(i).getType().equals("1"))
                        {
                            list_data.remove(i);
                        }
                    }
                    InforMationReycleViewBean  bean1 =new InforMationReycleViewBean("1",share.getString("name","0"),"http://p0.so.qhimgs1.com/bdr/_240_/t01e9783b7417515cc0.jpg",list_NIM.get(list_NIM.size()-1).getContent(),list_NIM.get(list_NIM.size()-1).getTime(),share_NIM.getString("NIM_numbers","0"));//姓名，头//姓名，头像链接，消息内容，时间，条数
                    list_data.add(bean1);
                    adapter.notifyDataSetChanged();
                    break;
                case 2://表示信鸽的新消息
                    for(int i= 0 ; i < list_data.size() ; i ++)
                    {
                        if(list_data.get(i).getType().equals("0"))
                        {
                            list_data.remove(i);
                        }
                    }
                    InforMationReycleViewBean bean  =new InforMationReycleViewBean("0","何医生","",list_XG.get(list_XG.size()-1).getContent(), new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()),list_XG.size()+"");//姓名，头像链接，消息内容，时间，条数
                    list_data.add(bean);
                    adapter.notifyDataSetChanged();
                    break;
                case 3://表示首次进入获取医生的accid
                    String doctor_data = (String) msg.obj;
                    document = Document.parse(doctor_data);
                    document_obj = document.get("obj",Document.class);
                    document_im = document_obj.get("im_account",Document.class);
                    save_doctor();
                    LoadingDialog.cancelDialogForLoading();
                    break;
                case 301:
                    LoadingDialog.cancelDialogForLoading();
                    break;
            }
        }
    }
    public void thread_doctor_deail() {
        LoadingDialog.showDialogForLoading(this, "请稍后..", true);
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
                        message.what = 3;
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

    private void save_doctor()
    {
        SharedPreferences share = getSharedPreferences(user+"doctor_im", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        if(document_im!= null) {
            edit.putString("accid",document_im.getString("accid"));
            edit.putString("token",document_im.getString("token"));
        }
        if(document_obj.getString("name")!= null) {
            edit.putString("name",document_obj.getString("name"));
        }
        if(document_obj.getString("hospital")!= null) {
            edit.putString("hospital", document_obj.getString("hospital"));
        }
        edit.commit();  //保存数据信息
    }
    private void save_NIM_numvbers(int i)
    {
        //指定操作的文件名称
        //指定操作的文件名称
        SharedPreferences share = getContext().getSharedPreferences(user+"nim", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("NIM_numbers", i+"");
        edit.commit();
    }
}
