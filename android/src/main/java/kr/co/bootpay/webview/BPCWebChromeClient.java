package kr.co.bootpay.webview;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
 
import android.content.Intent;
import android.annotation.SuppressLint;
import android.net.http.SslError;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactNoCrashSoftException;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.build.ReactBuildConfig;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;


import kr.co.bootpay.webview.events.TopLoadingProgressEvent;
import kr.co.bootpay.webview.events.TopOpenWindowEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.util.Log;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.DialogInterface;

public class BPCWebChromeClient extends WebChromeClient implements LifecycleEventListener {
    protected static final FrameLayout.LayoutParams FULLSCREEN_LAYOUT_PARAMS = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);

    protected static final int FULLSCREEN_SYSTEM_UI_VISIBILITY = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_IMMERSIVE |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    protected static final int COMMON_PERMISSION_REQUEST = 3;

    protected BPCWebView mWebView;

    protected View mVideoView;
    protected WebChromeClient.CustomViewCallback mCustomViewCallback;

    /*
     * - Permissions -
     * As native permissions are asynchronously handled by the PermissionListener, many fields have
     * to be stored to send permissions results to the webview
     */

    // Webview camera & audio permission callback
    protected PermissionRequest permissionRequest;
    // Webview camera & audio permission already granted
    protected List<String> grantedPermissions;

    // Webview geolocation permission callback
    protected GeolocationPermissions.Callback geolocationPermissionCallback;
    // Webview geolocation permission origin callback
    protected String geolocationPermissionOrigin;

    // true if native permissions dialog is shown, false otherwise
    protected boolean permissionsRequestShown = false;
    // Pending Android permissions for the next request
    protected List<String> pendingPermissions = new ArrayList<>();

    protected BPCWebView.ProgressChangedFilter progressChangedFilter = null;
    protected boolean mAllowsProtectedMedia = false;

    protected boolean mHasOnOpenWindowEvent = false; 

    public BPCWebChromeClient(BPCWebView webView) {
        this.mWebView = webView;
    }
  

    boolean isPopupWebView = false;
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {

        ThemedReactContext reactContext = (ThemedReactContext) view.getContext();
        final BPCWebView newWebView = new BPCWebView(reactContext);

        BPCWebChromeClient client = new BPCWebChromeClient(newWebView);
        client.setProgressChangedFilter(progressChangedFilter);
        newWebView.setWebChromeClient(client);

        Log.d("BPCWebChromeClient", "onCreateWindow");
        isPopupWebView = true;

        setWebSettingCopy(view, newWebView);


        newWebView.setWebViewClient(new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading (WebView subview, String url) {

            Log.d("bootpay url", url);

            // return true;
            if(BootpayUrlHelper.shouldOverrideUrlLoading(view, url)) {
                return true;
            }
            //   return shouldOverrideUrlLoadingRN(view, url);
            WritableMap event = Arguments.createMap();
            event.putString("targetUrl", url);

            ((BPCWebView) view).dispatchEvent(
                view,
                new TopOpenWindowEvent(view.getId(), event)
            );

            return false;
        }
        });

       Dialog popupDialog = new Dialog(view.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
       popupDialog.setContentView(newWebView);
       ViewGroup.LayoutParams params = popupDialog.getWindow().getAttributes();
       params.width = ViewGroup.LayoutParams.MATCH_PARENT;
       params.height = ViewGroup.LayoutParams.MATCH_PARENT;
       popupDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
       popupDialog.setOnDismissListener(dialog -> {
         onCloseWindow(view); //rn에선 딱히 동작하지 않음
       });
       newWebView.setDialog(popupDialog);
       popupDialog.show();


        final WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(newWebView);
        resultMsg.sendToTarget();

        // 🔹 팝업을 위한 FrameLayout을 React Native UI 트리에 추가
        // Activity activity = reactContext.getCurrentActivity();
        // if (activity != null) {
        //     activity.runOnUiThread(() -> {
        //         FrameLayout popupContainer = new FrameLayout(activity);
        //         popupContainer.setLayoutParams(new FrameLayout.LayoutParams(
        //                 ViewGroup.LayoutParams.MATCH_PARENT,
        //                 ViewGroup.LayoutParams.MATCH_PARENT
        //         ));
        //         popupContainer.addView(newWebView);
        //         ((ViewGroup) activity.findViewById(android.R.id.content)).addView(popupContainer);
        //     });
        // } else {
        //     Log.e("BPCWebChromeClient", "Activity is null, cannot add popup WebView.");
        // }
 

        return true;
    }


    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
        ((BPCWebView) window).dissmissDialog();

        Log.d("BPCWebChromeClient", "onCloseWindow");

        isPopupWebView = false;
    //    if(mainView != null) {
    //      mainView.removeView(window);
    //    }
    //    window.setVisibility(View.GONE);
    }


 

    void setWebSettingCopy(WebView view, WebView newWebView) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newWebView.getSettings().setMediaPlaybackRequiresUserGesture( view.getSettings().getMediaPlaybackRequiresUserGesture() );
        }

        newWebView.getSettings().setBuiltInZoomControls(view.getSettings().getBuiltInZoomControls());
        newWebView.getSettings().setDisplayZoomControls(view.getSettings().getDisplayZoomControls());
        newWebView.getSettings().setAllowFileAccess(view.getSettings().getAllowFileAccess());
        newWebView.getSettings().setAllowContentAccess(view.getSettings().getAllowContentAccess());
        newWebView.getSettings().setLoadWithOverviewMode(view.getSettings().getLoadWithOverviewMode());
    //      newWebView.getSettings().setEnableSmoothTransition(view.getSettings().);
        newWebView.getSettings().setSaveFormData(view.getSettings().getSaveFormData());
        newWebView.getSettings().setSavePassword(view.getSettings().getSavePassword());
        newWebView.getSettings().setTextZoom(view.getSettings().getTextZoom());

        newWebView.getSettings().setUseWideViewPort(view.getSettings().getUseWideViewPort());
        newWebView.getSettings().setSupportMultipleWindows(true);
        newWebView.getSettings().setLayoutAlgorithm(view.getSettings().getLayoutAlgorithm());
        newWebView.getSettings().setStandardFontFamily(view.getSettings().getStandardFontFamily());
        newWebView.getSettings().setFixedFontFamily(view.getSettings().getFixedFontFamily());
        newWebView.getSettings().setSansSerifFontFamily(view.getSettings().getSansSerifFontFamily());
        newWebView.getSettings().setSerifFontFamily(view.getSettings().getSerifFontFamily());
        newWebView.getSettings().setCursiveFontFamily(view.getSettings().getCursiveFontFamily());
        newWebView.getSettings().setFantasyFontFamily(view.getSettings().getFantasyFontFamily());
        newWebView.getSettings().setMinimumFontSize(view.getSettings().getMinimumFontSize());
        newWebView.getSettings().setMinimumLogicalFontSize(view.getSettings().getMinimumLogicalFontSize());
        newWebView.getSettings().setDefaultFontSize(view.getSettings().getDefaultFontSize());
        newWebView.getSettings().setDefaultFixedFontSize(view.getSettings().getDefaultFixedFontSize());
        newWebView.getSettings().setLoadsImagesAutomatically(view.getSettings().getLoadsImagesAutomatically());
        newWebView.getSettings().setBlockNetworkImage(view.getSettings().getBlockNetworkImage());
        newWebView.getSettings().setJavaScriptEnabled(view.getSettings().getJavaScriptEnabled());
        newWebView.getSettings().setDatabaseEnabled(view.getSettings().getDatabaseEnabled());
        newWebView.getSettings().setDomStorageEnabled(view.getSettings().getDomStorageEnabled());

        newWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(view.getSettings().getJavaScriptCanOpenWindowsAutomatically());
        newWebView.getSettings().setDefaultTextEncodingName(view.getSettings().getDefaultTextEncodingName());
        newWebView.getSettings().setUserAgentString(view.getSettings().getUserAgentString());
        newWebView.getSettings().setCacheMode(view.getSettings().getCacheMode());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            newWebView.getSettings().setMixedContentMode(view.getSettings().getMixedContentMode());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newWebView.getSettings().setSafeBrowsingEnabled(view.getSettings().getSafeBrowsingEnabled());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            newWebView.getSettings().setForceDark(view.getSettings().getForceDark());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newWebView.getSettings().setDisabledActionModeMenuItems(view.getSettings().getDisabledActionModeMenuItems());
        }
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage message) {
        if (ReactBuildConfig.DEBUG) {
            return super.onConsoleMessage(message);
        }
        // Ignore console logs in non debug builds.
        return true;
    }

    @Override
    public void onProgressChanged(WebView webView, int newProgress) {
        super.onProgressChanged(webView, newProgress);
        final String url = webView.getUrl();
        if (progressChangedFilter.isWaitingForCommandLoadUrl()) {
            return;
        }

        if(isPopupWebView) {
            return;
        }

      Log.e("BPCWebChromeClient", "" + isPopupWebView);
//        if(this.mWebView == null || this.mWebView.getThemedReactContext() == null) return;
//      UIManagerModule uiManager = (this.mWebView.getThemedReactContext()).getNativeModule(UIManagerModule.class);
//      if (uiManager == null) {
//        Log.e("BPCWebChromeClient", "UIManagerModule is null, skipping event dispatch.");
//        return;
//      }

        int reactTag = BPCWebViewWrapper.getReactTagFromWebView(webView);
        WritableMap event = Arguments.createMap();
        event.putDouble("target", reactTag);
        event.putString("title", webView.getTitle());
        event.putString("url", url);
        event.putBoolean("canGoBack", webView.canGoBack());
        event.putBoolean("canGoForward", webView.canGoForward());
        event.putDouble("progress", (float) newProgress / 100);

      UIManagerHelper.getEventDispatcherForReactTag(this.mWebView.getThemedReactContext(), reactTag).dispatchEvent(new TopLoadingProgressEvent(reactTag, event));

    }

    @Override
    public void onPermissionRequest(final PermissionRequest request) {

        grantedPermissions = new ArrayList<>();

        ArrayList<String> requestedAndroidPermissions = new ArrayList<>();
        for (String requestedResource : request.getResources()) {
            String androidPermission = null;

            if (requestedResource.equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE)) {
                androidPermission = Manifest.permission.RECORD_AUDIO;
            } else if (requestedResource.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                androidPermission = Manifest.permission.CAMERA;
            } else if(requestedResource.equals(PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID)) {
                if (mAllowsProtectedMedia) {
                  grantedPermissions.add(requestedResource);
                } else {
                  /**
                   * Legacy handling (Kept in case it was working under some conditions (given Android version or something))
                   *
                   * Try to ask user to grant permission using Activity.requestPermissions
                   *
                   * Find more details here: https://github.com/react-native-webview/react-native-webview/pull/2732
                   */
                  androidPermission = PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID;
                }            }
            // TODO: RESOURCE_MIDI_SYSEX, RESOURCE_PROTECTED_MEDIA_ID.
            if (androidPermission != null) {
                if (ContextCompat.checkSelfPermission(this.mWebView.getThemedReactContext(), androidPermission) == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(requestedResource);
                } else {
                    requestedAndroidPermissions.add(androidPermission);
                }
            }
        }

        // If all the permissions are already granted, send the response to the WebView synchronously
        if (requestedAndroidPermissions.isEmpty()) {
            request.grant(grantedPermissions.toArray(new String[0]));
            grantedPermissions = null;
            return;
        }

        // Otherwise, ask to Android System for native permissions asynchronously

        this.permissionRequest = request;

        requestPermissions(requestedAndroidPermissions);
    }


    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {

        if (ContextCompat.checkSelfPermission(this.mWebView.getThemedReactContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            /*
             * Keep the trace of callback and origin for the async permission request
             */
            geolocationPermissionCallback = callback;
            geolocationPermissionOrigin = origin;

            requestPermissions(Collections.singletonList(Manifest.permission.ACCESS_FINE_LOCATION));

        } else {
            callback.invoke(origin, true, false);
        }
    }

    private PermissionAwareActivity getPermissionAwareActivity() {
        Activity activity = this.mWebView.getThemedReactContext().getCurrentActivity();
        if (activity == null) {
            throw new IllegalStateException("Tried to use permissions API while not attached to an Activity.");
        } else if (!(activity instanceof PermissionAwareActivity)) {
            throw new IllegalStateException("Tried to use permissions API but the host Activity doesn't implement PermissionAwareActivity.");
        }
        return (PermissionAwareActivity) activity;
    }

    private synchronized void requestPermissions(List<String> permissions) {

        /*
         * If permissions request dialog is displayed on the screen and another request is sent to the
         * activity, the last permission asked is skipped. As a work-around, we use pendingPermissions
         * to store next required permissions.
         */

        if (permissionsRequestShown) {
            pendingPermissions.addAll(permissions);
            return;
        }

        PermissionAwareActivity activity = getPermissionAwareActivity();
        permissionsRequestShown = true;

        activity.requestPermissions(
                permissions.toArray(new String[0]),
                COMMON_PERMISSION_REQUEST,
                webviewPermissionsListener
        );

        // Pending permissions have been sent, the list can be cleared
        pendingPermissions.clear();
    }


    private PermissionListener webviewPermissionsListener = (requestCode, permissions, grantResults) -> {

        permissionsRequestShown = false;

        /*
         * As a "pending requests" approach is used, requestCode cannot help to define if the request
         * came from geolocation or camera/audio. This is why shouldAnswerToPermissionRequest is used
         */
        boolean shouldAnswerToPermissionRequest = false;

        for (int i = 0; i < permissions.length; i++) {

            String permission = permissions[i];
            boolean granted = grantResults[i] == PackageManager.PERMISSION_GRANTED;

            if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                    && geolocationPermissionCallback != null
                    && geolocationPermissionOrigin != null) {

                if (granted) {
                    geolocationPermissionCallback.invoke(geolocationPermissionOrigin, true, false);
                } else {
                    geolocationPermissionCallback.invoke(geolocationPermissionOrigin, false, false);
                }

                geolocationPermissionCallback = null;
                geolocationPermissionOrigin = null;
            }

            if (permission.equals(Manifest.permission.RECORD_AUDIO)) {
                if (granted && grantedPermissions != null) {
                    grantedPermissions.add(PermissionRequest.RESOURCE_AUDIO_CAPTURE);
                }
                shouldAnswerToPermissionRequest = true;
            }

            if (permission.equals(Manifest.permission.CAMERA)) {
                if (granted && grantedPermissions != null) {
                    grantedPermissions.add(PermissionRequest.RESOURCE_VIDEO_CAPTURE);
                }
                shouldAnswerToPermissionRequest = true;
            }

            if (permission.equals(PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID)) {
                if (granted && grantedPermissions != null) {
                    grantedPermissions.add(PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID);
                }
                shouldAnswerToPermissionRequest = true;
            }
        }

        if (shouldAnswerToPermissionRequest
                && permissionRequest != null
                && grantedPermissions != null) {
            permissionRequest.grant(grantedPermissions.toArray(new String[0]));
            permissionRequest = null;
            grantedPermissions = null;
        }

        if (!pendingPermissions.isEmpty()) {
            requestPermissions(pendingPermissions);
            return false;
        }

        return true;
    };

    protected void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType) {
      this.mWebView.getThemedReactContext().getNativeModule(BPCWebViewModule.class).startPhotoPickerIntent(filePathCallback, acceptType);
    }

    protected void openFileChooser(ValueCallback<Uri> filePathCallback) {
      this.mWebView.getThemedReactContext().getNativeModule(BPCWebViewModule.class).startPhotoPickerIntent(filePathCallback, "");
    }

    protected void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
      this.mWebView.getThemedReactContext().getNativeModule(BPCWebViewModule.class).startPhotoPickerIntent(filePathCallback, acceptType);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        String[] acceptTypes = fileChooserParams.getAcceptTypes();
        boolean allowMultiple = fileChooserParams.getMode() == WebChromeClient.FileChooserParams.MODE_OPEN_MULTIPLE;

        return this.mWebView.getThemedReactContext().getNativeModule(BPCWebViewModule.class).startPhotoPickerIntent(filePathCallback, acceptTypes, allowMultiple, fileChooserParams.isCaptureEnabled());
    }

    @Override
    public void onHostResume() {
        if (mVideoView != null && mVideoView.getSystemUiVisibility() != FULLSCREEN_SYSTEM_UI_VISIBILITY) {
            mVideoView.setSystemUiVisibility(FULLSCREEN_SYSTEM_UI_VISIBILITY);
        }
    }

    @Override
    public void onHostPause() { }

    @Override
    public void onHostDestroy() { }

    protected ViewGroup getRootView() {
        return this.mWebView.getThemedReactContext().getCurrentActivity().findViewById(android.R.id.content);
    }

    public void setProgressChangedFilter(BPCWebView.ProgressChangedFilter filter) {
        progressChangedFilter = filter;
    }

    /**
     * Set whether or not protected media should be allowed
     * /!\ Setting this to false won't revoke permission already granted to the current webpage.
     * In order to do so, you'd need to reload the page /!\
     */
    public void setAllowsProtectedMedia(boolean enabled) {
      mAllowsProtectedMedia = enabled;
    }

    public void setHasOnOpenWindowEvent(boolean hasEvent) {
      mHasOnOpenWindowEvent = hasEvent;
    }
}