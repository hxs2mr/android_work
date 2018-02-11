package microtech.hxswork.com.android_work.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import microtech.hxswork.com.android_work.R;


/**
 * Created by microtech on 2017/8/25.
 */

public class DictorActivity extends AppCompatActivity {
    public String toChatUsername;//聊天对象

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorinformation_fragment);
    }

    protected void onNewIntent(Intent intent) {
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }

}
