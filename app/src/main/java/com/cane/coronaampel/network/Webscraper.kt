package com.cane.coronaampel.network

import com.cane.coronaampel.data.Ampel
import com.cane.coronaampel.data.PressReleaseParser
import com.cane.coronaampel.ui.MainActivity
import com.prof.rssparser.Parser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.nio.charset.Charset

class Webscraper (val activity: MainActivity){
    // hardcoded url of the rss feed
    val url = "https://www.berlin.de/presse/pressemitteilungen/index/feed?institutions%5B%5D=Presse-+und+Informationsamt+des+Landes+Berlin&institutions%5B%5D=Senatskanzlei+-+Wissenschaft+und+Forschung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Bildung%2C+Jugend+und+Familie&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Finanzen&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Gesundheit%2C+Pflege+und+Gleichstellung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Inneres+und+Sport&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Integration%2C+Arbeit+und+Soziales&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Justiz%2C+Verbraucherschutz+und+Antidiskriminierung&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Kultur+und+Europa&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Stadtentwicklung+und+Wohnen&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Umwelt%2C+Verkehr+und+Klimaschutz&institutions%5B%5D=Senatsverwaltung+f%C3%BCr+Wirtschaft%2C+Energie+und+Betriebe"

    /**
     * parses all the information
     * gets the specific feed and analyzes it
     */
    suspend fun parse(){
        val parser = Parser.Builder()
            .context(activity)
            .charset(Charset.forName("ISO-8859-7"))
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
            val ampel = PressReleaseParser().parsePR(article, articleUrl, ampelitem.pubDate)
            // update the UI
            activity.updateUI(ampel)
        } catch (e: Exception) {
            e.printStackTrace()
            // Do nothing
        }
    }
}