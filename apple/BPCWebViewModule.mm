#import "BPCWebViewModule.h"

#import "BPCWebViewDecisionManager.h"
#import "BPCWKProcessPoolManager.h"

#ifdef RCT_NEW_ARCH_ENABLED
#import <React/RCTFabricComponentsPlugins.h>
#endif /* RCT_NEW_ARCH_ENABLED */

@implementation BPCWebViewModule

RCT_EXPORT_MODULE(BPCWebViewModule)

// 모듈 로드 시 자동으로 WebView 프리워밍 시작
// +load 대신 constructor attribute 사용 (RCT_EXPORT_MODULE이 이미 +load를 정의하므로 충돌 방지)
__attribute__((constructor))
static void BPCWebViewModuleAutoWarmUp(void) {
    dispatch_async(dispatch_get_main_queue(), ^{
        [[BPCWKProcessPoolManager sharedManager] warmUp];
    });
}

RCT_EXPORT_METHOD(warmUp) {
    [[BPCWKProcessPoolManager sharedManager] warmUp];
}

RCT_EXPORT_METHOD(warmUpWithDelay:(double)delay) {
    [[BPCWKProcessPoolManager sharedManager] warmUpWithDelay:delay];
}

RCT_EXPORT_METHOD(releaseWarmUp) {
    [[BPCWKProcessPoolManager sharedManager] releaseWarmUp];
}

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
