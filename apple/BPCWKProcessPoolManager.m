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
}
@end

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
  dispatch_async(dispatch_get_main_queue(), ^{
    if (self->_prewarmedWebView == nil) {
      WKWebViewConfiguration *config = [[WKWebViewConfiguration alloc] init];
      config.processPool = [self sharedProcessPool];

      self->_prewarmedWebView = [[WKWebView alloc] initWithFrame:CGRectZero configuration:config];
      // 빈 HTML을 로드하여 WebContent 프로세스 초기화
      [self->_prewarmedWebView loadHTMLString:@"" baseURL:nil];
    }
  });
}

- (void)releaseWarmUp {
  dispatch_async(dispatch_get_main_queue(), ^{
    self->_prewarmedWebView = nil;
  });
}

@end

