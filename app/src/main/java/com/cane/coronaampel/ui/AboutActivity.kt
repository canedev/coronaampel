package com.cane.coronaampel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.cane.coronaampel.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        if (this.supportActionBar != null) {
            this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }
    override fun onOptionsItemSelected(var1: MenuItem): Boolean {
        if (var1.itemId == android.R.id.home ) {
            this.finish()
            onBackPressed()
            return true
        } else {
            return super.onOptionsItemSelected(var1)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.act_from_right,
            R.anim.act_to_right
        );
    }
}
