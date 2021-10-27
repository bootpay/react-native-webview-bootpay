# react-native-webview-bootpay 라이브러리 

react-native-webview를 부트페이가 Fork 떠서 만든 웹뷰입니다. 이미 결제모듈이 동작하는 웹사이트에 webview로 링크만 연결하여 사용하실 웹앱 flutter 개발자분께서는 해당 모듈의 웹뷰를 사용하시면 쉽게 결제 진행이 가능하십니다. 

## 1-1. 설치하기 

### npm으로 설치하기 
```sh
npm install react-native-webview-bootpay
```

### yarn으로 설치하기 
```sh
yarn add react-native-webview-bootpay
```

또는 package.json 파일의 dependencies에 추가 후 yarn install을 합니다.

```sh
"dependencies": {
    "react-native-webview-bootpay": last_version
 }

```

## 설정하기 

### Android
따로 설정하실 것이 없습니다. 

### iOS
** {your project root}/ios/Runner/Info.plist **
``CFBundleURLName``과 ``CFBundleURLSchemes``의 값은 개발사에서 고유값으로 지정해주셔야 합니다. 외부앱(카드사앱)에서 다시 기존 앱으로 앱투앱 호출시 필요한 스키마 값입니다. 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    ...

    <key>NSAppTransportSecurity</key>
    <dict>
        <key>NSAllowsArbitraryLoads</key>
        <true/>
    </dict>
    <key>CFBundleURLTypes</key>
    <array>
        <dict>
            <key>CFBundleTypeRole</key>
            <string>Editor</string>
            <key>CFBundleURLName</key>
            <string>kr.co.bootpaySample</string> 
            <key>CFBundleURLSchemes</key>
            <array>
                <string>bootpaySample</string> 
            </array>
        </dict>
    </array>

    ...
</dict>
</plist>
```

## 사용예제 

```dart 
import React, {Component} from 'react';
import {
  StyleSheet,
  SafeAreaView,
  Linking
} from 'react-native';

import WebView from 'react-native-webview-bootpay';

export default class App extends Component {
  render() {
    return (
      <SafeAreaView style={styles.container}>
        <WebView
          source={{uri: "https://www.yourdomain.com"}}
          automaticallyAdjustContentInsets={false}
         
        />
      </SafeAreaView>
    );
  }
}
  
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5FCFF',
    padding: 8,
  }
});

```

## onMessage 사용시 
```
window.BootpayRNWebView.postMessage("test data"); //부트페이 웹뷰 
```
사용 방법은 react-native-webview와 동일하며 javascript interface 이름이 ReactNativeWebView 가 아닌 BootpayRNWebView 값으로 보내주시면 되겠습니다.

## Documentation

[부트페이 개발매뉴얼](https://bootpay.gitbook.io/docs/)을 참조해주세요

## 기술문의

[채팅](https://bootpay.channel.io/)으로 문의

## License

[MIT License](https://opensource.org/licenses/MIT).



