package com.cane.coronaampel

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Ampel (datestr:String? , val url:String?, val rLine:String , val niLine:String, val ibLine:String)  {

    val indicator_R = Indikator("R Wert", rLine, R.string.info_r)
    val indicator_Neuinfektionen = Indikator("Neuinfektionen", niLine, R.string.info_neuinfektionen)
    val indicator_Intensivauslastung = Indikator(
        "Intensivbetten Auslastung",
        ibLine,
        R.string.info_auslastung
    )
    val date = Date(datestr)




    override fun toString():String{
        var s = indicator_R.toString()+"\n"+
                indicator_Neuinfektionen.toString()+"\n"+
                indicator_Intensivauslastung.toString()+"\n"+
                "\n\n"+rLine+"\n" +niLine+"\n"+ibLine
        return s
    }


}