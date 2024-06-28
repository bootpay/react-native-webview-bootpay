import React, { useRef }  from 'react';

import WebView from 'react-native-webview-bootpay';

import {
  SafeAreaView,
  StyleSheet,
  Dimensions, 
  Linking
} from 'react-native'; 



export default function App() {
  
  return (
    <SafeAreaView style={styles.container}>
      <WebView
        source={{
          uri: 'https://camfit.co.kr/products/63c4bd0f393fe0001e5b820d',
          // uri: 'https://dev-js.bootapi.com/test/payment/',
          // uri: 'https://dev-js.bootapi.com/test/payment/',
          
          // uri: 'https://d-cdn.bootapi.com/test/payment/',
          
        }}
        startInLoadingState={true}
        scalesPageToFit={true}
        style={styles.webview}
        onShouldStartLoadWithRequest={(event) => {

          console.log('event.url : ' + event.url);

          if(event.url.includes("smartstore.naver")){

            Linking.openURL(event.url);
          
            return false;
          
           }
          
           return true;
                                              
          }}


      /> 
    </SafeAreaView>
  );

}


const deviceHeight = Dimensions.get('window').height;
const deviceWidth = Dimensions.get('window').width;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },

  webview: {
      width: deviceWidth,
      height: deviceHeight
  },
  button: {
    alignItems: "center",
    backgroundColor: "#DDDDDD",
    padding: 10,
    margin: 10,
  },
}); 
 

 