# TGram Reborn — reconstruction starter

An independently branded Android dashboard prototype created after inspection of the user-supplied `TGramHooks_v1.3.5.apk`.

## Current scope
- Native Kotlin Android UI
- Compatibility information panel
- Diagnostics panel
- Preferences feedback
- GitHub Actions workflow to build a debug APK

## Important limitations
This is a reconstructed UI starter, not a decompiled or functionally equivalent replacement for the original APK. The original compiled Xposed hooks have not been recreated, and this app does not modify Telegram or unlock paid features. The original APK contains an `assets/xposed_init` entry referring to `com.simo.tgramhooks.HookInit`; that class is not implemented here.

## Attribution
The supplied APK references the original author/project as Simo / TGramHooks. This reconstruction is a separate UI implementation and is not presented as the original project. Before redistributing derived resources or code, review the original project's license and preserve any attribution and notices it requires. No original images, icons, or source files have been copied into this starter.

## Build
Open this repository on GitHub, then use **Actions → Build TGram Reborn APK → Run workflow**. Download the `tgram-reborn-debug-apk` artifact from the completed run.
