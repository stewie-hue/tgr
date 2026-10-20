package dev.tgram.reborn

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.widget.*

class MainActivity : Activity() {
    private val bg = Color.rgb(18,18,18)
    private val panel = Color.rgb(43,43,43)
    private val accent = Color.rgb(255,181,188)
    private val text = Color.rgb(238,238,238)
    private val muted = Color.rgb(175,175,175)
    private val prefs by lazy { getSharedPreferences("tgram_settings", MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); home() }

    private fun home() {
        val root = LinearLayout(this).apply { orientation=1; setBackgroundColor(bg); setPadding(dp(18),dp(18),dp(18),dp(14)) }
        val scroll = ScrollView(this); val body = LinearLayout(this).apply { orientation=1 }; scroll.addView(body); root.addView(scroll,LinearLayout.LayoutParams(-1,0,1f))
        val header = LinearLayout(this).apply { orientation=0; gravity=Gravity.CENTER_VERTICAL }
        header.addView(TextView(this).apply { text="TR"; textSize=20f; typeface=Typeface.DEFAULT_BOLD; gravity=Gravity.CENTER; setTextColor(Color.WHITE); background=rounded(Color.rgb(175,82,91),16); layoutParams=LinearLayout.LayoutParams(dp(52),dp(52)) })
        val labels=LinearLayout(this).apply { orientation=1; setPadding(dp(12),0,0,0) }; labels.addView(txt("TGram Reborn",22f,text,true)); labels.addView(txt("LSPOSED MODULE  •  ALPHA",11f,muted,true)); header.addView(labels,LinearLayout.LayoutParams(0,-2,1f)); header.addView(txt("ACTIVE",10f,Color.rgb(25,25,25),true).apply { background=rounded(accent,20); setPadding(dp(12),dp(7),dp(12),dp(7)) }); body.addView(header)
        gap(body,25); body.addView(txt("Module scope",27f,text,true)); body.addView(txt("Apps requested by TGram Reborn",14f,muted)); gap(body,16)
        val search=EditText(this).apply { hint="Search apps"; setHintTextColor(muted); setTextColor(text); textSize=16f; singleLine=true; setPadding(dp(16),dp(12),dp(16),dp(12)); background=rounded(panel,32) }; body.addView(search,LinearLayout.LayoutParams(-1,dp(54))); gap(body,12)
        val list=LinearLayout(this).apply { orientation=1 }; body.addView(list)
        fun render(q:String="") { list.removeAllViews(); val apps=listOf(Triple("Telegram","org.telegram.messenger",true),Triple("TGram Reborn","dev.tgram.reborn",true)); apps.filter{it.first.contains(q,true)||it.second.contains(q,true)}.forEach { (name,pkg,checked)->
            val row=LinearLayout(this).apply { orientation=0; gravity=Gravity.CENTER_VERTICAL; setPadding(dp(5),dp(12),dp(5),dp(12)) }
            row.addView(TextView(this).apply { text=if(name=="Telegram") "➤" else "♟"; textSize=24f; gravity=Gravity.CENTER; setTextColor(if(name=="Telegram") Color.WHITE else accent); background=rounded(if(name=="Telegram") Color.rgb(40,165,218) else Color.rgb(65,65,65),18); layoutParams=LinearLayout.LayoutParams(dp(52),dp(52)) })
            val info=LinearLayout(this).apply { orientation=1; setPadding(dp(14),0,dp(5),0) }; info.addView(txt(name,18f,text)); info.addView(txt(pkg,13f,muted)); info.addView(txt(if(name=="Telegram") "Requested by this module" else "Module app · always in scope",12f,if(name=="Telegram") accent else muted,true)); row.addView(info,LinearLayout.LayoutParams(0,-2,1f))
            row.addView(CheckBox(this).apply { isChecked=checked; isEnabled=false; buttonTintList=android.content.res.ColorStateList.valueOf(accent); contentDescription="Requested scope: $pkg" })
            list.addView(row); list.addView(View(this).apply { setBackgroundColor(Color.rgb(48,48,48)) },LinearLayout.LayoutParams(-1,dp(1)))
        } }
        render(); search.addTextChangedListener(SimpleTextWatcher { render(it) })
        gap(body,18); card(body) { addView(txt("MODULE STATUS",11f,muted,true)); gap(this,9); addView(txt("●  Diagnostic hooks enabled",16f,text,true)); gap(this,5); addView(txt("Telegram is declared in the module's requested scope. Enable TGram Reborn in LSPosed and keep Telegram checked there.",13f,muted)) }
        gap(body,12); val buttons=LinearLayout(this).apply { orientation=0 }; buttons.addView(actionButton("Diagnostics") { diagnostics() },LinearLayout.LayoutParams(0,dp(50),1f)); buttons.addView(View(this),LinearLayout.LayoutParams(dp(8),1)); buttons.addView(actionButton("Settings") { settings() },LinearLayout.LayoutParams(0,dp(50),1f)); body.addView(buttons)
        gap(body,12); card(body) { addView(txt("ABOUT",11f,muted,true)); gap(this,7); addView(txt("TGram Reborn · v2.2.0-alpha",15f,text,true)); addView(txt("Independent rebuild · Original project TGramHooks by Simo",12f,muted)); gap(this,6); addView(txt("This build provides scope declaration, load diagnostics and local preferences. It does not claim to restore every original hook.",12f,muted)) }
        root.addView(LinearLayout(this).apply { gravity=Gravity.CENTER; addView(txt("HOME     •     DIAGNOSTICS     •     SETTINGS",11f,muted,true)) },LinearLayout.LayoutParams(-1,dp(38)))
        setContentView(root)
    }
    private fun diagnostics() {
        val pkg="org.telegram.messenger"; var ver="Not installed"; var target="—"
        try { val pi=packageManager.getPackageInfo(pkg,0); ver=pi.versionName ?: "unknown"; target=pi.applicationInfo?.targetSdkVersion?.toString() ?: "unknown" } catch (_:Exception) {}
        page("Diagnostics","Runtime and scope checks",listOf("Telegram package" to pkg,"Telegram installed" to if(ver=="Not installed") "No" else "Yes","Telegram version" to ver,"Telegram target SDK" to target,"Android" to "${android.os.Build.VERSION.RELEASE} · API ${android.os.Build.VERSION.SDK_INT}","Module scope" to "Declared: org.telegram.messenger","Hook engine" to "Load logger only; legacy Xposed API"),"Open LSPosed Manager") { try { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/LSPosed/LSPosed"))) } catch (_:Exception) {} }
    }
    private fun settings() {
        val root=LinearLayout(this).apply { orientation=1; setBackgroundColor(bg); setPadding(dp(20),dp(24),dp(20),dp(20)) }; root.addView(txt("TGram Reborn",14f,accent,true)); gap(root,18); root.addView(txt("Settings",28f,text,true)); root.addView(txt("Local app preferences",14f,muted)); gap(root,20)
        card(root) { addView(txt("Diagnostics",16f,text,true)); addView(txt("Keep diagnostic details available in the app.",13f,muted)); addView(Switch(this@MainActivity).apply { text="Verbose diagnostic display"; setTextColor(this@MainActivity.text); isChecked=prefs.getBoolean("verbose",false); setOnCheckedChangeListener { _,v -> prefs.edit().putBoolean("verbose",v).apply() } }) }
        gap(root,12); card(root) { addView(txt("Scope",16f,text,true)); gap(this,5); addView(txt("Telegram scope is declared by the module and is managed by LSPosed. This screen cannot override LSPosed's scope selection.",13f,muted)) }
        gap(root,12); root.addView(actionButton("Back to module scope") { home() },LinearLayout.LayoutParams(-1,dp(50))); setContentView(root)
    }
    private fun page(title:String,sub:String,rows:List<Pair<String,String>>,button:String,action:()->Unit) { val p=LinearLayout(this).apply { orientation=1; setBackgroundColor(bg); setPadding(dp(20),dp(24),dp(20),dp(20)) }; val s=ScrollView(this); val b=LinearLayout(this).apply { orientation=1 }; s.addView(b); p.addView(s,LinearLayout.LayoutParams(-1,0,1f)); b.addView(txt("TGram Reborn",14f,accent,true)); gap(b,18); b.addView(txt(title,28f,text,true)); b.addView(txt(sub,14f,muted)); gap(b,18); rows.forEach{(k,v)->card(b){addView(txt(k.uppercase(),10f,muted,true));gap(this,6);addView(txt(v,14f,text))};gap(b,9)}; b.addView(actionButton(button,action),LinearLayout.LayoutParams(-1,dp(50))); p.addView(actionButton("← Back") { home() },LinearLayout.LayoutParams(-1,dp(48))); setContentView(p) }
    private fun card(parent:LinearLayout, build:LinearLayout.()->Unit) { parent.addView(LinearLayout(this).apply { orientation=1; setPadding(dp(15),dp(15),dp(15),dp(15)); background=rounded(Color.rgb(31,31,31),18); build() },LinearLayout.LayoutParams(-1,-2)) }
    private fun actionButton(label:String,click:()->Unit)=Button(this).apply { text=label; textSize=13f; setTextColor(if(label.contains("Back")) text else Color.rgb(25,25,25)); backgroundTintList=android.content.res.ColorStateList.valueOf(if(label.contains("Back")) panel else accent); setOnClickListener { click() } }
    private fun txt(s:String,size:Float,color:Int,bold:Boolean=false)=TextView(this).apply { text=s; textSize=size; setTextColor(color); if(bold) typeface=Typeface.DEFAULT_BOLD; setLineSpacing(dp(2).toFloat(),1f) }
    private fun gap(parent:LinearLayout,h:Int) { parent.addView(View(this),LinearLayout.LayoutParams(1,dp(h))) }
    private fun rounded(color:Int,r:Int)=GradientDrawable().apply { setColor(color); cornerRadius=dp(r).toFloat() }
    private fun dp(v:Int)=(v*resources.displayMetrics.density).toInt()
}

private class SimpleTextWatcher(val changed:(String)->Unit): android.text.TextWatcher {
    override fun beforeTextChanged(s:CharSequence?,start:Int,count:Int,after:Int) {}
    override fun onTextChanged(s:CharSequence?,start:Int,before:Int,count:Int) { changed(s?.toString().orEmpty()) }
    override fun afterTextChanged(s:android.text.Editable?) {}
}
