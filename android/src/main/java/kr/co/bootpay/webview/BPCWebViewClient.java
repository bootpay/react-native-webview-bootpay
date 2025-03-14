package kr.co.bootpay.webview;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import kr.co.bootpay.webview.events.TopHttpErrorEvent;
import kr.co.bootpay.webview.events.TopLoadingErrorEvent;
import kr.co.bootpay.webview.events.TopLoadingFinishEvent;
import kr.co.bootpay.webview.events.TopLoadingStartEvent;
import kr.co.bootpay.webview.events.TopRenderProcessGoneEvent;
import kr.co.bootpay.webview.events.TopShouldStartLoadWithRequestEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.util.concurrent.atomic.AtomicReference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;

public class BPCWebViewClient extends WebViewClient {
    private static String TAG = "BPCWebViewClient";
    protected static final int SHOULD_OVERRIDE_URL_LOADING_TIMEOUT = 250;

    protected boolean mLastLoadFailed = false;
    protected BPCWebView.ProgressChangedFilter progressChangedFilter = null;
    protected @Nullable String ignoreErrFailedForThisURL = null;
    protected @Nullable BPCBasicAuthCredential basicAuthCredential = null;

    public void setIgnoreErrFailedForThisURL(@Nullable String url) {
        ignoreErrFailedForThisURL = url;
    }

    public void setBasicAuthCredential(@Nullable BPCBasicAuthCredential credential) {
        basicAuthCredential = credential;
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        String cookies = CookieManager.getInstance().getCookie(url);
        if (cookies != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().flush();
            }else {
                CookieSyncManager.getInstance().sync();
            }
        }

