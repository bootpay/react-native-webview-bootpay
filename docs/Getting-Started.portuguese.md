# Guia de introdução ao React Native WebView

Veja como começar rapidamente com o React Native WebView.

## 1. Adicione react-native-webview-bootpay às suas dependências

```
$ yarn add react-native-webview-bootpay
```

(ou)

Para caso use o npm

```
$ npm install --save react-native-webview-bootpay
```

## 2. Vincular dependências nativas

A partir do react-native 0.60, o autolinking cuidará da etapa do link, mas não se esqueça de executar o `pod install`

Os módulos React Native que incluem código nativo Objective-C, Swift, Java ou Kotlin precisam ser "vinculados" para que o compilador saiba incluí-los no aplicativo.

```
$ react-native link react-native-webview-bootpay
```

_NOTA: Se você precisar desinstalar o React Native WebView, execute `react-native unlink react-native-webview-bootpay` para desvinculá-lo._

### iOS & macOS:

Se estiver usando CocoaPods, no diretório `ios/` ou `macos/` execute:

```
$ pod install
```
Embora você possa vincular manualmente da maneira antiga usando [tutorial do react-native](https://reactnative.dev/docs/linking-libraries-ios), achamos mais fácil usar o CocoaPods.
Se você deseja usar o CocoaPods e ainda não o configurou, consulte [esse artigo](https://engineering.brigad.co/demystifying-react-native-modules-linking-ae6c017a6b4a).


### Android:

Android - react-native-webview-bootpay versão < 6:
Este módulo não requer nenhuma etapa extra após executar o comando link 🎉

Android - react-native-webview-bootpay versão >=6.X.X:
Verifique se o AndroidX está ativado em seu projeto editando `android/gradle.properties` e adicionando 2 linhas:

```
android.useAndroidX=true
android.enableJetifier=true
```
Para instalação manual no Android, consulte [este artigo](https://engineering.brigad.co/demystifying-react-native-modules-linking-964399ec731b), onde você pode encontrar as etapas detalhadas sobre como vincular qualquer projeto react-native.

### Windows:

A vinculação automática ainda não é compatível com ReactNativeWindows. Faça as seguintes adições aos arquivos fornecidos manualmente:

#### **windows/myapp.sln**

Adicione ao projeto `ReactNativeWebView` e `WebViewBridgeComponent` à sua solução.

1. Abra a solução no Visual Studio 2019
2. Clique com o botão direito do mouse no ícone Soluções > Gerenciador de Soluções > Adicionar > Projeto Existente
   Selecione `node_modules\react-native-webview-bootpay\windows\ReactNativeWebView\ReactNativeWebView.vcxproj`
   Selecione `node_modules\react-native-webview-bootpay\windows\WebViewBridgeComponent\WebViewBridgeComponent.vcxproj`

#### **windows/myapp/myapp.vcxproj**

Adicione uma referência a `ReactNativeWebView` ao seu projeto de aplicativo principal. Do Visual Studio 2019:

1. Clique com o botão direito do mouse no projeto do aplicativo principal > Adicionar > Referências...
   Verifique se `ReactNativeWebView` em soluções de Projeto.

2. Modifique os arquivos abaixo para adicionar os provedores de pacotes ao seu projeto de aplicativo principal

#### **pch.h**

Adicione `#include "winrt/ReactNativeWebView.h"`.

#### **app.cpp**

Adicione `PackageProviders().Append(winrt::ReactNativeWebView::ReactPackageProvider());` antes de `InitializeComponent();`.

Observe que se você deseja habilitar a rolagem com o Touch para o componente WebView, você deve desabilitar a perspectiva do seu aplicativo usando [ReactRootView.IsPerspectiveEnabled](https://microsoft.github.io/react-native-windows/docs/ReactRootView#isperspectiveenabled).

## 3. Importe a visualização da web para seu componente

```js
import React, { Component } from 'react';
import { WebView } from 'react-native-webview-bootpay';

class MyWeb extends Component {
  render() {
    return (
      <WebView
        source={{ uri: 'https://infinite.red' }}
        style={{ marginTop: 20 }}
      />
    );
  }
}
```

Exemplo mínimo com HTML:

```js
import React, { Component } from 'react';
import { WebView } from 'react-native-webview-bootpay';

class MyInlineWeb extends Component {
  render() {
    return (
      <WebView
        originWhitelist={['*']}
        source={{ html: '<h1>Hello world</h1>' }}
      />
    );
  }
}
```

Em seguida, confira o [Referência da API](Reference.portuguese.md) ou o [Guia detalhado](Guide.portuguese.md).

## Traduções

Esse arquivo está disponível em:

- [Inglês](Getting-Started.md)
- [Italiano](Getting-Started.italian.md)
