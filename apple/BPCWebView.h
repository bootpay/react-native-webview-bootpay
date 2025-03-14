// This guard prevent this file to be compiled in the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED
#import <React/RCTViewComponentView.h>
#import <React/RCTConversions.h>
#import <WebKit/WKDataDetectorTypes.h>

#if !TARGET_OS_OSX
#import <UIKit/UIKit.h>
#else
#import <React/RCTUIKit.h>
#endif // !TARGET_OS_OSX

#import <react/renderer/components/BPCWebViewSpec/Props.h>

#ifndef NativeComponentExampleComponentView_h
#define NativeComponentExampleComponentView_h

NS_ASSUME_NONNULL_BEGIN

@interface BPCWebView : RCTViewComponentView
@end

namespace facebook {
namespace react {
    bool operator==(const BPCWebViewMenuItemsStruct& a, const BPCWebViewMenuItemsStruct& b)
    {
        return b.key == a.key && b.label == a.label;
    }
}
}

NS_ASSUME_NONNULL_END

#endif /* NativeComponentExampleComponentView_h */
#endif /* RCT_NEW_ARCH_ENABLED */
