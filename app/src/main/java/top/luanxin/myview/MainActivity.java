package top.luanxin.myview;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView wv;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        方法1 设置状态栏为透明...全屏 配合布局文件中
        android:fitsSystemWindows="true"
        */
/*        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }*/

        // 方法2 配合布局文件中 android:clipToPadding="true" android:fitsSystemWindows="true"  方法1不需要
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

//        方法3，直接全屏，设置styles.xml  <item name="android:windowFullscreen">true</item>

        setContentView(R.layout.activity_main);

        wv = (WebView) findViewById(R.id.wv);// 获取webview组件
        pb = (ProgressBar) findViewById(R.id.pb);// 获取progressbar组件
        wv.getSettings().setBuiltInZoomControls(true);//启用缩放功能
        wv.getSettings().setJavaScriptEnabled(true);//启用js
        wv.setWebViewClient(new WebViewClient());//创建并使用webviewclient对象
        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                pb.setProgress(progress);//设置进度
                pb.setVisibility(progress < 100? View.VISIBLE:View.GONE);//依进度让进度条显示或消失
            }
        });

        wv.loadUrl("http://www.mlovedl.top");//加载网页

    }

    @Override
    public void onBackPressed() {
        if(wv.canGoBack()) { //如果webview有上一页
            wv.goBack(); // 返回上一页
            return;
        }
        super.onBackPressed(); // 调用父类的同名方法，以执行默认操作（结束程序）
    }

    //记录用户首次点击返回键的时间
    private long firstTime=0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    firstTime=secondTime;
                    onBackPressed(); // 调用返回上一页
                    return true;
                }else{
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


}
