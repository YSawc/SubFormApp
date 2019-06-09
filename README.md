# SNSアプリケーション
##### [play framework 2.5.10](https://www.playframework.com/documentation/2.5.x/Home) & java & scala
##### editor is IntelliJ

---

# 実施機能等

- ユーザー検索機能
- ユーザー自身のコメント非表示機能（非公開設定の場合、他人ユーザーから見られないように制御する機能）
- ログイン時、ログアウト時でのUIクリック時の制御
- 3つのデータベースと、外部キーによる関連付け
- ユーザー登録時のバリデーション制御（エラー時、フォーム入力欄の入力に応じた文面変化）
- ページング機能による利便性向上（ページが末端の場合、矢印ボタンのタグを制御）
- リンク書き込みによるURL文面の特殊措置（URLが書き込みされた際は、タグの変更を行う制御）
- 各ユーザーの書き込みが最終の書き込みの場合、最新の呟き欄とUIを変化させ、日時欄に「最新の呟き」と表示させる
- ページ内での表示呟き数が0の場合、ページングUIの文面が不自然になる為、自然になるように制御

---

* 現時点での課題点
    * CSRFによる攻撃(推測) ( 対策: Ajaxリクエストへのセキュリティトークンの埋め込み(推測) )
    * 多量の改行によるレイアウトの崩れ
    * 入力URLに日本語を含む場合、埋め込みリンクの崩れが生じる


