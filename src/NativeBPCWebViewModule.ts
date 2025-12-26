import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import { Double } from 'react-native/Libraries/Types/CodegenTypes';

export interface Spec extends TurboModule {
  isFileUploadSupported(): Promise<boolean>;
  shouldStartLoadWithLockIdentifier(
    shouldStart: boolean,
    lockIdentifier: Double
  ): void;
  /**
   * WebView 프로세스를 미리 초기화하여 첫 결제 화면 로딩 속도를 개선합니다.
   * 앱 시작 시 또는 결제 화면 진입 전에 호출하세요.
   */
  warmUp(): void;
  /**
   * 프리워밍된 WebView 리소스를 해제합니다.
   * 메모리가 부족할 때 호출할 수 있습니다.
   */
  releaseWarmUp(): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('BPCWebViewModule');
