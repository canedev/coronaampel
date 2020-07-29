package com.cane.coronaampel

import android.util.Log

class Indikator (val title: String, line:String){

    val indicatorValue = extractNumber(line)
    val indicatorColor = extractColor(line)

    fun extractColor(line: String): Ampelfarbe{
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
    fun extractNumber(line: String): Double{
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
        return title+": " + indicatorValue + "->" + indicatorColor
    }
}