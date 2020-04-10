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
package com.eroom.calendar.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.eroom.calendar.AirCalendarDatePickerActivity;

import java.util.ArrayList;

public class AirCalendarIntent  extends Intent implements Parcelable {

    public enum Language {
        EN,
        KO
    }

    private AirCalendarIntent() {
    }

    private AirCalendarIntent(Intent o) {
        super(o);
    }

    private AirCalendarIntent(String action) {
        super(action);
    }

    private AirCalendarIntent(String action, Uri uri) {
        super(action, uri);
    }

    private AirCalendarIntent(Context packageContext, Class<?> cls) {
        super(packageContext, cls);
    }

    public AirCalendarIntent(Context packageContext) {
        super(packageContext, AirCalendarDatePickerActivity.class);
    }

    /**
     * Sets whether the month label is displayed.
     * @param isLabel default false
     */
    public void isMonthLabels(boolean isLabel) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_MONTH_LABEL, isLabel);
    }

    /**
     * Sets whether the month label is displayed.
     * @param isFixed default false
     */
    public void isFixedStartDay(boolean isFixed) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_FIXED_START_DAY, isFixed);
    }

    /**
     * Select a day only.
     * (Range Deactivation)
     * @param isSingle default false
     */
    public void isSingleSelect(boolean isSingle) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_SINGLE_SELECT, isSingle);
    }

    /**
     * Sets the select button text
     */
    public void setSelectButtonText(String selectText) {
        this.putExtra(AirCalendarDatePickerActivity.SELECT_TEXT, selectText);
    }

    /**
     * Sets the reset button text
     */
    public void setResetBtnText(String resetBtnText) {
        this.putExtra(AirCalendarDatePickerActivity.RESET_TEXT, resetBtnText);
    }


    /**
     * Provide a list with the weekdays for unsupported languages
     * @param weekDay a list array
     */
    public void setCustomWeekDays(ArrayList<String> weekDay) {
        if (weekDay.size() != 7) {
            throw new RuntimeException("Week days must be exactly seven. Currently you have: " + weekDay.size());
        }
        this.putStringArrayListExtra(AirCalendarDatePickerActivity.CUSTOM_WEEK_ABREVIATIONS, weekDay);
    }

    /**
     * Provide a default language
     * if no parameter is specified, it will default to Korean
     */
    public void setWeekDaysLanguage(Language language) {
        this.putExtra(AirCalendarDatePickerActivity.WEEK_LANGUAGE, language.toString());
    }

    public void setWeekStart(int weekStart) {
        this.putExtra(AirCalendarDatePickerActivity.WEEK_START, weekStart);
    }

    /**
     * Disables the specified date to be selected in the calendar.
     * ( The date is required by setBookingDateArray ().
     * @param isBooking default false
     */
    public void isBooking(boolean isBooking) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_BOOIKNG, isBooking);
    }

    /**
     * Disables the specified date to be selected in the calendar.
     * (isBooking option should be true)
     * e.g 2018-11-01 , ArrayList < String > format
     * @param arrays format yyyy-MM-dd
     */
    public void setBookingDateArray(ArrayList<String> arrays) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_BOOKING_DATES, arrays);
    }

    /**
     * Lets the specified date be preselected in the calendar.
     * @param isSelect default false
     */
    public void isSelect(boolean isSelect) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_SELECT, isSelect);
    }

    /**
     * Lets the specified date be preselected in the calendar. ( Start date )
     * (isSelect option should be true)
     * @param year
     * @param month
     * @param day
     */
    public void setStartDate(int year , int month , int day){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_SY, year);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_SM, month);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_SD, day);

    }

    /**
     * Lets the specified date be preselected in the calendar. ( End date )
     * * (isSelect option should be true)
     * @param year
     * @param month
     * @param day
     */
    public void setEndDate(int year , int month , int day){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_EY, year);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_EM, month);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_ED, day);
    }

    /**
     * When setting for 3 months, the date after 3 months is disabled.
     * @param activeMonth e.g 3
     */
    public void setActiveMonth(int activeMonth){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_ACTIVE_MONTH_NUM , activeMonth);
    }

    /**
     * If the current date is November 1, 2018, the calendar will be activated until November 1, 2019.
     * @param maxYear e.g 2020
     */
    public void setMaxYear(int maxYear){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_MAX_YEAR , maxYear);
    }

    public void setStartYear(int startYear){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_START_YEAR , startYear);
    }
}