# TGram Reborn — Alpha

An independent Android/legacy-Xposed module reconstruction. Original project credit: TGramHooks by Simo. Preserve applicable upstream notices/licensing when distributing derivative work.

## Included
- LSPosed/Xposed module metadata and `assets/xposed_init` entry point.
- `assets/xposed_scope` requests `org.telegram.messenger` as a target package.
- Dark scope-style app UI with search, requested-package indicators, diagnostics, and a locally saved preference.
- Minimal hook confirms when the Telegram process loads by writing a message to the Xposed log.
- GitHub Actions workflow builds a debug APK and uploads it as an artifact.

## Honest limitations
This alpha does **not** recreate the original TGramHooks feature set. Its working module function is Telegram process-load diagnostics. The app UI cannot change LSPosed's actual scope checkboxes; the module declares Telegram in `assets/xposed_scope`, and the user should verify/enable it in LSPosed Manager. The local verbose preference currently affects only app settings storage, not the Xposed logger. No Premium entitlement spoofing, screenshot restriction bypass, or protected-media extraction is implemented.

## Build
Use GitHub Actions → **Build TGram Reborn APK** → Run workflow. The workflow installs SDK packages through separate quoted `sdkmanager` arguments to avoid the previous malformed combined-package error. Download `tgram-reborn-debug-apk` from the completed workflow run.

## Scope
Requested target: `org.telegram.messenger` (official Telegram Android package). LSPosed remains the source of truth for whether the module is enabled for that package.
