package com.cane.coronaampel.data

import android.util.Log
import com.cane.coronaampel.R
import java.util.*

class PressReleaseParser {
    fun parsePR(article:String, articleUrl:String?, datestring:String?):Ampel{
        // all this text is (hopefully) always the same
        val rLine = article.lines().find { it.contains("Reproduktionszahl „R“:") }
        val neuinfektionenLine = article.lines().find { it.contains("<strong>Inzidenz Neuinfektionen pro Woche:") }
        val intensivLine = article.lines().get(article.lines().indexOfFirst{ it.contains("<strong>Anteil der für <span class=\"caps\">COVID</span>-19-Patient*innen benötigten Plätze auf Intensivstationen:") } +1)

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
            Log.d("Ampel", s)
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
            Log.d("Ampel", s)
            var dv = s.replace(",",".").toDoubleOrNull();
            if(dv!=null){
                return dv
            }
        }
        return 0.0
    }
}