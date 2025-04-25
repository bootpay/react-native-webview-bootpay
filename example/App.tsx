import React, { useCallback, useRef } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  Dimensions,
  Linking,
  Button,
  View,
} from 'react-native';
import { WebView, WebViewMessageEvent } from 'react-native-webview-bootpay';

const { width: deviceWidth, height: deviceHeight } = Dimensions.get('window');

export default function App() {
  const webViewRef = useRef<WebView>(null);

  const handleError = useCallback((error) => {
    console.error('WebView Error:', error);
  }, []);

  const handleMessage = useCallback((message: WebViewMessageEvent) => {
    console.log('WebView Message:', message.nativeEvent.data);
    try {
      // 메시지 데이터 파싱
      const data = JSON.parse(message.nativeEvent.data);
      // 메시지 타입에 따른 처리
      if (data.type === 'webToApp') {
        // 웹에서 앱으로 보낸 메시지 처리
        alert(`웹에서 받은 메시지:\n${data.message}`);
      } else {
        // 기타 메시지 타입 처리
        console.log('메시지 타입:', data.type);
      }
    } catch (error) {
      console.error('메시지 파싱 에러:', error);
    }
  }, []);

  const shouldStartLoadWithRequest = useCallback((event) => {
    console.log('Request URL:', event.url);

    if (event.url.includes('smartstore.naver')) {
      Linking.openURL(event.url);
      return false;
    }
    return true;
  }, []);

  const sendMessageToWeb = useCallback(() => {
    console.log('Sending message to web...');
    if (webViewRef.current) {
      try {
        const message = JSON.stringify({
          type: 'greeting',
          message: '안녕하세요! 네이티브 앱에서 보낸 메시지입니다.',
        });
        console.log('Message content:', message);
        webViewRef.current.postMessage(message);
        console.log('Message sent successfully');
      } catch (error) {
        console.error('Error sending message:', error);
      }
    } else {
      console.error('WebView reference is not available');
    }
  }, []);

  // JS 주입: 웹에서 postMessage 처리하는 스크립트
  const injectedJavaScript = `
    (function() {
      // BootpayRNWebView 인터페이스 확인 및 설정
      window.BootpayRNWebView = window.BootpayRNWebView || {};
      
      // 디버깅용 로그
      console.log('BootpayRNWebView 인터페이스 존재 여부:', !!window.BootpayRNWebView);
      
      // 웹→앱 메시지 전송 함수 구현
      if (!window.BootpayRNWebView.postMessage) {
        window.BootpayRNWebView.postMessage = function(data) {
          console.log('웹에서 앱으로 메시지 전송:', data);
          window.postMessage(data, '*');
        };
      }

      // 웹 페이지에 메시지 전송 버튼 추가
      const createMessageButton = function() {
        const buttonContainer = document.createElement('div');
        buttonContainer.style.position = 'fixed';
        buttonContainer.style.bottom = '70px';
        buttonContainer.style.left = '50%';
        buttonContainer.style.transform = 'translateX(-50%)';
        buttonContainer.style.backgroundColor = '#007bff';
        buttonContainer.style.color = 'white';
        buttonContainer.style.padding = '10px 15px';
        buttonContainer.style.borderRadius = '5px';
        buttonContainer.style.zIndex = '9999';
        buttonContainer.style.cursor = 'pointer';
        buttonContainer.innerText = '앱으로 메시지 보내기';
        
        buttonContainer.addEventListener('click', function() {
          try {
            window.BootpayRNWebView.postMessage(JSON.stringify({
              type: 'webToApp',
              message: '웹에서 앱으로 보낸 메시지입니다!'
            }));
          } catch(e) {
            alert('메시지 전송 실패: ' + e.message);
          }
        });
        
        document.body.appendChild(buttonContainer);
      };
      
      // 앱→웹 메시지 수신 처리
      const messageHandler = function(event) {
        console.log('메시지 수신:', event.data);
        try {
          // 문자열이면 JSON 파싱
          let data = event.data;
          if (typeof data === 'string') {
            data = JSON.parse(data);
          }
          
          // 메시지 타입에 따른 처리
          if (data.type === 'greeting' || data.type === 'pageLoaded') {
            alert('앱에서 온 메시지: ' + data.message);
          } else if (data.what === 'clearSelection') {
            window.getSelection()?.removeAllRanges();
          }
        } catch(e) {
          console.error('메시지 처리 오류:', e);
        }
      };
      
      // 메시지 이벤트 리스너 등록
      window.addEventListener('message', messageHandler);
      document.addEventListener('message', messageHandler);
      
      // DOM이 로드된 후 버튼 생성
      if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', createMessageButton);
      } else {
        createMessageButton();
      }
      
      // 페이지 로드 완료 알림
      console.log('웹뷰 스크립트 초기화 완료');
      
      return true;
    })();
  `;

  return (
    <SafeAreaView style={styles.container}>
      <WebView
        ref={webViewRef}
        source={{ uri: 'https://dev-js.bootapi.com/test/payment/' }}
        startInLoadingState
        scalesPageToFit
        style={styles.webview}
        onError={handleError}
        onMessage={handleMessage} 
        onShouldStartLoadWithRequest={shouldStartLoadWithRequest}
        injectedJavaScript={injectedJavaScript}
      />
      <View style={styles.buttonContainer}>
        <Button title="웹으로 메시지 보내기" onPress={sendMessageToWeb} />
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  webview: {
    width: deviceWidth,
    height: deviceHeight - 50, // 버튼을 위한 공간 확보
  },
  buttonContainer: {
    position: 'absolute',
    bottom: 20,
    width: '90%',
  },
});
