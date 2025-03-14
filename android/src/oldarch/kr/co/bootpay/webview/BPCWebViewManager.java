package kr.co.bootpay.webview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
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

import java.util.Map;

public class BPCWebViewManager extends ViewGroupManager<BPCWebViewWrapper> {

    private final BPCWebViewManagerImpl mBPCWebViewManagerImpl;

    public BPCWebViewManager() {
        mBPCWebViewManagerImpl = new BPCWebViewManagerImpl();
    }

    @Override
    public String getName() {
        return BPCWebViewManagerImpl.NAME;
    }

    @Override
    public BPCWebViewWrapper createViewInstance(ThemedReactContext context) {
        return mBPCWebViewManagerImpl.createViewInstance(context);
    }

    public BPCWebViewWrapper createViewInstance(ThemedReactContext context, BPCWebView view) {
      return mBPCWebViewManagerImpl.createViewInstance(context, view);
    }

    @ReactProp(name = "allowFileAccess")
    public void setAllowFileAccess(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setAllowFileAccess(view, value);
    }

    @ReactProp(name = "allowFileAccessFromFileURLs")
    public void setAllowFileAccessFromFileURLs(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setAllowFileAccessFromFileURLs(view, value);

    }

    @ReactProp(name = "allowUniversalAccessFromFileURLs")
    public void setAllowUniversalAccessFromFileURLs(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setAllowUniversalAccessFromFileURLs(view, value);
    }

    @ReactProp(name = "allowsFullscreenVideo")
    public void setAllowsFullscreenVideo(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setAllowsFullscreenVideo(view, value);
    }

    @ReactProp(name = "allowsProtectedMedia")
    public void setAllowsProtectedMedia(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setAllowsProtectedMedia(view, value);
    }

    @ReactProp(name = "androidLayerType")
    public void setAndroidLayerType(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setAndroidLayerType(view, value);
    }

    @ReactProp(name = "applicationNameForUserAgent")
    public void setApplicationNameForUserAgent(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setApplicationNameForUserAgent(view, value);
    }

    @ReactProp(name = "basicAuthCredential")
    public void setBasicAuthCredential(BPCWebViewWrapper view, @Nullable ReadableMap value) {
        mBPCWebViewManagerImpl.setBasicAuthCredential(view, value);
    }

