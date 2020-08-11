package com.cane.coronaampel

import android.util.Log
import com.cane.coronaampel.data.Ampelfarbe
import com.cane.coronaampel.data.PressReleaseParser
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    var fails_r = 0;
    var fails_ni = 0;
    var fails_icb = 0;
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testAllPRs() {

        testArticle("35655.txt");


        assert(fails_r+fails_ni+fails_icb == 0)
    }
    fun testArticle(name:String){
        val article = ClassLoader.getSystemResource(name).readText()
        val ampel = PressReleaseParser().parsePR(article,"FAKEURL", "Tue, 28 Jul 2020 21:36:00 +0200")


        if( ampel.indicator_R.indicatorColor==Ampelfarbe.unknown)
            fails_r++;
        if( ampel.indicator_Intensivauslastung.indicatorColor==Ampelfarbe.unknown)
            fails_icb++;
        if( ampel.indicator_Neuinfektionen.indicatorColor==Ampelfarbe.unknown)
            fails_ni++;
        //System.out.println(ampel.toString())
    }
}
