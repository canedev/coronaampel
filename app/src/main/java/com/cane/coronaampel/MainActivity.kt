package com.cane.coronaampel

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.coroutines.*
import android.util.Log
import android.view.View
import com.prof.rssparser.Parser
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.Charset
import okhttp3.OkHttpClient
import okhttp3.Request


class MainActivity : AppCompatActivity() {

    val url = "https://www.berlin.de/presse/pressemitteilungen/index/feed?institutions%5B%5D=Presse-+und+Informationsamt+des+Landes+Berlin&institutions%5B%5D=Senatskanzlei+-+Wissenschaft+und+Forschung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Bildung%2C+Jugend+und+Familie&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Finanzen&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Gesundheit%2C+Pflege+und+Gleichstellung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Inneres+und+Sport&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Integration%2C+Arbeit+und+Soziales&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Justiz%2C+Verbraucherschutz+und+Antidiskriminierung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Kultur+und+Europa&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Stadtentwicklung+und+Wohnen&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Umwelt%2C+Verkehr+und+Klimaschutz&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Wirtschaft%2C+Energie+und+Betriebe"
    val logtag = "CA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        update()
    }

    fun updateUI(ampel:Ampel){

runOnUiThread {
    cv_intensiv.setIndicator(ampel.indicator_Intensivauslastung)
    cv_r.setIndicator(ampel.indicator_R)
    cv_ni.setIndicator(ampel.indicator_Neuinfektionen)
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


            val ampel = Ampel(rLine.toString(), neuinfektionenLine.toString(), intensivLine.toString())
            val test= ampel.toString()

            updateUI(ampel)
            Log.d(logtag, test)
            tv_debug.setText(test)
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the exception
        }
    }
    fun update(view: View) {
        update()
    }
    fun update() {
        GlobalScope.launch {
            parse()
        }

    }
}
