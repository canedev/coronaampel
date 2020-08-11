package com.cane.coronaampel.data

class PressReleaseParser {
    fun parsePR(article:String, articleUrl:String?, datestring:String?):Ampel{
        // all this text is (hopefully) always the same
        val rLine = article.lines().find { it.contains("Reproduktionszahl „R“:") }
        val neuinfektionenLine = article.lines().find { it.contains("<strong>Inzidenz Neuinfektionen pro Woche:") }
        val intensivLine = article.lines().get(article.lines().indexOfFirst{ it.contains("<strong>Anteil der für <span class=\"caps\">COVID</span>-19-Patient*innen benötigten Plätze auf Intensivstationen:") } +1)
        val ampel = Ampel(
            datestring,
            articleUrl,
            rLine.toString(),
            neuinfektionenLine.toString(),
            intensivLine.toString()
        )
        return ampel
    }
}