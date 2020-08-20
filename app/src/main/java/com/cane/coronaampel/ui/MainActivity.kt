package com.cane.coronaampel.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cane.coronaampel.data.Ampel
import com.cane.coronaampel.network.Webscraper
import com.cane.coronaampel.R
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
    }

    override fun onResume() {
        super.onResume()
        update()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.m_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivityForResult(intent, 0)
                overridePendingTransition(
                    R.anim.act_from_right,
                    R.anim.act_to_right
                )
                true
            }
            R.id.m_licenses -> {
                val intent = Intent(this, OssLicensesMenuActivity::class.java)
                startActivityForResult(intent, 0)
                overridePendingTransition(
                    R.anim.act_from_right,
                    R.anim.act_to_right
                )
                true
            }
            R.id.m_update -> {
                update()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun updateUI(ampel: Ampel) {
        runOnUiThread {
            cv_intensiv.setIndicator(ampel.indicator_Intensivauslastung)
            cv_r.setIndicator(ampel.indicator_R)
            cv_ni.setIndicator(ampel.indicator_Neuinfektionen)
            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val date = simpleDateFormat.format(ampel.date)
            tv_date.setText(date)
            loading_spinner.visibility = View.GONE
            tv_url.hint = ampel.url
        }
    }

    fun update() {
        loading_spinner.visibility = View.VISIBLE
        GlobalScope.launch {
            Webscraper(this@MainActivity).parse()
        }
    }

    fun openPM(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(tv_url.hint.toString()))
        startActivity(browserIntent)
    }
}
