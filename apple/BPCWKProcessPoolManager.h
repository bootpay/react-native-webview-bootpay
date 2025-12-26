/**
 * Copyright (c) 2015-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import <WebKit/WebKit.h>

@interface BPCWKProcessPoolManager : NSObject

+ (instancetype) sharedManager;
- (WKProcessPool *)sharedProcessPool;

/// WebView 프로세스를 미리 초기화하여 첫 결제 화면 로딩 속도를 개선합니다.
/// AppDelegate의 didFinishLaunchingWithOptions 또는 적절한 시점에 호출하세요.
- (void)warmUp;

/// 프리워밍된 WebView 리소스를 해제합니다.
- (void)releaseWarmUp;

@end
