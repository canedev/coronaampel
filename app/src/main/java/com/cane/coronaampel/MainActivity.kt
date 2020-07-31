package com.cane.coronaampel

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.coroutines.*
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.prof.rssparser.Parser
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.Charset
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    val url = "https://www.berlin.de/presse/pressemitteilungen/index/feed?institutions%5B%5D=Presse-+und+Informationsamt+des+Landes+Berlin&institutions%5B%5D=Senatskanzlei+-+Wissenschaft+und+Forschung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Bildung%2C+Jugend+und+Familie&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Finanzen&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Gesundheit%2C+Pflege+und+Gleichstellung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Inneres+und+Sport&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Integration%2C+Arbeit+und+Soziales&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Justiz%2C+Verbraucherschutz+und+Antidiskriminierung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Kultur+und+Europa&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Stadtentwicklung+und+Wohnen&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Umwelt%2C+Verkehr+und+Klimaschutz&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Wirtschaft%2C+Energie+und+Betriebe"
    val logtag = "CA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //todo use real data again
        //update()
        val fakeampel = Ampel("Tue, 28 Jan 2020 21:36:00 +0200", "FAKE DATA",
            "<strong>Reproduktionszahl „R“:</strong> Wert 1,40 → auf Rot",
            "<strong>Inzidenz Neuinfektionen pro Woche:</strong> Wert 25,9 → auf Gelb",
            "Wert 1,4 % → auf Grün")
            updateUI(fakeampel)
        //-------------------

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.m_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            R.id.m_licenses -> {
                startActivity(Intent(this, OssLicensesMenuActivity::class.java))

                true
            }
            R.id.m_update -> {
                update()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun updateUI(ampel:Ampel){

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
    fun getColorFromTLColor(col:Ampelfarbe): Int{
        when(col){
            Ampelfarbe.green -> return getColor(R.color.tlgreen)
            Ampelfarbe.yellow -> return getColor(R.color.tlyellow)
            Ampelfarbe.red -> return getColor(R.color.tlred)
        }
        return getColor(R.color.tlgrey)
    }

    suspend fun parse(){
        val parser = Parser.Builder()
            .context(this)
            .charset(Charset.forName("ISO-8859-7"))
            //.cacheExpirationMillis(24L * 60L * 60L * 100L) // one day
            .build()
        try {
            val channel = parser.getChannel(url)
            val ampelitem = channel.articles.find { it.title.equals("Corona-Ampel: Die aktuellen Indikatoren") }
            val articleUrl = ampelitem!!.link;
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(articleUrl)
                .build()
            val response = client.newCall(request).execute()
            val article = response.body()!!.string()
            val rLine = article.lines().find { it.contains("<strong>Reproduktionszahl „R“:</strong> Wert") }
            val neuinfektionenLine = article.lines().find { it.contains("<strong>Inzidenz Neuinfektionen pro Woche:</strong> Wert") }
            val intensivLine = article.lines().get(article.lines().indexOfFirst{ it.contains("<strong>Anteil der für <span class=\"caps\">COVID</span>-19-Patient*innen benötigten Plätze auf Intensivstationen:</strong><br />") } +1)
            val ampel = Ampel(ampelitem.pubDate, articleUrl, rLine.toString(), neuinfektionenLine.toString(), intensivLine.toString())

            updateUI(ampel)
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the exception
        }
    }

    fun update() {
        Log.d(logtag,"update")
        loading_spinner.visibility = View.VISIBLE
        GlobalScope.launch {
            parse()
        }

    }

    fun openPM(view: View) {
        Log.d(logtag, tv_url.hint.toString())
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(tv_url.hint.toString()))
        startActivity(browserIntent)
    }
}
