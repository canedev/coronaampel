package com.cane.coronaampel.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import com.cane.coronaampel.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        if (this.supportActionBar != null) {
            this.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        tv_data.text = Html.fromHtml(getString(R.string.about_data))

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

    fun openLink(view: View) {
        var url=""
        when(view) {
            link_dp_berlin_senat -> url =
                "https://www.berlin.de/sen/gpg/datenschutzerklaerung.703294.php"
            link_dp_playstore -> url = "https://policies.google.com/privacy"
            link_pr_feed -> url =
                "https://www.berlin.de/presse/pressemitteilungen/index/feed?institutions%5B%5D=Presse-+und+Informationsamt+des+Landes+Berlin&institutions%5B%5D=Senatskanzlei+-+Wissenschaft+und+Forschung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Bildung%2C+Jugend+und+Familie&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Finanzen&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Gesundheit%2C+Pflege+und+Gleichstellung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Inneres+und+Sport&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Integration%2C+Arbeit+und+Soziales&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Justiz%2C+Verbraucherschutz+und+Antidiskriminierung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Kultur+und+Europa&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Stadtentwicklung+und+Wohnen&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Umwelt%2C+Verkehr+und+Klimaschutz&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Wirtschaft%2C+Energie+und+Betriebe\""
            link_git -> url="https://github.com/canedev/coronaampel"
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}