    @ReactProp(name = "cacheEnabled")
    public void setCacheEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setCacheEnabled(view, value);
    }

    @ReactProp(name = "cacheMode")
    public void setCacheMode(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setCacheMode(view, value);
    }

    @ReactProp(name = "domStorageEnabled")
    public void setDomStorageEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setDomStorageEnabled(view, value);
    }

    @ReactProp(name = "downloadingMessage")
    public void setDownloadingMessage(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setDownloadingMessage(value);
    }

    @ReactProp(name = "forceDarkOn")
    public void setForceDarkOn(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setForceDarkOn(view, value);
    }

    @ReactProp(name = "geolocationEnabled")
    public void setGeolocationEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setGeolocationEnabled(view, value);
    }

    @ReactProp(name = "hasOnScroll")
    public void setHasOnScroll(BPCWebViewWrapper view, boolean hasScrollEvent) {
        mBPCWebViewManagerImpl.setHasOnScroll(view, hasScrollEvent);
    }

    @ReactProp(name = "incognito")
    public void setIncognito(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setIncognito(view, value);
    }

    @ReactProp(name = "injectedJavaScript")
    public void setInjectedJavaScript(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setInjectedJavaScript(view, value);
    }

    @ReactProp(name = "injectedJavaScriptBeforeContentLoaded")
    public void setInjectedJavaScriptBeforeContentLoaded(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptBeforeContentLoaded(view, value);
    }

    @ReactProp(name = "injectedJavaScriptForMainFrameOnly")
    public void setInjectedJavaScriptForMainFrameOnly(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptForMainFrameOnly(view, value);

    }

    @ReactProp(name = "injectedJavaScriptBeforeContentLoadedForMainFrameOnly")
    public void setInjectedJavaScriptBeforeContentLoadedForMainFrameOnly(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptBeforeContentLoadedForMainFrameOnly(view, value);

    }

    @ReactProp(name = "injectedJavaScriptObject")
    public void setInjectedJavaScriptObject(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setInjectedJavaScriptObject(view, value);
    }

    @ReactProp(name = "javaScriptCanOpenWindowsAutomatically")
    public void setJavaScriptCanOpenWindowsAutomatically(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setJavaScriptCanOpenWindowsAutomatically(view, value);
    }

    @ReactProp(name = "javaScriptEnabled")
    public void setJavaScriptEnabled(BPCWebViewWrapper view, boolean enabled) {
        mBPCWebViewManagerImpl.setJavaScriptEnabled(view, enabled);
    }

    @ReactProp(name = "lackPermissionToDownloadMessage")
    public void setLackPermissionToDownloadMessage(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setLackPermissionToDownloadMessage(value);
    }

    @ReactProp(name = "hasOnOpenWindowEvent")
    public void setHasOnOpenWindowEvent(BPCWebViewWrapper view, boolean hasEvent) {
        mBPCWebViewManagerImpl.setHasOnOpenWindowEvent(view, hasEvent);
    }

    @ReactProp(name = "mediaPlaybackRequiresUserAction")
    public void setMediaPlaybackRequiresUserAction(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setMediaPlaybackRequiresUserAction(view, value);
    }

    @ReactProp(name = "messagingEnabled")
    public void setMessagingEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setMessagingEnabled(view, value);
    }

    @ReactProp(name = "menuItems")
    public void setMenuCustomItems(BPCWebViewWrapper view, @Nullable ReadableArray items) {
        mBPCWebViewManagerImpl.setMenuCustomItems(view, items);
    }

    @ReactProp(name = "messagingModuleName")
    public void setMessagingModuleName(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setMessagingModuleName(view, value);
    }

    @ReactProp(name = "minimumFontSize")
    public void setMinimumFontSize(BPCWebViewWrapper view, int value) {
        mBPCWebViewManagerImpl.setMinimumFontSize(view, value);
    }

    @ReactProp(name = "mixedContentMode")
    public void setMixedContentMode(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setMixedContentMode(view, value);
    }

    @ReactProp(name = "nestedScrollEnabled")
    public void setNestedScrollEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setNestedScrollEnabled(view, value);
    }

    @ReactProp(name = "overScrollMode")
    public void setOverScrollMode(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setOverScrollMode(view, value);
    }

    @ReactProp(name = "saveFormDataDisabled")
    public void setSaveFormDataDisabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setSaveFormDataDisabled(view, value);
    }

    @ReactProp(name = "scalesPageToFit")
    public void setScalesPageToFit(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setScalesPageToFit(view, value);
    }

    @ReactProp(name = "setBuiltInZoomControls")
    public void setSetBuiltInZoomControls(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setSetBuiltInZoomControls(view, value);
    }

    @ReactProp(name = "setDisplayZoomControls")
    public void setSetDisplayZoomControls(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setSetDisplayZoomControls(view, value);
    }

    @ReactProp(name = "setSupportMultipleWindows")
    public void setSetSupportMultipleWindows(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setSetSupportMultipleWindows(view, value);
    }

    @ReactProp(name = "showsHorizontalScrollIndicator")
    public void setShowsHorizontalScrollIndicator(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setShowsHorizontalScrollIndicator(view, value);
    }

    @ReactProp(name = "showsVerticalScrollIndicator")
    public void setShowsVerticalScrollIndicator(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setShowsVerticalScrollIndicator(view, value);
    }

    @ReactProp(name = "source")
    public void setSource(BPCWebViewWrapper view, @Nullable ReadableMap value) {
        mBPCWebViewManagerImpl.setSource(view, value);
    }

    @ReactProp(name = "textZoom")
    public void setTextZoom(BPCWebViewWrapper view, int value) {
        mBPCWebViewManagerImpl.setTextZoom(view, value);
    }

    @ReactProp(name = "thirdPartyCookiesEnabled")
    public void setThirdPartyCookiesEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setThirdPartyCookiesEnabled(view, value);
    }

    @ReactProp(name = "webviewDebuggingEnabled")
    public void setWebviewDebuggingEnabled(BPCWebViewWrapper view, boolean value) {
        mBPCWebViewManagerImpl.setWebviewDebuggingEnabled(view, value);
    }

    @ReactProp(name = "userAgent")
    public void setUserAgent(BPCWebViewWrapper view, @Nullable String value) {
        mBPCWebViewManagerImpl.setUserAgent(view, value);
    }

    @Override
    protected void addEventEmitters(@NonNull ThemedReactContext reactContext, BPCWebViewWrapper viewWrapper) {
        // Do not register default touch emitter and let WebView implementation handle touches
        viewWrapper.getWebView().setWebViewClient(new BPCWebViewClient());
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
        mBPCWebViewManagerImpl.receiveCommand(reactWebView, commandId, args);
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