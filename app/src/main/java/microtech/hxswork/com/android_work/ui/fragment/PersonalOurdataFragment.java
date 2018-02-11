package microtech.hxswork.com.android_work.ui.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import microtech.hxswork.com.android_work.R;

import butterknife.Bind;
import microtech.hxswork.com.android_work.Util.Auth;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.Home_Bean1;
import microtech.hxswork.com.android_work.contract.PersonalMydeviceContract;
import microtech.hxswork.com.android_work.model.PersonalMydeviceModelImpl;
import microtech.hxswork.com.android_work.presenter.PersonalMydevicePresnterImpl;
import microtech.hxswork.com.android_work.ui.activity.ForgePassword;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.citypicker.city_20170724.CityPickerView;
import microtech.hxswork.com.citypicker.city_20170724.DayTimerPickerView;
import microtech.hxswork.com.citypicker.city_20170724.PersonPickerView;
import microtech.hxswork.com.citypicker.city_20170724.bean.CityBean;
import microtech.hxswork.com.citypicker.city_20170724.bean.DistrictBean;
import microtech.hxswork.com.citypicker.city_20170724.bean.ProvinceBean;
import microtech.hxswork.com.citypicker.id_cityname.IdToCityName;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonutils.KeyBordUtil;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;

import static android.R.attr.format;
import static android.R.attr.track;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static microtech.hxswork.com.android_work.MyApplication.mHandler;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.areaId;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.cityId;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.document_native;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getDateToString;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.proviceId;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_address;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_birthday;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_head_image;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_id_card;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_name;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_native;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_sex;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.PersonalFragment.dialog_photo;
import static microtech.hxswork.com.commom.commonutils.FormatUtil.isMobileNO;

/**
 * Created by microtech on 2017/9/12.
 */

public class PersonalOurdataFragment extends BaseFragment<PersonalMydevicePresnterImpl,PersonalMydeviceModelImpl> implements PersonalMydeviceContract.View, View.OnClickListener {
    @Bind(R.id.personal_seting_back_linear)
    LinearLayout personal_seting_back_linear;

    @Bind(R.id.our_linear1)
    LinearLayout our_linear1;//头像模块

    @Bind(R.id.our_headimage)
    CircleImageView our_headimage;

    @Bind(R.id.our_linear2)//用户名
    LinearLayout our_linear2;

    @Bind(R.id.our_name)
    EditText our_name;

    @Bind(R.id.our_linear3)
    LinearLayout our_linear3;//性别 做弹窗

    @Bind(R.id.our_sex)
    TextView our_sex;

    @Bind(R.id.our_linear4)
    LinearLayout our_linear4;//手机

    @Bind(R.id.our_phone)
    TextView our_phone;

    @Bind(R.id.our_linear5)
    LinearLayout our_linear5;//身份证

    @Bind(R.id.our_shen)
    EditText our_shen;

    @Bind(R.id.our_linear6)//出生日期
    LinearLayout our_linear6;

    @Bind(R.id.our_day)//做弹窗
    TextView our_day;

    @Bind(R.id.our_linear7)
    LinearLayout our_linear7;//地区
    @Bind(R.id.our_city)
    TextView our_city;

    @Bind(R.id.our_linear8)
    LinearLayout our_linear8;

    @Bind(R.id.our_city_xi)
    EditText our_city_xi;

    @Bind(R.id.our_bianji)
    TextView our_bianji;

    HomeActivity mActicity;
    int flage=0;
    //头像编辑模块
    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    public static final int CROP_PHOTO = 3;

    private  String cachPath;

    private File cacheFile;
    private  File cameraFile;

    private Uri imageUri;
    //相机
    private Button camera;
    //相册
    private Button album;
    //动态获取权限监听
    private static PermissionListener mListener;
    PopupWindow  headimage_pop;
    OkHttpClientManager ok;
    String our_Data[]={null};
    String our_url = "http://192.168.1.135:9999/otsmobile/app/userInfo?";

    String edit_url = "https://doc.newmicrotech.cn/otsmobile/app/userEdit?";
    Document document1;
    Handler handler;
    String number;
    String phone;
    String user_id;

    String AccessKey="YdSHCtErv3CoRgH9Oa7MTiU15g3XVC86snrsSfMa";//七牛模块
    String SecretKey="RHk263O-wGVGtuJlRqmbMRlXa0Rld2f0pFjp6jEv";

