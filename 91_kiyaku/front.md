# フロント規約




prettierの機能以外で気を付ける点
◆JavaScript
```

ES6以降の書き方でかけるものは、極力使用して記載する
    let, const ※基本constで。try/catchで変数参照できない場合やプリミティブではない変数が書き換わる可能性がある場合はlet
    アロー関数
    prototypeではなくclass
    関数のデフォルト引数
    配列／オブジェクトの分割代入
    テンプレートリテラル
    スプレッド構文、レストパラメータ（配列の結合）
    集合に対する演算方法
        map/find/filter/reduce/some/every/forEach など
        無理な場合はfor...of  ※for(let i=0;...)はほとんど見ない
    三項演算子 ※長くならないif/elseの場合のみ。複数の場合は素直にif/else if/else
    xxx || yyy, xxx && yyy
        条件付き演算子、jsx/tsxでもレンダー時に多用される
    null合体演算子（xxx ?? yyy） 
    オプショナルチェーン（xxx?.yyy）

定数について
    upper snake case
    一目で理解できない名前にはしないこと
    ※JavaScriptのconstは、厳密には定数ではないため、不変値であることを明示し意識させる目的

変数について
    lower camel case
    一目で理解できない名前にはしないこと

関数について
    lower camel case
    動詞から始める
    ただしイベントハンドラはハンドラであることを明示する handle+xxx or on+xxx
    定義はアロー関数を推奨

非同期処理について
    then より async/awaitで記載する








JHipsterに倣い、componentはdefault-export


```

◆JSX
```

hooksは、componentの最初に定義するよう努める


```