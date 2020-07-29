package com.cane.coronaampel

import android.util.Log

class Ampel (val rLine:String , val niLine:String, val ibLine:String){

    val indicator_R = Indikator("R Wert", rLine)
    val indicator_Neuinfektionen = Indikator("Neuinfektionen", niLine)
    val indicator_Intensivauslastung = Indikator("Intensivbetten Auslastun", ibLine)


    override fun toString():String{
        var s = indicator_R.toString()+"\n"+
                indicator_Neuinfektionen.toString()+"\n"+
                indicator_Intensivauslastung.toString()+"\n"+
                "\n\n"+rLine+"\n" +niLine+"\n"+ibLine
        return s
    }

}