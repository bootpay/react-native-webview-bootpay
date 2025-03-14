package kr.co.bootpay.webview;

import android.app.DownloadManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import android.webkit.ValueCallback;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = BPCWebViewModuleImpl.NAME)
public class BPCWebViewModule extends ReactContextBaseJavaModule {
    final private BPCWebViewModuleImpl mBPCWebViewModuleImpl;

    public BPCWebViewModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mBPCWebViewModuleImpl = new BPCWebViewModuleImpl(reactContext);
    }

    @ReactMethod
    public void isFileUploadSupported(final Promise promise) {
        promise.resolve(mBPCWebViewModuleImpl.isFileUploadSupported());
    }

    @ReactMethod
    public void shouldStartLoadWithLockIdentifier(boolean shouldStart, double lockIdentifier) {
        mBPCWebViewModuleImpl.shouldStartLoadWithLockIdentifier(shouldStart, lockIdentifier);
    }

    public void startPhotoPickerIntent(ValueCallback<Uri> filePathCallback, String acceptType) {
        mBPCWebViewModuleImpl.startPhotoPickerIntent(acceptType, filePathCallback);
    }

    public boolean startPhotoPickerIntent(final ValueCallback<Uri[]> callback, final String[] acceptTypes, final boolean allowMultiple, final boolean isCaptureEnabled) {
        return mBPCWebViewModuleImpl.startPhotoPickerIntent(acceptTypes, allowMultiple, callback, isCaptureEnabled);
    }

    public void setDownloadRequest(DownloadManager.Request request) {
        mBPCWebViewModuleImpl.setDownloadRequest(request);
    }

    public void downloadFile(String downloadingMessage) {
        mBPCWebViewModuleImpl.downloadFile(downloadingMessage);
    }

    public boolean grantFileDownloaderPermissions(String downloadingMessage, String lackPermissionToDownloadMessage) {
        return mBPCWebViewModuleImpl.grantFileDownloaderPermissions(downloadingMessage, lackPermissionToDownloadMessage);
    }

    @NonNull
    @Override
    public String getName() {
        return BPCWebViewModuleImpl.NAME;
    }
}