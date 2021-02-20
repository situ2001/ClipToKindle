package com.situ2001.cliptokindle

import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.situ2001.cliptokindle.fragment.RecyclerViewFragment
import com.situ2001.cliptokindle.text.Text
import com.situ2001.cliptokindle.text.TextSetHelper
import com.situ2001.cliptokindle.util.HttpApp
import com.situ2001.cliptokindle.util.PageGenerator
import com.situ2001.cliptokindle.util.Utils
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        //show RecycleViewFragment
        var fragment: RecyclerViewFragment? = null
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            fragment = RecyclerViewFragment()
            transaction.replace(R.id.fragment_content, fragment)
            transaction.commit()
        }
        val tvHttpServerStatus = findViewById<TextView>(R.id.text_server_status)
        val btClipBoard = findViewById<Button>(R.id.button_get_clip)
        val switchServer = findViewById<SwitchCompat>(R.id.switch_server)

        val serverPort = 8080
        val app = HttpApp(serverPort)
        switchServer.setOnCheckedChangeListener { switch, isChecked: Boolean ->
            if (isChecked) {
                try {
                    app.start()
                    tvHttpServerStatus.text = getString(R.string.server_status, serverPort, Utils.getIpAddress(this))
                } catch (e: IOException) {
                    e.printStackTrace()
                    tvHttpServerStatus.text = getString(R.string.server_start_fail)
                    switch.isChecked = false
                }
            } else {
                app.stop()
                tvHttpServerStatus.text = getString(R.string.server_offline)
            }
        }
        switchServer.isChecked = true
        val finalFragment = fragment // an effectively final variable

        val manager = getSystemService(ClipboardManager::class.java)
        btClipBoard.setOnClickListener {
            if (!manager.hasPrimaryClip()) {
                Toast.makeText(this, getString(R.string.no_text_in_clipboard), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val text = manager.primaryClip?.getItemAt(0)?.text.toString()
            TextSetHelper.get().add(Text(text))
            finalFragment?.adapter?.notifyItemInserted(TextSetHelper.getList().size - 1)
        }

        //listening to clipboard
        manager.addPrimaryClipChangedListener {
            if (!manager.hasPrimaryClip()) {
                Toast.makeText(this, getString(R.string.no_text_in_clipboard), Toast.LENGTH_SHORT).show()
                return@addPrimaryClipChangedListener
            }
            val text = manager.primaryClip?.getItemAt(0)?.text.toString()
            TextSetHelper.get().add(Text(text))
            finalFragment?.adapter?.notifyItemInserted(TextSetHelper.getList().size - 1)
            Toast.makeText(this, getString(R.string.added_to_list), Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        val textSet = TextSetHelper.get()
        Utils.setStoragePath(this)
        textSet.load()
        PageGenerator.build(textSet)
    }

    companion object {
        private val TAG: String = "MainActivity"
    }
}