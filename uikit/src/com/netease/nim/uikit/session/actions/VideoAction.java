package com.netease.nim.uikit.session.actions;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.session.helper.VideoMessageHelper;
import com.netease.nim.uikit.session.constant.RequestCode;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;

/**
 * Created by hzxuwen on 2015/6/12.
 */
public class VideoAction extends BaseAction {
    // 视频
    protected VideoMessageHelper videoMessageHelper;

    public VideoAction() {
        super(R.drawable.nim_message_plus_video_selector, R.string.input_panel_video);

    }

    @Override
    public void onClick() {
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(getActivity(),mPermissionList,123);
        }
        videoHelper().showVideoSource(makeRequestCode(RequestCode.GET_LOCAL_VIDEO), makeRequestCode(RequestCode.CAPTURE_VIDEO));
    }

    /**
     * ********************** 视频 *******************************
     */
    private void initVideoMessageHelper() {
        videoMessageHelper = new VideoMessageHelper(getActivity(), new VideoMessageHelper.VideoMessageHelperListener() {

            @Override
            public void onVideoPicked(File file, String md5) {
                MediaPlayer mediaPlayer = getVideoMediaPlayer(file);
                long duration = mediaPlayer == null ? 0 : mediaPlayer.getDuration();
                int height = mediaPlayer == null ? 0 : mediaPlayer.getVideoHeight();
                int width = mediaPlayer == null ? 0 : mediaPlayer.getVideoWidth();
                IMMessage message = MessageBuilder.createVideoMessage(getAccount(), getSessionType(), file, duration, width, height, md5);
                sendMessage(message);
            }
        });
    }

    /**
     * 获取视频mediaPlayer
     * @param file 视频文件
     * @return mediaPlayer
     */
    private MediaPlayer getVideoMediaPlayer(File file) {
        try {
            return MediaPlayer.create(getActivity(), Uri.fromFile(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case RequestCode.GET_LOCAL_VIDEO:
            videoHelper().onGetLocalVideoResult(data);
            break;
        case RequestCode.CAPTURE_VIDEO:
            videoHelper().onCaptureVideoResult(data);
            break;
        }
    }

    private VideoMessageHelper videoHelper() {
        if (videoMessageHelper == null) {
            initVideoMessageHelper();
        }
        return videoMessageHelper;
    }
}
