#ifndef BPCWebViewModule_h
#define BPCWebViewModule_h

#ifdef RCT_NEW_ARCH_ENABLED
#import "BPCWebViewSpec/BPCWebViewSpec.h"
#endif /* RCT_NEW_ARCH_ENABLED */

#import <React/RCTBridgeModule.h>

NS_ASSUME_NONNULL_BEGIN

@interface BPCWebViewModule : NSObject <
#ifdef RCT_NEW_ARCH_ENABLED
NativeBPCWebViewModuleSpec
#else
RCTBridgeModule
#endif /* RCT_NEW_ARCH_ENABLED */
>
@end

NS_ASSUME_NONNULL_END

#endif /* BPCWebViewModule_h */
