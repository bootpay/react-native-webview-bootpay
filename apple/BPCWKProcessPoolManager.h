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
/// @param delay 프리워밍 시작 전 대기 시간 (초). 기본값 0.1초. UI가 느려지면 0.5~1.0으로 늘려보세요.
- (void)warmUpWithDelay:(NSTimeInterval)delay;

/// 기본 딜레이(0.1초)로 프리워밍을 시작합니다.
- (void)warmUp;

/// 프리워밍된 WebView 리소스를 해제합니다.
- (void)releaseWarmUp;

/// 프리워밍 완료 여부를 확인합니다.
@property (nonatomic, readonly) BOOL isWarmedUp;

@end