    int headimage_flage=0;
    String headimage="";
    int gaibian=0;
    String new_city="";
    //存放图片路径的ArrayList
    private ArrayList<String> dataList = new ArrayList<String>();
    public static ArrayList<String> mDistrictBeanArrayList1 ;//添加关系哦

    // 七牛返回的文件名
    String  upimg="";
    ArrayList<String> upimg_key_list = new ArrayList<>();
    String qiqiu;
    Document document;
    int new_city_flage =0 ;
    String p_id;
    String c_id;
    String a_id;
    int p_i=0, c_i=0 , k_i=0;
    String city_p="",city_c="",city_q="";
      /*   for (int i = 0; i <dataList.; i++) {
        if (!dataList.get(i).equals("camera_default")) {
            getUpimg(dataList.get(i));
        }
    }*/
    @Override
    protected int getLayoutResource() {
        return R.layout.personal_ourdata_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mActicity = (HomeActivity) getActivity();
        handler =new MyHander();
        mDistrictBeanArrayList1 = new ArrayList<>();
        mDistrictBeanArrayList1.add("男");
        mDistrictBeanArrayList1.add("女");
        SharedPreferences share = getContext().getSharedPreferences(user+"123", MODE_PRIVATE);
        number = share.getString("device_no", "0");//设备编号
        phone =  share.getString("phone", "0");
        user_id = share.getString("user_id","0");//获取当前用户的user_id

        personal_seting_back_linear.setOnClickListener(this);
        our_bianji.setOnClickListener(this);
        mActicity.mFlToolbar.setVisibility(View.GONE);
        our_linear1.setOnClickListener(this);
        our_city.setOnClickListener(this);
        our_day.setOnClickListener(this);
        our_sex.setOnClickListener(this);
        our_headimage.setOnClickListener(this);
        cachPath=getDiskCacheDir(getContext())+ "/handimg.jpg";//图片路径

        cacheFile =getCacheFile(new File(getDiskCacheDir(getContext())),"handimg.jpg");
        //输出地址
        initrequest_no();
        
        init_ourdata();
    }

