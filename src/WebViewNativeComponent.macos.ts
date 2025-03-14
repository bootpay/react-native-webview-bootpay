import { requireNativeComponent } from 'react-native';
import type { NativeWebViewMacOS } from './WebViewTypes';

const BPCWebView: typeof NativeWebViewMacOS =
  requireNativeComponent('BPCWebView');

export default BPCWebView;
