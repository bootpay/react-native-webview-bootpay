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
          source={{uri: "https://www.google.com"}}
          automaticallyAdjustContentInsets={false}
         
        />
      </SafeAreaView>
    );
  }
}
  
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#a5FCFF',
    padding: 8,
  }
});
