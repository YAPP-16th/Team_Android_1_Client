package com.eroom.data.localclass

enum class SortBy(val itemName: String) {
    //승신좌
    ID("ID"),
    TITLE("TITLE"),
    START_DT("START_DT"),
    END_DT("END_DT"),
    JOIN_CNT("JOIN_CNT"),

    //현수짱
    CREATED_DT("createDt,desc"),    // 생성날짜기준 내림차순 (최근올린순)
    CREATED_ASC("createDt,asc"), // 생성날짜기준 오름차순 (오래전 올린순)
    JOINCOUNT_DESC("joinCount,desc"),  // 참여자수기준 내림차순
    JOINCOUNT_ASC("joinCount,asc"), // (많은순) 참여자수기준 오름차순
    ENDDT_ASC("endDt,asc"),  // 마감임박순
    ENDDT_DESC("endDt,desc")  // 마감 많이남은순
}