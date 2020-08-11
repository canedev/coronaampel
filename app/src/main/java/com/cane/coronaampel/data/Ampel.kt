package com.cane.coronaampel.data

import com.cane.coronaampel.R
import java.util.*

data class Ampel (val date:Date? , val url:String?, val indicator_R:Indikator , val indicator_Neuinfektionen:Indikator, val indicator_Intensivauslastung:Indikator)  {


}