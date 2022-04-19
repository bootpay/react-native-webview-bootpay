package kr.co.bootpay.webview;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.build.ReactBuildConfig;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.facebook.react.uimanager.ThemedReactContext;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.co.bootpay.webview.events.TopLoadingProgressEvent;

class BPCWebChromeClient extends WebChromeClient implements LifecycleEventListener {
  protected static final FrameLayout.LayoutParams FULLSCREEN_LAYOUT_PARAMS = new FrameLayout.LayoutParams(
    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  protected static final int FULLSCREEN_SYSTEM_UI_VISIBILITY = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
    View.SYSTEM_UI_FLAG_FULLSCREEN |
    View.SYSTEM_UI_FLAG_IMMERSIVE |
    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

  protected static final int COMMON_PERMISSION_REQUEST = 3;

  protected ReactContext mReactContext;
  protected View mWebView;

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

  public BPCWebChromeClient(ReactContext reactContext, WebView webView) {
    this.mReactContext = reactContext;
    this.mWebView = webView;
  }

//  Dialog popupDialog;
  @Override
  public void onCloseWindow(WebView window) {
    super.onCloseWindow(window);
    ((BPCWebView) window).dissmissDialog();
    window.setVisibility(View.GONE);
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
  public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {

    final BPCWebView newWebView = new BPCWebView((ThemedReactContext) view.getContext());
    BPCWebChromeClient client = new BPCWebChromeClient(this.mReactContext, view);
//    client.onCloseWindow();
    client.setProgressChangedFilter(progressChangedFilter);

    newWebView.setWebChromeClient(client);

    setWebSettingCopy(view, newWebView);

    newWebView.setWebViewClient(new BPCWebViewClient() {

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Intent intent = getIntentWithPackage(url);
        Context context = view.getContext();

        if(isIntent(url)) {
          if(isInstallApp(intent, context)) return startApp(intent, context);
          else return startGooglePlay(intent, context);
        } else if(isMarket(url)) {
          if(isInstallApp(intent, context)) return startApp(intent, context);
          else return startGooglePlay(intent, context);
        } else if(isSpecialCase(url)) {
          if(isInstallApp(intent, context)) return startApp(intent, context);
          else return startGooglePlay(intent, context);
        }
        return url.contains("vguardend");
      }
    });

    Dialog popupDialog = new Dialog(view.getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    popupDialog.setContentView(newWebView);
    ViewGroup.LayoutParams params = popupDialog.getWindow().getAttributes();
    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
    popupDialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
    popupDialog.setOnDismissListener(dialog -> {
      onCloseWindow(view);
//      Toast.makeText(view.getContext(), "ㅍㅏㅂ업취소", Toast.LENGTH_SHORT).show();
    });
    newWebView.setDialog(popupDialog);
    popupDialog.show();

    final WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
    transport.setWebView(newWebView);
    resultMsg.sendToTarget();

    return true;
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
    WritableMap event = Arguments.createMap();
    event.putDouble("target", webView.getId());
    event.putString("title", webView.getTitle());
    event.putString("url", url);
    event.putBoolean("canGoBack", webView.canGoBack());
    event.putBoolean("canGoForward", webView.canGoForward());
    event.putDouble("progress", (float) newProgress / 100);


    ((BPCWebView) webView).dispatchEvent(
      webView,
      new TopLoadingProgressEvent(
        webView.getId(),
        event));
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
        androidPermission = PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID;
      }
      // TODO: RESOURCE_MIDI_SYSEX, RESOURCE_PROTECTED_MEDIA_ID.

      if (androidPermission != null) {
        if (ContextCompat.checkSelfPermission(mReactContext, androidPermission) == PackageManager.PERMISSION_GRANTED) {
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

    if (ContextCompat.checkSelfPermission(mReactContext, Manifest.permission.ACCESS_FINE_LOCATION)
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
    Activity activity = mReactContext.getCurrentActivity();
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


  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
    BPCWebViewManager.getModule(mReactContext).startPhotoPickerIntent(filePathCallback, acceptType);
  }

  protected void openFileChooser(ValueCallback<Uri> filePathCallback) {
    BPCWebViewManager.getModule(mReactContext).startPhotoPickerIntent(filePathCallback, "");
  }

  protected void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
    BPCWebViewManager.getModule(mReactContext).startPhotoPickerIntent(filePathCallback, acceptType);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @Override
  public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
    String[] acceptTypes = fileChooserParams.getAcceptTypes();
    boolean allowMultiple = fileChooserParams.getMode() == WebChromeClient.FileChooserParams.MODE_OPEN_MULTIPLE;
    return BPCWebViewManager.getModule(mReactContext).startPhotoPickerIntent(filePathCallback, acceptTypes, allowMultiple);
  }

  @Override
  public void onHostResume() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mVideoView != null && mVideoView.getSystemUiVisibility() != FULLSCREEN_SYSTEM_UI_VISIBILITY) {
      mVideoView.setSystemUiVisibility(FULLSCREEN_SYSTEM_UI_VISIBILITY);
    }
  }

  @Override
  public void onHostPause() { }

  @Override
  public void onHostDestroy() { }

  protected ViewGroup getRootView() {
    return (ViewGroup) mReactContext.getCurrentActivity().findViewById(android.R.id.content);
  }

  public void setProgressChangedFilter(BPCWebView.ProgressChangedFilter filter) {
    progressChangedFilter = filter;
  }
}
