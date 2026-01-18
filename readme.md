
--- a/README.md
+++ b/README.md
@@ -8,6 +8,33 @@
 
 # Karaoke BH Remote (Android)
 
+## 📦 Downloads
+
+### Debug (CI)
+- A cada **push/PR** em `main`, o workflow **Android CI (Debug)** é executado e publica o **APK de debug** nos **Artifacts** do run.
+- Como baixar:
+  1. Acesse a página do workflow clicando na badge acima **“Android CI (Debug)”**.
+  2. Entre no run mais recente (ou no run do seu commit).
+  3. Em **Artifacts**, baixe **`app-debug-apk`** (contém `app-debug.apk`).
+
+> Dica: se quiser apontar diretamente para um run específico, copie o link do run em **Actions** e cole aqui como atalho rápido.
+
+### Release (Tags `v*.*.*`)
+- Ao **criar uma tag** no formato `vX.Y.Z`, o workflow **Android CI (Release)** é disparado, gera o **APK de release** e o publica como **artifact** (e, se a etapa opcional estiver ativa, também anexa no **GitHub Releases**).
+- Como acionar:
+  ```bash
+  git tag v1.0.0
+  git push origin v1.0.0
+  ```
+- Como baixar:
+  1. Clique na badge **“Android CI (Release)”** acima para abrir o workflow.
+  2. Entre no run da **tag** que você criou e baixe o **artifact** `app-release.apk`.
+  3. (Opcional) Se o workflow estiver configurado para **GitHub Releases**, o APK também ficará disponível na aba **Releases** do repositório.
+
+> Segurança: para builds **Release** assinados, configure os **secrets** de assinatura em  
+> **Settings → Secrets and variables → Actions**:  
+> `SIGNING_KEYSTORE_BASE64`, `SIGNING_STORE_PASSWORD`, `SIGNING_KEY_ALIAS`, `SIGNING_KEY_PASSWORD`.
+
+
 ## Como gerar APK (CI)
 - Em cada push/PR para `main`, o Actions compila e publica `app-debug.apk` em **Artifacts**.
``
