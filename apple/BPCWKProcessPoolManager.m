/**
 * Copyright (c) 2015-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import <Foundation/Foundation.h>
#import "BPCWKProcessPoolManager.h"

@interface BPCWKProcessPoolManager() {
  WKProcessPool *_sharedProcessPool;
  WKWebView *_prewarmedWebView;
  BOOL _isWarmUpComplete;
}
@end

/// 프리워밍용 최소 HTML (GPU/WebContent/Networking 프로세스 초기화 트리거)
static NSString *const kWarmUpHTML = @"<!DOCTYPE html><html><head><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"></head><body><canvas id=\"c\" width=\"1\" height=\"1\"></canvas><script>var c=document.getElementById('c').getContext('2d');c.fillRect(0,0,1,1);fetch('https://webview.bootpay.co.kr/health',{mode:'no-cors'}).catch(function(){});</script></body></html>";

@implementation BPCWKProcessPoolManager

+ (id) sharedManager {
  static BPCWKProcessPoolManager *_sharedManager = nil;
  @synchronized(self) {
    if(_sharedManager == nil) {
      _sharedManager = [[super alloc] init];
    }
    return _sharedManager;
  }
}

- (WKProcessPool *)sharedProcessPool {
  if (!_sharedProcessPool) {
    _sharedProcessPool = [[WKProcessPool alloc] init];
  }
  return _sharedProcessPool;
}

- (void)warmUp {
  [self warmUpWithDelay:0.1];
}

- (void)warmUpWithDelay:(NSTimeInterval)delay {
  if (_prewarmedWebView != nil) return;

  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delay * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    if (self->_prewarmedWebView == nil) {
      WKWebViewConfiguration *config = [[WKWebViewConfiguration alloc] init];
      config.processPool = [self sharedProcessPool];

      // WebView 생성 - GPU/WebContent 프로세스 시작
      self->_prewarmedWebView = [[WKWebView alloc] initWithFrame:CGRectMake(0, 0, 1, 1) configuration:config];

      // 실제 렌더링 + 네트워크 요청으로 모든 프로세스 초기화
      [self->_prewarmedWebView loadHTMLString:kWarmUpHTML baseURL:[NSURL URLWithString:@"https://webview.bootpay.co.kr"]];

      self->_isWarmUpComplete = YES;

#ifdef DEBUG
      NSLog(@"[Bootpay] warmUp started - WebView processes initializing...");
#endif
    }
  });
}

- (void)releaseWarmUp {
  dispatch_async(dispatch_get_main_queue(), ^{
    self->_prewarmedWebView = nil;
    self->_isWarmUpComplete = NO;
  });
}

- (BOOL)isWarmedUp {
  return _isWarmUpComplete && _prewarmedWebView != nil;
}

@end

