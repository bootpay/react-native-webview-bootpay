import React, { useCallback } from 'react';
import { SafeAreaView, StyleSheet, Dimensions, Linking } from 'react-native';
import { WebView } from 'react-native-webview-bootpay';


const { width: deviceWidth, height: deviceHeight } = Dimensions.get('window');

export default function App() {
  const handleError = useCallback((error) => {
    console.error('WebView Error:', error);
  }, []);

  const handleMessage = useCallback((message) => {
    console.log('WebView Message:', message.nativeEvent.data);
  }, []);

  const shouldStartLoadWithRequest = useCallback((event) => {
    console.log('Request URL:', event.url);

    if (event.url.includes("smartstore.naver")) {
      Linking.openURL(event.url);
      return false;
    }
    return true;
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <WebView
        source={{ uri: 'https://dev-js.bootapi.com/test/payment/' }}
        startInLoadingState
        scalesPageToFit
        style={styles.webview}
        onError={handleError}
        onMessage={handleMessage}
        onShouldStartLoadWithRequest={shouldStartLoadWithRequest}
      />
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
    height: deviceHeight,
  },
});
