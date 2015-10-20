package lk.gadmei.hemantha.gadmei;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
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
            final ProgressBar Pbar = (ProgressBar) findViewById(R.id.pBar);
            final TextView txtview = (TextView) findViewById(R.id.tV1);

            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            myWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100 && Pbar.getVisibility() == ProgressBar.GONE) {
                        Pbar.setVisibility(ProgressBar.VISIBLE);
                        txtview.setVisibility(View.VISIBLE);
                    }
                    Pbar.setProgress(progress);
                    if (progress == 100) {
                        Pbar.setVisibility(ProgressBar.GONE);
                        txtview.setVisibility(View.VISIBLE);
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
