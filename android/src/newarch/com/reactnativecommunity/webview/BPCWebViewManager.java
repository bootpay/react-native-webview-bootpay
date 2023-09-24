package kr.co.bootpay.webview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
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
public class BPCWebViewManager extends SimpleViewManager<BPCWebView>
        implements BPCWebViewManagerInterface<BPCWebView> {

    private final ViewManagerDelegate<BPCWebView> mDelegate;
    private final BPCWebViewManagerImpl mBPCWebViewManagerImpl;

    public BPCWebViewManager() {
        mDelegate = new BPCWebViewManagerDelegate<>(this);
        mBPCWebViewManagerImpl = new BPCWebViewManagerImpl();
    }

    @Nullable
    @Override
    protected ViewManagerDelegate<BPCWebView> getDelegate() {
        return mDelegate;
    }

    @NonNull
    @Override
    public String getName() {
        return BPCWebViewManagerImpl.NAME;
    }

    @NonNull
    @Override
    protected BPCWebView createViewInstance(@NonNull ThemedReactContext context) {
        return mBPCWebViewManagerImpl.createViewInstance(context);
    }

    @Override
    @ReactProp(name = "allowFileAccess")
    public void setAllowFileAccess(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setAllowFileAccess(view, value);
    }

    @Override
    @ReactProp(name = "allowFileAccessFromFileURLs")
    public void setAllowFileAccessFromFileURLs(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setAllowFileAccessFromFileURLs(view, value);

    }

    @Override
    @ReactProp(name = "allowUniversalAccessFromFileURLs")
    public void setAllowUniversalAccessFromFileURLs(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setAllowUniversalAccessFromFileURLs(view, value);
    }

    @Override
    @ReactProp(name = "allowsFullscreenVideo")
    public void setAllowsFullscreenVideo(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setAllowsFullscreenVideo(view, value);
    }

    @Override
    @ReactProp(name = "allowsProtectedMedia")
    public void setAllowsProtectedMedia(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setAllowsProtectedMedia(view, value);
    }

    @Override
    @ReactProp(name = "androidLayerType")
    public void setAndroidLayerType(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setAndroidLayerType(view, value);
    }

    @Override
    @ReactProp(name = "applicationNameForUserAgent")
    public void setApplicationNameForUserAgent(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setApplicationNameForUserAgent(view, value);
    }

    @Override
    @ReactProp(name = "basicAuthCredential")
    public void setBasicAuthCredential(BPCWebView view, @Nullable ReadableMap value) {
        mBPCWebViewManagerImpl.setBasicAuthCredential(view, value);
    }

    @Override
    @ReactProp(name = "cacheEnabled")
    public void setCacheEnabled(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setCacheEnabled(view, value);
    }

    @Override
    @ReactProp(name = "cacheMode")
    public void setCacheMode(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setCacheMode(view, value);
    }

    @Override
    @ReactProp(name = "domStorageEnabled")
    public void setDomStorageEnabled(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setDomStorageEnabled(view, value);
    }

    @Override
    @ReactProp(name = "downloadingMessage")
    public void setDownloadingMessage(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setDownloadingMessage(value);
    }

    @Override
    @ReactProp(name = "forceDarkOn")
    public void setForceDarkOn(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setForceDarkOn(view, value);
    }

    @Override
    @ReactProp(name = "geolocationEnabled")
    public void setGeolocationEnabled(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setGeolocationEnabled(view, value);
    }

    @Override
    @ReactProp(name = "hasOnScroll")
    public void setHasOnScroll(BPCWebView view, boolean hasScrollEvent) {
        mBPCWebViewManagerImpl.setHasOnScroll(view, hasScrollEvent);
    }

    @Override
    @ReactProp(name = "incognito")
    public void setIncognito(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setIncognito(view, value);
    }

    @Override
    @ReactProp(name = "injectedJavaScript")
    public void setInjectedJavaScript(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setInjectedJavaScript(view, value);
    }

    @Override
    @ReactProp(name = "injectedJavaScriptBeforeContentLoaded")
    public void setInjectedJavaScriptBeforeContentLoaded(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptBeforeContentLoaded(view, value);
    }

    @Override
    @ReactProp(name = "injectedJavaScriptForMainFrameOnly")
    public void setInjectedJavaScriptForMainFrameOnly(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptForMainFrameOnly(view, value);

    }

    @Override
    @ReactProp(name = "injectedJavaScriptBeforeContentLoadedForMainFrameOnly")
    public void setInjectedJavaScriptBeforeContentLoadedForMainFrameOnly(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptBeforeContentLoadedForMainFrameOnly(view, value);

    }

    @ReactProp(name = "injectedJavaScriptObject")
    public void setInjectedJavaScriptObject(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptObject(view, value);
    }

    @Override
    @ReactProp(name = "javaScriptCanOpenWindowsAutomatically")
    public void setJavaScriptCanOpenWindowsAutomatically(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setJavaScriptCanOpenWindowsAutomatically(view, value);
    }

    @ReactProp(name = "javaScriptEnabled")
    public void setJavaScriptEnabled(BPCWebView view, boolean enabled) {
        mBPCWebViewManagerImpl.setJavaScriptEnabled(view, enabled);
    }

    @Override
    @ReactProp(name = "lackPermissionToDownloadMessage")
    public void setLackPermissionToDownloadMessage(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setLackPermissionToDownloadMessage(value);
    }

    @Override
    @ReactProp(name = "hasOnOpenWindowEvent")
    public void setHasOnOpenWindowEvent(BPCWebView view, boolean hasEvent) {
        mBPCWebViewManagerImpl.setHasOnOpenWindowEvent(view, hasEvent);
    }

    @Override
    @ReactProp(name = "mediaPlaybackRequiresUserAction")
    public void setMediaPlaybackRequiresUserAction(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setMediaPlaybackRequiresUserAction(view, value);
    }

    @Override
    @ReactProp(name = "menuItems")
    public void setMenuItems(BPCWebView view, @Nullable ReadableArray items) {
        mBPCWebViewManagerImpl.setMenuCustomItems(view, items);
    }

    @Override
    @ReactProp(name = "suppressMenuItems ")
    public void setSuppressMenuItems(BPCWebView view, @Nullable ReadableArray items) {}

    @Override
    @ReactProp(name = "messagingEnabled")
    public void setMessagingEnabled(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setMessagingEnabled(view, value);
    }

    @Override
    @ReactProp(name = "messagingModuleName")
    public void setMessagingModuleName(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setMessagingModuleName(view, value);
    }

    @Override
    @ReactProp(name = "minimumFontSize")
    public void setMinimumFontSize(BPCWebView view, int value) {
        mBPCWebViewManagerImpl.setMinimumFontSize(view, value);
    }

    @Override
    @ReactProp(name = "mixedContentMode")
    public void setMixedContentMode(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setMixedContentMode(view, value);
    }

    @Override
    @ReactProp(name = "nestedScrollEnabled")
    public void setNestedScrollEnabled(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setNestedScrollEnabled(view, value);
    }

    @Override
    @ReactProp(name = "overScrollMode")
    public void setOverScrollMode(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setOverScrollMode(view, value);
    }

    @Override
    @ReactProp(name = "saveFormDataDisabled")
    public void setSaveFormDataDisabled(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setSaveFormDataDisabled(view, value);
    }

    @Override
    @ReactProp(name = "scalesPageToFit")
    public void setScalesPageToFit(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setScalesPageToFit(view, value);
    }

    @Override
    @ReactProp(name = "setBuiltInZoomControls")
    public void setSetBuiltInZoomControls(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setSetBuiltInZoomControls(view, value);
    }

    @Override
    @ReactProp(name = "setDisplayZoomControls")
    public void setSetDisplayZoomControls(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setSetDisplayZoomControls(view, value);
    }

    @Override
    @ReactProp(name = "setSupportMultipleWindows")
    public void setSetSupportMultipleWindows(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setSetSupportMultipleWindows(view, value);
    }

    @Override
    @ReactProp(name = "showsHorizontalScrollIndicator")
    public void setShowsHorizontalScrollIndicator(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setShowsHorizontalScrollIndicator(view, value);
    }

    @Override
    @ReactProp(name = "showsVerticalScrollIndicator")
    public void setShowsVerticalScrollIndicator(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setShowsVerticalScrollIndicator(view, value);
    }

    @Override
    @ReactProp(name = "newSource")
    public void setNewSource(BPCWebView view, @Nullable ReadableMap value) {
        mBPCWebViewManagerImpl.setSource(view, value, true);
    }

    @Override
    @ReactProp(name = "textZoom")
    public void setTextZoom(BPCWebView view, int value) {
        mBPCWebViewManagerImpl.setTextZoom(view, value);
    }

    @Override
    @ReactProp(name = "thirdPartyCookiesEnabled")
    public void setThirdPartyCookiesEnabled(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setThirdPartyCookiesEnabled(view, value);
    }

    @Override
    @ReactProp(name = "webviewDebuggingEnabled")
    public void setWebviewDebuggingEnabled(BPCWebView view, boolean value) {
        mBPCWebViewManagerImpl.setWebviewDebuggingEnabled(view, value);
    }

    /* iOS PROPS - no implemented here */
    @Override
    public void setAllowingReadAccessToURL(BPCWebView view, @Nullable String value) {}

    @Override
    public void setAllowsBackForwardNavigationGestures(BPCWebView view, boolean value) {}

    @Override
    public void setAllowsInlineMediaPlayback(BPCWebView view, boolean value) {}

    @Override
    public void setAllowsAirPlayForMediaPlayback(BPCWebView view, boolean value) {}

    @Override
    public void setAllowsLinkPreview(BPCWebView view, boolean value) {}

    @Override
    public void setAutomaticallyAdjustContentInsets(BPCWebView view, boolean value) {}

    @Override
    public void setAutoManageStatusBarEnabled(BPCWebView view, boolean value) {}

    @Override
    public void setBounces(BPCWebView view, boolean value) {}

    @Override
    public void setContentInset(BPCWebView view, @Nullable ReadableMap value) {}

    @Override
    public void setContentInsetAdjustmentBehavior(BPCWebView view, @Nullable String value) {}

    @Override
    public void setContentMode(BPCWebView view, @Nullable String value) {}

    @Override
    public void setDataDetectorTypes(BPCWebView view, @Nullable ReadableArray value) {}

    @Override
    public void setDecelerationRate(BPCWebView view, double value) {}

    @Override
    public void setDirectionalLockEnabled(BPCWebView view, boolean value) {}

    @Override
    public void setEnableApplePay(BPCWebView view, boolean value) {}

    @Override
    public void setHideKeyboardAccessoryView(BPCWebView view, boolean value) {}

    @Override
    public void setKeyboardDisplayRequiresUserAction(BPCWebView view, boolean value) {}

    @Override
    public void setPagingEnabled(BPCWebView view, boolean value) {}

    @Override
    public void setPullToRefreshEnabled(BPCWebView view, boolean value) {}

    @Override
    public void setScrollEnabled(BPCWebView view, boolean value) {}

    @Override
    public void setSharedCookiesEnabled(BPCWebView view, boolean value) {}

    @Override
    public void setUseSharedProcessPool(BPCWebView view, boolean value) {}

    @Override
    public void setLimitsNavigationsToAppBoundDomains(BPCWebView view, boolean value) {}

    @Override
    public void setTextInteractionEnabled(BPCWebView view, boolean value) {}

    @Override
    public void setHasOnFileDownload(BPCWebView view, boolean value) {}

    @Override
    public void setMediaCapturePermissionGrantType(BPCWebView view, @Nullable String value) {}

    @Override
    public void setFraudulentWebsiteWarningEnabled(BPCWebView view, boolean value) {}
    /* !iOS PROPS - no implemented here */

    @Override
    @ReactProp(name = "userAgent")
    public void setUserAgent(BPCWebView view, @Nullable String value) {
        mBPCWebViewManagerImpl.setUserAgent(view, value);
    }

    // These will never be called because we use the shared impl for now
  @Override
  public void goBack(BPCWebView view) {
    view.goBack();
  }

  @Override
  public void goForward(BPCWebView view) {
    view.goForward();
  }

  @Override
  public void reload(BPCWebView view) {
    view.reload();
  }

  @Override
  public void stopLoading(BPCWebView view) {
    view.stopLoading();
  }

  @Override
  public void injectJavaScript(BPCWebView view, String javascript) {
      view.evaluateJavascriptWithFallback(javascript);
  }

  @Override
  public void requestFocus(BPCWebView view) {
      view.requestFocus();
  }

  @Override
  public void postMessage(BPCWebView view, String data) {
      try {
        JSONObject eventInitDict = new JSONObject();
        eventInitDict.put("data", data);
        view.evaluateJavascriptWithFallback(
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
  public void loadUrl(BPCWebView view, String url) {
      view.loadUrl(url);
  }

  @Override
  public void clearFormData(BPCWebView view) {
      view.clearFormData();
  }

  @Override
  public void clearCache(BPCWebView view, boolean includeDiskFiles) {
      view.clearCache(includeDiskFiles);
  }

  @Override
  public void clearHistory(BPCWebView view) {
      view.clearHistory();
  }
  // !These will never be called

  @Override
    protected void addEventEmitters(@NonNull ThemedReactContext reactContext, BPCWebView view) {
        // Do not register default touch emitter and let WebView implementation handle touches
        view.setWebViewClient(new BPCWebViewClient());
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
    public void receiveCommand(@NonNull BPCWebView reactWebView, String commandId, @Nullable ReadableArray args) {
        mBPCWebViewManagerImpl.receiveCommand(reactWebView, commandId, args);
        super.receiveCommand(reactWebView, commandId, args);
    }

    @Override
    public void onDropViewInstance(@NonNull BPCWebView view) {
        mBPCWebViewManagerImpl.onDropViewInstance(view);
        super.onDropViewInstance(view);
    }
}