        if (!mLastLoadFailed) {
            BPCWebView reactWebView = (BPCWebView) webView;

            reactWebView.callInjectedJavaScript();

            emitFinishEvent(webView, url);
        }
    }

    @Override
    public void doUpdateVisitedHistory (WebView webView, String url, boolean isReload) {
      super.doUpdateVisitedHistory(webView, url, isReload);

      ((BPCWebView) webView).dispatchEvent(
        webView,
        new TopLoadingStartEvent(
          BPCWebViewWrapper.getReactTagFromWebView(webView),
          createWebViewEvent(webView, url)));
    }

    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
      super.onPageStarted(webView, url, favicon);
      mLastLoadFailed = false;

      BPCWebView reactWebView = (BPCWebView) webView;
      reactWebView.callInjectedJavaScriptBeforeContentLoaded();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

    //   Log.i("bootpay url", url);
      if(BootpayUrlHelper.shouldOverrideUrlLoading(view, url)) {
        return true;
      }
    //   return false;
      return shouldOverrideUrlLoadingRN(view, url);
    }


    
    public boolean shouldOverrideUrlLoadingRN(WebView view, String url) {
        final BPCWebView BPCWebView = (BPCWebView) view;
        final boolean isJsDebugging = BPCWebView.getReactApplicationContext().getJavaScriptContextHolder().get() == 0;

        if (!isJsDebugging && BPCWebView.mMessagingJSModule != null) {
            final Pair<Double, AtomicReference<BPCWebViewModuleImpl.ShouldOverrideUrlLoadingLock.ShouldOverrideCallbackState>> lock = BPCWebViewModuleImpl.shouldOverrideUrlLoadingLock.getNewLock();
            final double lockIdentifier = lock.first;
            final AtomicReference<BPCWebViewModuleImpl.ShouldOverrideUrlLoadingLock.ShouldOverrideCallbackState> lockObject = lock.second;

            final WritableMap event = createWebViewEvent(view, url);
            event.putDouble("lockIdentifier", lockIdentifier);
            BPCWebView.dispatchDirectShouldStartLoadWithRequest(event);

            try {
                assert lockObject != null;
                synchronized (lockObject) {
                    final long startTime = SystemClock.elapsedRealtime();
                    while (lockObject.get() == BPCWebViewModuleImpl.ShouldOverrideUrlLoadingLock.ShouldOverrideCallbackState.UNDECIDED) {
                        if (SystemClock.elapsedRealtime() - startTime > SHOULD_OVERRIDE_URL_LOADING_TIMEOUT) {
                            FLog.w(TAG, "Did not receive response to shouldOverrideUrlLoading in time, defaulting to allow loading.");
                            BPCWebViewModuleImpl.shouldOverrideUrlLoadingLock.removeLock(lockIdentifier);
                            return false;
                        }
                        lockObject.wait(SHOULD_OVERRIDE_URL_LOADING_TIMEOUT);
                    }
                }
            } catch (InterruptedException e) {
                FLog.e(TAG, "shouldOverrideUrlLoading was interrupted while waiting for result.", e);
                BPCWebViewModuleImpl.shouldOverrideUrlLoadingLock.removeLock(lockIdentifier);
                return false;
            }

            final boolean shouldOverride = lockObject.get() == BPCWebViewModuleImpl.ShouldOverrideUrlLoadingLock.ShouldOverrideCallbackState.SHOULD_OVERRIDE;
            BPCWebViewModuleImpl.shouldOverrideUrlLoadingLock.removeLock(lockIdentifier);

            return shouldOverride;
        } else {
            FLog.w(TAG, "Couldn't use blocking synchronous call for onShouldStartLoadWithRequest due to debugging or missing Catalyst instance, falling back to old event-and-load.");
            progressChangedFilter.setWaitingForCommandLoadUrl(true);

            int reactTag = BPCWebViewWrapper.getReactTagFromWebView(view);
            UIManagerHelper.getEventDispatcherForReactTag((ReactContext) view.getContext(), reactTag).dispatchEvent(new TopShouldStartLoadWithRequestEvent(
                    reactTag,
                    createWebViewEvent(view, url)));
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        final String url = request.getUrl().toString();
        return this.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (basicAuthCredential != null) {
            handler.proceed(basicAuthCredential.username, basicAuthCredential.password);
            return;
        }
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public void onReceivedSslError(final WebView webView, final SslErrorHandler handler, final SslError error) {
        // onReceivedSslError is called for most requests, per Android docs: https://developer.android.com/reference/android/webkit/WebViewClient#onReceivedSslError(android.webkit.WebView,%2520android.webkit.SslErrorHandler,%2520android.net.http.SslError)
        // WebView.getUrl() will return the top-level window URL.
        // If a top-level navigation triggers this error handler, the top-level URL will be the failing URL (not the URL of the currently-rendered page).
        // This is desired behavior. We later use these values to determine whether the request is a top-level navigation or a subresource request.
        String topWindowUrl = webView.getUrl();
        String failingUrl = error.getUrl();

        // Cancel request after obtaining top-level URL.
        // If request is cancelled before obtaining top-level URL, undesired behavior may occur.
        // Undesired behavior: Return value of WebView.getUrl() may be the current URL instead of the failing URL.
        handler.cancel();

        if (!topWindowUrl.equalsIgnoreCase(failingUrl)) {
            // If error is not due to top-level navigation, then do not call onReceivedError()
            Log.w(TAG, "Resource blocked from loading due to SSL error. Blocked URL: "+failingUrl);
            return;
        }

        int code = error.getPrimaryError();
        String description = "";
        String descriptionPrefix = "SSL error: ";

        // https://developer.android.com/reference/android/net/http/SslError.html
        switch (code) {
            case SslError.SSL_DATE_INVALID:
                description = "The date of the certificate is invalid";
                break;
            case SslError.SSL_EXPIRED:
                description = "The certificate has expired";
                break;
            case SslError.SSL_IDMISMATCH:
                description = "Hostname mismatch";
                break;
            case SslError.SSL_INVALID:
                description = "A generic error occurred";
                break;
            case SslError.SSL_NOTYETVALID:
                description = "The certificate is not yet valid";
                break;
            case SslError.SSL_UNTRUSTED:
                description = "The certificate authority is not trusted";
                break;
            default:
                description = "Unknown SSL Error";
                break;
        }

        description = descriptionPrefix + description;

        this.onReceivedError(
                webView,
                code,
                description,
                failingUrl
        );
    }

    void alertSSLError(WebView view, SslErrorHandler handler, SslError error) {
      // for SSLErrorHandler
      AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
      builder.setTitle("SSL Connection Error");
      builder.setMessage("Your device's Android version is outdated and may not securely connect to our service. To continue using the app securely, please update your device's operating system. If you choose to proceed without updating, it may expose you to security vulnerabilities.");
      builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          // Redirect the user to the system update settings


          Intent intent = new Intent("android.settings.SYSTEM_UPDATE_SETTINGS");
          if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
            view.getContext().startActivity(intent);
          } else {
            // If the device does not support system update settings intent
            Toast.makeText(view.getContext(), "System update option not available. Please check your device settings manually.", Toast.LENGTH_LONG).show();
          }
        }
      });
      builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          handler.cancel();
        }
      });
      AlertDialog dialog = builder.create();
      dialog.show();
    }

    @Override
    public void onReceivedError(
            WebView webView,
            int errorCode,
            String description,
            String failingUrl) {

        if (ignoreErrFailedForThisURL != null
                && failingUrl.equals(ignoreErrFailedForThisURL)
                && errorCode == -1
                && description.equals("net::ERR_FAILED")) {

            // This is a workaround for a bug in the WebView.
            // See these chromium issues for more context:
            // https://bugs.chromium.org/p/chromium/issues/detail?id=1023678
            // https://bugs.chromium.org/p/chromium/issues/detail?id=1050635
            // This entire commit should be reverted once this bug is resolved in chromium.
            setIgnoreErrFailedForThisURL(null);
            return;
        }

        super.onReceivedError(webView, errorCode, description, failingUrl);
        mLastLoadFailed = true;

        // In case of an error JS side expect to get a finish event first, and then get an error event
        // Android WebView does it in the opposite way, so we need to simulate that behavior
        emitFinishEvent(webView, failingUrl);

        WritableMap eventData = createWebViewEvent(webView, failingUrl);
        eventData.putDouble("code", errorCode);
        eventData.putString("description", description);

        int reactTag = BPCWebViewWrapper.getReactTagFromWebView(webView);
        UIManagerHelper.getEventDispatcherForReactTag((ReactContext) webView.getContext(), reactTag).dispatchEvent(new TopLoadingErrorEvent(reactTag, eventData));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedHttpError(
            WebView webView,
            WebResourceRequest request,
            WebResourceResponse errorResponse) {
        super.onReceivedHttpError(webView, request, errorResponse);

        if (request.isForMainFrame()) {
            WritableMap eventData = createWebViewEvent(webView, request.getUrl().toString());
            eventData.putInt("statusCode", errorResponse.getStatusCode());
            eventData.putString("description", errorResponse.getReasonPhrase());

            int reactTag = BPCWebViewWrapper.getReactTagFromWebView(webView);
            UIManagerHelper.getEventDispatcherForReactTag((ReactContext) webView.getContext(), reactTag).dispatchEvent(new TopHttpErrorEvent(reactTag, eventData));
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail detail) {
        // WebViewClient.onRenderProcessGone was added in O.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return false;
        }
        super.onRenderProcessGone(webView, detail);

        if(detail.didCrash()){
            Log.e(TAG, "The WebView rendering process crashed.");
        }
        else{
            Log.w(TAG, "The WebView rendering process was killed by the system.");
        }

        // if webView is null, we cannot return any event
        // since the view is already dead/disposed
        // still prevent the app crash by returning true.
        if(webView == null){
            return true;
        }

        WritableMap event = createWebViewEvent(webView, webView.getUrl());
        event.putBoolean("didCrash", detail.didCrash());
        int reactTag = BPCWebViewWrapper.getReactTagFromWebView(webView);
        UIManagerHelper.getEventDispatcherForReactTag((ReactContext) webView.getContext(), reactTag).dispatchEvent(new TopRenderProcessGoneEvent(reactTag, event));

        // returning false would crash the app.
        return true;
    }

    protected void emitFinishEvent(WebView webView, String url) {
        int reactTag = BPCWebViewWrapper.getReactTagFromWebView(webView);
        UIManagerHelper.getEventDispatcherForReactTag((ReactContext) webView.getContext(), reactTag).dispatchEvent(new TopLoadingFinishEvent(reactTag, createWebViewEvent(webView, url)));
    }

    protected WritableMap createWebViewEvent(WebView webView, String url) {
        WritableMap event = Arguments.createMap();
        event.putDouble("target", BPCWebViewWrapper.getReactTagFromWebView(webView));
        // Don't use webView.getUrl() here, the URL isn't updated to the new value yet in callbacks
        // like onPageFinished
        event.putString("url", url);
        event.putBoolean("loading", !mLastLoadFailed && webView.getProgress() != 100);
        event.putString("title", webView.getTitle());
        event.putBoolean("canGoBack", webView.canGoBack());
        event.putBoolean("canGoForward", webView.canGoForward());
        return event;
    }

    public void setProgressChangedFilter(BPCWebView.ProgressChangedFilter filter) {
        progressChangedFilter = filter;
    }
}