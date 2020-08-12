package com.cane.coronaampel.data

import android.util.Log
import com.cane.coronaampel.R
import java.util.*

class PressReleaseParser {
    fun parsePR(article:String, articleUrl:String?, datestring:String?):Ampel{
        // all this text is (hopefully) always the same
        //find the right place to start searching
        val indicators_startline = article.lines().indexOfFirst{ it.contains("steht die Ampel für die drei Indikatoren") }
        val important_lines = article.lines().subList(indicators_startline,indicators_startline+100)
        // search the imprtant range of lines
        val rLine = important_lines.find { it.contains("Reproduktionszahl „R“") }
        val neuinfektionenLine = important_lines.find { it.contains("Inzidenz Neuinfektionen pro Woche") }
        //for the New infections take 2 lines because sometimes the value is in the next line
        val ilinenr = important_lines.indexOfFirst{ it.contains("Anteil der für <span class=\"caps\">COVID</span>-19-Patient*innen benötigten Plätze auf Intensivstationen") }
        val intensivLine = important_lines.get( ilinenr) + important_lines.get( ilinenr+1)

        val indicator_R = Indikator(
            R.string.title_r,
            extractNumber(rLine), extractColor(rLine),
            R.string.info_r
        )
        val indicator_Neuinfektionen = Indikator(
            R.string.title_neuinfektionen, extractNumber(neuinfektionenLine), extractColor(neuinfektionenLine),
            R.string.info_neuinfektionen
        )
        val indicator_Intensivauslastung = Indikator(
            R.string.title_neuinfektionen, extractNumber(intensivLine), extractColor(intensivLine),
            R.string.info_auslastung
        )

        val ampel = Ampel(
            Date(datestring),
            articleUrl,
            indicator_R, indicator_Neuinfektionen, indicator_Intensivauslastung
        )
        return ampel
    }


    private fun extractColor(line: String?): Ampelfarbe {
        val ss = line!!.split(" ")
        for(s:String in ss){
//            Log.d("Ampel", s)
            when (s){
                "Grün" -> return Ampelfarbe.green
                "Gelb" -> return Ampelfarbe.yellow
                "Rot" -> return Ampelfarbe.red
            }
        }
        return Ampelfarbe.unknown
    }
    private fun extractNumber(line: String?): Double{
        val ss = line!!.split(" ")
        for(s:String in ss){
//            Log.d("Ampel", s)
            var dv = s.replace(",",".").toDoubleOrNull();
            if(dv!=null){
                return dv
            }
        }
        return 0.0
    }
}