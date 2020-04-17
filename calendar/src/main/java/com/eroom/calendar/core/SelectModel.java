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

public class SelectModel {
    private int fristYear;
    private int fristMonth;
    private int fristDay;
    private int lastYear;
    private int lastMonth;
    private int lastDay;
    private boolean isSelectd;

    public int getFristYear() {
        return fristYear;
    }

    public void setFristYear(int fristYear) {
        this.fristYear = fristYear;
    }

    public int getFristMonth() {
        return fristMonth;
    }

    public void setFristMonth(int fristMonth) {
        this.fristMonth = fristMonth;
    }

    public int getFristDay() {
        return fristDay;
    }

    public void setFristDay(int fristDay) {
        this.fristDay = fristDay;
    }

    public int getLastYear() {
        return lastYear;
    }

    public void setLastYear(int lastYear) {
        this.lastYear = lastYear;
    }

    public int getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(int lastMonth) {
        this.lastMonth = lastMonth;
    }

    public int getLastDay() {
        return lastDay;
    }

    public void setLastDay(int lastDay) {
        this.lastDay = lastDay;
    }

    public boolean isSelectd() {
        return isSelectd;
    }

    public void setSelectd(boolean selectd) {
        isSelectd = selectd;
    }
}
