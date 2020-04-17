/***********************************************************************************
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2017 - 2019 LeeYongBeom( top6616@gmail.com )
 * https://github.com/yongbeam
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.eroom.calendar.core.util;

import android.content.Context;

import com.eroom.calendar.R;
import com.eroom.calendar.core.AirCalendarIntent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AirCalendarUtils {

    private static List<String> airCalendarWeekdays = new ArrayList<>();
    private static AirCalendarIntent.Language airCalendarLanguage = AirCalendarIntent.Language.KO;

    /**
     * @return
     */
    public static boolean isWeekend(String strDate) {
        int weekNumber = getWeekNumberInt(strDate, "yyyy-MM-dd");
        if (weekNumber == 6 || weekNumber == 7) {
            return true;
        }
        return false;
    }

    /**
     * @param strDate
     * @param inFormat
     * @return String
     */
    public static int getWeekNumberInt(String strDate, String inFormat) {
        int week = 0;
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return 0;
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = 7;
                break;
            case 1:
                week = 1;
                break;
            case 2:
                week = 2;
                break;
            case 3:
                week = 3;
                break;
            case 4:
                week = 4;
                break;
            case 5:
                week = 5;
                break;
            case 6:
                week = 6;
                break;
        }
        return week;
    }

    /**
     * @param date
     * @param dateType
     * @return
     * @throws Exception
     */
    public static String getDateDay(Context context, String date, String dateType, int weekStart) throws Exception {
        String day = "" ;
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType) ;
        Date nDate = dateFormat.parse(date) ;
        Calendar cal = Calendar.getInstance() ;
        cal.setFirstDayOfWeek(weekStart);
        cal.setTime(nDate);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        if (dayNum == 1) {
            dayNum = 6;
        } else {
            dayNum-=2;
        }

        if (airCalendarWeekdays.isEmpty()) {
            switch (airCalendarLanguage) {
                case KO:
                    String[] weekdaysKo = context.getResources().getStringArray(R.array.label_calender_week);
                    day = weekdaysKo[dayNum];
                    break;
                case EN:
                    String[] weekdaysEn = context.getResources().getStringArray(R.array.label_calendar_en);
                    day = weekdaysEn[dayNum];
                    break;
            }
            return day ;
        } else {
            return airCalendarWeekdays.get(dayNum);
        }
    }

    public static void setWeekdays(List<String> weekdays) {
        airCalendarWeekdays.clear();
        airCalendarWeekdays.addAll(weekdays);
    }

    public static void setLanguage(AirCalendarIntent.Language language) {
        airCalendarLanguage = language;
    }

}
