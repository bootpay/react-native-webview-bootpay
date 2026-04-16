### 13.14.2
- Android `<queries>` 패키지 목록 보강 (Android 11+ package visibility)
  - 삼성 모니모(`net.ib.android.smcard`), 카카오뱅크(`com.kakaobank.channel`) 누락으로 인해
    나이스페이 삼성카드→모니모 선택 무반응 / 카카오뱅크 열기 시 다운로드 링크로만 이동하는 이슈 수정
  - 추가 패키지: 신한 SOL 뱅크(`com.shinhan.sbanking`), 안심클릭 백신(`net.nshc.droidxantivirus`),
    KCB(SKT, `com.om.sktelecom.tauth`), 티머니(`com.tmoney.inapp`, `com.tmoney.nfc_pay`),
    캐시비(`com.ebcard.cashbeeinapp`), 원스토어(`com.skt.skaf.A000Z00040`)

### 13.14.1
- React Native New Architecture (Fabric) 웹뷰 흰 화면 수정
  - codegenConfig에 ios.componentProvider 명시적 추가
  - RN codegen이 패키지 경로의 'react-native' 문자열을 필터링하여 BPCWebView Fabric 컴포넌트 등록이 누락되는 버그 우회

### 13.14.0
- iOS 빌드 에러 수정: RCT_EXPORT_MODULE과 +load 메서드 중복 선언 충돌 해결
  - +load 대신 __attribute__((constructor))로 변경하여 프리워밍 타이밍 유지
  - React Native 0.76+ (Expo 54) 환경에서 발생하는 duplicate declaration of method 'load' 에러 해결

### 13.13.49
- iOS warmUp 기능 개선
  - warmUpWithDelay 메서드 추가 (지연 시간 설정 가능)
  - GPU, WebContent, Networking 프로세스 완전 초기화
  - 최소 HTML 로드로 Canvas 렌더링 및 네트워크 요청 수행

### 13.13.48
- Android postMessage를 메인 UI 스레드에서 호출하도록 수정
- JavaScript 객체 주입 시 기존 postMessage 함수 보존

### 13.13.47
- BPCWebChromeClient onProgressChanged NullPointerException 수정
- progressChangedFilter, mWebView, ThemedReactContext null 체크 추가
- EventDispatcher 호출 시 try-catch 예외 처리 추가

### 13.13.45
- WebView version update

### 13.13.4
- 13.13.4 version forked

### 13.8.42
- android 설치된 app 못찾는 문제 해결 

### 13.8.41
- ios compile error bug fixed

### 13.8.4
- ssl error 발생시 안드로이드는 소프트업데이트로 안내

### 13.8.3
- ssl error event send

### 13.8.2
- webview version update
- npm install, react-native run-android 

### 13.6.13
- import error fixed

### 13.6.1
- webview 13.6.1 fork