    private void init_ourdata() {
        Glide.with(mActicity)
                .load("http://ov62zyke0.bkt.clouddn.com/"+start_head_image)
                .placeholder(R.mipmap.deadpool)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        our_headimage.setImageDrawable(resource);
                    }
                });
        our_name.setText(start_name);
        if(start_sex == 2) {
            our_sex.setText("女");
        }else {
            our_sex.setText("男");
        }
        our_phone.setText(phone);
        if(start_birthday !=1l)
        {
            our_day.setText(getDateToString(start_birthday));
        }
        our_city_xi.setText(start_address);
        our_shen.setText(start_id_card);
        if(!start_native.equals(""))
        {
            id_cityName();
            our_city.setText(city_p+"|"+city_c+"|"+city_q);
        }else {
            our_city.setText("");
        }
    }

    private void initrequest_no() {
        our_name.setFocusable(false);
        our_name.setFocusableInTouchMode(false);//设置不可编辑状态；

        our_sex.setFocusable(false);
        our_sex.setFocusableInTouchMode(false);//设置不可编辑状态；

        our_shen.setFocusable(false);
        our_shen.setFocusableInTouchMode(false);//设置不可编辑状态；

        our_city.setFocusable(false);
        our_city.setFocusableInTouchMode(false);//设置不可编辑状态；

        our_city_xi.setFocusable(false);
        our_city_xi.setFocusableInTouchMode(false);//设置不可编辑状态；

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case  R.id.personal_seting_back_linear:
                mActicity.onBackPressed();

                break;
            case R.id.our_bianji://编辑按钮
                System.out.println("*********new_cit******"+new_city);
                if(!our_bianji.getText().toString().equals("完成")) {
                    flage = 1;
                    initrequest_yes();
                    our_bianji.setText("完成");
                }else {
                    //网络提交请求
                    if(!our_name.getText().toString().equals("")&&!our_phone.getText().toString().equals(""))
                    {
                        if (isMobileNO(our_phone.getText().toString().trim())) {
                            our_bianji.setText("编辑");
                            if (headimage_flage == 0) {
                                //网络请求
                                //getUpimg(headimage);
                                thread(user_id, start_head_image, 1);//当只改变数据不改变头像
                            } else {
                                getUpimg(cachPath);
                            }
                            initrequest_no();
                        }else {
                            SAToast.makeText(getContext(),"手机格式不对").show();
                        }
                    }else {
                        SAToast.makeText(getContext(),"用户名和手机不能未空").show();
                    }
                }
                break;
            case R.id.our_linear1:
                if(flage == 1)//可选择相片和照相
                {
                    showPopuWindow_headimage();
                }else {
                    SAToast.makeText(getContext(),"点击编辑后编辑").show();
                }
                break;
            case R.id.our_headimage:
              /*  if(!start_head_image.equals(""))
                {
                    dialog_photo(mActicity);
                }*/
                break;
            case R.id.our_city:
                new KeyBordUtil().hideSoftKeyboard(getView());
                CityPickerView cityPicker = new CityPickerView.Builder(mActicity,getContext()).textSize(20)
                        .titleTextColor("#000000")
                        .backgroundPop(0xa0000000)
                        .province("贵州省")
                        .city("贵阳市")
                        .district("南明区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .build();
                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        //返回结果
                        our_city.setText(province.getName() + "|" + city.getName() + "|" + district.getName());
                        new_city = province.getId()+"000" + "|" + city.getId() + "000|" + district.getId()+"000";
                        p_id = province.getId();
                        c_id =  city.getId();
                        a_id = district.getId();
                        new_city_flage = 1;
                        WindowManager.LayoutParams lp = mActicity.getWindow().getAttributes();
                        lp.alpha=1f;
                        mActicity.getWindow().setAttributes(lp);
                    }

                    @Override
                    public void onCancel() {
                        WindowManager.LayoutParams lp = mActicity.getWindow().getAttributes();
                        lp.alpha=1f;
                        mActicity.getWindow().setAttributes(lp);
                    }
                });

                break;
            case R.id.our_day:
                new KeyBordUtil().hideSoftKeyboard(getView());
                DayTimerPickerView cityPicker1 = new DayTimerPickerView.Builder(mActicity,getContext()).textSize(20)
                        .titleTextColor("#000000")
                        .backgroundPop(0xffffff)
                        .province("2017")
                        .city("9")
                        .district("26")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(5)
                        .itemPadding(15)
                        .build();
                cityPicker1.show();
                cityPicker1.setOnCityItemClickListener(new DayTimerPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String province, String city, String district) {
                        int a =Integer.parseInt(city);
                        if(a>0&&a<10)
                        {
                            city="0"+city;
                        }
                        int b =Integer.parseInt(district);
                        if(b>0&&b<10)
                        {
                            district="0"+district;
                        }
                        //返回结果
                        our_day.setText(province+ "-" + city+ "-" + district);
                        WindowManager.LayoutParams lp = mActicity.getWindow().getAttributes();
                        lp.alpha=1f;
                        mActicity.getWindow().setAttributes(lp);
                    }
                    @Override
                    public void onCancel() {
                        WindowManager.LayoutParams lp = mActicity.getWindow().getAttributes();
                        lp.alpha=1f;
                        mActicity.getWindow().setAttributes(lp);
                    }
                });

                break;
            case R.id.our_sex://性别选择
                new KeyBordUtil().hideSoftKeyboard(getView());
                PersonPickerView cityPicker2 = new PersonPickerView.Builder(mActicity,getContext()).textSize(20)
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
                cityPicker2.show();
                cityPicker2.setOnCityItemClickListener(new PersonPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String province, String city, String district) {
                        //返回结果
                        our_sex.setText(district);
                        WindowManager.LayoutParams lp = mActicity.getWindow().getAttributes();
                        lp.alpha=1f;
                        mActicity.getWindow().setAttributes(lp);

                    }
                    @Override
                    public void onCancel() {
                        WindowManager.LayoutParams lp = mActicity.getWindow().getAttributes();
                        lp.alpha=1f;
                        mActicity.getWindow().setAttributes(lp);
                    }
                });
                break;
        }
    }

    private void showPopuWindow_headimage() {

        final View view = LayoutInflater.from(getContext()).inflate(R.layout.head_image_popu, null);
        TextView open_photo = view.findViewById(R.id.open_image);//打开相册
        TextView open_caram =  view.findViewById(R.id.open_caram);//打开照相机
        TextView open_back = view.findViewById(R.id.open_back);//取消

        headimage_pop = new PopupWindow(view);
        headimage_pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        headimage_pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        headimage_pop.setAnimationStyle(R.style.LoginPopWindowAnim);//设置进入和出场动画
        WindowManager.LayoutParams lp = mActicity.getWindow().getAttributes();
        lp.alpha=0.5f;
        mActicity.getWindow().setAttributes(lp);

        headimage_pop.setBackgroundDrawable(this.getResources().getDrawable(R.color.white));
        headimage_pop.setOutsideTouchable(true);
        headimage_pop.setFocusable(true);
        headimage_pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        headimage_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActicity.getWindow().getAttributes();
                lp.alpha=1f;
                mActicity.getWindow().setAttributes(lp);
            }
        });
        open_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoForAlbum();//打开相册
                headimage_pop.dismiss();
            }
        });
        open_caram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoForCamera();//打开相机
                headimage_pop.dismiss();
            }
        });
        open_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowManager.LayoutParams lp = mActicity.getWindow().getAttributes();
                lp.alpha=1f;
                mActicity.getWindow().setAttributes(lp);
                headimage_pop.dismiss();
            }
        });

    }


    private void initrequest_yes() {
        our_name.setFocusableInTouchMode(true);
        our_name.setFocusable(true);
        our_name.requestFocus();//设置可编辑状态

        our_shen.setFocusableInTouchMode(true);
        our_shen.setFocusable(true);
        our_shen.requestFocus();//设置可编辑状态

        our_city.setFocusableInTouchMode(true);
        our_city.setFocusable(true);
        our_city.requestFocus();//设置可编辑状态

        our_city_xi.setFocusableInTouchMode(true);
        our_city_xi.setFocusable(true);
        our_city_xi.requestFocus();//设置可编辑状态

    }



    private void takePhotoForAlbum() {  //打开相册

        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        requestRuntimePermission(permissions, new PermissionListener() {
            @Override
            public void onGranted() {
                openAlbum();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                //没有获取到权限，什么也不执行，看你心情
            }
        });
    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    private void takePhotoForCamera() {  //打开相机
        String[] permissions={Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestRuntimePermission(permissions, new PermissionListener() {
            @Override
            public void onGranted() {
                openCamera();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                //有权限被拒绝，什么也不做好了，看你心情
            }
        });

    }
    private void openCamera() {

        cameraFile = getCacheFile(new File(getDiskCacheDir(getContext())),"car_image.jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            imageUri = Uri.fromFile(cameraFile);
        } else {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageUri = FileProvider.getUriForFile(getContext(), "microtech.hxswork.com.android_work.fileprovider", cameraFile);
        }
        // 启动相机程序
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        startPhotoZoom(cameraFile,350);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;

            case CROP_PHOTO:
                try {
                    if (resultCode==RESULT_OK){
                        System.out.println("****cachPath***********"+cachPath);
                        System.out.println("****cachPath***********"+cachPath);

                        Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(Uri.fromFile(new File(cachPath))));
                        our_headimage.setImageBitmap(bitmap);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
        }
    }
    private  File getCacheFile(File parent, String child) {
        // 创建File对象，用于存储拍照后的图片
        File file = new File(parent, child);

        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        String imagePath= uriToPath(uri);
//        displayImage(imagePath); // 根据图片路径显示图片

        Log.i("TAG","file://"+imagePath+"选择图片的URI"+uri);
        startPhotoZoom(new File(imagePath),350);
    }
    private String uriToPath(Uri uri) {
        String path=null;
        if (DocumentsContract.isDocumentUri(getContext(), uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                path = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            path = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            path = uri.getPath();
        }
        return  path;
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
//        displayImage(imagePath);
        Log.i("TAG","file://"+imagePath+"选择图片的URI"+uri);
        startPhotoZoom(new File(imagePath),350);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContext().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    /**
     * 剪裁图片
     */
    private void startPhotoZoom(File file, int size) {
        Log.i("TAG",getImageContentUri(getContext(),file)+"裁剪照片的真实地址");

        headimage = getImageContentUri(getContext(),file)+"";
        headimage_flage = 1;
        System.out.println("headimage******"+headimage);
        System.out.println("headimage_flage******"+headimage_flage);
        our_bianji.setText("完成");
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(getImageContentUri(getContext(),file), "image/*");//自己使用Content Uri替换File Uri
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 180);
            intent.putExtra("outputY", 180);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(cacheFile));//定义输出的File Uri
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, CROP_PHOTO);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your device doesn't support the crop action!";
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 转化地址为content开头
     * @param context
     * @param imageFile
     * @return
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
    //andrpoid 6.0 需要写运行时权限
    public void requestRuntimePermission(String[] permissions, PermissionListener listener) {

        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) getContext(), permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            mListener.onGranted();
        }
    }
    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }
    public interface PermissionListener {
        /**
         * 成功获取权限
         */
        void onGranted();

        /**
         * 为获取权限
         * @param deniedPermission
         */
        void onDenied(List<String> deniedPermission);

    }



    private  void timer()
    {
        DatePickerDialog dd = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String dateInString = dayOfMonth +"-" +(monthOfYear+1)+"-"+year;
                            Date date = formatter.parse(dateInString);

                            formatter = new SimpleDateFormat("yyyy-MM-dd");

                            our_day.setText(our_day.getText().toString() +" "+formatter.format(date).toString());

                        } catch (Exception ex) {
                        }

                    }
                }, 1994, 12, 33);
        dd.show();
    }



    public void thread(final String user_id , final String uoimage, final int i) {
        LoadingDialog.showDialogForLoading(mActicity, "数据上传中..", true);
        final String new_sex;
        if (our_sex.getText().toString().equals("男"))
        {
            new_sex="1";
        }else {
            new_sex="2";
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ok = new OkHttpClientManager();

                    if(i==1) {     //用户编辑资料
                        final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                                //主界面
                                new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                                new OkHttpClientManager.Param("user_id", user_id),
                                new OkHttpClientManager.Param("name", our_name.getText().toString().trim()),
                                new OkHttpClientManager.Param("avatar", uoimage),
                                new OkHttpClientManager.Param("sex",new_sex),
                                new OkHttpClientManager.Param("phone", our_phone.getText().toString().trim()),
                                new OkHttpClientManager.Param("id_card", our_shen.getText().toString().trim()),
                                new OkHttpClientManager.Param("birthday", our_day.getText().toString().trim()),
                                new OkHttpClientManager.Param("city", new_city),
                                new OkHttpClientManager.Param("address", our_city_xi.getText().toString().trim())
                        };

                        our_Data[0] = ok.postOneHeadAsString(edit_url, number, params_home);//体征数据

                        System.out.println(device_no + "    " + phone + "    " + user_id);

                        System.out.println("修改资料数据:" + our_Data[0]);

                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = our_Data[0];
                        handler.sendMessage(msg);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class MyHander extends Handler {
        //跟新数据handler
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0://第一次进来获取数据 获取保存新的数据
                    break;

                case 1:
                    String refresh = (String) msg.obj;
                    if((refresh.charAt(0)+"").equals("<"))
                    {
                        toast("服务器异常");
                        LoadingDialog.cancelDialogForLoading();
                        return;
                    }
                    document = Document.parse(refresh);//解析主界面刷新的体征数据
                    if(document.getInteger("code") == 200)
                    {
                        flage = 0;
                        start_name= our_name.getText().toString().trim();
                                if(our_sex.getText().toString().trim().equals("男")) {
                                    start_sex = 1;
                                }else {
                                    start_sex = 2;
                                }
                        start_birthday = getStringToDate(our_day.getText().toString().trim());

                        start_address = our_city_xi.getText().toString().trim();

                        start_id_card = our_shen.getText().toString().trim();

                        if(new_city_flage == 1)
                        {
                            proviceId = p_id+"000";
                            cityId = c_id+"000";
                            areaId = a_id+"000";
                            start_native = p_id+c_id+a_id;
                        }
                        LoadingDialog.cancelDialogForLoading();
                        mActicity.onBackPressed();
                    }else {
                        LoadingDialog.cancelDialogForLoading();
                        toast("上传失败");
                    }
                    //************
                    break;
            }
        }
    }

    private void toast(final String msg) {
        mActicity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getUpimg(final String imagePath) {
        LoadingDialog.showDialogForLoading(mActicity, "数据上传中..", true);
        new Thread() {
            public void run() {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                UploadManager uploadManager = new UploadManager();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String key = "icon_" + sdf.format(new Date());


                // String key = "mk" + System.currentTimeMillis()+".png";

                System.out.println("*****Auth.create(AccessKey, SecretKey)********"+Auth.create(AccessKey, SecretKey));//生成七牛的上传token

                uploadManager.put(imagePath, null, Auth.create(AccessKey, SecretKey).uploadToken("microtech"),//可以加入命名key值
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                try {
                                    System.out.println("*****upimg********"+res + "11111111111111" + info);
                                    if(info.isOK())
                                    {
                                        System.out.println("******upimg********"+res+info);

                                        document = Document.parse(res.toString());//解析主界面刷新的体征数据

                                        final String hash=document.getString("hash");

                                        start_head_image= hash;

                                        headimage_flage = 0;
                                        System.out.println("*****hash********"+hash);
                                        System.out.println("*****name********"+our_name.getText().toString().trim());
                                        System.out.println("*****user_id********"+user_id);
                                        System.out.println("*****sex********"+our_sex.getText().toString());
                                        System.out.println("*****phone********"+our_phone.getText().toString().trim());
                                        System.out.println("*****birthday********"+our_day.getText().toString().trim());

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                String new_sex;
                                                if (our_sex.getText().toString().equals("男"))
                                                {
                                                    new_sex="1";
                                                }else {
                                                    new_sex="2";
                                                }
                                                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                                                        //主界面
                                                        new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                                                        new OkHttpClientManager.Param("user_id", user_id),
                                                        new OkHttpClientManager.Param("name", our_name.getText().toString().trim()),
                                                        new OkHttpClientManager.Param("avatar", hash),
                                                        new OkHttpClientManager.Param("sex",new_sex.trim()),
                                                        new OkHttpClientManager.Param("phone", our_phone.getText().toString().trim()),
                                                        new OkHttpClientManager.Param("id_card", our_shen.getText().toString().trim()),
                                                        new OkHttpClientManager.Param("birthday", our_day.getText().toString().trim()),
                                                        new OkHttpClientManager.Param("city", new_city),
                                                        new OkHttpClientManager.Param("address", our_city_xi.getText().toString().trim())
                                                };
                                                try {
                                                    our_Data[0] = ok.postOneHeadAsString(edit_url, device_no, params_home);//体征数据
                                                    start_name=our_name.getText().toString().trim();

                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                System.out.println(device_no + "    " + phone + "    " + user_id);
                                                System.out.println("修改资料数据:" + our_Data[0]);
                                                Message message =new Message();
                                                message.what=1;
                                                message.obj=our_Data[0];
                                                handler.sendMessage(message);
                                            }
                                        }).start();

                                    }else {
                                        toast("头像上传异常");
                                        LoadingDialog.cancelDialogForLoading();
                                    }
                                    // 七牛返回的文件名
                                    //upimg = res.getString("key");
                                    //System.out.println("upimg********"+upimg);
                                    //list集合中图片上传完成后，发送handler消息回主线程进行其他操作
                                    //if (upimg_key_list.size() == dataList
                                          //  .size()) {
                                     /*   Message message =new Message();
                                        message.what=1;
                                        handler.sendMessage(message);*/
                                    //}

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, null);
            }
        }.start();
    }
    private  void id_cityName()
    {
        System.out.println("**now_size***");

        System.out.println("**proviceId_size***"+proviceId);
        IdToCityName idToCityName = new IdToCityName(getContext());
                System.out.println("**size***"+idToCityName.mProvinceBeanArrayList.size()+"**size**"+idToCityName.mCityBeanArrayList.size());
                for (int i = 0; i < idToCityName.mProvinceBeanArrayList.size(); i++)
                {
                    if(idToCityName.mProvinceBeanArrayList.get(i).getId().equals(proviceId.substring(0,proviceId.length()-3)))
                    {
                        p_i =  i ;
                        city_p=idToCityName.mProvinceBeanArrayList.get(i).getName();
                    }
                }

                for(int j =0; j < idToCityName.mCityBeanArrayList.get(p_i).size();j++)
                {
                    if(idToCityName.mCityBeanArrayList.get(p_i).get(j).getId().equals(cityId.substring(0,cityId.length()-3)))
                    {
                        c_i = j;
                        city_c = idToCityName.mCityBeanArrayList.get(p_i).get(j).getName();
                    }
                }

                for(int k =0; k < idToCityName.mDistrictBeanArrayList.get(p_i).get(c_i).size();k++)
                {
                    System.out.println("*******id*******"+idToCityName.mDistrictBeanArrayList.get(p_i).get(c_i).get(k).getId());
                    if(idToCityName.mDistrictBeanArrayList.get(p_i).get(c_i).get(k).getId().equals(areaId.substring(0,areaId.length()-3)))
                    {
                        k_i = k;
                        city_q = idToCityName.mDistrictBeanArrayList.get(p_i).get(c_i).get(k).getName();
                    }
                }
        System.out.println("*******arread_id*******"+areaId.substring(0,areaId.length()-3));

    }
}
