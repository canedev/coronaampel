package com.cane.coronaampel.Data

import android.util.Log

class Indikator(val title: Int, line: String, val info: Int){

    val indicatorValue = extractNumber(line)
    val indicatorColor = extractColor(line)

    private fun extractColor(line: String): Ampelfarbe {
        val ss = line.split(" ")
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
    private fun extractNumber(line: String): Double{
        val ss = line.split(" ")
        for(s:String in ss){
            Log.d("Ampel", s)
            var dv = s.replace(",",".").toDoubleOrNull();
            if(dv!=null){
                return dv
            }
        }
        return 0.0
    }
    override fun toString():String{
        return "" + title + ": " + indicatorValue + "->" + indicatorColor
    }
}