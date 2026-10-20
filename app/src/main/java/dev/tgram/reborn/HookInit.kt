package dev.tgram.reborn

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Real legacy-Xposed entry point. This first alpha intentionally performs diagnostics only:
 * it does not alter Telegram behavior, paid entitlements, protected media, or privacy controls.
 */
class HookInit : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (!SUPPORTED_CLIENTS.contains(lpparam.packageName)) return

        XposedBridge.log("[TGram Reborn] Module loaded for ${lpparam.packageName}; version=${lpparam.appInfo?.targetSdkVersion ?: "unknown"}; diagnostic-only build 2.1.0-alpha1")
    }

    companion object {
        private val SUPPORTED_CLIENTS = setOf(
            "org.telegram.messenger",
            "org.telegram.messenger.beta",
            "org.telegram.messenger.web",
            "org.telegram.plus",
            "org.telegram.group",
            "org.telegram.mdgram",
            "com.xplus.messenger",
            "ir.ilmili.telegraph",
            "tw.nekomimi.nekogram",
            "nekox.messenger",
            "xyz.nextalone.nagram",
            "me.ninjagram.messenger",
            "me.ritmgram.messenger",
            "org.forkclient.messenger.beta",
            "org.forkgram.messenger",
            "com.exteragram.messenger",
            "ellipi.messenger"
        )
    }
}
