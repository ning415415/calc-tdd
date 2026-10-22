# calc-tdd

這是 Calc TDD 作業的起始 repo，Calc 只有 `add`，就是老師給的 Java 起始檔。

## 執行測試

```bash
mvn test        # 只跑測試
mvn verify      # 測試 + JaCoCo 覆蓋率（要求 100%）+ SpotBugs 靜態分析，與 CI 相同
```

覆蓋率報告：`target/site/jacoco/index.html`

## 第一次設定（請自己執行，commit 作者才會是你）

```bash
git init
git add .
git commit -m "chore: starter Calc (add only) + Maven/CI setup"
# 在 GitHub 建一個空 repo 後：
git remote add origin <你的 repo URL>
git push -u origin main
```

## 建議的 commit 慣例

每一輪 TDD 至少兩個 commit：

```
test: <行為描述>      ← 只動 src/test（紅燈）
feat: <行為描述>      ← 只動 src/main（綠燈）
refactor: <描述>      ← 選擇性；測試要保持綠燈
```

Q6 可以用以下指令證明 agent 在實作時沒有碰測試：

```bash
git show --stat <feat commit>      # 確認只改了 src/main
git log --oneline -- src/test      # 測試檔的修改歷史
```

## 注意

`mvn verify` 要求 100% 分支覆蓋率。Q1 中若加了沒被測到的分支，CI 會失敗，這是刻意設計的。
