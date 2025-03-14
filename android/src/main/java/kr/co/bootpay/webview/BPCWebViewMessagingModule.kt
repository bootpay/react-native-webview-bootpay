package kr.co.bootpay.webview

import com.facebook.react.bridge.JavaScriptModule
import com.facebook.react.bridge.WritableMap

internal interface BPCWebViewMessagingModule : JavaScriptModule {
  fun onShouldStartLoadWithRequest(event: WritableMap)
  fun onMessage(event: WritableMap)
}
