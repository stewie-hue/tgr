package dev.tgram.reborn

import android.content.pm.PackageManager
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage

/** Minimal, real legacy-Xposed integration: confirms Telegram process loading and logs version. */
class HookInit : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "org.telegram.messenger") return
        val version = try {
            val info = lpparam.appInfo
            "targetSdk=${info?.targetSdkVersion ?: "unknown"}"
        } catch (_: Throwable) { "targetSdk=unknown" }
        XposedBridge.log("[TGram Reborn] Telegram process loaded; $version; diagnostic module active")
    }
}
