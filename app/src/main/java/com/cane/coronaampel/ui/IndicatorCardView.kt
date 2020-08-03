package com.cane.coronaampel.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.cane.coronaampel.data.Ampelfarbe
import com.cane.coronaampel.data.Indikator
import com.cane.coronaampel.R
import kotlinx.android.synthetic.main.indicatorcardview.view.*

class IndicatorCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    fun setIndicator(indicator: Indikator) {
        indicator_card.setCardBackgroundColor(getColorFromTLColor(indicator.indicatorColor))
        tv_indicatortitle.setText(indicator.title)
        tv_value.setText(indicator.indicatorValue.toString()+" -> "+ getColorNameFromTLColor(indicator.indicatorColor))
        tv_info.setText(indicator.info)
    }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.indicatorcardview, this, true)
    }

    fun getColorFromTLColor(col: Ampelfarbe): Int{
        when(col){
            Ampelfarbe.green -> return context.getColor(R.color.tlgreen)
            Ampelfarbe.yellow -> return context.getColor(R.color.tlyellow)
            Ampelfarbe.red -> return context.getColor(R.color.tlred)
        }
        return context.getColor(R.color.tlgrey)
    }

    fun getColorNameFromTLColor(col: Ampelfarbe): String{
        when(col){
            Ampelfarbe.green -> return resources.getString(R.string.green)
            Ampelfarbe.yellow -> return resources.getString(R.string.yellow)
            Ampelfarbe.red -> return resources.getString(R.string.red)
        }
        return resources.getString(R.string.unknown)
    }
}