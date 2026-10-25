# Calc TDD 作業繳交說明

- 學生：許家寧（315552051）
- Repo：https://github.com/ning415415/calc-tdd
- 語言／工具：Java 17、JUnit 4.13.2、Maven、GitHub Actions（JaCoCo、SpotBugs）

## 繳交項目與位置

| 繳交項目 | 位置 |
|---|---|
| Tests（測試） | `src/test/java/CalcTest.java`：14 個測試（Q1：subtract、multiply、divide 共 7 個；Q6：modulo、power 共 7 個）。Q7 的性質測試：`q7/CalcPropertyTest.java` |
| Final Calc（最終版 Calc） | `src/main/java/Calc.java`（add、subtract、multiply、divide、modulo、power），全文也在 `REPORT.pdf` 附錄 B |
| Git log | 根目錄 `git-log.txt`（完整），`REPORT.pdf` 附錄 C；也可在 GitHub 的 commit 歷史查看 |
| Screenshot: all tests pass（全部測試通過截圖） | `q6-evidence/screenshots/c7-green-all-14-pass.png`（Tests run: 14, Failures: 0, Errors: 0），也收錄在 `REPORT.pdf`；GitHub Actions 的 CI 綠燈截圖見 `REPORT.pdf` §3.2 |
| Narratives（敘述） | `REPORT.pdf`：Q1 每輪紅綠重構 §1.3；Q6 每輪紀錄與審查 §2.3、agent 違規紀錄 §2.4、agent 未經詢問的決定 §2.5；Q2 學生與 agent 修復比較 §3.2；Q3 §3.3；Q7 §4 |
| AI usage log（AI 使用紀錄） | `AI-usage-log.pdf`：與 Claude 的完整對話，逐字 prompt 與完整回應（Q1 僅說明指導、Q2、Q3、Q6、Q7）；Q7 全新 sub-agent 的逐字紀錄另見 `q7/agent-transcript.md` |

## 其他證據

- `q6-evidence/`：Q6 每輪紅燈和綠燈的 Maven 輸出（`cycleN-red.txt`、`cycleN-green.txt`）、agent 端測試紀錄（`agent-run-log.txt`）、每輪截圖（`screenshots/`）
- `q7/`：只看測試寫出的 Calc（`Calc-fresh-agent.java`）、刻意只對測試輸入有效的對照組（`Calc-overfit-strawman.java`）、三者測試結果（`q7-results.txt`）
- `.github/workflows/ci.yml`、`pom.xml`：CI 設定，JaCoCo 要求 100% 分支覆蓋率，SpotBugs 靜態分析
- GitHub Actions 執行紀錄：https://github.com/ning415415/calc-tdd/actions

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
