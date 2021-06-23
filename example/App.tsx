import React, {Component} from 'react';
import {
  StyleSheet,
  SafeAreaView,
  Text,
  TouchableOpacity,
  View,
  Keyboard,
  Button,
  Platform,
} from 'react-native';

import WebView from 'react-native-webview-bootpay';

export default class App extends Component<Props, State> {
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
  },
  exampleContainer: {
    padding: 16,
    backgroundColor: '#FFF',
    borderColor: '#EEE',
    borderTopWidth: 1,
    borderBottomWidth: 1,
    flex: 1,
  },
  exampleTitle: {
    fontSize: 18,
  },
  exampleDescription: {
    color: '#333333',
    marginBottom: 16,
  },
  exampleInnerContainer: {
    borderColor: '#EEE',
    borderTopWidth: 1,
    paddingTop: 10,
    flex: 1,
  },
  restartButton: {
    padding: 6,
    fontSize: 16,
    borderRadius: 5,
    backgroundColor: '#F3F3F3',
    alignItems: 'center',
    justifyContent: 'center',
    alignSelf: 'flex-end',
  },
  closeKeyboardView: {
    width: 5,
    height: 5,
  },
  testPickerContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
});
