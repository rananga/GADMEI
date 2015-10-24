package lk.gadmei.hemantha.gadmei;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            myWebView = (WebView) findViewById(R.id.webView);

            final LinearLayout loMain = (LinearLayout) findViewById(R.id.loMain);
            final LinearLayout loLeft = (LinearLayout) findViewById(R.id.loLeft);
            final FrameLayout loRight = (FrameLayout) findViewById(R.id.loRight);
            final FrameLayout loInnerLeft = (FrameLayout) findViewById(R.id.loInnerLeft);
            final FrameLayout loInnerRight = (FrameLayout) findViewById(R.id.loInnerRight);

            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            myWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100 && loMain.getVisibility() == ProgressBar.GONE) {
                        loMain.setVisibility(ProgressBar.VISIBLE);
                    }
                    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                    llp.weight = progress;
                    loLeft.setLayoutParams(llp);

                    llp.weight = 100 - progress;
                    loRight.setLayoutParams(llp);
                    if (progress == 100) {
                        loMain.setVisibility(ProgressBar.GONE);
                    }
                }
            });

            final Activity activity = this;

            myWebView.setWebViewClient(new WebViewClient() {
                @TargetApi(Build.VERSION_CODES.M)
                public void onReceivedError(WebView view, WebResourceRequest wrr, WebResourceError err) {
                    Toast.makeText(activity, "Oh no! " + err.getDescription(), Toast.LENGTH_SHORT).show();
                }

                @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }
            });

            myWebView.loadUrl("http://www.gadmei.lk");
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }
        }
    }
}
