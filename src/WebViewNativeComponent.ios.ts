import { requireNativeComponent } from "react-native";
import type { NativeWebViewIOS } from "./WebViewTypes";

const BPCWebView: typeof NativeWebViewIOS = requireNativeComponent(
  'BPCWebView',
);

export default BPCWebView;
