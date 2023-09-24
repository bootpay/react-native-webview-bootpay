# React Native WebView - Um moderno, WebView multiplataforma para React Native

[![star this repo](http://githubbadges.com/star.svg?user=react-native-webview-bootpay&repo=react-native-webview-bootpay&style=flat)](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![All Contributors](https://img.shields.io/badge/all_contributors-16-orange.svg?style=flat-square)](#contributors)
[![Known Vulnerabilities](https://snyk.io/test/github/react-native-webview-bootpay/react-native-webview-bootpay/badge.svg?style=flat-square)](https://snyk.io/test/github/react-native-webview-bootpay/react-native-webview-bootpay)
<a href="https://www.npmjs.com/package/react-native-webview-bootpay"><img src="https://img.shields.io/npm/v/react-native-webview-bootpay.svg"></a>

**React Native WebView** é um moderno WebView multiplataforma para React Native. Ele foi projetado para substituir o WebView embutido(que será [removido do core](https://github.com/react-native-community/discussions-and-proposals/pull/3)).

## Principais Mantenedores - Empresas Patrocinadoras

_Esse projeto é mantido gratuitamente por essas pessoas usando ambos, seu tempo livre e tempo de trabalho na empresa._

- [Thibault Malbranche](https://github.com/Titozzz) ([Twitter @titozzz](https://twitter.com/titozzz)) from [Brigad](https://brigad.co/about)
- [Jamon Holmgren](https://github.com/jamonholmgren) ([Twitter @jamonholmgren](https://twitter.com/jamonholmgren)) from [Infinite Red](https://infinite.red/react-native)
- [Alexander Sklar](https://github.com/asklar) ([Twitter @alexsklar](https://twitter.com/alexsklar)) from [React Native Windows @ Microsoft](https://microsoft.github.io/react-native-windows/)
- [Chiara Mooney](https://github.com/chiaramooney) from [React Native Windows @ Microsoft](https://microsoft.github.io/react-native-windows/)

## Plataformas suportadas

- [x] iOS
- [x] Android
- [x] macOS
- [x] Windows

_Nota: O suporte da Expo para o React Native WebView começou com [Expo SDK v33.0.0](https://blog.expo.io/expo-sdk-v33-0-0-is-now-available-52d1c99dfe4c)._

## Começando

Leia nosso [Getting Started Guide](docs/Getting-Started.md). Se algum passo não ficou claro, por favor, crie um issue detalhado.

## Versionamento

Esse projeto segue [versionamento semântico](https://semver.org/). Não hesitamos em lançar as alterações mais recentes, mas elas estarão em uma versão principal.

**Histórico:**

Versão atual: ![version](https://img.shields.io/npm/v/react-native-webview-bootpay.svg)

- [11.0.0](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/releases/tag/v11.0.0) - Android setSupportMultipleWindows.
- [10.0.0](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/releases/tag/v10.0.0) - O plugin Android Gradle só é necessário ao abrir o projeto _stand-alone_.
- [9.0.0](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/releases/tag/v9.0.0) - Atualizações de _props_ para injectedJavaScript não são mais imutáveis.
- [8.0.0](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/releases/tag/v8.0.0) - onNavigationStateChange agora é disparado quando alterado o hash da URL.
- [7.0.1](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/releases/tag/v7.0.1) - UIWebView removido.
- [6.0.**2**](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/releases/tag/v6.0.2) - Update para AndroidX. Tenha certeza de habilitar no `android/gradle.properties` do seu projeto. Veja o [Getting Started Guide](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/blob/master/docs/Getting-Started.md).
- [5.0.**1**](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/releases/tag/v5.0.0) - Refatorou a antiga implementação postMessage para comunicação da visualização da webview para nativa.
- [4.0.0](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/releases/tag/v4.0.0) - Cache adicionada(habilitada por padrão).
- [3.0.0](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/releases/tag/v3.0.0) - WKWebview: Adicionado um pool de processos compartilhados para que os cookies e o localStorage sejam compartilhados nas webviews no iOS (habilitadas por padrão)
- [2.0.0](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/releases/tag/v2.0.0) - Primeiro lançamento, esta é uma réplica do componente principal do webview.

**Próximos Passos:**

- Remoção do this.webView.postMessage() (
  nunca foi documentado e é menos flexível que o injectJavascript) -> [Como migrar](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/issues/809)
- Reescrita em Kotlin
- Talvez reescrita em Swift

## Uso

Importe o componente `WebView` `react-native-webview-bootpay` e use assim:

```jsx
import React, { Component } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { WebView } from 'react-native-webview-bootpay';

// ...
class MyWebComponent extends Component {
  render() {
    return <WebView source={{ uri: 'https://reactnative.dev/' }} />;
  }
}
```

Para mais informações, leia a [API Reference](./docs/Reference.md) e o [Guia](./docs/Guide.md). Se você está interessado em contribuir, confira o [Guia de contribuição](./docs/Contributing.md).

## Problemas comuns

- Se você está recebendo `Invariant Violation: Native component for "BPCWebView does not exist"` provavelmente significa que você esqueceu de rodar `react-native link` ou houve algum erro no processo de linking.
- Se você encontrar um erro de compilação durante a tarefa: `app:mergeDexRelease`, será necessário habilitar o suporte multidex em `android/app/build.gradle` conforme discutido [neste problema](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/issues/1344#issuecomment-650544648)

## Contribuindo

Veja [Contributing.md](https://github.com/react-native-webview-bootpay/react-native-webview-bootpay/blob/master/docs/Contributing.md)

## Contribuidores

Um grande obrigado vai para essas pessoas maravilhosas ([emoji key](https://github.com/all-contributors/all-contributors#emoji-key-)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore -->
<table><tr><td align="center"><a href="https://twitter.com/titozzz"><img src="https://avatars1.githubusercontent.com/u/6181446?v=4" width="100px;" alt="Thibault Malbranche"/><br /><sub><b>Thibault Malbranche</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=titozzz" title="Code">💻</a> <a href="#ideas-titozzz" title="Ideas, Planning, & Feedback">🤔</a> <a href="#review-titozzz" title="Reviewed Pull Requests">👀</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=titozzz" title="Documentation">📖</a> <a href="#maintenance-titozzz" title="Maintenance">🚧</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=titozzz" title="Tests">⚠️</a> <a href="#infra-titozzz" title="Infrastructure (Hosting, Build-Tools, etc)">🚇</a> <a href="#question-titozzz" title="Answering Questions">💬</a></td><td align="center"><a href="https://jamonholmgren.com"><img src="https://avatars3.githubusercontent.com/u/1479215?v=4" width="100px;" alt="Jamon Holmgren"/><br /><sub><b>Jamon Holmgren</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=jamonholmgren" title="Code">💻</a> <a href="#ideas-jamonholmgren" title="Ideas, Planning, & Feedback">🤔</a> <a href="#review-jamonholmgren" title="Reviewed Pull Requests">👀</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=jamonholmgren" title="Documentation">📖</a> <a href="#maintenance-jamonholmgren" title="Maintenance">🚧</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=jamonholmgren" title="Tests">⚠️</a> <a href="#example-jamonholmgren" title="Examples">💡</a> <a href="#question-jamonholmgren" title="Answering Questions">💬</a></td><td align="center"><a href="https://github.com/andreipfeiffer"><img src="https://avatars1.githubusercontent.com/u/2570562?v=4" width="100px;" alt="Andrei Pfeiffer"/><br /><sub><b>Andrei Pfeiffer</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=andreipfeiffer" title="Code">💻</a> <a href="#review-andreipfeiffer" title="Reviewed Pull Requests">👀</a> <a href="#ideas-andreipfeiffer" title="Ideas, Planning, & Feedback">🤔</a></td><td align="center"><a href="https://twitter.com/mikediarmid"><img src="https://avatars0.githubusercontent.com/u/5347038?v=4" width="100px;" alt="Michael Diarmid"/><br /><sub><b>Michael Diarmid</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=Salakar" title="Code">💻</a> <a href="#review-Salakar" title="Reviewed Pull Requests">👀</a> <a href="#ideas-Salakar" title="Ideas, Planning, & Feedback">🤔</a> <a href="#tool-Salakar" title="Tools">🔧</a></td><td align="center"><a href="http://smathson.github.io"><img src="https://avatars3.githubusercontent.com/u/932981?v=4" width="100px;" alt="Scott Mathson"/><br /><sub><b>Scott Mathson</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=smathson" title="Code">💻</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=smathson" title="Documentation">📖</a></td><td align="center"><a href="https://github.com/YangXiaomei"><img src="https://avatars0.githubusercontent.com/u/8221990?v=4" width="100px;" alt="Margaret"/><br /><sub><b>Margaret</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=YangXiaomei" title="Code">💻</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=YangXiaomei" title="Documentation">📖</a></td><td align="center"><a href="https://stylisted.com"><img src="https://avatars2.githubusercontent.com/u/1173161?v=4" width="100px;" alt="Jordan Sexton"/><br /><sub><b>Jordan Sexton</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=jordansexton" title="Code">💻</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=jordansexton" title="Documentation">📖</a></td></tr><tr><td align="center"><a href="https://github.com/MalcolmScruggs"><img src="https://avatars1.githubusercontent.com/u/22333355?v=4" width="100px;" alt="Malcolm Scruggs"/><br /><sub><b>Malcolm Scruggs</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=MalcolmScruggs" title="Code">💻</a> <a href="#tool-MalcolmScruggs" title="Tools">🔧</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=MalcolmScruggs" title="Tests">⚠️</a></td><td align="center"><a href="https://github.com/Momazo7u7"><img src="https://avatars0.githubusercontent.com/u/42069617?v=4" width="100px;" alt="Momazo7u7"/><br /><sub><b>Momazo7u7</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=Momazo7u7" title="Documentation">📖</a></td><td align="center"><a href="https://marco-nett.de"><img src="https://avatars1.githubusercontent.com/u/3315507?v=4" width="100px;" alt="Marco"/><br /><sub><b>Marco</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=marconett" title="Documentation">📖</a></td><td align="center"><a href="https://github.com/jeluard"><img src="https://avatars1.githubusercontent.com/u/359723?v=4" width="100px;" alt="Julien Eluard"/><br /><sub><b>Julien Eluard</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=jeluard" title="Documentation">📖</a></td><td align="center"><a href="https://github.com/CubeSugar"><img src="https://avatars3.githubusercontent.com/u/3667305?v=4" width="100px;" alt="Jian Wei"/><br /><sub><b>Jian Wei</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=CubeSugar" title="Code">💻</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=CubeSugar" title="Documentation">📖</a></td><td align="center"><a href="https://www.linkedin.com/in/svbutko/"><img src="https://avatars2.githubusercontent.com/u/14828004?v=4" width="100px;" alt="Sergei Butko"/><br /><sub><b>Sergei Butko</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=svbutko" title="Documentation">📖</a></td><td align="center"><a href="https://github.com/TMomemt"><img src="https://avatars3.githubusercontent.com/u/42024947?v=4" width="100px;" alt="TMomemt"/><br /><sub><b>TMomemt</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=TMomemt" title="Code">💻</a></td></tr><tr><td align="center"><a href="http://www.try.com"><img src="https://avatars0.githubusercontent.com/u/674503?v=4" width="100px;" alt="Eric Lewis"/><br /><sub><b>Eric Lewis</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=ericlewis" title="Code">💻</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=ericlewis" title="Documentation">📖</a></td><td align="center"><a href="https://bzfx.net"><img src="https://avatars2.githubusercontent.com/u/1542454?v=4" width="100px;" alt="Daniel Vicory"/><br /><sub><b>Daniel Vicory</b></sub></a><br /><a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=dvicory" title="Code">💻</a> <a href="https://github.com/react-native-community/react-native-webview-bootpay/commits?author=dvicory" title="Documentation">📖</a></td></tr></table>

<!-- ALL-CONTRIBUTORS-LIST:END -->

Esse projeto segue a especificação [all-contributors](https://github.com/all-contributors/all-contributors). Contribuições de qualquer tipo são bem-vindas!

## Licença

MIT

## Traduções

Esse readme está disponível em:

- [Francês](README.french.md)
- [Inglês](../README.md)
- [Italiano](README.italian.md)
