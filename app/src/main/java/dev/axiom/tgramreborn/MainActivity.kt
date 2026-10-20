package dev.axiom.tgramreborn

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private val bg = Color.rgb(11, 16, 32)
    private val panel = Color.rgb(22, 30, 51)
    private val purple = Color.rgb(139, 92, 246)
    private val muted = Color.rgb(165, 176, 201)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        window.decorView.systemUiVisibility = 0

        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(18), dp(20), dp(24))
            setBackgroundColor(bg)
        }
        val scroll = ScrollView(this).apply { addView(page) }

        page.addView(label("TG / REBORN", 13, purple, true))
        page.addView(label("Module control", 30, Color.WHITE, true).also { it.setPadding(0, dp(8), 0, dp(4)) })
        page.addView(label("A clean rebuild dashboard", 15, muted, false))
        page.addView(space(22))

        val status = card()
        status.addView(label("MODULE STATUS", 11, muted, true))
        status.addView(label("Standalone dashboard", 20, Color.WHITE, true).also { it.setPadding(0, dp(8), 0, dp(5)) })
        status.addView(label("This build is the reconstructed UI shell. Xposed hooks are not included or active.", 14, muted, false))
        status.addView(space(12))
        status.addView(pill("NOT CONNECTED", Color.rgb(245, 158, 11)))
        page.addView(status)
        page.addView(space(14))

        page.addView(sectionTitle("TOOLS"))
        page.addView(actionCard("Compatibility", "Review Android and module compatibility", "View details") {
            showInfo("Compatibility", "This dashboard does not yet detect Xposed/LSPosed automatically. Verify your framework and target app versions before enabling any module.")
        })
        page.addView(space(10))
        page.addView(actionCard("Diagnostics", "Check the state of this app build", "Run check") {
            showInfo("Diagnostics", "Dashboard loaded successfully. No Xposed entry point is registered in this reconstruction, and no Telegram client is modified.")
        })
        page.addView(space(10))
        page.addView(actionCard("Preferences", "Choose your dashboard appearance", "Dark mode") {
            Toast.makeText(this, "Dark appearance is enabled", Toast.LENGTH_SHORT).show()
        })
        page.addView(space(22))

        page.addView(sectionTitle("PROJECT NOTES"))
        val notes = card()
        notes.addView(label("Rebuilt edition", 17, Color.WHITE, true))
        notes.addView(space(7))
        notes.addView(label("This project is a fresh, independently branded UI reconstruction inspired by the supplied TGramHooks APK. Original attribution should be retained where required by the original project's license. Original author referenced by the APK: Simo.", 14, muted, false))
        notes.addView(space(12))
        notes.addView(label("No original source code was recovered in this starter project. The APK's compiled hooks require separate review and are intentionally not reproduced here.", 13, muted, false))
        page.addView(notes)
        page.addView(space(20))
        page.addView(label("TGRAM REBORN  •  VERSION 1.0.0", 11, muted, true).apply { gravity = Gravity.CENTER })
        setContentView(scroll)
    }

    private fun actionCard(title: String, subtitle: String, action: String, click: () -> Unit): View {
        val box = card().apply { isClickable = true; isFocusable = true; setOnClickListener { click() } }
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL }
        val copy = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; layoutParams = LinearLayout.LayoutParams(0, -2, 1f) }
        copy.addView(label(title, 17, Color.WHITE, true))
        copy.addView(space(4))
        copy.addView(label(subtitle, 13, muted, false))
        row.addView(copy)
        row.addView(label("›", 27, purple, true))
        box.addView(row)
        return box
    }

    private fun card(): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(17), dp(16), dp(17), dp(16))
        background = rounded(panel, 18)
        layoutParams = LinearLayout.LayoutParams(-1, -2)
    }

    private fun label(text: String, size: Int, color: Int, bold: Boolean): TextView = TextView(this).apply {
        this.text = text
        textSize = size.toFloat()
        setTextColor(color)
        if (bold) setTypeface(typeface, Typeface.BOLD)
        setLineSpacing(dp(2).toFloat(), 1f)
    }

    private fun sectionTitle(text: String) = label(text, 11, muted, true).apply {
        setPadding(dp(2), 0, 0, dp(10))
        letterSpacing = 0.12f
    }

    private fun pill(text: String, color: Int) = TextView(this).apply {
        this.text = text
        textSize = 11f
        setTextColor(color)
        setTypeface(typeface, Typeface.BOLD)
        setPadding(dp(10), dp(7), dp(10), dp(7))
        background = rounded(Color.rgb(39, 43, 57), 40)
        layoutParams = LinearLayout.LayoutParams(-2, -2)
    }

    private fun space(height: Int) = View(this).apply { layoutParams = LinearLayout.LayoutParams(1, dp(height)) }
    private fun rounded(color: Int, radius: Int) = GradientDrawable().apply { setColor(color); cornerRadius = dp(radius).toFloat() }
    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()

    private fun showInfo(title: String, message: String) {
        android.app.AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Got it", null)
            .show()
    }
}
