package kr.co.bootpay.webview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.BPCWebViewManagerDelegate;
import com.facebook.react.viewmanagers.BPCWebViewManagerInterface;
import com.facebook.react.views.scroll.ScrollEventType;
import kr.co.bootpay.webview.events.TopCustomMenuSelectionEvent;
import kr.co.bootpay.webview.events.TopHttpErrorEvent;
import kr.co.bootpay.webview.events.TopLoadingErrorEvent;
import kr.co.bootpay.webview.events.TopLoadingFinishEvent;
import kr.co.bootpay.webview.events.TopLoadingProgressEvent;
import kr.co.bootpay.webview.events.TopLoadingStartEvent;
import kr.co.bootpay.webview.events.TopMessageEvent;
import kr.co.bootpay.webview.events.TopOpenWindowEvent;
import kr.co.bootpay.webview.events.TopRenderProcessGoneEvent;
import kr.co.bootpay.webview.events.TopShouldStartLoadWithRequestEvent;

import android.webkit.WebChromeClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

@ReactModule(name = BPCWebViewManagerImpl.NAME)
public class BPCWebViewManager extends ViewGroupManager<BPCWebViewWrapper>
        implements BPCWebViewManagerInterface<BPCWebViewWrapper> {

    private final ViewManagerDelegate<BPCWebViewWrapper> mDelegate;
    private final BPCWebViewManagerImpl mBPCWebViewManagerImpl;

    public BPCWebViewManager() {
        mDelegate = new BPCWebViewManagerDelegate<>(this);
        mBPCWebViewManagerImpl = new BPCWebViewManagerImpl(true);
    }

    @Nullable
    @Override
    protected ViewManagerDelegate<BPCWebViewWrapper> getDelegate() {
        return mDelegate;
    }

    @NonNull
    @Override
    public String getName() {
        return BPCWebViewManagerImpl.NAME;
    }

    @NonNull
    @Override
    protected BPCWebViewWrapper createViewInstance(@NonNull ThemedReactContext context) {
        return mBPCWebViewManagerImpl.createViewInstance(context);
    }

    @Override
    @ReactProp(name = "allowFileAccess")
    public void setAllowFileAccess(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setAllowFileAccess(view, value);
    }

    @Override
    @ReactProp(name = "allowFileAccessFromFileURLs")
    public void setAllowFileAccessFromFileURLs(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setAllowFileAccessFromFileURLs(view, value);

    }

    @Override
    @ReactProp(name = "allowUniversalAccessFromFileURLs")
    public void setAllowUniversalAccessFromFileURLs(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setAllowUniversalAccessFromFileURLs(view, value);
    }

    @Override
    @ReactProp(name = "allowsFullscreenVideo")
    public void setAllowsFullscreenVideo(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setAllowsFullscreenVideo(view, value);
    }

    @Override
    @ReactProp(name = "allowsProtectedMedia")
    public void setAllowsProtectedMedia(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setAllowsProtectedMedia(view, value);
    }

    @Override
    @ReactProp(name = "androidLayerType")
    public void setAndroidLayerType(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setAndroidLayerType(view, value);
    }

    @Override
    @ReactProp(name = "applicationNameForUserAgent")
    public void setApplicationNameForUserAgent(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setApplicationNameForUserAgent(view, value);
    }

    @Override
    @ReactProp(name = "basicAuthCredential")
    public void setBasicAuthCredential(BPCWebViewWrapper view, @Nullable ReadableMap value) {
        mBPCWebViewManagerImpl.setBasicAuthCredential(view, value);
    }

    @Override
    @ReactProp(name = "cacheEnabled")
    public void setCacheEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setCacheEnabled(view, value);
    }

    @Override
    @ReactProp(name = "cacheMode")
    public void setCacheMode(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setCacheMode(view, value);
    }

    @Override
    @ReactProp(name = "domStorageEnabled")
    public void setDomStorageEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setDomStorageEnabled(view, value);
    }

    @Override
    @ReactProp(name = "downloadingMessage")
    public void setDownloadingMessage(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setDownloadingMessage(value);
    }

    @Override
    @ReactProp(name = "forceDarkOn")
    public void setForceDarkOn(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setForceDarkOn(view, value);
    }

    @Override
    @ReactProp(name = "geolocationEnabled")
    public void setGeolocationEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setGeolocationEnabled(view, value);
    }

    @Override
    @ReactProp(name = "hasOnScroll")
    public void setHasOnScroll(BPCWebViewWrapper view, boolean hasScrollEvent) {
        mBPCWebViewManagerImpl.setHasOnScroll(view, hasScrollEvent);
    }

    @Override
    @ReactProp(name = "incognito")
    public void setIncognito(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setIncognito(view, value);
    }

    @Override
    @ReactProp(name = "injectedJavaScript")
    public void setInjectedJavaScript(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setInjectedJavaScript(view, value);
    }

    @Override
    @ReactProp(name = "injectedJavaScriptBeforeContentLoaded")
    public void setInjectedJavaScriptBeforeContentLoaded(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptBeforeContentLoaded(view, value);
    }

    @Override
    @ReactProp(name = "injectedJavaScriptForMainFrameOnly")
    public void setInjectedJavaScriptForMainFrameOnly(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptForMainFrameOnly(view, value);

    }

    @Override
    @ReactProp(name = "injectedJavaScriptBeforeContentLoadedForMainFrameOnly")
    public void setInjectedJavaScriptBeforeContentLoadedForMainFrameOnly(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptBeforeContentLoadedForMainFrameOnly(view, value);

    }

    @ReactProp(name = "injectedJavaScriptObject")
    public void setInjectedJavaScriptObject(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptObject(view, value);
    }

    @Override
    @ReactProp(name = "javaScriptCanOpenWindowsAutomatically")
    public void setJavaScriptCanOpenWindowsAutomatically(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setJavaScriptCanOpenWindowsAutomatically(view, value);
    }

    @ReactProp(name = "javaScriptEnabled")
    public void setJavaScriptEnabled(BPCWebViewWrapper view, boolean enabled) {
        mBPCWebViewManagerImpl.setJavaScriptEnabled(view, enabled);
    }

    @Override
    @ReactProp(name = "lackPermissionToDownloadMessage")
    public void setLackPermissionToDownloadMessage(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setLackPermissionToDownloadMessage(value);
    }

    @Override
    @ReactProp(name = "hasOnOpenWindowEvent")
    public void setHasOnOpenWindowEvent(BPCWebViewWrapper view, boolean hasEvent) {
        mBPCWebViewManagerImpl.setHasOnOpenWindowEvent(view, hasEvent);
    }

    @Override
    @ReactProp(name = "mediaPlaybackRequiresUserAction")
    public void setMediaPlaybackRequiresUserAction(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setMediaPlaybackRequiresUserAction(view, value);
    }

    @Override
    @ReactProp(name = "menuItems")
    public void setMenuItems(BPCWebViewWrapper view, @Nullable ReadableArray items) {
        mBPCWebViewManagerImpl.setMenuCustomItems(view, items);
    }

    @Override
    @ReactProp(name = "suppressMenuItems ")
    public void setSuppressMenuItems(BPCWebViewWrapper view, @Nullable ReadableArray items) {}

    @Override
    @ReactProp(name = "messagingEnabled")
    public void setMessagingEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setMessagingEnabled(view, value);
    }

    @Override
    @ReactProp(name = "messagingModuleName")
    public void setMessagingModuleName(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setMessagingModuleName(view, value);
    }

    @Override
    @ReactProp(name = "minimumFontSize")
    public void setMinimumFontSize(BPCWebViewWrapper view, int value) {
        mBPCWebViewManagerImpl.setMinimumFontSize(view, value);
    }

    @Override
    @ReactProp(name = "mixedContentMode")
    public void setMixedContentMode(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setMixedContentMode(view, value);
    }

    @Override
    @ReactProp(name = "nestedScrollEnabled")
    public void setNestedScrollEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setNestedScrollEnabled(view, value);
    }

    @Override
    @ReactProp(name = "overScrollMode")
    public void setOverScrollMode(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setOverScrollMode(view, value);
    }

    @Override
    @ReactProp(name = "saveFormDataDisabled")
    public void setSaveFormDataDisabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setSaveFormDataDisabled(view, value);
    }

    @Override
    @ReactProp(name = "scalesPageToFit")
    public void setScalesPageToFit(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setScalesPageToFit(view, value);
    }

    @Override
    @ReactProp(name = "setBuiltInZoomControls")
    public void setSetBuiltInZoomControls(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setSetBuiltInZoomControls(view, value);
    }

    @Override
    @ReactProp(name = "setDisplayZoomControls")
    public void setSetDisplayZoomControls(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setSetDisplayZoomControls(view, value);
    }

    @Override
    @ReactProp(name = "setSupportMultipleWindows")
    public void setSetSupportMultipleWindows(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setSetSupportMultipleWindows(view, value);
    }

    @Override
    @ReactProp(name = "showsHorizontalScrollIndicator")
    public void setShowsHorizontalScrollIndicator(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setShowsHorizontalScrollIndicator(view, value);
    }

    @Override
    @ReactProp(name = "showsVerticalScrollIndicator")
    public void setShowsVerticalScrollIndicator(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setShowsVerticalScrollIndicator(view, value);
    }

    @Override
    @ReactProp(name = "newSource")
    public void setNewSource(BPCWebViewWrapper view, @Nullable ReadableMap value) {
        mBPCWebViewManagerImpl.setSource(view, value);
    }

    @Override
    @ReactProp(name = "textZoom")
    public void setTextZoom(BPCWebViewWrapper view, int value) {
        mBPCWebViewManagerImpl.setTextZoom(view, value);
    }

    @Override
    @ReactProp(name = "thirdPartyCookiesEnabled")
    public void setThirdPartyCookiesEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setThirdPartyCookiesEnabled(view, value);
    }

    @Override
    @ReactProp(name = "webviewDebuggingEnabled")
    public void setWebviewDebuggingEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setWebviewDebuggingEnabled(view, value);
    }

    /* iOS PROPS - no implemented here */
    @Override
    public void setAllowingReadAccessToURL(BPCWebViewWrapper view, @Nullable String value) {}

    @Override
    public void setAllowsBackForwardNavigationGestures(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setAllowsInlineMediaPlayback(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setAllowsPictureInPictureMediaPlayback(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setAllowsAirPlayForMediaPlayback(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setAllowsLinkPreview(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setAutomaticallyAdjustContentInsets(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setAutoManageStatusBarEnabled(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setBounces(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setContentInset(BPCWebViewWrapper view, @Nullable ReadableMap value) {}

    @Override
    public void setContentInsetAdjustmentBehavior(BPCWebViewWrapper view, @Nullable String value) {}

    @Override
    public void setContentMode(BPCWebViewWrapper view, @Nullable String value) {}

    @Override
    public void setDataDetectorTypes(BPCWebViewWrapper view, @Nullable ReadableArray value) {}

    @Override
    public void setDecelerationRate(BPCWebViewWrapper view, double value) {}

    @Override
    public void setDirectionalLockEnabled(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setEnableApplePay(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setHideKeyboardAccessoryView(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setKeyboardDisplayRequiresUserAction(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setPagingEnabled(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setPullToRefreshEnabled(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setRefreshControlLightMode(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setScrollEnabled(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setSharedCookiesEnabled(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setUseSharedProcessPool(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setLimitsNavigationsToAppBoundDomains(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setTextInteractionEnabled(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setHasOnFileDownload(BPCWebViewWrapper view, boolean value) {}

    @Override
    public void setMediaCapturePermissionGrantType(BPCWebViewWrapper view, @Nullable String value) {}

    @Override
    public void setFraudulentWebsiteWarningEnabled(BPCWebViewWrapper view, boolean value) {}
    /* !iOS PROPS - no implemented here */

    @Override
    @ReactProp(name = "userAgent")
    public void setUserAgent(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setUserAgent(view, value);
    }

  @Override
  public void goBack(BPCWebViewWrapper view) {
    view.getWebView().goBack();
  }

  @Override
  public void goForward(BPCWebViewWrapper view) {
    view.getWebView().goForward();
  }

  @Override
  public void reload(BPCWebViewWrapper view) {
    view.getWebView().reload();
  }

  @Override
  public void stopLoading(BPCWebViewWrapper view) {
    view.getWebView().stopLoading();
  }

  @Override
  public void injectJavaScript(BPCWebViewWrapper view, String javascript) {
      view.getWebView().evaluateJavascriptWithFallback(javascript);
  }

  @Override
  public void requestFocus(BPCWebViewWrapper view) {
      view.requestFocus();
  }

  @Override
  public void postMessage(BPCWebViewWrapper view, String data) {
      try {
        JSONObject eventInitDict = new JSONObject();
        eventInitDict.put("data", data);
        view.getWebView().evaluateJavascriptWithFallback(
          "(function () {" +
            "var event;" +
            "var data = " + eventInitDict.toString() + ";" +
            "try {" +
            "event = new MessageEvent('message', data);" +
            "} catch (e) {" +
            "event = document.createEvent('MessageEvent');" +
            "event.initMessageEvent('message', true, true, data.data, data.origin, data.lastEventId, data.source);" +
            "}" +
            "document.dispatchEvent(event);" +
            "})();"
        );
      } catch (JSONException e) {
        throw  new RuntimeException(e);
      }
  }

  @Override
  public void loadUrl(BPCWebViewWrapper view, String url) {
      view.getWebView().loadUrl(url);
  }

  @Override
  public void clearFormData(BPCWebViewWrapper view) {
      view.getWebView().clearFormData();
  }

  @Override
  public void clearCache(BPCWebViewWrapper view, boolean includeDiskFiles) {
      view.getWebView().clearCache(includeDiskFiles);
  }

  @Override
  public void clearHistory(BPCWebViewWrapper view) {
      view.getWebView().clearHistory();
  }

  @Override
    protected void addEventEmitters(@NonNull ThemedReactContext reactContext, BPCWebViewWrapper view) {
        // Do not register default touch emitter and let WebView implementation handle touches
        view.getWebView().setWebViewClient(new BPCWebViewClient());
    }

    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        Map<String, Object> export = super.getExportedCustomDirectEventTypeConstants();
        if (export == null) {
            export = MapBuilder.newHashMap();
        }
        // Default events but adding them here explicitly for clarity
        export.put(TopLoadingStartEvent.EVENT_NAME, MapBuilder.of("registrationName", "onLoadingStart"));
        export.put(TopLoadingFinishEvent.EVENT_NAME, MapBuilder.of("registrationName", "onLoadingFinish"));
        export.put(TopLoadingErrorEvent.EVENT_NAME, MapBuilder.of("registrationName", "onLoadingError"));
        export.put(TopMessageEvent.EVENT_NAME, MapBuilder.of("registrationName", "onMessage"));
        // !Default events but adding them here explicitly for clarity

        export.put(TopLoadingProgressEvent.EVENT_NAME, MapBuilder.of("registrationName", "onLoadingProgress"));
        export.put(TopShouldStartLoadWithRequestEvent.EVENT_NAME, MapBuilder.of("registrationName", "onShouldStartLoadWithRequest"));
        export.put(ScrollEventType.getJSEventName(ScrollEventType.SCROLL), MapBuilder.of("registrationName", "onScroll"));
        export.put(TopHttpErrorEvent.EVENT_NAME, MapBuilder.of("registrationName", "onHttpError"));
        export.put(TopRenderProcessGoneEvent.EVENT_NAME, MapBuilder.of("registrationName", "onRenderProcessGone"));
        export.put(TopCustomMenuSelectionEvent.EVENT_NAME, MapBuilder.of("registrationName", "onCustomMenuSelection"));
        export.put(TopOpenWindowEvent.EVENT_NAME, MapBuilder.of("registrationName", "onOpenWindow"));
        return export;
    }

    @Override
    public @Nullable
    Map<String, Integer> getCommandsMap() {
        return mBPCWebViewManagerImpl.getCommandsMap();
    }

    @Override
    public void receiveCommand(@NonNull BPCWebViewWrapper reactWebView, String commandId, @Nullable ReadableArray args) {
        super.receiveCommand(reactWebView, commandId, args);
    }

    @Override
    protected void onAfterUpdateTransaction(@NonNull BPCWebViewWrapper view) {
        super.onAfterUpdateTransaction(view);
        mBPCWebViewManagerImpl.onAfterUpdateTransaction(view);
    }

    @Override
    public void onDropViewInstance(@NonNull BPCWebViewWrapper view) {
        mBPCWebViewManagerImpl.onDropViewInstance(view);
        super.onDropViewInstance(view);
    }
}