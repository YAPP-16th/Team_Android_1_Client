package com.eroom.data.response

import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.Pageable
import com.eroom.data.entity.Sort
import com.fasterxml.jackson.annotation.JsonProperty

class InterestedGoalsResponse(
    @JsonProperty("content") var content: ArrayList<GoalContent>,
    @JsonProperty("pageable") var pageable: Pageable,
    @JsonProperty("totalPages") var totalPages: Int,
    @JsonProperty("totalElements") var totalElements: Int,
    @JsonProperty("last") var last: Boolean,
    @JsonProperty("number") var number: Int,
    @JsonProperty("size") var size: Int,
    @JsonProperty("first") var first: Boolean,
    @JsonProperty("sort") var sort: Sort,
    @JsonProperty("numberOfElements") var numberOfElements: Int,
    @JsonProperty("empty") var empty: Boolean
)

//{
//    "content": [
//    {
//        "goalId": 35,
//        "role": "OWNER",
//        "isEnd": false,
//        "copyCount": 0,
//        "startDt": "2020-04-25T20:39:02",
//        "endDt": "2020-04-30T00:00:00",
//        "minimalGoalDetail": {
//        "id": 35,
//        "title": "목표 1 하나둘셋",
//        "description": "설명",
//        "joinCount": 1,
//        "isEnd": false,
//        "isDateFixed": false,
//        "jobInterests": [
//        {
//            "id": 1,
//            "name": "개발",
//            "jobInterestType": "JOB_GROUP"
//        }
//        ]
//    }
//    },
//    {
//        "goalId": 37,
//        "role": "OWNER",
//        "isEnd": false,
//        "copyCount": 0,
//        "startDt": "2020-04-25T20:39:23",
//        "endDt": "2020-04-30T00:00:00",
//        "minimalGoalDetail": {
//        "id": 37,
//        "title": "목표 1 하나둘셋",
//        "description": "설명",
//        "joinCount": 1,
//        "isEnd": false,
//        "isDateFixed": false,
//        "jobInterests": [
//        {
//            "id": 1,
//            "name": "개발",
//            "jobInterestType": "JOB_GROUP"
//        }
//        ]
//    }
//    }
//    ],
//    "pageable": {
//    "sort": {
//        "sorted": true,
//        "unsorted": false,
//        "empty": false
//    },
//    "offset": 0,
//    "pageNumber": 0,
//    "pageSize": 10,
//    "paged": true,
//    "unpaged": false
//},
//    "totalElements": 2,
//    "last": true,
//    "totalPages": 1,
//    "size": 10,
//    "number": 0,
//    "sort": {
//    "sorted": true,
//    "unsorted": false,
//    "empty": false
//},
//    "first": true,
//    "numberOfElements": 2,
//    "empty": false
//}