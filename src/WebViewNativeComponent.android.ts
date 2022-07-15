import { requireNativeComponent } from "react-native";
import type { NativeWebViewAndroid } from "./WebViewTypes";

const BPCWebView: typeof NativeWebViewAndroid = requireNativeComponent(
  'BPCWebView',
);

export default BPCWebView;
