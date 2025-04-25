#import "BPCWebViewModule.h"

#import "BPCWebViewDecisionManager.h"

#ifdef RCT_NEW_ARCH_ENABLED
#import <React/RCTFabricComponentsPlugins.h>
#endif /* RCT_NEW_ARCH_ENABLED */

@implementation BPCWebViewModule

RCT_EXPORT_MODULE(BPCWebViewModule)

RCT_EXPORT_METHOD(isFileUploadSupported:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject) {
    if (resolve) {
        resolve(@(YES));
    }
}

RCT_EXPORT_METHOD(shouldStartLoadWithLockIdentifier:(BOOL)shouldStart lockIdentifier:(double)lockIdentifier)
{
    [[BPCWebViewDecisionManager getInstance] setResult:shouldStart forLockIdentifier:(int)lockIdentifier];
}

#ifdef RCT_NEW_ARCH_ENABLED
- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:(const facebook::react::ObjCTurboModule::InitParams &)params {
  return std::make_shared<facebook::react::NativeBPCWebViewModuleSpecJSI>(params);
}
#endif /* RCT_NEW_ARCH_ENABLED */

Class BPCWebViewModuleCls(void) {
  return BPCWebViewModule.class;
}

@end
