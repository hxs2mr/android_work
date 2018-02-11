package microtech.hxswork.com.android_work.ui.fragment;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import microtech.hxswork.com.android_work.R;

import butterknife.Bind;
import microtech.hxswork.com.android_work.contract.HeathlyWebViewContract;
import microtech.hxswork.com.android_work.model.HeathlyWebViewModelImpl;
import microtech.hxswork.com.android_work.presenter.HeathlyWebViewPresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.commom.base.BaseFragment;

/**
 * Created by microtech on 2017/9/6.
 */

public class HeathlyWebViewFragment extends BaseFragment<HeathlyWebViewPresenterImpl,HeathlyWebViewModelImpl> implements HeathlyWebViewContract.View, View.OnClickListener {
    @Bind(R.id.webview_next_back)
    ImageView webview_next_backl;
    @Bind(R.id.webview_title_text)
    TextView webview_title_text;
    @Bind(R.id.webview)
    WebView webView;
    HomeActivity mActivity;
    @Override
    protected int getLayoutResource() {
        return R.layout.heathlywebview_fragment;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        mActivity = (HomeActivity) getActivity();
        mActivity.mFlToolbar.setVisibility(View.GONE);
        webview_next_backl.setOnClickListener(this);
        String web_url = getArguments().getString("weburl");
        WebView(web_url);
    }
    public void WebView(String webview_url)
    {
        webView.loadUrl(webview_url);
        WebSettings websettings = webView.getSettings();
        //设置屏幕适应
        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        websettings.setUseWideViewPort(true);
        websettings.setLoadWithOverviewMode(true);
        websettings.setDefaultTextEncodingName("UTF-8");
        websettings.setJavaScriptEnabled(true);
        //webView.addJavascriptInterface(new ThreeFragment.JavascriptInterface(getContext()),"imagelistner");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(String.valueOf(request.getUrl()));
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                //当页面错误的时候
                super.onReceivedError(view, request, error);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if(newProgress == 100)
                {
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                getActivity().setTitle(title);
                webview_title_text.setText(title);
                System.out.println("*****************88："+view.getTitle());
                System.out.println("*****************88："+view.getUrl());
                System.out.println("**********:"+title);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.webview_next_back:
                mActivity.onBackPressed();
                break;
        }
    }
}
