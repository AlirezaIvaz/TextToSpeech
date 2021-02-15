package ir.ari.tts

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    private var edt1: EditText? = null
    private var spinner1: Spinner? = null
    private var spinner2:Spinner? = null
    //Switch switch1;
    private var btn: Button? = null
    private var tts: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt1 = findViewById(R.id.mainEditText1)
        //edt2 = findViewById(R.id.mainEditText2)
        spinner1 = findViewById(R.id.mainSpinner1)
        spinner2 = findViewById(R.id.mainSpinner2)
        //switch1 = findViewById(R.id.mainSwitch1)
        btn = findViewById(R.id.mainButton1)
//        spinner1?.setOnItemSelectedListener(this)
        val adapter1 =
            ArrayAdapter.createFromResource(
                    this,
                    R.array.rate,
                    android.R.layout.simple_spinner_item
            )
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1?.adapter = adapter1
        spinner1?.setSelection(2)

//        spinner2?.setOnItemSelectedListener(this)
        val adapter2 =
            ArrayAdapter.createFromResource(
                    this,
                    R.array.dialect,
                    android.R.layout.simple_spinner_item
            )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2?.adapter = adapter2
        //spinner2.setSelection(0);

        //spinner2.setSelection(0);
        tts = TextToSpeech(this@MainActivity) { status ->
            if (status != TextToSpeech.ERROR) {
                tts!!.language = Locale.ENGLISH
                btn?.setOnClickListener {
                    val toSpeak = edt1?.text.toString()
                    when (spinner1?.selectedItemPosition) {
                        0 -> tts!!.setSpeechRate(0.1.toFloat())
                        1 -> tts!!.setSpeechRate(0.5.toFloat())
                        2 -> tts!!.setSpeechRate(1f)
                        3 -> tts!!.setSpeechRate(3f)
                        4 -> tts!!.setSpeechRate(6f)
                    }
                    when (spinner2?.selectedItemPosition) {
                        0 -> tts!!.language = Locale.ENGLISH
                        1 -> tts!!.language = Locale.US
                        2 -> tts!!.language = Locale.UK
                        3 -> tts!!.language = Locale.CANADA
                        4 -> tts!!.language = Locale.FRENCH
                        5 -> tts!!.language = Locale.CANADA_FRENCH
                    }
                    if (edt1?.text.toString().isEmpty()) {
                        Toast.makeText(this@MainActivity, "Nothing to speak!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        if (Build.VERSION.SDK_INT >= 21) {
                            tts!!.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
                        } else {
                            tts!!.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
                        }
                    }
                }
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                val alert = AlertDialog.Builder(
                        ContextThemeWrapper(
                                this@MainActivity,
                                R.style.Theme_TTS
                        )
                )
                alert.setTitle(R.string.about)
                val wv = WebView(applicationContext)
                //wv.getSettings().setBuiltInZoomControls(true);
                wv.isVerticalScrollBarEnabled = false
                //wv.setHorizontalScrollBarEnabled(false);
                //wv.loadDataWithBaseURL(null, "HTML content here", "text/html", "utf-8", null);
                wv.loadUrl("file:///android_asset/LICENSE.html")
                alert.setView(wv)
                alert.setNegativeButton(
                        R.string.close
                ) { dialog, _ -> dialog.dismiss() }
                alert.show()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }
}