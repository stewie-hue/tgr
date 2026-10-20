package dev.tgram.reborn

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {
    private val bg = Color.rgb(13,14,24)
    private val panel = Color.rgb(25,27,43)
    private val purple = Color.rgb(153,105,255)
    private val muted = Color.rgb(164,168,190)
    private val white = Color.rgb(246,246,255)
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); dashboard() }
    private fun dashboard() {
        val page = LinearLayout(this).apply { orientation=1; setBackgroundColor(bg); setPadding(dp(20),dp(22),dp(20),dp(16)) }
        val scroll=ScrollView(this); val body=LinearLayout(this).apply { orientation=1 }; scroll.addView(body); page.addView(scroll,LinearLayout.LayoutParams(-1,0,1f))
        val head=LinearLayout(this).apply { orientation=0; gravity=Gravity.CENTER_VERTICAL }
        head.addView(TextView(this).apply { text="TR"; textSize=19f; typeface=Typeface.DEFAULT_BOLD; gravity=Gravity.CENTER; setTextColor(white); background=rounded(purple,16); layoutParams=LinearLayout.LayoutParams(dp(48),dp(48)) })
        val title=LinearLayout(this).apply { orientation=1; setPadding(dp(12),0,0,0) }; title.addView(txt("TGram Reborn",22f,white,true)); title.addView(txt("CONTROL CENTER  •  v2.0.0",10f,muted,true)); head.addView(title,LinearLayout.LayoutParams(0,-2,1f)); head.addView(txt("BETA",11f,white,true).apply { background=rounded(Color.rgb(70,48,115),20); setPadding(dp(12),dp(7),dp(12),dp(7)) }); body.addView(head)
        gap(body,25); body.addView(txt("Welcome back.",28f,white,true)); body.addView(txt("Your Telegram customization workspace.",14f,muted)); gap(body,18)
        card(body) { addView(txt("MODULE STATUS",11f,muted,true)); gap(this,10); addView(txt("🟠  Module connection not verified",16f,white,true)); gap(this,5); addView(txt("Open your module manager to confirm activation.",12f,muted)); gap(this,10); addView(txt("This dashboard cannot verify hook activation yet.",12f,muted)) }
        gap(body,15); body.addView(txt("QUICK ACCESS",12f,muted,true)); gap(body,9)
        val row=LinearLayout(this).apply { orientation=0 }; row.addView(tile("⚙","Settings","Preferences") { settings() },LinearLayout.LayoutParams(0,dp(112),1f)); row.addView(View(this),LinearLayout.LayoutParams(dp(12),1)); row.addView(tile("⌁","Diagnostics","Run local checks") { diagnostics() },LinearLayout.LayoutParams(0,dp(112),1f)); body.addView(row)
        gap(body,14); card(body) { addView(txt("COMPATIBILITY",11f,muted,true)); gap(this,8); addView(txt("Telegram version · Not detected",15f,white,true)); gap(this,5); addView(txt("Android API · ${android.os.Build.VERSION.SDK_INT}",13f,muted)); gap(this,5); addView(txt("Hook framework · Check manually",13f,muted)); gap(this,10); addView(txt("Compatibility remains informational until the original hook engine is integrated.",12f,muted)) }
        gap(body,14); card(body) { addView(txt("PROJECT CREDITS",11f,muted,true)); gap(this,8); addView(txt("Original project: TGramHooks",15f,white,true)); addView(txt("Original developer credit: Simo",13f,muted)); gap(this,8); addView(txt("Independent UI reconstruction. Original hook functionality is not included.",12f,muted)) }
        val nav=LinearLayout(this).apply { orientation=0; gravity=Gravity.CENTER; setPadding(0,dp(10),0,0) }
        listOf("Home" to { dashboard() },"Diagnostics" to { diagnostics() },"Settings" to { settings() }).forEach { (name,fn) -> nav.addView(Button(this).apply { text=name; setTextColor(white); backgroundTintList=android.content.res.ColorStateList.valueOf(panel); setOnClickListener { fn() } },LinearLayout.LayoutParams(0,dp(48),1f).apply { marginStart=dp(3); marginEnd=dp(3) }) }; page.addView(nav); setContentView(page)
    }
    private fun diagnostics() = subpage("Diagnostics","Local device checks",listOf("Android" to "${android.os.Build.VERSION.RELEASE} · API ${android.os.Build.VERSION.SDK_INT}","Device" to "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}","App build" to "TGram Reborn 2.0.0","Module status" to "Not verified by this dashboard","Hook integration" to "Not included in this prototype"),"Run checks") { Toast.makeText(this,"Local checks complete. Verify hook status manually.",Toast.LENGTH_LONG).show() }
    private fun settings() = subpage("Settings","Personalize your workspace",listOf("Appearance" to "Dark purple theme","Notifications" to "No notifications requested","Privacy" to "No account login or network permission","Diagnostics" to "Available locally","Credits" to "Simo · TGramHooks"),"Reset preferences") { Toast.makeText(this,"No saved preferences to reset yet.",Toast.LENGTH_SHORT).show() }
    private fun subpage(title:String,sub:String,rows:List<Pair<String,String>>,button:String,action:()->Unit) {
        val p=LinearLayout(this).apply { orientation=1; setBackgroundColor(bg); setPadding(dp(20),dp(24),dp(20),dp(20)) }; val s=ScrollView(this); val b=LinearLayout(this).apply { orientation=1 }; s.addView(b); p.addView(s,LinearLayout.LayoutParams(-1,0,1f)); b.addView(txt("TGram Reborn",13f,purple,true)); gap(b,20); b.addView(txt(title,29f,white,true)); b.addView(txt(sub,14f,muted)); gap(b,20)
        rows.forEach { (h,v)-> card(b) { addView(txt(h.uppercase(),10f,muted,true)); gap(this,7); addView(txt(v,15f,white)) }; gap(b,10) }
        val btn=Button(this).apply { text=button; setTextColor(white); backgroundTintList=android.content.res.ColorStateList.valueOf(purple); setOnClickListener { action() } }; b.addView(btn,LinearLayout.LayoutParams(-1,dp(52))); val back=Button(this).apply { text="← Back to dashboard"; setTextColor(white); backgroundTintList=android.content.res.ColorStateList.valueOf(panel); setOnClickListener { dashboard() } }; p.addView(back,LinearLayout.LayoutParams(-1,dp(48))); setContentView(p)
    }
    private fun tile(icon:String,title:String,sub:String,click:()->Unit):View = LinearLayout(this).apply { orientation=1; setPadding(dp(14),dp(14),dp(12),dp(12)); background=rounded(panel,18); isClickable=true; isFocusable=true; setOnClickListener { click() }; addView(txt(icon,22f,purple,true)); gap(this,7); addView(txt(title,16f,white,true)); addView(txt(sub,12f,muted)) }
    private fun card(parent:LinearLayout,build:LinearLayout.()->Unit) { parent.addView(LinearLayout(this).apply { orientation=1; setPadding(dp(16),dp(16),dp(16),dp(16)); background=rounded(panel,18); build() },LinearLayout.LayoutParams(-1,-2)) }
    private fun txt(s:String,size:Float,color:Int,bold:Boolean=false)=TextView(this).apply { text=s; textSize=size; setTextColor(color); if(bold) typeface=Typeface.DEFAULT_BOLD; setLineSpacing(dp(2).toFloat(),1f) }
    private fun gap(parent:LinearLayout,h:Int) { parent.addView(View(this),LinearLayout.LayoutParams(1,dp(h))) }
    private fun rounded(color:Int,r:Int)=GradientDrawable().apply { setColor(color); cornerRadius=dp(r).toFloat() }
    private fun dp(v:Int)=(v*resources.displayMetrics.density).toInt()
}
