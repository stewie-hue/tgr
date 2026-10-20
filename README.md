# TGram Reborn — Module Integration Alpha

TGram Reborn 2.1.0-alpha1 combines the existing dark dashboard with a real classic-Xposed module entry point. It is intended for LSPosed installations that support the legacy Xposed API.

## What is implemented
- `assets/xposed_init` points to `dev.tgram.reborn.HookInit`.
- Android manifest includes Xposed module metadata.
- `HookInit` implements `IXposedHookLoadPackage` and logs when one of the listed Telegram-family clients loads.
- Client package names are based partly on strings observed in the supplied TGramHooks v1.3.5 APK; the list is not a guarantee of compatibility.
- Existing dashboard and local diagnostics are retained.

## Important limitations
This alpha is a **diagnostic-only integration**, not a full recreation of TGramHooks. It does not hook or change Telegram UI/behavior and does not report live activation inside the dashboard. Verify loading in LSPosed and inspect its logs. No APK was built or runtime-tested in this environment because Android Gradle/SDK tooling is unavailable here; use the GitHub Actions workflow to build it.

The original public repository identifies TGramHooks as MIT-licensed and credits Simo. This is an independent reconstruction; original hook implementation is not copied into this project. Credits to the original project and developer are retained in the app UI.

## Build from a phone using GitHub
1. Extract this ZIP.
2. Upload the **contents** of the `TGram-Reborn-Module` folder to the root of a GitHub repository.
3. Open **Actions** → **Build TGram Reborn APK** → **Run workflow**.
4. Download the `tgram-reborn-debug-apk` artifact after the workflow succeeds.
5. Install `app-debug.apk`, then enable TGram Reborn in LSPosed.
6. Select the Telegram-family app you want to test in the LSPosed scope, force-stop it, and relaunch it.
7. Check LSPosed logs for `[TGram Reborn] Module loaded for ...`.

## Next development stage
After verifying this diagnostic entry point loads correctly on the user's phone, add only explicitly supported, benign UI customizations and make dashboard diagnostics reflect verified status. Do not treat the existence of the entry point as proof that any functional hooks are implemented.
