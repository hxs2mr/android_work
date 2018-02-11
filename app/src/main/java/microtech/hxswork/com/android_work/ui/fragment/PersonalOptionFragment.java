package microtech.hxswork.com.android_work.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.bson.Document;
import org.json.JSONObject;

import microtech.hxswork.com.android_work.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import microtech.hxswork.com.android_work.Util.Auth;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.adapter.PhotoAdapter;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.ui.activity.OptionNextActivity;
import microtech.hxswork.com.android_work.widget.RecyclerItemClickListener;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;
import microtech.hxswork.com.photopicker.PhotoPicker;
import microtech.hxswork.com.photopicker.PhotoPreview;

import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phone_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_head_image;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_name;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.phone;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;

/**
 * Created by microtech on 2017/9/5.吐槽入口
 */

public class PersonalOptionFragment extends AppCompatActivity implements View.OnClickListener {

    RecyclerView option_recycleview;

    LinearLayout  option_back_linear;
    ImageView next_back;
    public ArrayList<String> selectedPhotos=new ArrayList<>();
    public PhotoAdapter photoAdapter;
    public List<String> photos= null;
    String AccessKey="YdSHCtErv3CoRgH9Oa7MTiU15g3XVC86snrsSfMa";//七牛模块
    String SecretKey="RHk263O-wGVGtuJlRqmbMRlXa0Rld2f0pFjp6jEv";
    Document document;
    OkHttpClientManager ok;
    String our_Data[]={null};
    String option_url="https://doc.newmicrotech.cn/otsmobile/app/feedbacks?";
    EditText option_edittext;
    EditText option_phone;
    List<String> list_image;
    List<String> list_endimage;
    TextView option_tijiao;
    Handler handler;
    PopupWindow pop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_option_fragment);
        initView();
    }
    protected void initView() {
        list_image = new ArrayList<>();
        list_endimage = new ArrayList<>();
        handler = new MyHander();
        option_recycleview = (RecyclerView) findViewById(R.id.option_recycleview);
        option_back_linear = (LinearLayout) findViewById(R.id.option_back_linear);
        option_edittext  = (EditText) findViewById(R.id.option_edittext);
        option_phone = (EditText) findViewById(R.id.option_phone);
        next_back = (ImageView) findViewById(R.id.next_back);
        option_tijiao = (TextView) findViewById(R.id.option_tijiao);

        option_back_linear.setOnClickListener(this);
        next_back.setOnClickListener(this);
        option_tijiao.setOnClickListener(this);

        photoAdapter = new PhotoAdapter(getBaseContext(),selectedPhotos);

        option_recycleview.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));//设置recycleview的布局

        option_recycleview.setAdapter(photoAdapter);

        option_recycleview.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD)
                {
                    PhotoPicker.builder()
                            .setPhotoCount(PhotoAdapter.MAX)
                            .setShowCamera(true)
                            .setPreviewEnabled(false)
                            .setSelected(selectedPhotos)
                            .start(PersonalOptionFragment.this);
                }else {
                    PhotoPreview.builder()
                            .setPhotos(selectedPhotos)
                            .setCurrentItem(position)
                            .start(PersonalOptionFragment.this);
                }
            }
        }));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {


            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for(int i=0 ; i < photos.size() ; i++) {
                    System.out.println("photos***************:"+photos.get(i));
                }
                list_image = photos;
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.option_back_linear:
                finish();
                break;
            case R.id.next_back:
                startActivity(new Intent(this, OptionNextActivity.class));
                break;
            case R.id.option_tijiao:
                if(!option_edittext.getText().toString().trim().equals("")) {
                    if(list_image.size()>0) {
                        LoadingDialog.showDialogForLoading(this, "上传中..", true);
                        for (int i = 0; i < list_image.size(); i++) {
                                getUpimg(list_image.get(i));
                        }
                    }else {
                        thread_noimgae();
                    }
                }else {
                    SAToast.makeText(this,"反馈意见不能为空!").show();
                }
                break;
        }
    }


    public void getUpimg(final String imagePath) {
        ok = new OkHttpClientManager();
        new Thread() {
            public void run() {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                UploadManager uploadManager = new UploadManager();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String key = "icon_" + sdf.format(new Date());

                System.out.println("*****Auth.create(AccessKey, SecretKey)********"+ Auth.create(AccessKey, SecretKey));//生成七牛的上传token

                uploadManager.put(imagePath, null, Auth.create(AccessKey, SecretKey).uploadToken("microtech"),//可以加入命名key值
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                try {
                                    System.out.println("*****upimg********"+res + "11111111111111" + info);
                                    //七牛返回的文件名
                                    System.out.println("upimg********"+res.getString("hash"));
                                    if(info.isOK()) {
                                        System.out.println("******upimg********"+res+info);
                                        document = Document.parse(res.toString());//解析主界面刷新的体征数据
                                        final String hash=document.getString("hash");
                                        list_endimage.add(hash);
                                    }
                                    System.out.println("list_endimage********"+list_endimage.size());
                                    //list集合中图片上传完成后，发送handler消息回主线程进行其他操作

                                    if (list_image.size() == list_endimage.size()) {//等待图片上传完成后

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                                                        //主界面
                                                        new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                                                        new OkHttpClientManager.Param("name", start_name),
                                                        new OkHttpClientManager.Param("channel", "Android"),
                                                        new OkHttpClientManager.Param("user_id",user_id),
                                                        new OkHttpClientManager.Param("content", option_edittext.getText().toString().trim()),
                                                        new OkHttpClientManager.Param("images", list_endimage.toString()),
                                                        new OkHttpClientManager.Param("contact",option_phone.getText().toString().trim()),
                                                        new OkHttpClientManager.Param("bc_id", "")
                                                };
                                                try {
                                                    our_Data[0] = ok.postOneHeadAsString(option_url, device_no, params_home);//反馈意见的提交
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                System.out.println(device_no + "    " + phone + "    " + user_id);
                                                System.out.println("修改资料数据:" + our_Data[0]);
                                                Message message =new Message();
                                                message.what=0;//表示又图片
                                                message.obj=our_Data[0];
                                                handler.sendMessage(message);
                                            }
                                        }).start();

                                    }else {
                                        toast("头像上传异常");
                                        LoadingDialog.cancelDialogForLoading();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, null);
            }
        }.start();
    }

    private void thread_noimgae()
    {
        LoadingDialog.showDialogForLoading(this, "上传中..", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                        new OkHttpClientManager.Param("name", start_name),
                        new OkHttpClientManager.Param("channel", "Android"),
                        new OkHttpClientManager.Param("user_id",user_id),
                        new OkHttpClientManager.Param("content", option_edittext.getText().toString().trim()),
                        new OkHttpClientManager.Param("images", ""),
                        new OkHttpClientManager.Param("contact",option_phone.getText().toString().trim()),
                        new OkHttpClientManager.Param("bc_id", "")
                };
                try {
                    our_Data[0] = ok.postOneHeadAsString(option_url, device_no, params_home);//反馈意见的提交
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(device_no + "    " + phone + "    " + user_id);
                System.out.println("反馈意见数据:" + our_Data[0]);
                Message message =new Message();
                message.what=1;//表示只有内容
                message.obj=our_Data[0];
                handler.sendMessage(message);
            }
        }).start();
    }

    class MyHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    LoadingDialog.cancelDialogForLoading();
                    list_image = new ArrayList<>();
                    list_endimage = new ArrayList<>();
                    SAToast.makeText(PersonalOptionFragment.this,"反馈成功!").show();
                    option_edittext.setText("");
                    selectedPhotos.clear();
                    photoAdapter.notifyDataSetChanged();
                    dialog_photo();
                    break;
                case 1:
                    LoadingDialog.cancelDialogForLoading();
                    SAToast.makeText(PersonalOptionFragment.this,"反馈成功!").show();
                    option_edittext.setText("");
                    dialog_photo();
                    break;
            }
        }
    }
    private void toast(final String msg) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PersonalOptionFragment.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void dialog_photo()//查看头像的大图
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View imgEntryView = inflater.inflate(R.layout.option_pop, null); // 加载自定义的布局文件
        LinearLayout bind_linear_back = imgEntryView.findViewById(R.id.bind_linear_back);

        TextView qianok_back = imgEntryView.findViewById(R.id.qianok_back);
        pop = new PopupWindow(imgEntryView);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setAnimationStyle(R.style.BintPopWindowAnim);//设置进入和出场动画
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(imgEntryView, Gravity.BOTTOM, 0, 0);
        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
        bind_linear_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                WindowManager.LayoutParams lp =getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
                pop.dismiss();
                finish();
            }
        });
        qianok_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
                pop.dismiss();
                finish();
            }
        });
    }
}
