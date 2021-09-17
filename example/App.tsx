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
          source={{uri: "https://camfit.co.kr/camp/6114cf52daa19a001e332f0b"}}
          automaticallyAdjustContentInsets={false}
          onShouldStartLoadWithRequest={request => {
            
            // console.log(request.url);
            // if(request.url == "https://campingagains3.s3.ap-northeast-2.amazonaws.com/_22d0bac3fd.jpeg") {
            //   console.log(12341234);
            //   Linking.openURL(request.url);
            // }
            if(!request.url.includes('camfit.co.kr')) {
              Linking.openURL(request.url);
              return false; 
            }

            // return onLoadWebViewOnClick(request)
            return true;
          }}
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
