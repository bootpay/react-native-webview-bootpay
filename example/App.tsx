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
          source={{uri: "http://d-cdn.bootapi.com/test/payment/"}}
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
