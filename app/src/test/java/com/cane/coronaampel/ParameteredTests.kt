package com.cane.coronaampel

import com.cane.coronaampel.data.Ampelfarbe
import com.cane.coronaampel.data.PressReleaseParser
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ParameteredTests(private val articlename: String) {
    var fails_r = 0;
    var fails_ni = 0;
    var fails_icb = 0;


    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            "72967.txt","73467.txt","72995.txt","72899.txt","72056.txt","70900.txt","69918.txt","69145.txt","68156.txt","68115.txt","68100.txt","67354.txt","67024.txt","66373.txt","65977.txt","65456.txt","65417.txt","65319.txt","64909.txt","64384.txt","63718.txt","63278.txt","62235.txt","62214.txt","61977.txt","61469.txt","61034.txt","60488.txt","59990.txt","59492.txt","59476.txt","59467.txt","59008.txt","58329.txt","57594.txt","56891.txt","56103.txt","56086.txt","56074.txt","55340.txt","54635.txt","54027.txt","53260.txt","52274.txt","52249.txt","52235.txt","51446.txt","50820.txt","50145.txt","49170.txt","48468.txt","48405.txt","48373.txt","47836.txt","46969.txt","46220.txt","45285.txt","44567.txt","44543.txt","44513.txt","44014.txt","43347.txt","42766.txt","42096.txt","41674.txt","41639.txt","41538.txt","41003.txt","40565.txt","40036.txt","39509.txt","39447.txt","39396.txt","38946.txt","38479.txt","38002.txt","35655.txt"
        )

    }

    @Test
    fun parseValues() {
        System.out.println("parsing " + articlename);
        val article = ClassLoader.getSystemResource(articlename).readText()
        val ampel =
            PressReleaseParser().parsePR(article, "FAKEURL", "Tue, 28 Jul 2020 21:36:00 +0200")

        var correct = true;
        if (ampel.indicator_R.indicatorColor == Ampelfarbe.unknown) {
            fails_r++;
            correct = false
            System.out.println("R wrong")
        }
        if (ampel.indicator_Intensivauslastung.indicatorColor == Ampelfarbe.unknown) {
            fails_icb++;
            correct = false
            System.out.println("ICB wrong")
        }
        if (ampel.indicator_Neuinfektionen.indicatorColor == Ampelfarbe.unknown) {
            fails_ni++;
            correct = false
            System.out.println("NI wrong")
        }
        assert(correct)

    }

}