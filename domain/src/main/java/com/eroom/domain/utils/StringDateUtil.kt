package com.eroom.domain.utils

import java.util.*

object StringDateUtil {

    // "startDt": "2020-04-25T20:39:02",
    // "endDt": "2020-04-30T00:00:00",

   // "startDt": "2020.04.25T20:39:02",
   // "endDt": "2020.04.30T00:00:00",

    fun stringForTime(startDt: String , endDt: String ): String {

        val startDt_ = startDt.replace("-",".").also{ it.substring(2,10)}
        val endDt_ = endDt.replace("-", ".").also{ it.substring(2,10)}


        val mFormatter = Formatter()
        return if (startDt_.substring(0,2).equals(endDt_.substring(0,2))) {
            mFormatter.format("%s", startDt_ + endDt_.substring(3,8)).toString()
        } else {
            mFormatter.format("%s", startDt_ + endDt_ ).toString()
        }
    }

}